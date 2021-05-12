package com.mrteapot.dotsandboxes.functional.classes;

import com.mrteapot.dotsandboxes.dataclasses.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinesExaminer{
    private final List<Line> untakenLines = new ArrayList<>();
    private final Line[][] horizontalLines;
    private final Line[][] verticalLines;

    public LinesExaminer(Line[][] horizontalLines, Line[][] verticalLines) {
        fillList(horizontalLines, verticalLines);
        this.horizontalLines = horizontalLines;
        this.verticalLines = verticalLines;
    }

    private void fillList(Line[][] horizontalLines, Line[][] verticalLines) {
        for (Line[] lines : horizontalLines){
            untakenLines.addAll(Arrays.asList(lines));
        }

        for (Line[] lines : verticalLines){
            untakenLines.addAll(Arrays.asList(lines));
        }
    }

    public boolean isAnyLineTaken(float positionX, float positionY){
        int widthForTouch = 30;
        for (Line[] lines : horizontalLines){
            for (Line currentLine : lines){
                if ((currentLine.getStartX() <= positionX && currentLine.getStopX() >= positionX)
                        && (currentLine.getStartY() + widthForTouch >= positionY && currentLine.getStartY() - widthForTouch <= positionY)
                        && !currentLine.isTaken() && untakenLines.contains(currentLine)){
                    currentLine.setTaken(true);
                    untakenLines.remove(currentLine);
                    return true;
                }
            }
        }

        for (Line[] lines : verticalLines){
            for (Line currentLine : lines){
                if ((currentLine.getStartX() + widthForTouch >= positionX && currentLine.getStopX() - widthForTouch <= positionX)
                        && (currentLine.getStartY() <= positionY && currentLine.getStopY() >= positionY)
                        && !currentLine.isTaken() && untakenLines.contains(currentLine)){
                    currentLine.setTaken(true);
                    untakenLines.remove(currentLine);
                    return true;
                }
            }
        }

        return false;
    }

    public void setColorToTakenLine(int color) {
        for (Line[] lines : horizontalLines){
            for (Line currentLine : lines){
                if (currentLine.getColor() == Line.getDefaultColor() && currentLine.isTaken()){
                    currentLine.setColor(color);
                    return;
                }
            }
        }

        for (Line[] lines : verticalLines){
            for (Line currentLine : lines){
                if (currentLine.getColor() == Line.getDefaultColor() && currentLine.isTaken()){
                    currentLine.setColor(color);
                    return;
                }
            }
        }
    }
}
