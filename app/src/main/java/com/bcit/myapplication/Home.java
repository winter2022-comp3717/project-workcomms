package com.bcit.myapplication;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class extends application and hence validates the state of the user ie. checks if the
 * user is logged in.
 */
public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (firebaseUser != null) {
            db.collection("Users")
                    .whereEqualTo("email", firebaseUser.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    if (documentSnapshot.getData().get("userType").equals("Employer")){
                                        startActivity(new Intent(Home.this, EmployerMainMenu.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    }
                                    else {
                                        startActivity(new Intent(Home.this, EmployeeMainMenu.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    }
                                }
                            }
                            else {
                                Log.d("Error", "task failed");
                            }
                        }
                    });
        } else {
            startActivity(new Intent(this, StartPageRegisterOptions.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
