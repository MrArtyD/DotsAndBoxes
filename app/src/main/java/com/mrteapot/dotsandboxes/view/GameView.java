package com.mrteapot.dotsandboxes.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.mrteapot.dotsandboxes.dataclasses.Box;
import com.mrteapot.dotsandboxes.dataclasses.DrawingValuesHolder;
import com.mrteapot.dotsandboxes.dataclasses.Line;
import com.mrteapot.dotsandboxes.dataclasses.Player;
import com.mrteapot.dotsandboxes.functional.classes.BoxExaminer;
import com.mrteapot.dotsandboxes.functional.interfaces.GameSupervisor;

public class GameView extends View {

    private final int gridSize = 5;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final DrawingValuesHolder drawingValues = new DrawingValuesHolder();
    private final Line[][] horizontalLines = new Line[gridSize+1][gridSize];
    private final Line[][] verticalLines = new Line[gridSize][gridSize+1];
    private final Box[][] boxes = new Box[gridSize][gridSize];

    private GameSupervisor gameSupervisor;
    private final BoxExaminer boxExaminer = new BoxExaminer();
    private Player currentPlayer;

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initValues();
        this.setOnTouchListener((view, motionEvent) -> {
            checkTouchCoordinates(motionEvent);
            return false;
        });
    }

    private void initValues() {
        for (int i = 0; i <= gridSize; i++){
            for (int j = 0; j <= gridSize; j++){
                if (j != gridSize){
                    horizontalLines[i][j] = new Line();
                }
                if (i != gridSize){
                    verticalLines[i][j] = new Line();
                }
            }
        }

        for (int i = 0; i < gridSize; i++){
            for (int j = 0; j < gridSize; j++){
                boxes[i][j] = new Box();
                Box currentBox = boxes[i][j];

                currentBox.setTopLine(horizontalLines[i][j]);
                currentBox.setBottomLine(horizontalLines[i+1][j]);
                currentBox.setLeftLine(verticalLines[i][j]);
                currentBox.setRightLine(verticalLines[i][j+1]);
            }
        }

    }

    public void startGame(Player player, GameSupervisor supervisor){
        currentPlayer = player;
        gameSupervisor = supervisor;
    }

    public void changePlayer(Player player){
        currentPlayer = player;
    }

    private void checkTouchCoordinates(MotionEvent event) {
        float positionX = event.getX();
        float positionY = event.getY();

        boolean isLIneTouched = checkIfLineTouched(positionX, positionY);

        if (isLIneTouched){
            if (boxExaminer.isBoxTaken(boxes, currentPlayer)){
                currentPlayer.increaseScore();
                if (boxExaminer.isBoxTaken(boxes, currentPlayer)){
                    currentPlayer.increaseScore();
                }
                gameSupervisor.updateScore(currentPlayer);
            }else {
                gameSupervisor.changeTurn(currentPlayer);
            }
            if (boxExaminer.isAllBoxesTaken(boxes)){
                gameSupervisor.endGame();
            }
        }
        invalidate();
    }

    private boolean checkIfLineTouched(float positionX, float positionY) {
        for (int i = 0; i <= gridSize; i++){
            for (int j = 0; j <= gridSize; j++){
                Line currentLine;
                if (j != gridSize){
                    currentLine = horizontalLines[i][j];
                    if ((currentLine.getStartX() <= positionX && currentLine.getStopX() >= positionX)
                            && (currentLine.getStartY() + 30 >= positionY && currentLine.getStartY() - 30 <= positionY)
                            && !currentLine.isTaken()){
                        currentLine.setTaken(true);
                        currentLine.setColor(currentPlayer.getColor());
                        return true;
                    }
                }

                if (i != gridSize){
                    currentLine = verticalLines[i][j];
                    if ((currentLine.getStartX() + 30 >= positionX && currentLine.getStopX() - 30 <= positionX)
                            && (currentLine.getStartY() <= positionY && currentLine.getStopY() >= positionY)
                            && !currentLine.isTaken()){
                        currentLine.setTaken(true);
                        currentLine.setColor(currentPlayer.getColor());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        calculateDrawingValues(width, height);

        drawRectangles(canvas);
        drawLines(canvas, drawingValues.getStartPointX(), drawingValues.getStartPointY(), drawingValues.getDrawingStep());
        drawDots(canvas, drawingValues.getStartPointX(), drawingValues.getStartPointY(), drawingValues.getDrawingStep());
    }

    private void calculateDrawingValues(float width, float height) {
        float startPointX = width / (10*2);
        float drawingStepX = (width - startPointX*2) / gridSize;

        float startPointY =  height / (10*2);
        float drawingStepY = (height - startPointY*2)/gridSize;

        float drawingStep = Math.min(drawingStepX, drawingStepY);

        if (drawingStep == drawingStepX){
            startPointY = (height - drawingStep*gridSize)/2;
        }else {
            startPointX = (width - drawingStep*gridSize)/2;
        }

        drawingValues.setDrawingStep(drawingStep);
        drawingValues.setStartPointX(startPointX);
        drawingValues.setStartPointY(startPointY);
    }

    private void drawRectangles(Canvas canvas) {
        for (Box[] lineOfBoxes : boxes){
            for(Box box : lineOfBoxes){
                if (box.isSurrounded()){
                    paint.setColor(box.getColor());
                    float left = box.getLeftLine().getStartX();
                    float top = box.getTopLine().getStartY();
                    float right = box.getRightLine().getStopX();
                    float bottom = box.getBottomLine().getStopY();

                    canvas.drawRect(left, top, right, bottom, paint);
                }
            }
        }
    }

    private void drawLines(Canvas canvas, float startPointX, float startPointY, float drawingStep) {

        for (int i = 0; i <= gridSize; i++){
            for (int j = 0; j <= gridSize; j++){
                Line currentLine;
                float startX = startPointX + drawingStep * j;
                float startY = startPointY + drawingStep * i;
                float stopX;
                float stopY;

                if (j != gridSize){
                    currentLine = horizontalLines[i][j];
                    stopX = startPointX + drawingStep * (j + 1);
                    stopY = startPointY + drawingStep * i;

                    currentLine.setCoordinates(startX, startY, stopX, stopY);
                    if (currentLine.isTaken()){
                        paint.setStrokeWidth(10);
                    }

                    paint.setColor(currentLine.getColor());
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                }
                paint.setStrokeWidth(1);

                if (i != gridSize){
                    currentLine = verticalLines[i][j];
                    stopX = startPointX + drawingStep * j;
                    stopY = startPointY + drawingStep * (i + 1);

                    currentLine.setCoordinates(startX, startY, stopX, stopY);
                    if (currentLine.isTaken()){
                        paint.setStrokeWidth(10);
                    }

                    paint.setColor(currentLine.getColor());
                    canvas.drawLine(startX, startY,startPointX + drawingStep * j,startPointY + drawingStep * (i + 1), paint);
                }
                paint.setStrokeWidth(1);
            }
        }
    }

    private void drawDots(Canvas canvas, float startPointX, float startPointY, float dotStep) {
        float radius = 110f / gridSize;
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i <= gridSize; i++){
            for (int j = 0; j <= gridSize; j++){
                canvas.drawCircle(startPointX + dotStep * j, startPointY + dotStep * i, radius, paint);
            }
        }
    }

}
