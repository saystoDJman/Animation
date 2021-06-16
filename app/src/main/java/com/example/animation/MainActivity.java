package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    String loginPlayer = LoginActivity.loginPlayer;
    String loginPassword = LoginActivity.loginPassword;

    private ImageView ball;
    private ImageView hole;
    private ImageView topWall, bottomWall, leftWall, rightWall;

    TextView score;

    private int screenWidth;
    private int screenHeight;

    private float x_pos = 1.0f;
    private float y_pos = 1.0f;

    private float x_max;
    private float y_max;

    boolean allowX = true, allowY = true, allowBall = true;

    private float x_value = 0.0f;
    private float y_value = 0.0f;
    private float z_value = 0.0f;

    private boolean gameOver = false;

    private Handler handler = new Handler();
    Timer timer = new Timer();

    SensorManager sensorManager;
    Sensor sensor;
    public int counter = 500;

    public static int finalScore;

    CountDownTimer c;

    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ball = findViewById(R.id.ball);
        hole = findViewById(R.id.hole);
        ball.getLayoutParams().height = 100;
        ball.getLayoutParams().width = 100;
        topWall = findViewById(R.id.topWall);
        leftWall = findViewById(R.id.leftWall);
        bottomWall = findViewById(R.id.bottomWall);
        rightWall = findViewById(R.id.rightWall);
        score = findViewById(R.id.txtScore);

        DB = new DBHelper(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        x_max = (float) screenWidth;
        y_max = (float) screenHeight;

        ball.setX(x_pos);
        ball.setY(y_pos);

        c = new CountDownTimer(500000, 1000){
            public void onTick(long millisUntilFinished){
                finalScore = counter;
                score.setText(String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                Toast.makeText(getBaseContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getBaseContext(),GameOverActivity.class);
                startActivity(gameOverIntent);
                finish();
            }
        }.start();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 20);

        String s = "Left: " + Integer.toString(hole.getLeft()) + "Right: " + Integer.toString(hole.getRight()) + "Top: " + Integer.toString(hole.getTop()) +"Bottom: " + Integer.toString(hole.getBottom());
        Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();

    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(acceleListener);
    }


    public void onResume() {
        super.onResume();
        sensorManager.registerListener(acceleListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(acceleListener);
    }

    public SensorEventListener acceleListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {

        }

        public void onSensorChanged(SensorEvent event) {
            x_value = event.values[0];
            y_value = event.values[1];
            z_value = event.values[2];

            //x = (int) x;
            //y = (int) y;
        }
    };

    public void changePos(){

        allowBall = true;

        if(!gameOver){

            if(x_value < 0){
                x_pos += 2.0f;
            }else{
                x_pos -= 2.0f;
            }

            if(y_value > 0){
                y_pos += 2.0f;
            }else{
                y_pos -= 2.0f;
            }

            int[] location = new int[2];
            hole.getLocationOnScreen(location);
            float hole_x = (float) location[0];
            float hole_y = (float) location[1];

            //Toast.makeText(getBaseContext(),"X_Pos: "+Float.toString(x_pos)+"Y_Pos:"+Float.toString(y_pos),Toast.LENGTH_SHORT).show();

            if(y_pos >  (float) hole.getTop() && y_pos < (float) hole.getBottom()-60.0f){
                if(x_pos > (float) hole.getLeft() && x_pos < (float) hole.getRight()-60.0f){
                    gameOver = true;
                    c.cancel();
                    Cursor res = DB.getPlayer(loginPlayer);
                    int oldScore = 0;
                    if(res.getCount() != 0){
                        while (res.moveToNext()) {
                            oldScore = res.getInt(2);
                        }
                    }

                    if(oldScore < finalScore){
                        Boolean checkUpdate = DB.updateScore(loginPlayer,loginPassword,finalScore);
                    }
                    Toast.makeText(getBaseContext(),"Game Over",Toast.LENGTH_SHORT).show();
                    Intent gameOverIntent = new Intent(getBaseContext(),GameOverActivity.class);
                    startActivity(gameOverIntent);
                    finish();
                }
            }


            if(x_pos > 60.0f && x_pos < 560.0f){//TopWall
                if(y_pos > 195.0f && y_pos < 375.0f) {
                    Log.d("X", Float.toString(x_pos));
                    Log.d("Y:", Float.toString(y_pos));
                    //String s = "Left: " + Integer.toString(topWall.getLeft()) + " Right: " + Integer.toString(topWall.getRight()) + " Top: " + Integer.toString(topWall.getTop()) +" Bottom: " + Integer.toString(topWall.getBottom());
                    //Log.d("TopWall",s);
                    allowBall = false;
                    if (x_value < 0) {
                        x_pos -= 1.0f;
                    } else {
                        x_pos += 1.0f;
                    }

                    if (y_value > 0) {
                        y_pos -= 1.0f;
                    } else {
                        y_pos += 1.0f;
                    }
                }
            }

            if(x_pos > 60.0f && x_pos < 240.0f){//LeftWall
                if(y_pos > 375.0f && y_pos < 880.0f) {
                    Log.d("X", Float.toString(x_pos));
                    Log.d("Y:", Float.toString(y_pos));
                    //String s = "Left: " + Integer.toString(topWall.getLeft()) + " Right: " + Integer.toString(topWall.getRight()) + " Top: " + Integer.toString(topWall.getTop()) +" Bottom: " + Integer.toString(topWall.getBottom());
                    //Log.d("TopWall",s);
                    allowBall = false;
                    if (x_value < 0) {
                        x_pos -= 1.0f;
                    } else {
                        x_pos += 1.0f;
                    }

                    if (y_value > 0) {
                        y_pos -= 1.0f;
                    } else {
                        y_pos += 1.0f;
                    }
                }
            }

            if(x_pos > 60.0f && x_pos < 560.0f){//BottomWall
                if(y_pos > 865.0f && y_pos < 1050.0f) {
                    Log.d("X", Float.toString(x_pos));
                    Log.d("Y:", Float.toString(y_pos));
                    //String s = "Left: " + Integer.toString(topWall.getLeft()) + " Right: " + Integer.toString(topWall.getRight()) + " Top: " + Integer.toString(topWall.getTop()) +" Bottom: " + Integer.toString(topWall.getBottom());
                    //Log.d("TopWall",s);
                    allowBall = false;
                    if (x_value < 0) {
                        x_pos -= 1.0f;
                    } else {
                        x_pos += 1.0f;
                    }

                    if (y_value > 0) {
                        y_pos -= 1.0f;
                    } else {
                        y_pos += 1.0f;
                    }
                }
            }

            if(x_pos > 415.0f && x_pos < 590.0f){//RightWall
                if(y_pos > 270.0f && y_pos < 770.0f) {
                    Log.d("X", Float.toString(x_pos));
                    Log.d("Y:", Float.toString(y_pos));
                    //String s = "Left: " + Integer.toString(topWall.getLeft()) + " Right: " + Integer.toString(topWall.getRight()) + " Top: " + Integer.toString(topWall.getTop()) +" Bottom: " + Integer.toString(topWall.getBottom());
                    //Log.d("TopWall",s);
                    allowBall = false;
                    if (x_value < 0) {
                        x_pos -= 1.0f;
                    } else {
                        x_pos += 1.0f;
                    }

                    if (y_value > 0) {
                        y_pos -= 1.0f;
                    } else {
                        y_pos += 1.0f;
                    }
                }
            }


            if(allowBall){
                if(x_pos >= 0 && x_pos < x_max-100.0f) {
                    ball.setX(x_pos);
                    //Log.d("x",Float.toString(x_pos));
                }
                if(y_pos >= 0 && y_pos < y_max-100.0f){
                    ball.setY(y_pos);
                    //Log.d("y",Float.toString(y_pos));
                }
            }
            Log.d("X",Float.toString(x_pos));
            Log.d("Y:",Float.toString(y_pos));

        }

    }

}