package com.mrteapot.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mrteapot.dotsandboxes.dataclasses.Player;
import com.mrteapot.dotsandboxes.functional.interfaces.GameSupervisor;
import com.mrteapot.dotsandboxes.view.GameView;

public class GameActivity extends AppCompatActivity implements GameSupervisor {

    private GameView gameView;
    private TextView tvPlayerOneName;
    private TextView tvPlayerTwoName;
    private TextView tvPlayerOneScore;
    private TextView tvPlayerTwoScore;
    private TextView tvTurn;

    private Player playerOne;
    private Player playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initGame();
    }

    private void initGame() {
        gameView = findViewById(R.id.view_game_field);
        tvPlayerOneName = findViewById(R.id.tv_player_one_name);
        tvPlayerTwoName = findViewById(R.id.tv_player_two_name);
        tvPlayerOneScore = findViewById(R.id.tv_player_one_score);
        tvPlayerTwoScore = findViewById(R.id.tv_player_two_score);
        tvTurn = findViewById(R.id.tv_turn);

        playerOne = new Player("One", Color.GREEN);
        playerTwo = new Player("Two", Color.BLUE);

        tvPlayerOneName.setText(playerOne.getName());
        tvPlayerTwoName.setText(playerTwo.getName());
        tvPlayerOneScore.setText(getString(R.string.score, playerOne.getScore()));
        tvPlayerTwoScore.setText(getString(R.string.score, playerTwo.getScore()));
        tvTurn.setText(getString(R.string.turn_of, playerOne.getName()));

        gameView.startGame(playerOne, this);
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
        }else {
            tvTurn.setText(getString(R.string.turn_of, playerOne.getName()));
            gameView.changePlayer(playerOne);
        }
    }

    @Override
    public void endGame() {
        if (playerOne.getScore() > playerTwo.getScore()){
            Toast.makeText(this, playerOne.getName() + " wins!", Toast.LENGTH_SHORT).show();
        }else if (playerOne.getScore() == playerTwo.getScore()){
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, playerTwo.getName() + " wins!", Toast.LENGTH_SHORT).show();
        }
    }


}