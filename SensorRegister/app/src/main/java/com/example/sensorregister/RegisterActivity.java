package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensorregister.requestUtilities.event.EventRequest;
import com.example.sensorregister.requestUtilities.event.EventResponse;
import com.example.sensorregister.requestUtilities.register.RegisterRequest;
import com.example.sensorregister.requestUtilities.register.RegisterResponse;
import com.example.sensorregister.requestUtilities.services.RequestService;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class RegisterActivity extends AppCompatActivity {
    private Bundle bundle;
    private TextView mail, pass, name, lastname, dni, commission;
    private Button submitBttn;
    private Retrofit retrofit;
    private RequestService requestService;
    private RequestService eventService;
    private String env;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        env = getString(R.string.prodEnv);
        setupViews();
        loadFromIntent();
        setupRetrofit();
    }

    public View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.submitBttn:
                    register();
                    break;
            }
        }
    };

    private void setupRetrofit() {

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getString(R.string.uri))
                .build();

        requestService = retrofit.create(RequestService.class);
        eventService = retrofit.create(RequestService.class);
    }

    private boolean areFieldsValid() {
        if (!mail.getText().toString().isEmpty() &&
                !pass.getText().toString().isEmpty() &&
                !name.getText().toString().isEmpty() &&
                !lastname.getText().toString().isEmpty() &&
                !dni.getText().toString().isEmpty() &&
                !commission.getText().toString().isEmpty())
            return true;
        Toast.makeText(getApplicationContext(), getString(R.string.invalidForm), Toast.LENGTH_SHORT).show();
        return false;
    }

    private RegisterRequest populateRequest() {
        return new RegisterRequest(
                mail.getText().toString(),
                pass.getText().toString(),
                name.getText().toString(),
                lastname.getText().toString(),
                Long.parseLong(dni.getText().toString()),
                Long.parseLong(commission.getText().toString()),
                getString(R.string.testEnv)
        );
    }

    private void register() {
        if (!areFieldsValid()) return;
        RegisterRequest request = populateRequest();
        Toast.makeText(getApplicationContext(), getString(R.string.registerSubmitMsg), Toast.LENGTH_SHORT).show();
        Call<RegisterResponse> call = requestService.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.registerSuccessMsg), Toast.LENGTH_SHORT).show();
                    Log.i(getString(R.string.registerTagLog), getString(R.string.registerSuccessMsgLog));
                    token = response.body().getToken();
                    postEvent(new EventRequest(env, getString(R.string.eventRegister), getString(R.string.eventRegisterDesc)));
                    Intent intent = new Intent(RegisterActivity.this, SensorActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                } else {
                    Log.e(getString(R.string.registerTagLog), getString(R.string.registerErrorMsgLog));
                    try {
                        JSONObject jsonResponse = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jsonResponse.getString(getString(R.string.errorResponseMsgKey)), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(getString(R.string.exception), e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e(getString(R.string.registerTagLog), t.getMessage());
            }
        });
    }

    private void setupViews() {
        mail = (TextView) findViewById(R.id.registerMail);
        name = (TextView) findViewById(R.id.name);
        pass = (TextView) findViewById(R.id.registerPass);
        lastname = (TextView) findViewById(R.id.lastname);
        dni = (TextView) findViewById(R.id.dni);
        commission = (TextView) findViewById(R.id.commission);
        submitBttn = (Button) findViewById(R.id.submitBttn);
        submitBttn.setOnClickListener(buttonsListener);
    }

    private void loadFromIntent() {
        bundle = getIntent().getExtras();
        mail.setText(bundle.getString("mail"));
    }

    private void postEvent(EventRequest request) {
        Call<EventResponse> call = eventService.event("Bearer" + token, request);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(getString(R.string.eventTagLog), getString(R.string.eventSuccessMsgLog));
                    Toast.makeText(getApplicationContext(), getString(R.string.eventSuccessMsg), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(getString(R.string.eventTagLog), getString(R.string.eventErrorMsgLog));
                    try {
                        JSONObject jsonResponse = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jsonResponse.getString(getString(R.string.errorResponseMsgKey)), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(getString(R.string.exception), e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e(getString(R.string.eventTagLog), t.getMessage());
            }
        });
    }
}