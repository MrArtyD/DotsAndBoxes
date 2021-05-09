package com.mrteapot.dotsandboxes.dataclasses;

import android.graphics.Color;

public class Line {
    private float startX ;
    private float startY;
    private float stopX;
    private float stopY;
    private boolean isTaken;
    private int color;

    public Line() {
        isTaken = false;
        color = Color.BLACK;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public void setCoordinates(float startX, float startY, float stopX, float stopY){
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStopX() {
        return stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
