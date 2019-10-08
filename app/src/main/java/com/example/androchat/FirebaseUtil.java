package com.example.androchat;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {
    private static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseStorage mStorage;
    private static ChatListActivity caller;
    public static  LoginActivity loginActivityCaller;
    private  FirebaseUtil firebaseUtil;
    public static StorageReference mStorageRef;
    private static FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 324;
    private static FirebaseDatabase mDatabase;
    private static FirebaseUser mFirebaseUser;
    private static DatabaseReference mDatabaseRef;
    private  Context mContext;

    private FirebaseUtil() {
    }


    public void openFbReference(final Context context, final ChatListActivity callerActivity) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            caller = callerActivity;
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        //FirebaseUtil.signIn();
                        Toast.makeText(callerActivity.getBaseContext(), "Welcome back!",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        context.startActivity(new Intent(callerActivity, LoginActivity.class));
                    }
                }


            };
        }
    }


    }
