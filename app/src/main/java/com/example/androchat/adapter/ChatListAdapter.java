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
import com.example.androchat.model.User;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>
        implements View.OnClickListener{
private ArrayList<User> mUser;
private Context mContext;

public ChatListAdapter(ArrayList<User> user, Context context){
    mUser = user;
    mContext = context;
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
        User user = mUser.get(position);
        holder.userName.setText(user.getName());
        holder.lastMessage.setText(user.getUser_id());
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    @Override
    public void onClick(View v) {
        Intent chatIntent = new Intent(mContext, ChatActivity.class);
        mContext.startActivity(chatIntent);
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView lastMessage;
       public ChatListViewHolder(@NonNull View itemView) {
           super(itemView);
           userName = itemView.findViewById(R.id.item_view_name_tv);
           lastMessage = itemView.findViewById(R.id.item_view_last_message_tv);
       }
   }
}
