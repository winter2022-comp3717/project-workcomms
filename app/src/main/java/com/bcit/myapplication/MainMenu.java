package com.bcit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Button logout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        logout = (Button) findViewById(R.id.logout_main_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        logout.setOnClickListener(this);
        firebaseUser = firebaseAuth.getCurrentUser();
        TextView message = findViewById(R.id.message_main_menu);
        message.setText(firebaseUser.getEmail());
    }

    @Override
    public void onClick(View view) {
        Intent loginActivity = new Intent(MainMenu.this, MainActivity.class);
        switch (view.getId()){
            case R.id.logout_main_menu:
                firebaseAuth.signOut();
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginActivity);
                return;
        }
    }
}