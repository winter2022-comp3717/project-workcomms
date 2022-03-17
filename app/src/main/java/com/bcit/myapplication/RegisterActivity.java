package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Button register = (Button) findViewById(R.id.button_register);
        TextView login_banner = (TextView) findViewById(R.id.textView_login_banner);
        register.setOnClickListener(this);
        login_banner.setOnClickListener(this);
    }

    public String getUserType(RadioButton r1){
        if(r1.isChecked()){
            return "Employee";
        }
        return "Employer";
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.textView_login_banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button_register:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        EditText name = (EditText) findViewById(R.id.edit_text_name_registration);
        EditText password = (EditText) findViewById(R.id.edit_text_password_registration);
        String userType = getUserType((RadioButton) findViewById(R.id.radioButton_employee));
        EditText email = (EditText) findViewById(R.id.edit_text_email_registration);
        String name_field = name.getText().toString();
        String password_field = password.getText().toString();
        String usertype_field = userType;
        String email_field = email.getText().toString();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_registration);

        CollectionReference UsersDb = db.collection("Users");
        User user = new User(name_field, usertype_field, email_field);


        if(name_field.equals("")){
            name.setError("Please enter the name");
            name.requestFocus();
            return;
        }
        if (email_field.equals("")){
            email.setError("Please enter an email");
            email.requestFocus();
            return;
        }
        if(password_field.equals("") || password_field.length() < 8){
            password.setError("Please enter a valid password");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email_field, password_field)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UsersDb.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

}
