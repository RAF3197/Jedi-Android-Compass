package com.jediupc.helloandroid;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass extends AppCompatActivity implements SensorEventListener {

    ImageView compassss;
    float oldHeading = 0f;
    SensorManager mSensorManager;
    Sensor mCompass;
    TextView head;

    boolean first=true;

    @Override
    protected void onCreateee(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compass = findViewById(R.id.compassImage);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        head = findViewById(R.id.compassPosition);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event){
       float newHeading = event.values[0];

       head.setText(String.valueOf(event.values[0]));

        RotateAnimation anim = new RotateAnimation(oldHeading,-newHeading, RotateAnimation.RELATIVE_TO_SELF,(float)0.5, Animation.RELATIVE_TO_SELF,(float)0.5);

        anim.setDuration(200);

        anim.setFillAfter(true);

        compass.startAnimation(anim);

        oldHeading = -newHeading;



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL);
    }
}
