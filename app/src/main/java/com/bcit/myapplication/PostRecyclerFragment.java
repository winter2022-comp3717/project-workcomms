package com.bcit.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostRecyclerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    public PostRecyclerAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ArrayList<PostModel> mParam1;

    public static PostRecyclerFragment newInstance(ArrayList<PostModel> param1) {
        PostRecyclerFragment fragment = new PostRecyclerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public PostRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (ArrayList<PostModel>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Create ArrayList for data storage of Recycler
        mParam1 = new ArrayList<>();

        //Obtain references to Firebase auto and database for querying
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Setup recycler
        RecyclerView recyclerView = view.findViewById(R.id.recycler_post_viewer);
        adapter = new PostRecyclerAdapter(mParam1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        helper();

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Helper function responsible for querying for the logged in user's Company ID.
     */
    private void helper(){
        db.collection("Users")
                .whereEqualTo("email", firebaseUser.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        EventListenerOnPostsAdded(doc.getData().get("companyID").toString());
                    }
                }
            }
        });
    }

    /**
     * Event listener responsible for checking if the Posts collection has been updated of
     * the user's Company Collection and notifies the Recyclers adapter that it needs to be rebuilt.
     *
     * @param companyID a String of the logged in user's company ID.
     */
    private void EventListenerOnPostsAdded(String companyID) {
        db.collection("Posts")
                .whereEqualTo("companyID", companyID)
                .orderBy("dateTime", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(
                            @Nullable QuerySnapshot value,
                            @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                QueryDocumentSnapshot snapshot = dc.getDocument();
                                String companyID = (String) snapshot.getData().get("companyID");
                                String posterName = (String) snapshot.getData().get("posterName");
                                String senderId = (String) snapshot.getData().get("senderID");
                                long dateTime = (long) snapshot.getData().get("dateTime");
                                String message = snapshot.getData().get("message").toString();
                                PostModel newPost = new PostModel(
                                        companyID,
                                        posterName,
                                        senderId,
                                        dateTime, message);
                                mParam1.add(0, newPost);
                            }
                        }
                        //Notify recycler adapter that Post collection has been updated
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}