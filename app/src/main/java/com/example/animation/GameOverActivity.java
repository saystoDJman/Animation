package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView scorelabel;
    Button getPlHighScore, mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scorelabel = findViewById(R.id.scoreTv);
        getPlHighScore = findViewById(R.id.highScorebtn);
        mainMenu = findViewById(R.id.btnMainMenu);

        scorelabel.setText(Integer.toString(MainActivity.finalScore));

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