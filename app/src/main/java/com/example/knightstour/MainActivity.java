package com.example.knightstour;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenW = displayMetrics.widthPixels;
        buttonArr = new Button[boardS][boardS];
        solvedBoard = new int[boardS][boardS];

        // Setting up array of buttons
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String newButton = "b" + i + j;
                int resourceButton = getResources().getIdentifier(newButton, "id", getPackageName());
                buttonArr[j][i] =  findViewById(resourceButton);
            }
        }

        // Adding knight view
        LinearLayout layout = findViewById(R.id.main);
        knight = new ImageView(this);
        knight.setImageResource(R.drawable.knight);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((screenW / boardS),(screenW / (boardS+2)));
        knight.setLayoutParams(layoutParams);
        layout.addView(knight);

        countView = findViewById(R.id.runs);
        counterTimer();

    }


    static int[][] solvedBoard;
    static int boardS = 8;
    public static ImageView knight;
    static Button[][] buttonArr;
    public static int m = 0;
    private Timer timerA;
    private boolean resetReady = false;
    public static long counter;
    public static TextView countView;

    //Possible turns for the knight
    private static final int[] xt = {2,1,-1,-2,-2,-1,1,2};
    private static final int[] yt = {1,2,2,1,-1,-2,-2,-1};

    private boolean isSafe(int y, int x) {
        return x >= 0 && y >= 0 && x < boardS && y < boardS;
    }

    //Prints the board
    private void printBoard(int[][] board) {
        for (int[] ints : board) {
            Log.i("Main", Arrays.toString(ints));
            Log.i("Main", "");
        }
    }

    //Handles all button clicks
    public void onClick(View v) {
        if (resetReady) {
            activityReset();
            resetReady = false;
        }
        updateBoard(false);
        Point point = findPoint(v);
        tour(solvedBoard, point.y, point.x, 1);
    }

    //Sets all buttons enabled state
    private void updateBoard(boolean state) {
        for (int i=0;i<boardS;i++) {
            for (int j=0;j<boardS;j++) {
                buttonArr[i][j].setEnabled(state);
            }
        }
    }

    //Takes a view and returns x and y coordinates in button grid
    private Point findPoint(View v) {
        int x = -1,y = -1;
        for (int i=0;i<boardS;i++) {
            for (int j=0;j<boardS;j++) {
                if (v.equals(buttonArr[i][j])) {
                    x = i;
                    y = j;
                    return new Point(x,y);
                }
            }
        }
        return new Point(x,y);
    }

    //Main backtracking function
    private boolean tour(int[][] solve, int y, int x, int mov) {
        solve[y][x] = mov;
        counter++;

        if (mov == boardS*boardS) {
            printBoard(solve);
            solvedBoard = solve;
            doAnimation();
            updateBoard(true);
            return true;
        }

       for (int i=0;i<8;i++) {
           int turnX = x + xt[i];
           int turnY = y + yt[i];

           if(isSafe(turnY,turnX) && solve[turnY][turnX] == 0) {
               if (tour(solve, turnY, turnX, mov + 1)) {
                   return true;
               }
           }
       }
       solve[y][x] = 0;
       if (mov ==  1) System.out.println("No solution");
       return false;
    }

    //Checks if there is a valid way to get to inputted tile
    private boolean isAccessible(int y, int x, int[][] board) {
        for (int i=0;i<8;i++) {
            int testX = x + xt[i];
            int testY = y + yt[i];
            if (isSafe(testY,testX) && board[testY][testX] == 0)  return true;
        }
        return false;
    }

    //Checks if there is a tile that cannot be reached
    private boolean isOver(int[][] board) {
        for (int i=0;i<boardS;i++) {
            for (int j=0;j<boardS;j++) {
                if (board[i][j] == 0) {
                    if (!isAccessible(i,j,board)) return true;
                }
            }
        }
        return false;
    }

    //Takes pixel coordinates and moves knight view
    public static void moveKnight(final int nx, final int ny, final int time, final View v) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animation = ObjectAnimator.ofFloat(v, "x",nx);
                animation.setDuration(time);
                animation.start();
                ObjectAnimator animationY = ObjectAnimator.ofFloat(v, "y",ny);
                animationY.setDuration(time);
                animationY.start();
            }
        });

    }

    //Takes a turn and returns x and y coordinates for solved board
    public static int[] returnIndex(int turn) {
        for (int i=0;i<boardS;i++) {
            for (int j=0;j<boardS;j++) {
                if (solvedBoard[i][j] == turn) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public static int incrementM() {
        m++;
        return m;
    }

    //Handles animation timer
    private void doAnimation() {
        Timer timer = new Timer();
        timer.schedule(new AnimTimer(),0, 250);
        timerA = timer;
    }

    //Handles updating run counter. Doesn't work right
    private void counterTimer() {
        Timer timer = new Timer();
        timer.schedule(new getNum(), 0, 250);
    }

    //Resets board
    private void activityReset() {
        for (int i=0;i<boardS;i++) {
            for (int j=0;j<boardS;j++) {
                buttonArr[i][j].setText("");
            }
        }
        solvedBoard = new int[boardS][boardS];
        m = 0;
        counter = 0;
    }

    public static long returnCounter() {
        return counter;
    }

    //Changes counter text
    public static void changeText(long counter) {
        final long loCounter = counter;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                countView.setText("Runs: " + loCounter);
            }
        });

    }

    class getNum extends TimerTask {
        @Override
        public void run() {
            final long counter = MainActivity.returnCounter();
            MainActivity.changeText(counter);
        }
    }

    class AnimTimer extends TimerTask {
        @Override
        public void run() {
            int m = MainActivity.incrementM();
            int[] index = MainActivity.returnIndex(m);
            assert index != null;
            Button button = MainActivity.buttonArr[index[1]][index[0]];
            int[] coords = new int[2];
            button.getLocationInWindow(coords);
            MainActivity.moveKnight(coords[0],coords[1]-220,100,MainActivity.knight);
            button.setText("" + m);
            if (m == 64) {
                timerA.cancel();
                resetReady = true;
            }
        }
    }
}