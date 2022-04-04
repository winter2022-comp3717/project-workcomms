package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login_btn;
    private TextView register_banner;
    private EditText emailTextView, passwordTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    /**
     * Initialize the essential components on this activity.
     *
     * @param savedInstanceState a Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        login_btn = (Button) findViewById(R.id.button_login);
        register_banner = (TextView) findViewById(R.id.textView_register_banner);
        emailTextView = (EditText) findViewById(R.id.edit_text_email_login);
        passwordTextView = (EditText) findViewById(R.id.edit_text_password_login);
        login_btn.setOnClickListener(this);
        register_banner.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(this);
        register_banner.setOnClickListener(this);
        login_btn.setBackgroundColor(getResources().getColor(R.color.blue));
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

    /**
     * this function authenticates the login activity from the firebase firestore
     */
    private void login(){
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailTextView.setError("Please Enter the email!");
            return;
        }
        if(TextUtils.isEmpty(password)){
            passwordTextView.setError("Please enter the password!");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // for existing users
                        if(task.isSuccessful()){
                            initiateActivitySelector(email);
                        } else {
                            // for new employee.
                            createNewEmployee(email, password);
                            return;
                        }
                    }
                });
    }

    /**
     * Create new Employee to firebaseAuth and start Employee intent.
     *
     * @param email a String
     * @param password a String
     */
    private void createNewEmployee(String email, String password) {
        db.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                firebaseAuth.signInWithEmailAndPassword(email, password);
                                                startActivity(new Intent(
                                                        MainActivity.this,
                                                        EmployeeMainMenu.class
                                                ));
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    /**
     * Start activity for appropriate user type upon successful login of email account.
     *
     * @param email a String representing the user's email
     */
    private void initiateActivitySelector(String email) {
        Intent employerIntent = new Intent(this, EmployerMainMenu.class);
        Intent employeeIntent = new Intent(this, EmployeeMainMenu.class);
        db.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot: task.getResult()) {
                                String userType = snapshot.get("userType").toString();
                                if (userType.equals("Employee")) {
                                    startActivity(employeeIntent);
                                } else if (userType.equals("Employer")) {
                                    startActivity(employerIntent);
                                } else {
                                    Log.e("ERR", "Error with user type");
                                }
                            }
                        }
                    }
                });
    }
}