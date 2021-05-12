package com.mrteapot.dotsandboxes.dataclasses;

import android.graphics.Color;

import com.mrteapot.dotsandboxes.view.Direction;

import java.util.concurrent.atomic.AtomicInteger;

public class Line {
    private final Direction direction;
    private float startX ;
    private float startY;
    private float stopX;
    private float stopY;
    private boolean isTaken;
    private static final int defaultColor = Color.BLACK;
    private int color;

    public Line(Direction direction) {
        this.direction = direction;
        isTaken = false;
        color = defaultColor;
    }

    public Direction getDirection() {
        return direction;
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

    public static int getDefaultColor() {
        return defaultColor;
    }

}
