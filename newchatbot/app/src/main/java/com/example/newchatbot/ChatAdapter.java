package com.example.newchatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    ArrayList<ChatData> mData=new ArrayList<>();
    public ChatAdapter(ArrayList<ChatData> mData) {
        this.mData = mData;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name;
        TextView chatcontent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name=(TextView)itemView.findViewById(R.id.name);
            this.chatcontent=(TextView)itemView.findViewById(R.id.content);
        }
    }
    //채팅시에 좌우구분을 위해서

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getUsername().equals("user")){
            return 1;
        }else{
            return 2;
        }

    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);


        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message, parent, false);


        }
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
    holder.name.setText(mData.get(position).username);
    holder.chatcontent.setText(mData.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
