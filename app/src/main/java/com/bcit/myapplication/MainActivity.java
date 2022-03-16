package com.bcit.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_employer = findViewById(R.id.button_main_employer);
        Button button_employee = findViewById(R.id.button_main_employee);

        button_employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, button_employer.getText());
                startActivity(intent);
            }
        });

        button_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, button_employee.getText());
                startActivity(intent);
            }
        });
    }
}