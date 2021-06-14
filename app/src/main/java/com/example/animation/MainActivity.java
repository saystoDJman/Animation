package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView ball;
    private ImageView hole;

    private int screenWidth;
    private int screenHeight;

    private float x_pos = 0.0f;
    private float y_pos = 0.0f;

    private float x_max;
    private float y_max;

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
        ball = findViewById(R.id.ball);
        hole = findViewById(R.id.hole);
        ball.getLayoutParams().height = 100;
        ball.getLayoutParams().width = 100;

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

        if(x_value < 0){
            x_pos += 3.0f;
        }else{
            x_pos -= 3.0f;
        }

        if(y_value > 0){
            y_pos += 3.0f;
        }else{
            y_pos -= 3.0f;
        }


        int[] location = new int[2];
        hole.getLocationOnScreen(location);
        float hole_x = (float) location[0];
        float hole_y = (float) location[1];

        //String s = "Left: " + Integer.toString(hole.getLeft()) + "Right: " + Integer.toString(hole.getRight()) + "Top: " + Integer.toString(hole.getTop()) +"Bottom: " + Integer.toString(hole.getBottom());
        //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();

        if(y_pos >=  (float) hole.getTop() && y_pos <= (float) hole.getBottom() ){
            if(x_pos >= (float) hole.getLeft() && y_pos <= (float) hole.getLeft()){
                Toast.makeText(getBaseContext(),"Game Over",Toast.LENGTH_SHORT).show();
            }
        }

        //Toast.makeText(getBaseContext(),"X:"+Float.toString(hole_x)+"Y:"+Float.toString(hole_y),Toast.LENGTH_SHORT).show();


        if(x_pos > 0 && x_pos < x_max-100.0f) {
            ball.setX(x_pos);
            Log.d("x",Float.toString(x_pos));
        }
        if(y_pos > 0 && y_pos < y_max-100.0f){
            ball.setY(y_pos);
            Log.d("y",Float.toString(y_pos));
        }



    }

}