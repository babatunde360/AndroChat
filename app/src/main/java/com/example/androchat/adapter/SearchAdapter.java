package com.example.androchat.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androchat.ProfileActivity;
import com.example.androchat.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private ArrayList<String> fullNameList;
    private ArrayList<String> userNameList;
    private ArrayList<String> profilePicList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView full_name, user_name;

        SearchViewHolder(View itemView) {
            super(itemView);
            profileImage =  itemView.findViewById(R.id.profile_activity_profile_pic);
            full_name =  itemView.findViewById(R.id.full_name);
            user_name =  itemView.findViewById(R.id.user_name);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> fullNameList, ArrayList<String> userNameList, ArrayList<String> profilePicList) {
        this.context = context;
        this.fullNameList = fullNameList;
        this.userNameList = userNameList;
        this.profilePicList = profilePicList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {
        final String fullName = fullNameList.get(position);
        final String userName = userNameList.get(position);
        final String profilePic = profilePicList.get(position);

        holder.full_name.setText(fullName);
        holder.user_name.setText(userName);
        Glide.with(context).load(profilePic).placeholder(R.mipmap.ic_launcher_round).into(holder.profileImage);

        holder.full_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_FULL_NAME,fullName);
                intent.putExtra(ProfileActivity.EXTRA_USERNAME,userName);
                intent.putExtra(ProfileActivity.EXTRA_PROFILE_PIC,profilePic);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }
}
