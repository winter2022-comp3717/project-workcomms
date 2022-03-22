package com.bcit.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartPageRegisterOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page_register_options);
        goToRegisterActivity();
        goToRegisterCompanyActivity();


    }

    public void goToRegisterActivity(){
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        Button registerUser = (Button) findViewById(R.id.register_user_btn);
        registerUser.setOnClickListener(view -> startActivity(registerActivity));
    }

    public void goToRegisterCompanyActivity(){
        Intent registerCompanyActivity = new Intent(this, RegisterCompany.class);
        Button registerCompany = (Button) findViewById(R.id.register_company_btn);
        registerCompany.setOnClickListener(view -> startActivity(registerCompanyActivity));

    }
}