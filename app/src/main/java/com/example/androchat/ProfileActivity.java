package com.example.androchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    public static String EXTRA_FULL_NAME = "com.example.androchat.profileactivity.FULLNAME";
    public static String EXTRA_USERNAME = "com.example.androchat.profileactivity.USERNAME";
    public static String EXTRA_PROFILE_PIC = "com.example.androchat.profileactivity.PROFILE_PIC";
    public static String EXTRA_USER_ID = "com.example.androchat.profileactivity.USER_ID";

    private String mFullName_extra;
    private String mUsername_extra;
    private String mProfilePic_extra;
    private ImageView profilePic;
    private TextView fullName;
    private TextView username;
    private Button sendMessageBtn;
    private String mUserId_extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        populateDataWithIntentExtra();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        sendMessageBtn = findViewById(R.id.profile_activity_send_message_btn);

        assert currentUser != null;
        if(mUserId_extra.equals(currentUser.getUid())){
            sendMessageBtn.setVisibility(View.INVISIBLE);
        }

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(ProfileActivity.this,ChatActivity.class);
                chatIntent.putExtra(ChatActivity.EXTRA_OTHER_USER_ID,mUserId_extra);
                startActivity(chatIntent);
            }
        });
    }

    private void populateDataWithIntentExtra() {
        final Intent intent = getIntent();
        mFullName_extra = intent.getStringExtra(EXTRA_FULL_NAME);
        mUsername_extra = intent.getStringExtra(EXTRA_USERNAME);
        mProfilePic_extra = intent.getStringExtra(EXTRA_PROFILE_PIC);
        mUserId_extra  = intent.getStringExtra(EXTRA_USER_ID);

        fullName = findViewById(R.id.profile_activity_full_name);
        username = findViewById(R.id.profile_activity_username);
        profilePic = findViewById(R.id.profile_activity_profile_pic);


        fullName.setText(mFullName_extra);
        username.setText(mUsername_extra);
        Glide.with(this).load(mProfilePic_extra).placeholder(R.mipmap.ic_launcher_round).into(profilePic);

    }
}
