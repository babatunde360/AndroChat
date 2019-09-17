package com.example.androchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.R;
import com.example.androchat.model.User;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
private ArrayList<User> mUser;

public ChatListAdapter(ArrayList<User> user){
    mUser = user;
}
    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_view,parent,false);
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
