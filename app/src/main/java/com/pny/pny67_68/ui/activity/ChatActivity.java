package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.Chat;
import com.pny.pny67_68.repository.model.User;
import com.pny.pny67_68.ui.ContactAdapter;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    Button sendMessage;
    EditText messageText;
    String recieverID;
    String recieverName;
    String senderId;
    String senderName;
    String chat_id;
    SharedPreferences sharedPreferences;
    DatabaseReference messageRef;
    RecyclerView chatRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendMessage = findViewById(R.id.sendMessage);
        messageText = findViewById(R.id.messageText);
        chatRV = findViewById(R.id.chatRV);

        chatRV.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        if (intent != null) {
            recieverID = intent.getStringExtra("recieverID");
            recieverName = intent.getStringExtra("recieverName");
        }

        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);

        senderId = sharedPreferences.getString("userId", "");
        senderName = sharedPreferences.getString("name", "");

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

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String key = messageRef.push().getKey();

                String txtMessage = messageText.getText().toString();

                if (txtMessage.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Please enter valid message", Toast.LENGTH_SHORT).show();
                } else {
                    messageRef.child(key).setValue(getChatObj(txtMessage, key, chat_id));
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