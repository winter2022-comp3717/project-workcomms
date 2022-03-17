package com.bcit.myapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login_btn;
    private TextView register_banner;
    private EditText emailTextView, passwordTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_btn = (Button) findViewById(R.id.button_login);
        register_banner = (TextView) findViewById(R.id.textView_register_banner);
        emailTextView = (EditText) findViewById(R.id.edit_text_email_login);
        passwordTextView = (EditText) findViewById(R.id.edit_text_password_login);
        login_btn.setOnClickListener(this);
        register_banner.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(this);
        register_banner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:
                login();
                return;
            case R.id.textView_register_banner:
                startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private void login(){
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        Intent mainMenu = new Intent(this, MainMenu.class);

        if(TextUtils.isEmpty(email)){
            emailTextView.setError("Please Enter the email!");
            return;
        }
        if(TextUtils.isEmpty(password)){
            passwordTextView.setError("Please enter the password!");
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(mainMenu);
                        } else {
                            return;
                        }
                    }
                });
    }
}