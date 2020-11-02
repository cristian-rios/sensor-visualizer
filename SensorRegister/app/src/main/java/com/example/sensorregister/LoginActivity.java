package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensorregister.requestUtilities.login.LoginRequest;
import com.example.sensorregister.requestUtilities.login.LoginResponse;
import com.example.sensorregister.requestUtilities.services.RequestService;

import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Button loginBttn;
    private TextView registerText;
    private TextView mail;
    private TextView password;
    private Retrofit retrofit;
    private RequestService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupRetrofit();
    }

    public View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginBttn:
                    login();
                    break;
                case R.id.signUpButton:
                    goToRegisterActivity();
                    break;

            }
        }
    };

    private boolean areFieldsValid() {
        if (!mail.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty())
            return true;
        Toast.makeText(getApplicationContext(), getString(R.string.invalidForm), Toast.LENGTH_SHORT).show();
        return false;
    }

    private LoginRequest populateRequest() {
        return new LoginRequest(
                mail.getText().toString(),
                password.getText().toString()
        );
    }

    private void login() {
        if (!areFieldsValid()) return;
        LoginRequest request = populateRequest();
        Toast.makeText(getApplicationContext(), getString(R.string.loginSubmitMsg), Toast.LENGTH_SHORT).show();
        Call<LoginResponse> call = loginService.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.loginSuccessMsg), Toast.LENGTH_SHORT).show();
                    Log.i(getString(R.string.loginTagLog), getString(R.string.loginSuccessMsgLog));
                    Intent intent = new Intent(LoginActivity.this, SensorActivity.class);
                    startActivity(intent);
                } else {
                    Log.e(getString(R.string.loginTagLog), getString(R.string.loginErrorMsgLog));
                    try {
                        JSONObject jsonResponse = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jsonResponse.getString(getString(R.string.errorResponseMsgKey)), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(getString(R.string.exception), e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(getString(R.string.loginTagLog), t.getMessage());
            }
        });
    }

    private void goToRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("mail", mail.getText().toString());
        startActivity(intent);
    }

    private void setupViews() {
        mail = (TextView) findViewById(R.id.mail);
        loginBttn = (Button) findViewById(R.id.loginBttn);
        loginBttn.setOnClickListener(buttonsListener);
        registerText = (TextView) findViewById(R.id.signUpButton);
        registerText.setOnClickListener(buttonsListener);
        password = (TextView) findViewById(R.id.pass);
    }

    private void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getString(R.string.uri))
                .build();

        loginService = retrofit.create(RequestService.class);
    }

}