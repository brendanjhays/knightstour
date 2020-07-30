package com.example.knightstour;

import android.util.Log;

import java.util.Arrays;

public class Algorithm {

    int startX, startY, mov;
    int[][] solveB;
    int[][] solvedFinal;

    public Algorithm(int[] start) {
        this.startX = start[0];
        this.startY = start[1];
        this.mov = 1;
        this.solveB = new int[Const.bSize][Const.bSize];
    }

    public void solve() {

    }

    public boolean isSafe(int y, int x) {
        return x >= 0 && y >= 0 && x < Const.bSize && y < Const.bSize;
    }

    public void printBoard(int[][] board) {
        for (int[] ints : board) {
            Log.i("Main", Arrays.toString(ints));
            Log.i("Main", "");
        }
    }





}
