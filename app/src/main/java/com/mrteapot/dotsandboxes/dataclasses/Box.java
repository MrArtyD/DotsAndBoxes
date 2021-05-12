package com.mrteapot.dotsandboxes.dataclasses;

import android.graphics.Color;

public class Box {
    private Line topLine;
    private Line rightLine;
    private Line bottomLine;
    private Line leftLine;
    private Line[] lines;
    private boolean isTaken;
    private static final int defaultColor = Color.YELLOW;
    private int color;

    public Box() {
        isTaken = false;
        color = defaultColor;
    }

    public void fillLines(Line topLine, Line bottomLine, Line leftLine, Line rightLine){
        this.topLine = topLine;
        this.rightLine = rightLine;
        this.bottomLine = bottomLine;
        this.leftLine = leftLine;

        lines = new Line[]{topLine, bottomLine, leftLine, rightLine};
    }

    public Line[] getLines() {
        return lines;
    }

    public Line getTopLine() {
        return topLine;
    }

    public Line getRightLine() {
        return rightLine;
    }

    public Line getBottomLine() {
        return bottomLine;
    }

    public Line getLeftLine() {
        return leftLine;
    }

    public boolean isTaken() {
        isTaken = topLine.isTaken() && rightLine.isTaken() && bottomLine.isTaken() && leftLine.isTaken();
        return isTaken;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static int getDefaultColor() {
        return defaultColor;
    }

}
