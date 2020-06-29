package com.creapple.movieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;


public class MainActivity extends AppCompatActivity {
    RecyclerView mrecyclerView;

    Button btn_good, btn_bad, writecomment, show_btn;
    int counter1, counter2, index;
    boolean likestate = false, badstate = false;
    TextView num_good, num_bad, movie_content;
    ArrayList<CommentInfo> commentInfoArrayList;
    AdapterComment adapterComment;

    RatingBar starbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecyclerView = findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mLayoutManger);

        starbar = (RatingBar) findViewById(R.id.star_bar);
        btn_good = (Button) findViewById(R.id.btn_good);
        btn_bad = (Button) findViewById(R.id.btn_bad);
        writecomment = (Button) findViewById(R.id.writecomment);
        show_btn = (Button) findViewById(R.id.show_btn);

        num_good = (TextView) findViewById(R.id.num_good);
        num_bad = (TextView) findViewById(R.id.num_bad);
        //movie_content = (TextView) findViewById(R.id.searchResult);


        counter1 = 2;
        counter2 = 3;
        index = 0;
        num_good.setText(String.valueOf(counter1));
        num_bad.setText(String.valueOf(counter2));


        commentInfoArrayList = new ArrayList<>();
        adapterComment = new AdapterComment(commentInfoArrayList);
        mrecyclerView.setAdapter(adapterComment);



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(), mLayoutManger.getOrientation());
        mrecyclerView.addItemDecoration(dividerItemDecoration);
        commentInfoArrayList.add(new CommentInfo("thd8686", 1, "10분전", "구리다 구려"));
        commentInfoArrayList.add(new CommentInfo("seungjne", 2, "20분전", "이딴걸 영화라고"));
        commentInfoArrayList.add(new CommentInfo("youlime_m3", 4, "10분전", "생각보다 나쁘지 않은 듯?"));
        commentInfoArrayList.add(new CommentInfo("soso_", 3, "20분전", "호불호가 갈릴듯 .."));



        btn_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (badstate == false & likestate == false) {
                    counter1 += 1;
                    num_good.setText(String.valueOf(counter1));
                    btn_good.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    likestate = !likestate;


                } else if (badstate == false & likestate == true) {
                    counter1 -= 1;
                    num_good.setText(String.valueOf(counter1));
                    btn_good.setBackgroundResource(R.drawable.ic_thumb_up);
                    likestate = !likestate;


                } else if (badstate == true & likestate == false) {
                    counter1 += 1;
                    counter2 -= 1;
                    num_good.setText(String.valueOf(counter1));
                    num_bad.setText(String.valueOf(counter2));
                    btn_good.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    btn_bad.setBackgroundResource(R.drawable.ic_thumb_down);
                    badstate = !badstate;
                    likestate = !likestate;


                }

            }

        });

        btn_bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (badstate == false & likestate == false) {

                    counter2 += 1;
                    num_bad.setText(String.valueOf(counter2));

                    btn_bad.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                    badstate = !badstate;


                } else if (badstate == true & likestate == false) {
                    counter2 -= 1;
                    num_bad.setText(String.valueOf(counter2));
                    btn_bad.setBackgroundResource(R.drawable.ic_thumb_down);
                    badstate = !badstate;


                } else if (likestate == true & badstate == false) {
                    counter1 -= 1;
                    counter2 += 1;
                    num_good.setText(String.valueOf(counter1));
                    num_bad.setText(String.valueOf(counter2));
                    btn_good.setBackgroundResource(R.drawable.ic_thumb_up);
                    btn_bad.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                    badstate = !badstate;
                    likestate = !likestate;


                }


            }
        });


        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSendCommentdate();



            }
        });
        writecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), WriteComment.class);
                startActivityForResult(intent, 101);


            }
        });


        starbar.setEnabled(true);
        starbar.setClickable(true);
        starbar.setRating(0);

        starbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starbar.setRating(rating);


            }
        });


    }

    public void startSendCommentdate(){

        Intent intent =new Intent(getApplicationContext(),showAllData.class);
       ArrayList <CommentInfo> Comment =new ArrayList<>();
       // Comment.add();
//        intent.putParcelableArrayListExtra("list",   Comment);
        startActivity(intent);






    }

//응답을 받아주는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        String writedata = data.getStringExtra("comment");
        float writerating1 = data.getExtras().getFloat("inputrating",0F); //rating 받을시에 getExtras().getFloat ??이거 왜일까 /.
        Log.e("testval11", writedata);

        float writerating = data.getFloatExtra("inputrating",11.1F); //rating 받을시에 getExtras().getFloat ??이거 왜일까 /.
        Log.e("testval22", String.valueOf(writerating));


        if (requestCode == 101) {
            if(data !=null) {
                CommentInfo newInfo = new CommentInfo("", writerating, "1분전", writedata); //정보 연결
                commentInfoArrayList.add(newInfo);
                adapterComment.notifyDataSetChanged();


            }else{

            }


        }

    }
}






















