package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView scorelabel,highScoreList,playerHighScList;
    Button getPlHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scorelabel = findViewById(R.id.scoreTv);
        highScoreList = findViewById(R.id.playerHighScoreTv);
        playerHighScList = findViewById(R.id.highScoreTv);

        getPlHighScore = findViewById(R.id.highScorebtn);

        getPlHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}