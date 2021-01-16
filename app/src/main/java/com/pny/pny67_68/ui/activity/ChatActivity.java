package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.Chat;
import com.pny.pny67_68.repository.model.User;
import com.pny.pny67_68.ui.ContactAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    Button sendMessage;
    EditText messageText;
    String recieverID;
    String recieverName;
    String recieverPhone;
    String senderId;
    String senderName;
    String senderPhone;
    String chat_id;
    SharedPreferences sharedPreferences;
    DatabaseReference messageRef;
    RecyclerView chatRV;

    String serverKey = "key=AAAATZQ2RsA:APA91bEahX9yBvTlj_ZUaIEQsgFm5UJJtJHq4-Mbq80OrfJm-e_NcG5nh9I6GpgmBPfMtpcEhIJa8ts5ykUOJ5dWPB4MSEQBMzYmP1MX9r8P9-an7IsL0dS6eHr_DjGWZYTUBBYudWeq";
    final private String contentType = "application/json";
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendMessage = findViewById(R.id.sendMessage);
        messageText = findViewById(R.id.messageText);
        chatRV = findViewById(R.id.chatRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        chatRV.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();

        if (intent != null) {
            recieverID = intent.getStringExtra("recieverID");
            recieverName = intent.getStringExtra("recieverName");
            recieverPhone = intent.getStringExtra("recieverPhone");
        }

        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);

        senderId = sharedPreferences.getString("userId", "");
        senderName = sharedPreferences.getString("name", "");
        senderPhone = sharedPreferences.getString("Phone", "");

        int intSenderid = Integer.parseInt(senderId);
        int intReceiverid = Integer.parseInt(recieverID);


        if (intSenderid > intReceiverid) {
            chat_id = intReceiverid + "-" + intSenderid;
        } else {
            chat_id = intSenderid + "-" + intReceiverid;
        }

        messageRef = FirebaseDatabase.getInstance()
                .getReference("message").child(chat_id);

        getChatData();


        FirebaseMessaging.getInstance().subscribeToTopic(chat_id);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String key = messageRef.push().getKey();

                String txtMessage = messageText.getText().toString();

                if (txtMessage.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Please enter valid message", Toast.LENGTH_SHORT).show();
                } else {
                    messageRef.child(key).setValue(getChatObj(txtMessage, key, chat_id));
                    sendNotifToUser(senderName,txtMessage);
                    messageText.setText("");
                }

            }
        });

    }


    public void getChatData() {

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Chat chat = snapshot.getValue(Chat.class);

                if (chat != null) {

                    ArrayList<Chat> chats = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Chat userChat = dataSnapshot.getValue(Chat.class);
                        chats.add(userChat);

                    }

                    ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, chats);
                    chatRV.setAdapter(chatAdapter);

                    if(chats.size()>0){
                        chatRV.scrollToPosition(chats.size() - 1);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Chat getChatObj(String msg, String messageId, String chat_id) {

        Date date = new Date();
        long timeStamp = date.getTime();

        List<String> test = new ArrayList<>();

        test.add("hello");
        test.add("hello 1 ");
        test.add("hello 2");
        test.add("hello 3");

        return new Chat(test,chat_id
                , messageId
                , senderId
                , senderName
                , recieverID
                , recieverName
                , msg
                , Long.toString(timeStamp));

    }

    public void sendNotifToUser(String userName , String text){

        TOPIC = "/topics/"+recieverPhone;


        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();

        NOTIFICATION_TITLE = "message from "+userName;
        NOTIFICATION_MESSAGE = text;

        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);
            notifcationBody.put("recieverID",senderId);
            notifcationBody.put("recieverName",senderName);
            notifcationBody.put("recieverPhone",senderPhone);
            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e("TAG", "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);
    }


    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ChatActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }

                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };

        RequestQueue que= Volley.newRequestQueue(this);
        que.add(jsonObjectRequest);

    }
}