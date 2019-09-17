package com.example.androchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.adapter.AllUserAdapter;
import com.example.androchat.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUsers extends AppCompatActivity {
    private FirebaseDatabase mDb;
    private DatabaseReference mDbRef;
    private static final String TAG = "AllUsers";
    private ArrayList<User> mAllUsers;
    private RecyclerView mRecyclerView;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        mAllUsers = new ArrayList<>();
        mDb = FirebaseDatabase.getInstance();
        mDbRef = mDb.getReference().child(getString(R.string.dbnode_users));

        initDisplay();

        getAllUserData();
    }

    private void initDisplay() {

        mRecyclerView = findViewById(R.id.all_user_recycler_view);
        final AllUserAdapter adapter = new AllUserAdapter(mAllUsers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    private void getAllUserData() {

        mValueEventListener = new ValueEventListener() {

            User singleUser = new User();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    singleUser = child.getValue(User.class);
                    mAllUsers.add(singleUser);
                    mRecyclerView.swapAdapter(new AllUserAdapter(mAllUsers),false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDbRef.addValueEventListener(mValueEventListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mDbRef.removeEventListener(mValueEventListener);
    }
}
