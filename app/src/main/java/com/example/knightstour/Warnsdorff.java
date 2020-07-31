package com.example.knightstour;

import android.graphics.Point;

public class Warnsdorff extends Alg {
    public Warnsdorff(Point start, MainActivity main) {
        super(start, main);
    }

    private int accessibility(int[][] solve, int x, int y) {
        int count = 0;
        for (int i=0;i<8;i++) {
            if (isEmpty(y,x,solve) && isSafe(y,x)) count++;
        }
        return count;
    }

    private Point nxMov(int[][] solve, int x, int y) {

    }


}
