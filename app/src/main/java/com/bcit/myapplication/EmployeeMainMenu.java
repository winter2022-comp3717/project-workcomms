package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EmployeeMainMenu extends AppCompatActivity implements View.OnClickListener {
    private ImageButton logout;
    private Button post_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private ArrayList<PostModel> posts;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main_menu);
        logout = (ImageButton) findViewById(R.id.logout_btn_employee);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        logout.setOnClickListener(this);
        firebaseUser = firebaseAuth.getCurrentUser();
        TextView message = findViewById(R.id.label_name_employee);
        setUserTextView(db, message);
        posts = new ArrayList<>();
        queryPosts();

    }

    @Override
    public void onClick(View view) {
        Intent loginActivity = new Intent(EmployeeMainMenu.this, MainActivity.class);
        switch (view.getId()){
            case R.id.logout_btn_employee:
                firebaseAuth.signOut();
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginActivity);
                return;
        }
    }

    /**
     * Set welcome message textView to the current logged in user.
     *
     * @param db FireBaseFirestore instance
     * @param message a TextView
     */
    private void setUserTextView(FirebaseFirestore db, TextView message) {
        db.collection("Users")
                .whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                message.setText(document.getData().get("name").toString());
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void queryPosts(BottomNavigationItemView navigationItemView) {
        navigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationItemView.requestFocus();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.post_fragment_container_employee, PostRecyclerFragment.newInstance(posts));
                ft.commit();
            }
        });

    }
}