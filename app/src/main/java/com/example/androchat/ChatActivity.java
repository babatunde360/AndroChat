package com.example.androchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.adapter.ChatAdapter;
import com.example.androchat.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity {
    List<ChatMessage> messageList = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUserId;
    Button sendButton;
    EditText chatBoxEditText;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mMessageDatabaseRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkLogin();

        sendButton = findViewById(R.id.button_chatbox_send);
        chatBoxEditText = findViewById(R.id.edit_text_chatbox);
        mDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseRef = mDatabase.getReference().child(getString(R.string.db_messages_node));

        RecyclerView messageListRecycler = findViewById(R.id.rv_message_list);
        messageListRecycler.setLayoutManager(new LinearLayoutManager(this));
        ChatAdapter adapter = new ChatAdapter(this,messageList,currentUserId);
        messageListRecycler.setAdapter(adapter);

        Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
        final String date = DateUtils.formatDateTime(this, calendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatBoxEditText.getText().toString().length() > 0){
                    Toast.makeText(ChatActivity.this, "Send button has been clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void checkLogin(){
        if(mAuth == null){
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            currentUserId = currentUser.getUid();
        }
    }
}
