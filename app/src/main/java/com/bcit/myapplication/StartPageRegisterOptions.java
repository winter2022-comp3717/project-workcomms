package com.bcit.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Creates the StartPageRegisterOptions activity.
 */
public class StartPageRegisterOptions extends AppCompatActivity {

    /**
     * Creates the essential component in this activity.
     *
     * @param savedInstanceState a Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page_register_options);
        goToRegisterActivity();
        goToRegisterCompanyActivity();


    }

    /**
     * Go to the register user activity by clicking the register user button.
     */
    public void goToRegisterActivity(){
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        Button registerUser = (Button) findViewById(R.id.register_user_btn);
        registerUser.setOnClickListener(view -> startActivity(registerActivity));
    }

    /**
     * Go to the register company activity by clicking the register company button.
     */
    public void goToRegisterCompanyActivity(){
        Intent registerCompanyActivity = new Intent(this, RegisterCompany.class);
        Button registerCompany = (Button) findViewById(R.id.register_company_btn);
        registerCompany.setOnClickListener(view -> startActivity(registerCompanyActivity));

    }
}