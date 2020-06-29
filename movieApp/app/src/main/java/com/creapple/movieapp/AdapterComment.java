package com.creapple.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterComment extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CommentInfo> CommentInfoArrayList =new ArrayList<>();



    public static  class  MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView imgpicture;
        TextView id,Comment,textViewtime;
        RatingBar ratingBar;

        public MyViewHolder(View view){
            super(view);

            imgpicture=view.findViewById(R.id.userp);
            id=view.findViewById(R.id.userid);
            textViewtime=view.findViewById(R.id.time);
            Comment=view.findViewById(R.id.content);
            ratingBar=view.findViewById(R.id.rating1);


        }




    }

    AdapterComment(ArrayList<CommentInfo> CommentInfoArrayList){

        this.CommentInfoArrayList=CommentInfoArrayList;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder=(MyViewHolder) holder;
        myViewHolder.id.setText(CommentInfoArrayList.get(position).id);
        myViewHolder.textViewtime.setText((CommentInfoArrayList.get(position).time));
        myViewHolder.Comment.setText(CommentInfoArrayList.get(position).comment);
        myViewHolder.ratingBar.setRating(CommentInfoArrayList.get(position).rating);


    }

    @Override
    public int getItemCount() {


        return CommentInfoArrayList.size();
    }
}
