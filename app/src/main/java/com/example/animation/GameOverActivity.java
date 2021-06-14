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

        scorelabel = findViewById(R.id.scoreTv);
        highScoreList = findViewById(R.id.highScoreTv);
        playerHighScList = findViewById(R.id.playerHighScoreTv);

        getPlHighScore = findViewById(R.id.highScorebtn);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


        getPlHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}