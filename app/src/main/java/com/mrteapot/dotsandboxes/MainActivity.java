package com.mrteapot.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_BOOLEAN = "com.mrteapot.dotsandboxes.BOOLEAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playerFirst(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_BOOLEAN, true);
        startActivity(intent);
    }

    public void aiFirst(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_BOOLEAN, false);
        startActivity(intent);
    }
}