package com.example.androchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.adapter.ChatListAdapter;
import com.example.androchat.model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";
    ArrayList<User> myUsers;
    ArrayList<String> mAllFriends;
    ArrayList<String> mUserName;
    ArrayList<String> mName;
    ArrayList<String> mProfile_Url;
    ArrayList<String> mUserId;
    private DatabaseReference mFirebaseRef;
    public FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private DatabaseReference mCurrentUserRef;
    private String currentUserId;
    private DatabaseReference mMessageDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        myUsers = new ArrayList<>();
        mUserId = new ArrayList<>();
        mUserName = new ArrayList<>();
        mName = new ArrayList<>();
        mProfile_Url = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else {
            currentUser = mAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance();
            mFirebaseRef = mDatabase.getReference();
            currentUserId = currentUser.getUid();
            readFriendsDetailsFromDb();

        }

        mAllFriends = new ArrayList<>();
        fab = findViewById(R.id.floatingActionButton);
        fab.setExpanded(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatListActivity.this, SearchUserActivity.class));
            }
        });

    }

    private void initializeView() {
        mRecyclerView = findViewById(R.id.main_chat_recyclerView);
        ChatListAdapter adapter = new ChatListAdapter(this,mUserName,mName,mProfile_Url,mUserId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_list_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                signOut();
                return true;
            case R.id.account_settings:
                startActivity(new Intent(this, SettingsActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ChatListActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                        Intent signOutIntent = new Intent(ChatListActivity.this, LoginActivity.class);
                        startActivity(signOutIntent);
                    }
                });
    }

    private void readAllFriendsFromDB(){
        mCurrentUserRef = mDatabase.getReference()
                .child(getString(R.string.dbnode_users)).child(currentUserId)
                .child("friends");
    mCurrentUserRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String friend = snapshot.getKey();
                mAllFriends.add(friend);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

    private void readFriendsDetailsFromDb(){
        readAllFriendsFromDB();
        mMessageDatabaseRef = mDatabase.getReference().child(getString(R.string.dbnode_users));
        mMessageDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int friendsSize = mAllFriends.size();
                int counter = 0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String name = snapshot.child("name").getValue(String.class);
                    String userId = snapshot.child("user_id").getValue(String.class);
                    String user_name = snapshot.child("username").getValue(String.class);
                    String profile_image = snapshot.child("profile_image").getValue(String.class);
                    if (mAllFriends.get(counter).equals(userId)) {
                        mName.add(name);
                        mUserId.add(userId);
                        mProfile_Url.add(profile_image);
                        mUserName.add(user_name);
                        counter++;
                    }
                    if (counter == friendsSize){
                        break;
                    }
                }
                initializeView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
