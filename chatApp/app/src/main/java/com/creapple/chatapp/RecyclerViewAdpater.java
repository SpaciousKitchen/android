package com.creapple.chatapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdpater extends RecyclerView.Adapter<RecyclerViewAdpater.ViewHolder> {
    @NonNull
    //뷰를 위한 아이템을 제공한다.
    ArrayList<String>mDataset=new ArrayList<>();

    public  static  class ViewHolder extends  RecyclerView.ViewHolder{
        protected  TextView room;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.room=(TextView)itemView.findViewById(R.id.roomname);
        }
    }

    //데이터 생성자를 제공
    public RecyclerViewAdpater(@NonNull ArrayList<String> list) {
        this.mDataset = list;
    }


    //뷰를 제공

    //새로운 뷰를 생성(생명 주기와 같은것)

    @Override
    public RecyclerViewAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.roomlist,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }
    //xml이랑 뷰를 바인딩

    //뷰의 내용을 아이템과 접목 시킨다.



    public void onBindViewHolder(@NonNull RecyclerViewAdpater.ViewHolder holder, int position) {
        //뷰의 내용을 Dataset의 내용으로 변화 시킨다.
        holder.room.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount() {

        return  mDataset.size();
    }

}
