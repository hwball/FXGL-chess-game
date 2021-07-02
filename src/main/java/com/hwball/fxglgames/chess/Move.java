package com.hwball.fxglgames.chess;

public class Move {
    public double x;
    public double y;
    public boolean obstruction;
    public String obstructionSide;

    public Move(double x, double y, boolean obstruction, String obstructionSide) {
        this.x = x;
        this.y = y;
        this.obstruction = obstruction;
        this.obstructionSide = obstructionSide;

    }
}
