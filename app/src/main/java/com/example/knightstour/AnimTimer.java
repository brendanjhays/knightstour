package com.example.knightstour;

import android.widget.Button;

import java.util.TimerTask;

public class AnimTimer extends TimerTask {
    int m = 1;
    int[][] solve;

    public AnimTimer(int[][] board) {
        this.solve = board;
    }

    @Override
    public void run() {
        int[] index = HelperFunc.returnIndex(m, solve);
        assert index != null;
        Button button = MainActivity.buttonArr[index[1]][index[0]];
        int[] coords = new int[2];
        button.getLocationInWindow(coords);
        MainActivity.moveKnight(coords[0],coords[1]-220,100,MainActivity.knight);
        button.setText(String.valueOf(m));
        if (m == 64) {
            MainActivity.timerA.cancel();
        }
        m++;
    }
}
