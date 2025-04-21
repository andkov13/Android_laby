package com.example.lab_5;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private ImageView compassImage;
    private TextView directionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassImage = findViewById(R.id.dialImage);
        directionText = findViewById(R.id.angleText);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (rotationSensor == null) {
            directionText.setText("Датчик обертання не підтримується!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private float previousAzimuth = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

            float[] orientation = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);

            float azimuthInRadians = orientation[0];
            float newAzimuth = (float) Math.toDegrees(azimuthInRadians);
            newAzimuth = (newAzimuth + 360) % 360;

            float delta = newAzimuth - previousAzimuth;

            if (delta > 180) delta -= 360;
            else if (delta < -180) delta += 360;

            if (Math.abs(delta) < 1) return;

            float smoothAzimuth = previousAzimuth + delta * 0.05f;
            smoothAzimuth = (smoothAzimuth + 360) % 360;
            previousAzimuth = smoothAzimuth;

            compassImage.setRotation(-smoothAzimuth);
            String newAngleText = (int) smoothAzimuth + "°";
            directionText.setText(newAngleText);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}