package com.creapple.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChattingActivity extends AppCompatActivity {
    String CHATNAME;
    String USER;
    EditText chat_edit;
    Button chat_send;
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    ChatData chat;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ChatData> arraychat;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
     DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        recyclerView=(RecyclerView) findViewById(R.id.chat_View);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.send);
        arraychat =new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.chat_View);
        //in content do not change the layout size od recyclerView
        recyclerView.setHasFixedSize(true);
        //use a linear layout manager
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //구분선 주기
        chatAdapter=new ChatAdapter(arraychat);
        recyclerView.setAdapter(chatAdapter);

        Intent intent=getIntent();
        CHATNAME= intent.getStringExtra("chatname");
        USER=intent.getStringExtra("username");
        showChat();

        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat =new ChatData(USER,chat_edit.getText().toString());
                databaseReference.child("chat").child(CHATNAME).push().setValue(chat);
                chat_edit.setText("");

                //데이터 전송
            }
        });
    }

   public void showChat(){
        databaseReference.child("chat").child(CHATNAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               //ChatData chatclass=dataSnapshot.getValue(ChatData.class);
                Log.e("Log :","data :"+dataSnapshot.getValue(ChatData.class));
                    ChatData chatclass=dataSnapshot.getValue(ChatData.class);
                arraychat.add(chatclass);

                //chatAdapter.notifyDataSetChanged();
            }

            @Override
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
