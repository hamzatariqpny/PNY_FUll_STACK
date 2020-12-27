package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.Movies;
import com.pny.pny67_68.repository.model.User;

public class FirebaseSendMessageActivity extends AppCompatActivity {

    EditText userName , userPhone , userGender;
    String strName , strPhone , strGender , strUid;
    Button  updateUserData;
    Button Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_send_message);

        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userGender = findViewById(R.id.userGendeer);
        updateUserData = findViewById(R.id.updateUserData);
        Logout = findViewById(R.id.Logout);

        strUid = getIntent().getStringExtra("uid");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference reference = firebaseDatabase.getReference("user");


        updateUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strName = userName.getText().toString();
                strPhone = userPhone.getText().toString();
                strGender = userGender.getText().toString();

                User user = new User();

                user.setUserId(strUid);
                user.setUserName(strName);
                user.setUserGender(strGender);
                user.setUserPhone(strPhone);

                reference.child(strUid).setValue(user);

                getUserData(reference);

            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.signOut();

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null){
                   finish();
                }
            }
        });

    }

    public void getUserData(DatabaseReference reference ){

        reference.child(strUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                if(user != null) {
                    Log.d("FirebaseActivity", "Value is: " + user.getUserName());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FirebaseSendMessageActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}