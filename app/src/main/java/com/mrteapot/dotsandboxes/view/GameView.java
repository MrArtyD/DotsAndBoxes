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

import com.mrteapot.dotsandboxes.ai.AI;
import com.mrteapot.dotsandboxes.dataclasses.Box;
import com.mrteapot.dotsandboxes.dataclasses.DrawingValuesHolder;
import com.mrteapot.dotsandboxes.dataclasses.Line;
import com.mrteapot.dotsandboxes.dataclasses.Player;
import com.mrteapot.dotsandboxes.functional.classes.BoxesExaminer;
import com.mrteapot.dotsandboxes.functional.classes.LinesExaminer;
import com.mrteapot.dotsandboxes.functional.interfaces.GameSupervisor;

public class GameView extends View {

    private final int gridSize = 3;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final DrawingValuesHolder drawingValues = new DrawingValuesHolder();
    private final Line[][] horizontalLines = new Line[gridSize+1][gridSize];
    private final Line[][] verticalLines = new Line[gridSize][gridSize+1];
    private final Box[][] boxes = new Box[gridSize][gridSize];

    private GameSupervisor gameSupervisor;
    private final BoxesExaminer boxesExaminer;
    private final LinesExaminer linesExaminer;
    private Player currentPlayer;

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initValues();
        boxesExaminer = new BoxesExaminer(boxes);
        linesExaminer = new LinesExaminer(horizontalLines, verticalLines);

        this.setOnTouchListener((view, motionEvent) -> {
            checkTouchCoordinates(motionEvent);
            return false;
        });
    }

    private void initValues() {
        for (int i = 0; i <= gridSize; i++){
            for (int j = 0; j <= gridSize; j++){
                if (j != gridSize){
                    horizontalLines[i][j] = new Line(Direction.HORIZONTAL);
                }
                if (i != gridSize){
                    verticalLines[i][j] = new Line(Direction.VERTICAL);
                }
            }
        }

        for (int i = 0; i < gridSize; i++){
            for (int j = 0; j < gridSize; j++){
                boxes[i][j] = new Box();
                Box currentBox = boxes[i][j];

                currentBox.fillLines(horizontalLines[i][j], horizontalLines[i+1][j], verticalLines[i][j], verticalLines[i][j+1]);
            }
        }

    }

    public void setGameSupervisor(GameSupervisor supervisor){
        gameSupervisor = supervisor;
    }

    public void setFirstPlayer(Player player){
        currentPlayer = player;
    }

    public void changePlayer(Player player){
        currentPlayer = player;
    }

    private void checkTouchCoordinates(MotionEvent event) {
        float positionX = event.getX();
        float positionY = event.getY();

        boolean isLIneTaken = linesExaminer.isAnyLineTaken(positionX, positionY);

        if (isLIneTaken){
            linesExaminer.setColorToTakenLine(currentPlayer.getColor());

            invalidate();

            if (boxesExaminer.isBoxTaken()){
                boxesExaminer.setColorToTakenBox(currentPlayer.getColor());
                currentPlayer.increaseScore();

                if (boxesExaminer.isBoxTaken()){
                    boxesExaminer.setColorToTakenBox(currentPlayer.getColor());
                    currentPlayer.increaseScore();
                }

                gameSupervisor.updateScore(currentPlayer);
            }else {
                gameSupervisor.changeTurn(currentPlayer);
            }

            invalidate();

            if (boxesExaminer.isAllBoxesTaken()){
                gameSupervisor.endGame();
                return;
            }

            if (currentPlayer instanceof AI){
                gameSupervisor.makeAIMove();
            }
        }

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

        if (!gameSupervisor.isGameStarted()){
            gameSupervisor.startGame();
            gameSupervisor.setGameAsStarted();
        }
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
                if (box.isTaken()){
                    paint.setColor(box.getColor());
                    int indentation = 15;

                    float left = box.getLeftLine().getStartX() + indentation;
                    float top = box.getTopLine().getStartY() + indentation;
                    float right = box.getRightLine().getStopX() - indentation;
                    float bottom = box.getBottomLine().getStopY() - indentation;

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
        float radius = 80f / gridSize;
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i <= gridSize; i++){
            for (int j = 0; j <= gridSize; j++){
                canvas.drawCircle(startPointX + dotStep * j, startPointY + dotStep * i, radius, paint);
            }
        }
    }

    public Line[][] getHorizontalLines() {
        return horizontalLines;
    }

    public Line[][] getVerticalLines() {
        return verticalLines;
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public int getGridSize() {
        return gridSize;
    }

    public BoxesExaminer getBoxesExaminer() {
        return boxesExaminer;
    }
}
