package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EmployerMainMenu extends AppCompatActivity implements View.OnClickListener {
    private ImageButton logout;
    private Button post_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private ArrayList<PostModel> posts;
    private User user;

    /**
     * Initialize the essential components on this activity.
     *
     * @param savedInstanceState a Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_employer);
        logout = (ImageButton) findViewById(R.id.logout_main_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        logout.setOnClickListener(this);
        firebaseUser = firebaseAuth.getCurrentUser();
        TextView message = findViewById(R.id.message_main_menu);
        setUserTextView(db, message);
        DocumentReference docRef = db.collection("cities").document();
        posts = new ArrayList<PostModel>();
        queryPosts();
        BottomNavigationItemView post_btn = (BottomNavigationItemView) findViewById(R.id.add_post);
        post(post_btn);
        navbarTest();
    }

    private void queryPosts() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainerView_main_menu, PostRecyclerFragment.newInstance(posts));
        ft.commit();
    }

    /**
     * Query from the user type information from database and go to the corresponding
     * fragment with the right bottom navbar.
     */
    private void navbarTest() {
        db.collection("Users")
                .whereEqualTo("uid", firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String name = doc.getData().get("name").toString();
                                String companyID = doc.getData().get("companyID").toString();
                                String email = doc.getData().get("email").toString();
                                String uid = doc.getData().get("uid").toString();
                                String usertype = doc.getData().get("userType").toString();
                                user = new User(
                                        name,
                                        usertype,
                                        uid,
                                        email,
                                        companyID);
                            }
//                            if (user.getUserType().equals("Employee")) {
//                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//                                FragmentContainerView fragmentContainerView = findViewById(R.id.fragmentContainerView_main_menu);
//                                FragmentContainerViewModel fragmentContainerViewModel = new FragmentContainerViewModel(fragmentContainerView);
//
//
//                                ft.replace(R.id.fragmentContainerView_bot_nav_main_menu, EmployeeBotNavFragment.newInstance(fragmentContainerViewModel));
//                                ft.commit();
//
//                                EmployeeBotNavFragment employeeBotNavFragment = new EmployeeBotNavFragment();
//
//                                MenuItem menuItem = employeeBotNavFragment.getGroup();
//
//
//                            } else if (user.getUserType().equals("Employer")) {
//                                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                                ft1.replace(R.id.fragmentContainerView_bot_nav_main_menu, EmployerBotNavBarFragment.newInstance("lala", "jashan"));
//                                ft1.commit();
//                            }
                        }
                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Set welcome message textView to the current logged in user.
     *
     * @param db FireBaseFirestore instance
     * @param message a TextView
     */
    private void setUserTextView(FirebaseFirestore db, TextView message) {
        db.collection("Users")
                .whereEqualTo("uid", firebaseUser.getUid())
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

    /**
     * Sign out the user.
     *
     * @param view a View
     */
    @Override
    public void onClick(View view) {
        Intent loginActivity = new Intent(EmployerMainMenu.this, MainActivity.class);
        switch (view.getId()){
            case R.id.logout_main_menu:
                firebaseAuth.signOut();
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginActivity);
                return;
        }
    }

    public void post(BottomNavigationItemView post_btn){
        post_btn.setOnClickListener(new View.OnClickListener() {
            String input_text  = "";
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployerMainMenu.this);
                builder.setTitle("Make a post");
                final EditText input = new EditText(EmployerMainMenu.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("POST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input_text = input.getText().toString();
                        db.collection("Users").whereEqualTo("email", firebaseUser.getEmail()).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                                String companyID = documentSnapshot.getData().get("companyID").toString();
                                                String posterName = documentSnapshot.getData().get("name").toString();
                                                String senderID = documentSnapshot.getId();
                                                long dateTime = System.currentTimeMillis();
                                                PostModel postModel = new PostModel(companyID, posterName, senderID, dateTime, input_text);
                                                db.collection("Posts").add(postModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()){
                                                            Log.d("Tag", "Added post");


                                                        }
                                                        else {
                                                            Log.d("Tag", "Failed to add post");
                                                        }
                                                    }
                                                });
                                            }




                                        }
                                    }
                                });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }

}

































