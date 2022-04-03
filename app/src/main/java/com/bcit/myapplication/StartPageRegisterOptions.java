package com.bcit.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        View someView = findViewById(R.id.register_company_btn);
        View root = someView.getRootView();
        root.setBackgroundResource(R.drawable.main_background);
        goToRegisterActivity();
        goToRegisterCompanyActivity();
        goToMainActivity();
    }

    /**
     * Go to the register user activity by clicking the register user button.
     */
    public void goToRegisterActivity(){
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        Button registerUser = (Button) findViewById(R.id.register_user_btn);
        registerUser.setBackgroundColor(getResources().getColor(R.color.dark_orange));
        registerUser.setOnClickListener(view -> startActivity(registerActivity));
    }

    /**
     * Go to the register company activity by clicking the register company button.
     */
    public void goToRegisterCompanyActivity(){
        Intent registerCompanyActivity = new Intent(this, RegisterCompany.class);
        Button registerCompany = (Button) findViewById(R.id.register_company_btn);
        registerCompany.setBackgroundColor(getResources().getColor(R.color.orange));
        registerCompany.setOnClickListener(view -> startActivity(registerCompanyActivity));
    }

    public void goToMainActivity() {
        Button login = (Button) findViewById(R.id.button_login_landing);
        login.setBackgroundColor(getResources().getColor(R.color.blue));
    }
}