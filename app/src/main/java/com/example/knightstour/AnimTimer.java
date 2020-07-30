// Dealing with animation of knight

package com.example.knightstour;

import android.widget.Button;

import java.util.TimerTask;

public class AnimTimer extends TimerTask {
    int m = 1;
    int[][] solve;
    MainActivity main;
    Button prev;

    public AnimTimer(int[][] board, MainActivity main) {
        this.main = main;
        this.solve = board;
    }

    @Override
    public void run() {
        int[] index = HelperFunc.returnIndex(m, solve);
        assert index != null;
        Button button = MainActivity.buttonArr[index[1]][index[0]];
        int[] loc = new int[2];
        button.getLocationInWindow(loc);
        main.moveKnight(loc[0],loc[1]-220,100,main.knight);
        if (m == 64) {
            MainActivity.timerA.cancel();
            main.updateSepThread(true);
        }
        if (m != 1) prev.setText(String.valueOf(m-1));
        prev = button;
        m++;
    }
}
