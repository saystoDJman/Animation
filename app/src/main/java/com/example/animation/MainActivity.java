package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView ball;

    private int screenWidth;
    private int screenHeight;

    private float x_pos = 0.0f;
    private float y_pos = 0.0f;

    private float x_value = 0.0f;
    private float y_value = 0.0f;
    private float z_value = 0.0f;

    private Handler handler = new Handler();
    Timer timer = new Timer();

    SensorManager sensorManager;
    Sensor sensor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ball = findViewById(R.id.basket_ball);
        ball.getLayoutParams().height = 200;
        ball.getLayoutParams().width = 200;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        Toast.makeText(getBaseContext(), Integer.toString(screenWidth),Toast.LENGTH_SHORT).show();

        ball.setX(x_pos);
        ball.setY(y_pos);



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

    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
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

        if(x_value < 0){
            x_pos += 5.0f;
        }else{
            x_pos -= 5.0f;
        }

        if(y_value > 0){
            y_pos += 5.0f;
        }else{
            y_pos -= 5.0f;
        }

        if(x_pos > 0 && x_pos < 520.0f) {
            ball.setX(x_pos);
            Log.d("x",Float.toString(x_pos));
        }
        if(y_pos > 0 && y_pos < 1220.0f){
            ball.setY(y_pos);
            Log.d("y",Float.toString(y_pos));
        }


    }

}