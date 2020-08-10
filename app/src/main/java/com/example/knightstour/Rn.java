//Random
package com.example.knightstour;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collections;

public class Rn extends Alg{

    public Rn(Point start, MainActivity main) {
        super(start, main);
    }

    public void solve() {
        solvedFinal = randomTour(x,y);
        main.onSolve();
    }

    public int[][] randomTour(int x, int y) {
        while (true) {
            solveB[y][x] = mov++;
            if (mov == 65) {
                return solveB;
            }
            Point turn = nextTurn(x,y);
            if (turn.x == -1) {
                solveB = new int[Const.bSize][Const.bSize];
                mov = 1;
                turn.x = 0;
                turn.y = 0;
            }
            x = turn.x;
            y = turn.y;
        }
    }

    public Point nextTurn(int x, int y) {
        ArrayList<Integer> list = assembleTurns();
        for (int i=0;i<8;i++) {
            int tmpX = x + Const.xt[list.get(i)];
            int tmpY = y + Const.yt[list.get(i)];
            if (isSafe(tmpY,tmpX) && solveB[y][x] == 0) {
                Point point = new Point();
                point.x = x;
                point.y = y;
                return point;
            }
        }
        Point point = new Point();
        point.x = -1;
        return point;
    }

    public ArrayList<Integer> assembleTurns() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<8; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }
}
