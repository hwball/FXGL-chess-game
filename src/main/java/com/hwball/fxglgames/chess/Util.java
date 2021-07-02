package com.hwball.fxglgames.chess;

import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/***
 *       Move Map
 *
 *       t     y
 *    a  j  f  k  o
 *       l     r
 *    z  n  b  m  p
 *       c     v
 */

public class Util {
    public static boolean moveIsOnBoard(double y, double x) {
        if (y >= 0 && y < 8 && x >= 0 && x < 8) return true;
        return false;
    }

    public static List<String> buildFilesDirections(List<String> directions) {
        List<String> out = new ArrayList<String>();
        for (String direction:directions) {
            int i  = 1;
            while (i < 8) {
                out.add(direction.repeat(i));
                i++;
            }
        }

        return out;
    }

    public static Move parseMove(String move, double x, double y, String side, Entity[][] board) {
        boolean obstruction = false;
        String obstructionSide = "NULL";
        for (int i = 0; i < move.length(); i++) {
            switch (move.charAt(i)) {
                case 'f':
                    y = side.equals("White") ? y - 1 : y + 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'b':
                    y = side.equals("White") ? y + 1 : y - 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'l':
                    x = side.equals("White") ? x - 1 : x + 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'r':
                    x = side.equals("White") ? x + 1 : x - 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 't':
                    y = side.equals("White") ? y - 2 : y + 2;
                    x = side.equals("White") ? x - 1 : x + 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'y':
                    y = side.equals("White") ? y - 2 : y + 2;
                    x = side.equals("White") ? x + 1 : x - 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'j':
                    y = side.equals("White") ? y - 1 : y + 1;
                    x = side.equals("White") ? x - 1 : x + 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'k':
                    y = side.equals("White") ? y - 1 : y + 1;
                    x = side.equals("White") ? x + 1 : x - 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'n':
                    y = side.equals("White") ? y + 1 : y - 1;
                    x = side.equals("White") ? x - 1 : x + 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'm':
                    y = side.equals("White") ? y + 1 : y - 1;
                    x = side.equals("White") ? x + 1 : x - 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'o':
                    y = side.equals("White") ? y - 1 : y + 1;
                    x = side.equals("White") ? x + 2 : x - 2;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'p':
                    y = side.equals("White") ? y + 1 : y - 1;
                    x = side.equals("White") ? x + 2 : x - 2;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'v':
                    y = side.equals("White") ? y + 2 : y - 2;
                    x = side.equals("White") ? x + 1 : x - 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'c':
                    y = side.equals("White") ? y + 2 : y - 2;
                    x = side.equals("White") ? x - 1 : x + 1;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'a':
                    y = side.equals("White") ? y - 1 : y + 1;
                    x = side.equals("White") ? x - 2 : x + 2;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;
                case 'z':
                    y = side.equals("White") ? y + 1 : y - 1;
                    x = side.equals("White") ? x - 2 : x + 2;
                    obstruction = obstruction || (Util.moveIsOnBoard(y, x) && board[(int) y][(int) x] != null);
                    break;

            }
            if (obstruction && obstructionSide.equals("NULL")) {
                obstructionSide = board[(int) y][(int) x].getComponent(PieceViewComponent.class).getSide();
                break;
            }
        }
        return new Move(x, y, obstruction, obstructionSide);
    }
}
