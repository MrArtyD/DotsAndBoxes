package com.mrteapot.dotsandboxes.functional.classes;

import com.mrteapot.dotsandboxes.dataclasses.Box;
import com.mrteapot.dotsandboxes.dataclasses.Line;

import java.util.List;

public class GameEvaluator {
    private int aiBoxesTaken = 0;
    private int playerBoxesTaken = 0;
    private final int gridSize;

    public GameEvaluator(int gridSize) {
        this.gridSize = gridSize;
    }

    public int evaluatePosition(boolean isMaximizer, Box[][] boxes){
        return checkUntakenBoxes(isMaximizer, boxes);
    }

    public int evaluateWin(boolean isMaximizing){

        if (isMaximizing){
            return 100;
        }else {
            return -100;
        }
    }

    public boolean isWinningMove(Box[][] boxes){
        for (Box[] lineOfBoxes : boxes){
            for(Box box : lineOfBoxes){
                if (!box.isTaken()){
                    int selectedLines = 0;
                    for (Line line : box.getLines()){
                        if (line.isTaken()){
                            selectedLines++;
                        }
                    }

                    if (selectedLines != 2){
                        return false;
                    }
                }

            }
        }

        return true;
    }

    private int checkUntakenBoxes(boolean isMaximizer, Box[][] boxes){
        int move = 0;
        for (Box[] lineOfBoxes : boxes){
            for(Box box : lineOfBoxes){
                if (!box.isTaken()){
                    int selectedLines = 0;
                    for (Line line : box.getLines()){
                        if (line.isTaken()){
                            selectedLines++;
                        }
                    }

                    switch (selectedLines){
                        case 3:
                            if (isMaximizer){
                                return 3;
                            }else {
                                return -3;
                            }
                        case 0:
                        case 1:
                            selectedLines = 2;
                            break;
                        case 2:
                            selectedLines = 1;
                            break;
                    }

                    if (isMaximizer){
                        move = Math.max(move, selectedLines);
                    }else {
                        move = Math.min(move, -selectedLines);
                    }

                }

            }
        }

        return move;
    }

}
