package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterEmployee extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        Button registerEmployee = (Button) findViewById(R.id.register_employee_btn);
        registerEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        EditText name = (EditText) findViewById(R.id.edittext_name_employee);
        EditText password = (EditText) findViewById(R.id.edittext_password_employee);
        String userType = "Employee";
        EditText email = (EditText) findViewById(R.id.edittext_email_employee);
        String name_field = name.getText().toString();
        String password_field = password.getText().toString();
        String email_field = email.getText().toString();
        RadioGroup designation = (RadioGroup) findViewById(R.id.radio_group_employee);
        int selected_radio_btn = designation.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selected_radio_btn);
        String designation_field = radioButton.getText().toString();


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

//        firebaseAuth.createUserWithEmailAndPassword(email_field, password_field)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    }
//                });

        db.collection("Users")
                .whereEqualTo("uid", firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                User employee = new User(name_field,
                                        userType, "", email_field,
                                        documentSnapshot.getData().get("companyID").toString(),
                                        designation_field);
                                db.collection("Users").add(employee)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()){
                                                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterEmployee.this).create();
                                                    alertDialog.setTitle("Alert");
                                                    alertDialog.setMessage("Employee Registered");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                            new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.dismiss();
                                                            startActivity(new Intent(RegisterEmployee.this, EmployerMainMenu.class));
                                                        }
                                                    });
                                                    alertDialog.show();
                                                    Log.d("Tag", "onComplete: employee Added");
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }
}