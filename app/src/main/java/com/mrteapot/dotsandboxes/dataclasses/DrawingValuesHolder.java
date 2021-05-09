package com.mrteapot.dotsandboxes.dataclasses;

public class DrawingValuesHolder {
    private float startPointX;
    private float startPointY;
    private float drawingStep;

    public DrawingValuesHolder() {
    }

    public float getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(float startPointX) {
        this.startPointX = startPointX;
    }

    public float getStartPointY() {
        return startPointY;
    }

    public void setStartPointY(float startPointY) {
        this.startPointY = startPointY;
    }

    public float getDrawingStep() {
        return drawingStep;
    }

    public void setDrawingStep(float drawingStep) {
        this.drawingStep = drawingStep;
    }
}
