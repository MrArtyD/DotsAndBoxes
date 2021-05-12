package com.mrteapot.dotsandboxes.functional.interfaces;

import com.mrteapot.dotsandboxes.dataclasses.Player;

public interface GameSupervisor {
    boolean isGameStarted();
    void setGameAsStarted();
    void startGame();
    void updateScore(Player player);
    void changeTurn(Player player);
    void endGame();
    void makeAIMove();
}
