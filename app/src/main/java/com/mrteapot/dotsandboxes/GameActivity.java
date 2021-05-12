package com.mrteapot.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mrteapot.dotsandboxes.ai.AI;
import com.mrteapot.dotsandboxes.dataclasses.Player;
import com.mrteapot.dotsandboxes.functional.interfaces.GameSupervisor;
import com.mrteapot.dotsandboxes.view.GameView;

public class GameActivity extends AppCompatActivity implements GameSupervisor {

    private GameView gameView;
    private TextView tvPlayerOneScore;
    private TextView tvPlayerTwoScore;
    private TextView tvTurn;

    private Player playerOne;
    private Player playerTwo;

    boolean isGameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initGame();
    }

    private void initGame() {
        gameView = findViewById(R.id.view_game_field);
        TextView tvPlayerOneName = findViewById(R.id.tv_player_one_name);
        TextView tvPlayerTwoName = findViewById(R.id.tv_player_two_name);
        tvPlayerOneScore = findViewById(R.id.tv_player_one_score);
        tvPlayerTwoScore = findViewById(R.id.tv_player_two_score);
        tvTurn = findViewById(R.id.tv_turn);

        playerOne = new Player("Player One", Color.GREEN);
        playerTwo = new AI("AI", Color.BLUE, gameView);

        tvPlayerOneName.setText(playerOne.getName());
        tvPlayerTwoName.setText(playerTwo.getName());
        tvPlayerOneScore.setText(getString(R.string.score, playerOne.getScore()));
        tvPlayerTwoScore.setText(getString(R.string.score, playerTwo.getScore()));
        tvTurn.setText(getString(R.string.turn_of, playerOne.getName()));

        TextView colorONe = findViewById(R.id.player_one_color);
        colorONe.setBackgroundColor(playerOne.getColor());
        TextView colorTwo = findViewById(R.id.player_two_color);
        colorTwo.setBackgroundColor(playerTwo.getColor());

        gameView.setGameSupervisor(this);
    }

    @Override
    public void startGame() {
        boolean turnOfPlayerOne = getIntent().getBooleanExtra(MainActivity.EXTRA_BOOLEAN, true);

        if (turnOfPlayerOne){
            gameView.setFirstPlayer(playerOne);
        }else {
            gameView.setFirstPlayer(playerTwo);
            makeAIMove();
        }
    }

    @Override
    public boolean isGameStarted() {
        return isGameStarted;
    }

    @Override
    public void setGameAsStarted() {
        this.isGameStarted = true;
    }

    @Override
    public void makeAIMove() {
        if (playerTwo instanceof  AI){
            ((AI) playerTwo).makeBestMove();
        }
    }

    @Override
    public void updateScore(Player player) {
        if (player.equals(playerOne)){
            tvPlayerOneScore.setText(getString(R.string.score, playerOne.getScore()));
        }else {
            tvPlayerTwoScore.setText(getString(R.string.score, playerTwo.getScore()));
        }
    }

    @Override
    public void changeTurn(Player player) {
        if (player.equals(playerOne)){
            tvTurn.setText(getString(R.string.turn_of, playerTwo.getName()));
            gameView.changePlayer(playerTwo);
            makeAIMove();

        }else {
            tvTurn.setText(getString(R.string.turn_of, playerOne.getName()));
            gameView.changePlayer(playerOne);
        }
    }

    @Override
    public void endGame() {
        if (playerOne.getScore() > playerTwo.getScore()){
            tvTurn.setText(playerOne.getName() + " won!");
        }else if (playerOne.getScore() == playerTwo.getScore()){
            tvTurn.setText("Draw!");
        }else {
            tvTurn.setText(playerTwo.getName() + " won!");
        }

        Toast.makeText(this, "Game ended!", Toast.LENGTH_SHORT).show();
    }

    public void restartGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}