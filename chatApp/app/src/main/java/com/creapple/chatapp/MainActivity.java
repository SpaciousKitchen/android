package com.creapple.chatapp;

import androidx.annotation.NonNull;
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
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     EditText chatname, username;
     Button button;
    String user;
     RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String Chatname;
    RecyclerViewAdpater recyclerViewAdpater;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> list;
    DividerItemDecoration dividerItemDecoration;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatname = (EditText) findViewById(R.id.chatname);
        username = (EditText) findViewById(R.id.user);
        button = (Button) findViewById(R.id.enterchat);
         list=new ArrayList<String>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //in content do not change the layout size od recyclerView

        recyclerView.setHasFixedSize(true);
        //use a linear layout manager
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //구분선 주기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        recyclerViewAdpater =new RecyclerViewAdpater(list);
        recyclerView.setAdapter(recyclerViewAdpater);

         database=FirebaseDatabase.getInstance(); //데이터 베이스 인스턴스를 가져옴
        databaseReference=database.getReference();
        show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chatname=chatname.getText().toString();
             user=username.getText().toString();
              if(Chatname.equals("")||user.equals("")){
                  return;
              }

              Intent intent=new Intent(getApplicationContext(), ChattingActivity.class);
              intent.putExtra("chatname",Chatname);
              intent.putExtra("username",user);
              startActivity(intent);
              //list.add(Chatname);
                // show();






            }
        });


    }
    public void show(){




        databaseReference.child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            //리스너를 처음 사용하기 시작할 때, 파이어베이스 데이터베이스의 데이터를 받아옵니다.
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("LOG!!!!!!!!!!!!!!!","data:"+dataSnapshot.getKey());

                String chatroom=dataSnapshot.getKey();
                list.add(chatroom);
                recyclerViewAdpater.notifyDataSetChanged();

            }

            @Override
            //록의 항목에 대한 변경을 수신 대기합니다.
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
