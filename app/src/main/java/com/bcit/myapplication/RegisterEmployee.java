package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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
        setupSpinner();
    }

    private void registerUser(){
        EditText name = (EditText) findViewById(R.id.edittext_name_employee);
        String userType = "Employee";
        EditText email = (EditText) findViewById(R.id.edittext_email_employee);
        String name_field = name.getText().toString();
        String email_field = email.getText().toString();
        RadioGroup designation = (RadioGroup) findViewById(R.id.radio_group_employee);
        int selected_radio_btn = designation.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selected_radio_btn);
        String designation_field = radioButton.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.spinner_choose_group);
        String chosenGroup = spinner.getSelectedItem().toString();


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

        db.collection("Users")
                .whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                String companyID = documentSnapshot.getData().get("companyID").toString();

                                db.collection("Companies").document(companyID).collection("Groups")
                                        .whereEqualTo("name", chosenGroup)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                        String groupID = documentSnapshot1.getId();
                                                        Employee employee = new Employee(name_field,
                                                                userType, "", email_field,
                                                                documentSnapshot.getData().get("companyID").toString(),
                                                                designation_field, groupID);
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
                                                        db.collection("Companies").document(companyID)
                                                                .collection("Groups").document(groupID)
                                                                .update("memberID", FieldValue.arrayUnion(email_field));
                                                    }
                                                }
                                            }
                                        });


                            }
                        }
                    }
                });
    }

    private void setupSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_choose_group);
        db.collection("Users")
                .whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                String companyID = documentSnapshot.getData().get("companyID").toString();
                                CollectionReference groups = db.collection("Companies")
                                        .document(companyID).collection("Groups");
                                groups.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        List<String> groupName = new ArrayList<>();
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot1:task.getResult()){
                                                groupName.add(String.valueOf(documentSnapshot1.get("name")));
                                            }
                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterEmployee.this,
                                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, groupName);
                                            spinner.setAdapter(arrayAdapter);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }
}