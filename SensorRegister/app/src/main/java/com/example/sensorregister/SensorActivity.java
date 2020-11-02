package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView accelX, accelY, accelZ;
    private TextView proximity;
    private TextView light;
    private TextView gravX, gravY, gravZ;
    private TextView gyroX, gyroY, gyroZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        setupViews();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            String sensor = sensorEvent.sensor.getName();
            switch (sensorEvent.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    accelX.setText(String.valueOf(sensorEvent.values[0]));
                    accelY.setText(String.valueOf(sensorEvent.values[1]));
                    accelZ.setText(String.valueOf(sensorEvent.values[2]));
                    break;
                case Sensor.TYPE_LIGHT:
                    light.setText(String.valueOf(sensorEvent.values[0]));
                    break;
                case Sensor.TYPE_PROXIMITY:
                    proximity.setText(String.valueOf(sensorEvent.values[0]));
                    break;
                case Sensor.TYPE_GRAVITY:
                    gravX.setText(String.valueOf(sensorEvent.values[0]));
                    gravY.setText(String.valueOf(sensorEvent.values[1]));
                    gravZ.setText(String.valueOf(sensorEvent.values[2]));
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    gyroX.setText(String.valueOf(sensorEvent.values[0]));
                    gyroY.setText(String.valueOf(sensorEvent.values[1]));
                    gyroZ.setText(String.valueOf(sensorEvent.values[2]));
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void setupViews() {
        accelX = (TextView) findViewById(R.id.accelX);
        accelY = (TextView) findViewById(R.id.accelY);
        accelZ = (TextView) findViewById(R.id.accelZ);
        proximity = (TextView) findViewById(R.id.prox);
        light = (TextView) findViewById(R.id.light);
        gravX = (TextView) findViewById(R.id.gravityX);
        gravY = (TextView) findViewById(R.id.gravityY);
        gravZ = (TextView) findViewById(R.id.gravityZ);
        gyroX = (TextView) findViewById(R.id.gyroX);
        gyroY = (TextView) findViewById(R.id.gyroY);
        gyroZ = (TextView) findViewById(R.id.gyroZ);
    }

    private void registerListeners() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterListeners() {
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
    }

    @Override
    protected void onStop() {
        unregisterListeners();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterListeners();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        unregisterListeners();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        registerListeners();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        registerListeners();
        super.onResume();
    }
}