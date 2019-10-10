package com.example.androchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private final int PICTURE_RESULT = 432;
    ImageView imageView;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference imageRef;
    public FirebaseDatabase mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    //Firebase
    FirebaseStorage mStorage = FirebaseStorage.getInstance();
    StorageReference mStorageRef = mStorage.getReference().child("profile_pictures");
    private Uri imageUri;
    private ValueEventListener eventListener;
    private DatabaseReference mAllUserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        mAllUserRef = mDatabase.getReference().child(getString(R.string.dbnode_all_users));
        mDatabaseRef = mDatabase.getReference().child(getString(R.string.dbnode_users)).child(mAuth.getUid());
        imageRef = mDatabaseRef.child("profile_image");

        final EditText userName =  findViewById(R.id.activity_settings_username_et);
        imageView =  findViewById(R.id.activity_settings_iv);
        Button submitButton = findViewById(R.id.activity_settings_submit_btn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null) {
                    sendProfilePicToDatabase(imageUri);
                }
                String userNameValue = userName.getText().toString().toLowerCase();

                    mDatabaseRef.child("username").setValue(userNameValue);
                   // mAllUserRef.child(userNameValue).setValue(mAuth.getUid());
            }
        });

        displayImage();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICTURE_RESULT);
            }
        });
    }

    private void displayImage() {
        eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    showImage(dataSnapshot.getValue(String.class));
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        imageRef.addValueEventListener(eventListener);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK){
            if(data != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);


            }
        }
    }
    public void showImage(String url){
        if(url != null && !url.isEmpty()){
            Picasso.get()
                    .load(url)
                    .resize(160,160)
                    .centerCrop()
                    .into(imageView);
        }
    }

    public void sendProfilePicToDatabase(Uri imageUri){
        StorageReference profilePicRef = mStorageRef.child(currentUser.getUid());
        profilePicRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SettingsActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageRef.setValue(uri.toString());
                        Log.d(TAG, uri.toString());
                    }
                });

            }
        });
    }

    @Override
    protected void onStop() {
        imageRef.removeEventListener(eventListener);
        super.onStop();
    }
}
