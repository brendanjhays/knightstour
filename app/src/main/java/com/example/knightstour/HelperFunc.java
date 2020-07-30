// Functions I use in various places

package com.example.knightstour;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

public class HelperFunc {

    public static int[] returnIndex(int turn, int[][] solve) {
        for (int i=0;i<Const.bSize;i++) {
            for (int j=0;j<Const.bSize;j++) {
                if (solve[i][j] == turn) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public static void printBoard(int[][] board) {
        for (int[] ints : board) {
            Log.i("Main", Arrays.toString(ints));
            Log.i("Main", "");
        }
    }

    public static Point findPoint(View v) {
        int x = -1,y = -1;
        for (int i=0;i<Const.bSize;i++) {
            for (int j=0;j<Const.bSize;j++) {
                if (v.equals(MainActivity.buttonArr[i][j])) {
                    x = i;
                    y = j;
                    return new Point(x,y);
                }
            }
        }
        return new Point(x,y);
    }
}
