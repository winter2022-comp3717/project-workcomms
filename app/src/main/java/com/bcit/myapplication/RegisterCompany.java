package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterCompany extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);
        db = FirebaseFirestore.getInstance();
        registerCompany();

    }

    public void registerCompany(){
        Button registerCompany = (Button) findViewById(R.id.button_register_company_register_activity);
        EditText nameOfTheCompany = (EditText) findViewById(R.id.editText_company_name);
        EditText licenceNumber = (EditText) findViewById(R.id.editText_license);
        EditText location = (EditText) findViewById(R.id.editText_location);

        Intent registerOptionsActivity = new Intent(this, StartPageRegisterOptions.class);

        CollectionReference companyDB = db.collection("Companies");

        registerCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Company company = new Company(nameOfTheCompany.getText().toString(),
                        licenceNumber.getText().toString(), location.getText().toString());
                companyDB.add(company).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Success");
                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterCompany.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Company Registered");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            startActivity(registerOptionsActivity);
                                        }
                                    });
                            alertDialog.show();

                        } else {
                            Log.d("TAG", "Success");
                        }
                    }
                });

            }
        });
    }
}