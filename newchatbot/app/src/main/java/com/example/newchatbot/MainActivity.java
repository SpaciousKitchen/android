package com.example.newchatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaCas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import ai.api.AIDataService;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class MainActivity extends AppCompatActivity {
    String uuid=UUID.randomUUID().toString();
    Button button;
    EditText editText;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference ref;

    ChatAdapter chatAdapter;
    String ACESSE_KEY="ebb879cdf1c646128a563c9912d5a899";
    AIConfiguration config;
    AIDataService aiDataService;
    AIRequest aiRequest;
    Button voice;
    SessionsClient sessionsClient;
    SessionName sessionName;
    final ArrayList<ChatData> chatarray=new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        button =(Button) findViewById(R.id.btn);
        editText=(EditText)findViewById(R.id.input_text);
        voice=(Button)findViewById(R.id.voice);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference();
        //데이터 베이스 저장을 위한 열기


        recyclerView.setHasFixedSize(true);

        chatAdapter=new ChatAdapter(chatarray);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
        //Add AIService and make your activity implement AIListener:
        //config =new AIConfiguration(ACESSE_KEY,AIConfiguration.SupportedLanguages.Korean,AIConfiguration.RecognitionEngine.System);
        //aiDataService=new AIDataService(config);
        //aiRequest=new AIRequest();


        //hen it calls the aiDataService.request method. Please note, that you must call aiDataService.request method from background thread, using AsyncTask class


       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                final String content = editText.getText().toString();
               if (content.trim().isEmpty()) {
                   return;
               }

               ChatData chatData = new ChatData("user", content);
               ref.child("userchat").push().setValue(chatData);
               sendMessage(chatData);

               Log.e("data :","user:"+content);

               QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(content).setLanguageCode("ko-KR")).build();
               new RequestV2Task( sessionName, sessionsClient, queryInput,MainActivity.this).execute();
               initchatbot();



              

           }

       });

        }
        public  void initchatbot(){

            try {


                InputStream stream = getResources().openRawResource(R.raw.praticeagent);
                GoogleCredentials credentials = GoogleCredentials.fromStream(stream);

                String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

                SessionsSettings.Builder settingBuilder = SessionsSettings.newBuilder();
                SessionsSettings sessionsSettings = settingBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
                sessionsClient = SessionsClient.create(sessionsSettings);
                sessionName = SessionName.of(projectId, uuid);

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        public void sendMessage(ChatData data){
                chatarray.add(data);
                chatAdapter.notifyDataSetChanged();
                 Log.e("datasize :","size : "+chatarray.size());
                recyclerView.scrollToPosition(chatarray.size()-1);
                 editText.setText("");

        }
            public void callbackV2(DetectIntentResponse response) {
                if(response!=null){
                    String botreply=response.getQueryResult().getFulfillmentText();
                    Log.e("data :","chatbot:"+botreply);
                    ChatData chatData=new ChatData("BOT",botreply);
                    sendMessage(chatData);
                }
            }

        }