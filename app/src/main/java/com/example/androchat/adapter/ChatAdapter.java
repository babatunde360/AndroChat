package com.example.androchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androchat.R;
import com.example.androchat.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<ChatMessage> mMessageList;
    private Context mContext;
    private String mCurrentUserId;

    public ChatAdapter(Context context, List<ChatMessage> messageList, String currentUserId){
    mMessageList = messageList;
    mContext = context;
    mCurrentUserId = currentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent,parent,false);
            return new SentMessageHolder(view);

        }else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received,parent,false);
            return new ReceivedMessageHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatMessage currentMessage = mMessageList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(currentMessage);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(currentMessage);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = mMessageList.get(position);
        if (message.getUser_id().equals(mCurrentUserId)){
            return VIEW_TYPE_MESSAGE_SENT;
        }else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{

        TextView messageSent;
        TextView messageTime;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageSent = itemView.findViewById(R.id.text_message_sent_body);
            messageTime = itemView.findViewById(R.id.text_message_sent_time);
        }
        void bind(ChatMessage message){
            messageSent.setText(message.getMessage());
            messageTime.setText(message.getTimestamp());
        }
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder{

        TextView messageReceived;
        TextView messageReceivedTime;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageReceived = itemView.findViewById(R.id.text_message_received_body);
            messageReceivedTime = itemView.findViewById(R.id.text_message_received_time);
        }
        void bind(ChatMessage message){
            messageReceived.setText(message.getMessage());
            messageReceivedTime.setText(message.getTimestamp());
        }
    }

}
