package com.example.androchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.ChatActivity;
import com.example.androchat.R;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
private ArrayList<String> mUserName;
private ArrayList<String> mName;
private ArrayList<String> mProfile_Url;
private ArrayList<String> mUserId;
private Context mContext;
String userIdString;

public ChatListAdapter(Context context, ArrayList<String> userName, ArrayList<String> name, ArrayList<String> profile_url, ArrayList<String> userId){
    mContext = context;
    mUserName = userName;
    mName = name;
    mProfile_Url = profile_url;
    mUserId = userId;

}
    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_item_view,parent,false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.userName.setText(mName.get(position));
        holder.lastMessage.setText(mUserName.get(position));
        userIdString = mUserId.get(position);
    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView lastMessage;
       ChatListViewHolder(@NonNull View itemView) {
           super(itemView);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent chatIntent = new Intent(mContext, ChatActivity.class);
                   chatIntent.putExtra(ChatActivity.EXTRA_OTHER_USER_ID,userIdString);
                   mContext.startActivity(chatIntent);
               }
           });
           userName = itemView.findViewById(R.id.item_view_name_tv);
           lastMessage = itemView.findViewById(R.id.item_view_last_message_tv);
       }
    }
}
