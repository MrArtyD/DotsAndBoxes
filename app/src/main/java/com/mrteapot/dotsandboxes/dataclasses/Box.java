package com.mrteapot.dotsandboxes.dataclasses;

import android.graphics.Color;

public class Box {
    private Line topLine;
    private Line rightLine;
    private Line bottomLine;
    private Line leftLine;
    private boolean isSurrounded;
    private boolean isTaken;
    private int color;

    public Box() {
        isSurrounded = false;
        isTaken = false;
        color = Color.YELLOW;
    }

    public Line getTopLine() {
        return topLine;
    }

    public void setTopLine(Line topLine) {
        this.topLine = topLine;
    }

    public Line getRightLine() {
        return rightLine;
    }

    public void setRightLine(Line rightLine) {
        this.rightLine = rightLine;
    }

    public Line getBottomLine() {
        return bottomLine;
    }

    public void setBottomLine(Line bottomLine) {
        this.bottomLine = bottomLine;
    }

    public Line getLeftLine() {
        return leftLine;
    }

    public void setLeftLine(Line leftLine) {
        this.leftLine = leftLine;
    }

    public boolean isSurrounded() {
        isSurrounded = topLine.isTaken() && rightLine.isTaken() && bottomLine.isTaken() && leftLine.isTaken();
        return isSurrounded;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
