package com.bcit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

        View someView = findViewById(R.id.label_name_employee);
        View root = someView.getRootView();
        root.setBackgroundResource(R.color.light_blue);

        logout = (ImageButton) findViewById(R.id.logout_btn_employee);
        logout.setBackgroundResource(R.color.light_blue);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        logout.setOnClickListener(this);
        firebaseUser = firebaseAuth.getCurrentUser();
        TextView message = findViewById(R.id.label_name_employee);
        setUserTextView(db, message);
        posts = new ArrayList<>();
        BottomNavigationItemView chatBtn = (BottomNavigationItemView) findViewById(R.id.addToGroupChat);
        BottomNavigationItemView notices = (BottomNavigationItemView) findViewById(R.id.notice);
        BottomNavigationItemView chatGroup = (BottomNavigationItemView) findViewById(R.id.groupChat);
        queryPosts(notices);
        queryGroupChat(chatGroup);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatGroup.performClick();
                post();
            }
        });
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
                posts.clear();
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

    public void queryGroupChat(BottomNavigationItemView bottomNavigationItemView) {
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationItemView.requestFocus();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.post_fragment_container_employee, GroupChatFragment.newInstance(posts));
                ft.commit();
            }
        });
    }

    public void post(){

        final String[] input_text = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeMainMenu.this, R.style.AlertDialogStyle);
                builder.setTitle("Add a chat");


                final EditText input = new EditText(EmployeeMainMenu.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input_text[0] = input.getText().toString();
                        db.collection("Users").whereEqualTo("email", firebaseUser.getEmail()).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                                String companyID = documentSnapshot.getData().get("companyID").toString();
                                                String groupID = documentSnapshot.getData().get("groupID").toString();
                                                String posterName = documentSnapshot.getData().get("name").toString();
                                                String senderID = documentSnapshot.getId();
                                                long dateTime = System.currentTimeMillis();
                                                PostModel postModel = new PostModel(groupID, posterName, senderID, dateTime, input_text[0]);
                                                db.collection("Companies")
                                                        .document(companyID)
                                                        .collection("Posts")
                                                        .add(postModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
}