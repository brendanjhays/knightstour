package com.example.knightstour;

import android.widget.Button;

import java.util.TimerTask;

public class AnimTimer extends TimerTask {
    int m = 1;
    int[][] solve;
    MainActivity main;

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
        button.setText(String.valueOf(m));
        if (m == 64) {
            MainActivity.timerA.cancel();
            main.updateSepThread(true);
        }
        m++;
    }
}
