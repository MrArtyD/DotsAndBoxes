package com.mrteapot.dotsandboxes.dataclasses;

import java.util.Objects;

public class Player {
    private String name;
    private int color;
    private int score;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
        score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(){
        score++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return color == player.color &&
                score == player.score &&
                name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, score);
    }
}
