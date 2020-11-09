package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sensorregister.requestUtilities.event.EventRequest;
import com.example.sensorregister.requestUtilities.event.EventResponse;
import com.example.sensorregister.requestUtilities.login.LoginResponse;
import com.example.sensorregister.requestUtilities.services.RequestService;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import resources.EventManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.MusicService;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView accelX, accelY, accelZ;
    private TextView proximity;
    private TextView light;
    private TextView gravX, gravY, gravZ;
    private TextView gyroX, gyroY, gyroZ;
    private Button musicBttn;
    private boolean musicSounding;
    private Intent musicIntent;
    private Retrofit retrofit;
    private RequestService eventService;
    private EventManager eventManager;
    private String env;
    private String token;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        env = getString(R.string.prodEnv);
        setupViews();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        musicSounding = false;
        musicIntent = new Intent(SensorActivity.this, MusicService.class);
        setupRetrofit();
        loadFromIntent();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    if(sensorEvent.values[0] > 10 || sensorEvent.values[1] > 10 || sensorEvent.values[2] > 10)
                        eventManager.post(new EventRequest(env, getString(R.string.eventAccelerometer), getString(R.string.eventAccelerometerDesc)));
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
                    if(sensorEvent.values[0] > 5 && sensorEvent.values[1] > 5 && sensorEvent.values[2] > 5)
                        eventManager.post(new EventRequest(env, getString(R.string.eventGravity), getString(R.string.eventGravityDesc)));
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

    private void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getString(R.string.uri))
                .build();

        eventManager = new EventManager(getApplicationContext(), retrofit.create(RequestService.class));
    }

    public View.OnClickListener buttonsListener = view -> {
        switch (view.getId()) {
            case R.id.musicBttn:
                if (!musicSounding) {
                    ThreadAsynctask thread = new ThreadAsynctask();
                    thread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getString(R.string.waitThreadId));
                } else {
                    musicBttn.setText(getString(R.string.startMusicBttn));
                    musicSounding = false;
                    stopService(musicIntent);
                }
                break;
        }
    };

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
        musicBttn = (Button) findViewById(R.id.musicBttn);
        musicBttn.setOnClickListener(buttonsListener);
    }

    private void registerReceivers() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterReceivers() {
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
    }

    @Override
    protected void onStop() {
        unregisterReceivers();
        stopService(musicIntent);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceivers();
        stopService(musicIntent);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        unregisterReceivers();
        stopService(musicIntent);
        super.onPause();
    }

    @Override
    protected void onRestart() {
        registerReceivers();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        registerReceivers();
        super.onResume();
    }

    private class ThreadAsynctask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), getString(R.string.beginWaiting), Toast.LENGTH_SHORT).show();
            musicBttn.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                Log.e(getString(R.string.exception), e.getMessage());
            }
            return getString(R.string.startingMusic);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            musicBttn.setEnabled(true);
            musicBttn.setText(getString(R.string.stopMusicBttn));
            musicSounding = true;
            startService(musicIntent);
            eventManager.post(new EventRequest(env, getString(R.string.eventMusic), getString(R.string.eventMusicDesc)));
        }
    }

    private void loadFromIntent() {
        bundle = getIntent().getExtras();
        token = bundle.getString("token");
        eventManager.setToken(token);
    }
}