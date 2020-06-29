package com.creapple.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class WriteComment extends AppCompatActivity {
    RatingBar ratingBar;
    EditText inputComment;
    String comment;
    float rating_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        ratingBar =(RatingBar) findViewById(R.id.rating);
        inputComment =(EditText) findViewById(R.id.inputComment);
        Button save =(Button) findViewById(R.id.save);
        Button cancle =(Button) findViewById(R.id.cancle);


        ratingBar.setClickable(true);
        ratingBar.setEnabled(true);
        ratingBar.setRating(0);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                comment= inputComment.getText().toString();
                rating_value =ratingBar.getRating();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent inputIntent =new Intent();
                inputIntent.putExtra("writecomment",comment);
                inputIntent.putExtra("inputrating",rating_value);
                setResult(RESULT_OK,inputIntent); //이 액티비티가 종료되기전에 이 메소드를 호출함으로써 메인액티비티에 응답을 보낼 수 있다.



                finish();


            }

        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소",Toast.LENGTH_LONG).show();


                finish();



            }
        });

    }
}
