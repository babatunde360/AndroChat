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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";
    ArrayList<User> myUsers;
    private DatabaseReference mFirebaseRef;
    public FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseRef = mDatabase.getReference();
        myUsers = new ArrayList<>();
        checkSignedInUser();

        init();


    }

    private void checkSignedInUser(){
        if(currentUser == null){
            startActivity(new Intent(ChatListActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void init() {
        mRecyclerView = findViewById(R.id.main_chat_recyclerView);
        ChatListAdapter adapter = new ChatListAdapter(myUsers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        fab = findViewById(R.id.floatingActionButton);
        fab.setExpanded(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatListActivity.this, SearchUserActivity.class));
            }
        });
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

}
