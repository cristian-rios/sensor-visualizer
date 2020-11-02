package com.example.sensorregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private Bundle bundle;
    private TextView mail, pass, name, lastname, dni, commission;
    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        loadFromIntent();
    }

    public View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.signUpButton:
                    break;
            }
        }
    };

    private void register() {

    }

    private void setupViews() {
        mail = (TextView) findViewById(R.id.registerMail);
        name = (TextView) findViewById(R.id.name);
        pass = (TextView) findViewById(R.id.registerPass);
        lastname = (TextView) findViewById(R.id.lastname);
        dni = (TextView) findViewById(R.id.dni);
        commission = (TextView) findViewById(R.id.commission);
        signUpButton = (Button) findViewById(R.id.signUpButton);
    }

    private void loadFromIntent() {
        bundle = getIntent().getExtras();
        mail.setText(bundle.getString("mail"));
    }
}