package com.example.androchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androchat.model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 324;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        signIn();
        }

    private void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.app_logo)
                        .build(),
                RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG,"Result code :" + resultCode + " requestCode : " + requestCode);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                mFirebaseUser = mAuth.getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance();
                mDatabaseRef = mDatabase.getReference().child(getString(R.string.dbnode_users));
                final User newUser = new User();
                newUser.setAllValues(mFirebaseUser.getUid(),mFirebaseUser.getDisplayName(),"");
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(mAuth.getUid())){
                            Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                        }else{
                            mDatabaseRef.child(mAuth.getUid()).setValue(newUser);
                            startActivity(new Intent(LoginActivity.this,SettingsActivity.class));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(LoginActivity.this, ChatListActivity.class));
            } else {
                Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
