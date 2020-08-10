// UI related things

package com.example.knightstour;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenW = displayMetrics.widthPixels;
        buttonArr = new Button[Const.bSize][Const.bSize];

        typeSwitch = findViewById(R.id.btorrn);
        typeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(getBaseContext(),"Random enabled",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(),"Backtracking enabled",Toast.LENGTH_SHORT).show();
                }
                type = b;
            }
        });

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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((screenW / Const.bSize),(screenW / (Const.bSize+2)));
        knight.setLayoutParams(layoutParams);
        layout.addView(knight);
        knight.setVisibility(View.INVISIBLE);
    }


    public ImageView knight;
    static Button[][] buttonArr;
    public static Timer timerA;
    public int[][] solveB;
    public Alg alg;
    private boolean type;
    private Switch typeSwitch;

    //Handles all button clicks
    public void onClick(View v) {
        activityReset();
        updateBoard(false);
        Point point = HelperFunc.findPoint(v);
        if (type) alg = new Rn(point, this);
        else alg = new Bt(point, this);
        alg.solve();
        knight.setVisibility(View.VISIBLE);
    }

    //Sets all buttons enabled state
    public void updateBoard(boolean state) {
        for (int i=0;i<Const.bSize;i++) {
            for (int j=0;j<Const.bSize;j++) {
                buttonArr[i][j].setEnabled(state);
            }
        }
        typeSwitch.setEnabled(state);
    }


    //Takes pixel coordinates and moves knight view
    public void moveKnight(final int nx, final int ny, final int time, final View v) {
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

    //Handles animation timer
    private void doAnimation() {
        Timer timer = new Timer();
        timer.schedule(new AnimTimer(solveB, this),0, 250);
        timerA = timer;
    }

    //Resets board
    private void activityReset() {
        for (int i=0;i<Const.bSize;i++) {
            for (int j=0;j<Const.bSize;j++) {
                buttonArr[i][j].setText("");
            }
        }
    }

    public void onSolve() {
        solveB = alg.getSolve();
        doAnimation();
    }

    public void updateSepThread(final boolean state) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateBoard(state);
            }
        });
    }
}