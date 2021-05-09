package com.mrteapot.dotsandboxes.functional.interfaces;

import com.mrteapot.dotsandboxes.dataclasses.Player;

public interface GameSupervisor {
    void updateScore(Player player);
    void changeTurn(Player player);
    void endGame();
}
