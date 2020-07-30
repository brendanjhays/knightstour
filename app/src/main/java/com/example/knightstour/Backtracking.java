package com.example.knightstour;

import android.graphics.Point;

public class Backtracking extends Algorithm {
    public Backtracking(Point start) {
        super(start);
    }

    public boolean tour(int[][] solve, int y, int x, int mov) {
        solve[y][x] = mov;
        if (mov == Const.bSize*Const.bSize) {
            solvedFinal = solveB;
            return true;
        }

        for (int i=0;i<8;i++) {
            int turnX = x + Const.xt[i];
            int turnY = y + Const.yt[i];

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

    public void solve() {
        tour(solveB, startY, startX, mov);
    }
}
