package com.creapple.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class showAllData extends AppCompatActivity {
    RecyclerView mrecyclerView;
    TextView num_good, num_bad, movie_content;
    ArrayList<CommentInfo> commentInfoArrayList;
    AdapterComment adapterComment;
    RatingBar starbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_data);

        mrecyclerView = findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this);

        mrecyclerView.setLayoutManager(mLayoutManger);
        starbar = (RatingBar) findViewById(R.id.star_bar);
        Toolbar mToobar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        commentInfoArrayList = new ArrayList<>();
        adapterComment = new AdapterComment(commentInfoArrayList);
        mrecyclerView.setAdapter(adapterComment);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(), mLayoutManger.getOrientation());
        mrecyclerView.addItemDecoration(dividerItemDecoration);
        Intent i=getIntent();
       // commentInfoArrayList=i.getParcelableArrayListExtra("list");







    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
