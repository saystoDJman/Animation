package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView scorelabel, gameOver;
    Button getPlHighScore, mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        gameOver = findViewById(R.id.txtGameOver);
        scorelabel = findViewById(R.id.scoreTv);
        getPlHighScore = findViewById(R.id.highScorebtn);
        mainMenu = findViewById(R.id.btnMainMenu);

        scorelabel.setText(Integer.toString(MainActivity.finalScore));

        ObjectAnimator animator = ObjectAnimator.ofInt(gameOver, "backgroundColor", Color.BLACK);

        // duration of one color
        animator.setDuration(700);
        animator.setEvaluator(new ArgbEvaluator());

        // color will be show in reverse manner
        animator.setRepeatCount(Animation.REVERSE);

        // It will be repeated up to infinite time
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();

        getPlHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Top3Players.class);
                startActivity(intent);
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),StartGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}