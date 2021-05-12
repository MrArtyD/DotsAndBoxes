package com.mrteapot.dotsandboxes.functional.classes;

import com.mrteapot.dotsandboxes.dataclasses.Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoxesExaminer{

    private final List<Box> untakenBoxes = new ArrayList<>();
    private final Box[][] allBoxes;

    public BoxesExaminer(Box[][] boxes) {
        fillList(boxes);
        this.allBoxes = boxes;
    }

    private void fillList(Box[][] boxes) {
        for (Box[] lineOFBoxes: boxes) {
            untakenBoxes.addAll(Arrays.asList(lineOFBoxes));
        }
    }

    public boolean isBoxTaken(){
        for (Box[] lineOfBoxes : allBoxes){
            for(Box box : lineOfBoxes){
                if (box.isTaken() && untakenBoxes.contains(box)){
                    untakenBoxes.remove(box);
                    return true;
                }
                if (!box.isTaken() && !untakenBoxes.contains(box)){
                    untakenBoxes.add(box);
                }
            }
        }

        return false;
    }

    public boolean isAllBoxesTaken(){
        return untakenBoxes.isEmpty();
    }

    public void setColorToTakenBox(int color) {
        for (Box[] lineOfBoxes : allBoxes){
            for(Box box : lineOfBoxes){
                if (box.isTaken() && box.getColor() == Box.getDefaultColor()){
                    box.setColor(color);
                }
            }
        }
    }
}
