package com.pny.pny67_68.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.Chat;

import java.security.Timestamp;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    Button sendMessage;
    EditText messageText;
    String recieverID;
    String recieverName;
    String senderId;
    String senderName;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendMessage = findViewById(R.id.sendMessage);
        messageText = findViewById(R.id.messageText);

        Intent intent = getIntent();

        if (intent != null) {
            recieverID = intent.getStringExtra("recieverID");
            recieverName = intent.getStringExtra("recieverName");
        }

        sharedPreferences = getSharedPreferences("user_pref",MODE_PRIVATE);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                senderId = sharedPreferences.getString("uid", "");
                senderName = sharedPreferences.getString("name", "");

                int intSenderid = Integer.parseInt(senderId);
                int intReceiverid = Integer.parseInt(recieverID);
                String chat_id;

                if(intSenderid>intReceiverid){
                    chat_id= intReceiverid + "-" + intSenderid;
                }else {
                    chat_id = intSenderid + "-" + intReceiverid;
                }

                DatabaseReference messageRef = FirebaseDatabase.getInstance()
                        .getReference("message").child(chat_id);

                String key = messageRef.push().getKey();

                String txtMessage = messageText.getText().toString();

                if(txtMessage.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Please enter valid message", Toast.LENGTH_SHORT).show();
                }else {
                    messageRef.child(key).setValue(getChatObj(txtMessage,key,chat_id));
                }

            }
        });

    }


    public Chat getChatObj(String msg, String messageId, String chat_id) {

        Date date = new Date();
        long timeStamp = date.getTime();

        return new Chat(chat_id
                , messageId
                , senderId
                , senderName
                , recieverID
                , recieverName
                , msg
                , Long.toString(timeStamp));

    }


}