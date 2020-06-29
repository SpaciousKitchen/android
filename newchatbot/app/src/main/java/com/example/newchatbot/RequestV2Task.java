package com.example.newchatbot;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

public class RequestV2Task extends AsyncTask<Void,Void, DetectIntentResponse> {
    SessionName sessionName;
    SessionsClient sessionsClient;
    QueryInput queryInput;
    Activity activity;

    public RequestV2Task(SessionName sessionName, SessionsClient sessionsClient, QueryInput queryInput, Activity activity) {
        this.sessionName = sessionName;
        this.sessionsClient = sessionsClient;
        this.queryInput = queryInput;
        this.activity = activity;
    }

    @Override
    protected DetectIntentResponse doInBackground(Void... voids) {
        try{
            DetectIntentRequest dectIntentRequest=
                    DetectIntentRequest.newBuilder().setSession(sessionName.toString()).setQueryInput(queryInput)
                    .build();
            return  sessionsClient.detectIntent(dectIntentRequest);

        }catch (Exception e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(DetectIntentResponse detectIntentResponse) {
        ((MainActivity)activity).callbackV2(detectIntentResponse);
    }
}
