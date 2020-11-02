package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Button loginBttn;
    private TextView registerText;
    private TextView mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = (TextView) findViewById(R.id.mail);
        loginBttn = (Button) findViewById(R.id.loginBttn);
        loginBttn.setOnClickListener(buttonsListener);
        registerText = (TextView) findViewById(R.id.signUpButton);
        registerText.setOnClickListener(buttonsListener);
}

    public View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){
                case R.id.loginBttn:
                    break;
                case R.id.signUpButton:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.putExtra("mail", mail.getText().toString());
                    startActivity(intent);
                    break;

            }
        }
    };
}