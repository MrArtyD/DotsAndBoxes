package com.mrteapot.dotsandboxes.ai;

import android.os.SystemClock;
import android.view.MotionEvent;

import com.mrteapot.dotsandboxes.dataclasses.Line;
import com.mrteapot.dotsandboxes.dataclasses.Player;
import com.mrteapot.dotsandboxes.functional.classes.BoxesExaminer;
import com.mrteapot.dotsandboxes.functional.classes.GameEvaluator;
import com.mrteapot.dotsandboxes.view.Direction;
import com.mrteapot.dotsandboxes.view.GameView;

import java.util.ArrayList;
import java.util.List;

public class AI extends Player {
    private final GameView gameField;
    private final GameEvaluator evaluator;
    private final BoxesExaminer boxesExaminer;

    public AI(String name, int color, GameView gameView) {
        super(name, color);
        gameField = gameView;
        boxesExaminer = gameView.getBoxesExaminer();
        evaluator = new GameEvaluator(gameView.getGridSize());
    }

    public void makeBestMove() {
        int bestValue = Integer.MIN_VALUE;
        Line bestLine = null;
        List<Line[]> allLines = fillLinesAsList();

        for (Line[] lineOfLines : allLines) {
            for (Line line : lineOfLines) {
                if (!line.isTaken()) {
                    line.setTaken(true);
                    int moveValue = minimaxWithAlphaBeta(allLines, 4, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    line.setTaken(false);

                    boxesExaminer.isBoxTaken();
                    if (moveValue > bestValue) {
                        bestValue = moveValue;
                        bestLine = line;
                    }
                }
            }
        }

        if (bestLine != null) {
            if (bestLine.getDirection() == Direction.HORIZONTAL) {
                performTouch(bestLine.getStartX() + 100, bestLine.getStartY());
            } else {
                performTouch(bestLine.getStartX(), bestLine.getStartY() + 100);
            }
        }
    }

    private int minimaxWithAlphaBeta(List<Line[]> lines, int depth, boolean isMaximizing, int alpha, int beta) {

        if (evaluator.isWinningMove(gameField.getBoxes())) {
            if (isMaximizing) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        if (depth == 0 || boxesExaminer.isBoxTaken()) {
            if (depth != 0) {
                return evaluator.evaluateWin(!isMaximizing) + depth;
            } else {
                return evaluator.evaluatePosition(isMaximizing, gameField.getBoxes());
            }
        }

        if (isMaximizing) {
            int bestValue = Integer.MIN_VALUE;
            for (Line[] lineOfLines : lines) {
                for (Line line : lineOfLines) {
                    if (!line.isTaken()) {
                        line.setTaken(true);
                        int moveValue = minimaxWithAlphaBeta(lines, depth - 1, false, alpha, beta);

                        bestValue = Math.max(bestValue, moveValue);
                        alpha = Math.max(alpha, bestValue);

                        line.setTaken(false);

                        if (alpha >= beta) {
                            break;
                        }
                    }
                }
            }
            return bestValue;
        } else {
            int worstValue = Integer.MAX_VALUE;
            for (Line[] lineOfLines : lines) {
                for (Line line : lineOfLines) {
                    if (!line.isTaken()) {
                        line.setTaken(true);
                        int moveValue = minimaxWithAlphaBeta(lines, depth - 1, true, alpha, beta);

                        worstValue = Math.min(worstValue, moveValue);
                        beta = Math.min(beta, worstValue);

                        line.setTaken(false);

                        if (alpha >= beta) {
                            break;
                        }
                    }
                }
            }
            return worstValue;
        }
    }

    private void performTouch(float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        int metaState = 0;

        MotionEvent motionEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                metaState
        );
        gameField.dispatchTouchEvent(motionEvent);
    }

    private List<Line[]> fillLinesAsList() {
        List<Line[]> lines = new ArrayList<>();
        for (int i = 0; i < gameField.getHorizontalLines().length; i++) {
            lines.add(gameField.getHorizontalLines()[i]);
            if (i != gameField.getHorizontalLines().length - 1) {
                lines.add(gameField.getVerticalLines()[i]);
            }
        }

        return lines;
    }

}
