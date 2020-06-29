package com.creapple.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter    extends  RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<ChatData> mData=new ArrayList<ChatData>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView chatcontent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.username=(TextView) itemView.findViewById(R.id.user_name);
            this.chatcontent = (TextView) itemView.findViewById(R.id.chat_content);

        }
    }

    public ChatAdapter(ArrayList<ChatData> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.chatcontent,parent,false);

        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.username.setText(mData.get(position).username+": ");
        holder.chatcontent.setText(mData.get(position).message);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
