package com.mrteapot.dotsandboxes.functional.classes;

import com.mrteapot.dotsandboxes.dataclasses.Box;
import com.mrteapot.dotsandboxes.dataclasses.Player;

public class BoxExaminer {
    public boolean isBoxTaken(Box[][] boxes, Player player){

        for (Box[] lineOfBoxes : boxes){
            for(Box box : lineOfBoxes){
                if (box.isSurrounded() && !box.isTaken()){
                    box.setTaken(true);
                    box.setColor(player.getColor());
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isAllBoxesTaken(Box[][] boxes){
        for (Box[] lineOfBoxes : boxes){
            for(Box box : lineOfBoxes){
                if (!box.isTaken()){

                    return false;
                }
            }
        }

        return true;
    }
}
