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
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Creates the RegisterCompany Activity.
 */
public class RegisterCompany extends AppCompatActivity {
    private FirebaseFirestore db;

    /**
     * Initialize the essential components on this activity.
     *
     * @param savedInstanceState a Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);
        db = FirebaseFirestore.getInstance();
        registerCompany();

    }

    /**
     * Updates the company info to the firebase.
     */
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
                boolean validateInput = validateInput(nameOfTheCompany, licenceNumber, location);
                if (validateInput) {
                    companyDB.whereEqualTo("name", nameOfTheCompany.getText().toString())
                            .whereEqualTo("location", location.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (!task.getResult().isEmpty()) {
                                        AlertDialog alertNotUnique = companyNotUniqueAlert();
                                        alertNotUnique.show();
                                    } else {
                                        createCompanyDoc(
                                                nameOfTheCompany.getText().toString(),
                                                licenceNumber.getText().toString(),
                                                location.getText().toString(),
                                                companyDB,
                                                registerOptionsActivity
                                        );
                                    }
                                }
                            });
                }
            }
        });
    }

    private void createCompanyDoc(String nameOfTheCompany,
                                  String licenceNumber,
                                  String location,
                                  CollectionReference companyDB,
                                  Intent registerOptionsActivity) {

        Company company = new Company(nameOfTheCompany, licenceNumber, location);
        companyDB.add(company).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "Success");
                    AlertDialog alertDialog = successfulRegisterAlert(
                            registerOptionsActivity
                    );
                    alertDialog.show();

                } else {
                    Log.d("TAG", "Success");
                }
            }
        });
    }

    private boolean validateInput(EditText first, EditText second, EditText third) {
        String firstStr = first.getText().toString();
        String secondStr = second.getText().toString();
        String thirdStr = third.getText().toString();

        AlertDialog alert = new AlertDialog.Builder(RegisterCompany.this).create();
        alert.setTitle("Alert");
        alert.setMessage("Cannot have empty fields");
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        if (firstStr.equals("") || secondStr.equals("") || thirdStr.equals("")) {
            alert.show();
            return false;
        }
        return true;
    }

    private AlertDialog successfulRegisterAlert(Intent registerOptionsActivity) {
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
        return alertDialog;
    }

    private AlertDialog companyNotUniqueAlert() {
        AlertDialog alertAlreadyRegistered = new AlertDialog.Builder(RegisterCompany.this).create();
        alertAlreadyRegistered.setTitle("Alert");
        alertAlreadyRegistered.setMessage("Company already exists");
        alertAlreadyRegistered.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return alertAlreadyRegistered;
    }
}