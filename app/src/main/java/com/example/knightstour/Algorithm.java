package com.example.knightstour;

import android.graphics.Point;

public class Algorithm {

    int startX, startY, mov;
    int[][] solveB;
    int[][] solvedFinal;
    MainActivity main;

    public Algorithm(Point start, MainActivity main) {
        this.startX = start.x;
        this.startY = start.y;
        this.mov = 1;
        this.solveB = new int[Const.bSize][Const.bSize];
        this.main = main;
    }

    public void solve() {
    }

    public boolean isSafe(int y, int x) {
        return x >= 0 && y >= 0 && x < Const.bSize && y < Const.bSize;
    }

    public int[][] getSolve() {
        return solvedFinal;
    }
}
