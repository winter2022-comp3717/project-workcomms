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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public PostRecyclerAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    // TODO: Rename and change types of parameters
    private ArrayList<PostModel> mParam1;

    public GroupChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GroupChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupChatFragment newInstance(ArrayList<PostModel> param1) {
        GroupChatFragment fragment = new GroupChatFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_group_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mParam1 = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        RecyclerView recyclerView = view.findViewById(R.id.group_chat_recycler_view);
        adapter = new PostRecyclerAdapter(mParam1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        helper();

        super.onViewCreated(view, savedInstanceState);
    }

    private void helper(){
        db.collection("Users")
                .whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                String companyID = documentSnapshot.getData().get("companyID").toString();
                                String groupID = documentSnapshot.getData().get("groupID").toString();
                                db.collection("Companies")
                                        .document(companyID).collection("Posts")
                                        .whereEqualTo("companyID", groupID)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                if (error != null){
                                                    Log.e("Firestore Error", error.getMessage());
                                                }
                                                for (DocumentChange dc : value.getDocumentChanges()){
                                                    if (dc.getType() == DocumentChange.Type.ADDED){
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
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

                            }
                        }
                    }
                });

    }


    private void EventListenerOnPostsAdded(String companyID) {
        db.collection("Posts")
                .whereEqualTo("companyID", companyID)
                .orderBy("dateTime", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
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
                        adapter.notifyDataSetChanged();
                    }

                });
    }
}