// Algorithm base class

package com.example.knightstour;

import android.graphics.Point;

public class Alg {

    int x, y, mov;
    int[][] solveB;
    int[][] solvedFinal;
    MainActivity main;

    public Alg(Point start, MainActivity main) {
        this.x = start.x;
        this.y = start.y;
        this.mov = 1;
        this.solveB = new int[Const.bSize][Const.bSize];
        this.main = main;
    }

    public void solve() {
    }

    public boolean isSafe(int y, int x) {
        return x >= 0 && y >= 0 && x < Const.bSize && y < Const.bSize;
    }

    public boolean isEmpty(int y, int x, int[][] solve) {
        return (solve[y][x] == 0);
    }

    public int[][] getSolve() {
        return solvedFinal;
    }
}
