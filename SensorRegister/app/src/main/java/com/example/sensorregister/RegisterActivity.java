package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private Bundle bundle;
    private TextView mail, pass, name, lastname, dni, commission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        loadFromIntent();
    }

    private void setupViews() {
        mail = (TextView) findViewById(R.id.registerMail);
        name = (TextView) findViewById(R.id.name);
        pass = (TextView) findViewById(R.id.registerPass);
        lastname = (TextView) findViewById(R.id.lastname);
        dni = (TextView) findViewById(R.id.dni);
        commission = (TextView) findViewById(R.id.commission);
    }

    private void loadFromIntent() {
        bundle = getIntent().getExtras();
        mail.setText(bundle.getString("mail"));
    }
}