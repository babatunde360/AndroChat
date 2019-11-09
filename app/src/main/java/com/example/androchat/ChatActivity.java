package com.example.androchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.adapter.ChatAdapter;
import com.example.androchat.model.ChatMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public static String EXTRA_OTHER_USER_ID = "com.example.androchat.chatActivity.OTHER_USER_ID";

    List<ChatMessage> messageList = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUserId;
    Button sendButton;
    EditText chatBoxEditText;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mMessageDatabaseRef;
    private DatabaseReference mPrivateConversationRef;

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
        Intent getChatIntent = getIntent();
        String mOtherUserId = getChatIntent.getStringExtra(EXTRA_OTHER_USER_ID);
        mPrivateConversationRef = mMessageDatabaseRef.child(mOtherUserId + "_" + currentUserId);

        RecyclerView messageListRecycler = findViewById(R.id.rv_message_list);
        messageListRecycler.setLayoutManager(new LinearLayoutManager(this));
        ChatAdapter adapter = new ChatAdapter(this,messageList,currentUserId);
        messageListRecycler.setAdapter(adapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatBoxEditText.getText().toString();
                if (message.length() > 0){
                    Toast.makeText(ChatActivity.this, "Send button has been clicked", Toast.LENGTH_SHORT).show();
                    ChatMessage currentMessage = new ChatMessage();
                    currentMessage.setDbMessage(message,currentUserId);
                    mPrivateConversationRef.push().setValue(currentMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            chatBoxEditText.setText("");

                        }
                    });
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
