package com.bcit.myapplication;

import static com.bcit.myapplication.R.id.button_register;
import static com.bcit.myapplication.R.id.textView_login_banner;
import static com.bcit.myapplication.R.id.textView_register_company_banner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Button register = (Button) findViewById(button_register);
        TextView login_banner = (TextView) findViewById(textView_login_banner);
        TextView register_company_banner = (TextView) findViewById(textView_register_company_banner);
        register_company_banner.setOnClickListener(this);
        register.setOnClickListener(this);
        login_banner.setOnClickListener(this);

        spinnerSetup();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case textView_login_banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case button_register:
                registerUser();
                break;
            case R.id.textView_register_company_banner:
                startActivity(new Intent(this, RegisterCompany.class));
                break;
        }
    }

    private void spinnerSetup(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_company_registration);
        CollectionReference companiesDB = db.collection("Companies");
        companiesDB.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<String> nameList = new ArrayList<>();
                List<String> locationList = new ArrayList<>();
                List<String> combinedList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                    nameList.add(String.valueOf(documentSnapshot.get("name")));
                    locationList.add(String.valueOf(documentSnapshot.get("location")));
                }

                for (int i = 0; i < nameList.size(); i++){
                    combinedList.add(nameList.get(i) + ", " + locationList.get(i));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        RegisterActivity.this, R.layout.my_dropdown_item,
                        combinedList);
                arrayAdapter.setDropDownViewResource(R.layout.item_spinner_list);
                spinner.setAdapter(arrayAdapter);

            } else {
                Log.d("TAG", "Failed to add to the spinner");
            }
        });
    }

    private void registerUser(){
        EditText name = (EditText) findViewById(R.id.edit_text_name_registration);
        EditText password = (EditText) findViewById(R.id.edit_text_password_registration);
        String userType = "Employer";
        EditText email = (EditText) findViewById(R.id.edit_text_email_registration);
        String name_field = name.getText().toString();
        String password_field = password.getText().toString();
        String email_field = email.getText().toString();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_registration);
        Spinner spinnerCompany = (Spinner) findViewById(R.id.spinner_company_registration);
        String companyName = spinnerCompany.getSelectedItem().toString();
        CollectionReference UsersDb = db.collection("Users");
        Intent mainMenu = new Intent(this, EmployerMainMenu.class);


        if(spinnerCompany.getAdapter().getCount() == 0){
            ((TextView)spinnerCompany.getSelectedView()).setError("There are 0 workplaces " +
                    "available right now. Try after some time!");
            spinnerCompany.requestFocus();
            return;
        }
        if(spinnerCompany.getSelectedItem() == null && spinnerCompany.getAdapter().getCount() != 0){
            ((TextView)spinnerCompany.getSelectedView()).setError("select a Workplace");
            spinnerCompany.requestFocus();
            return;
        }
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

//        Creating the user with email and password for firebaseAuth -> Authentication
        firebaseAuth.createUserWithEmailAndPassword(email_field, password_field)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

//                        Signing in the app after registration
                        firebaseAuth.signInWithEmailAndPassword(email_field, password_field)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){

//                                        getting the reference to the Companies Collection
                                        CollectionReference companyDB = db.collection("Companies");

//                                        splitting the nameLocation in the spinner since the
//                                        spinner shows the combo of name and the location of
//                                        the company whereas the database stores name and the
//                                        location separately.
                                        String[] nameLocationList = companyName.split(",");

//                                        getting the document from the name and location
                                        companyDB.whereEqualTo("name", nameLocationList[0].trim())
                                                .whereEqualTo("location", nameLocationList[1].trim())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()){

//                                                            creating the user now since we have
//                                                            the document Id of the company
                                                            User user = new User(name_field, userType,
                                                                    firebaseAuth.getCurrentUser().getUid(), email_field,
                                                                    task.getResult().getDocuments().get(0).getId());
                                                            UsersDb.add(user)
                                                                    .addOnSuccessListener(documentReference -> {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        startActivity(mainMenu);
                                                                    }).addOnFailureListener(e ->
                                                                    progressBar.setVisibility(View.GONE));
                                                        }
                                                        else{
                                                            Log.d("TAG", "Cannot find the ID for this company");
                                                        }
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "Could not sign in");
                                    }
                                });
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
