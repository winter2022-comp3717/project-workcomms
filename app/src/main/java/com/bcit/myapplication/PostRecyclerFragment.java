package com.bcit.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostRecyclerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private PostRecyclerAdapter adapter;
    private FirebaseFirestore db;

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
        db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_post_viewer);
        adapter = new PostRecyclerAdapter(mParam1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        EventListenerOnPostsAdded();
        super.onViewCreated(view, savedInstanceState);
    }

    private void EventListenerOnPostsAdded() {
        db.collection("Posts").orderBy("dateTime", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    QueryDocumentSnapshot snapshot = dc.getDocument();
                                    String companyID = (String) snapshot.getData().get("CompanyID");
                                    String posterName = (String) snapshot.getData().get("PosterName");
                                    String senderId = (String) snapshot.getData().get("SenderID");
                                    long dateTime = (long) snapshot.getData().get("dateTime");
                                    String message = snapshot.getData().get("message").toString();
                                    PostModel newPost = new PostModel(
                                            companyID,
                                            posterName,
                                            senderId,
                                            dateTime, message);
                                    mParam1.add(newPost);
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }
}