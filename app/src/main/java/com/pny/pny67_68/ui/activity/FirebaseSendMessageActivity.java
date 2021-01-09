package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.Movies;
import com.pny.pny67_68.repository.model.User;

public class FirebaseSendMessageActivity extends AppCompatActivity {

    EditText userName , userPhone , userGender;
    String strName , strPhone , strGender , strUid , StrId , strUserId = "";
    Button  updateUserData;
    Button  addNewUser;
    Button Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_send_message);

        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userGender = findViewById(R.id.userGendeer);
        addNewUser = findViewById(R.id.addNewUser);
        updateUserData = findViewById(R.id.updateUserData);
        Logout = findViewById(R.id.Logout);

        strUid = getSharedPreferences("user_pref",MODE_PRIVATE).getString("uid","");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference reference = firebaseDatabase.getReference("user");

        getUserData(reference);

        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strName = userName.getText().toString();
                strPhone = userPhone.getText().toString();
                strGender = userGender.getText().toString();

                User user = new User();

                if(strUserId.isEmpty()){
                    user.setUserId(StrId);
                }

                user.setUserName(strName);
                user.setUserGender(strGender);
                user.setUserPhone(strPhone);

                SharedPreferences sharedPreferences = getSharedPreferences("user_pref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", strName);
                editor.putString("Phone", strPhone);
                editor.putString("userId", user.userId);
                editor.apply();

                reference.child(strUid).setValue(user);

                startActivity(new Intent(FirebaseSendMessageActivity.this, UsersActivity.class));

            }
        });


        updateUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirebaseSendMessageActivity.this, UsersActivity.class));

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

        Query lastQuery = reference.orderByKey().limitToLast(1);

        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        User user = snapshot.getValue(User.class);

                        if(user != null) {
                            int uid = Integer.parseInt(user.userId);
                            ++uid;
                            StrId = uid+"";
                        }else {
                            StrId = "1" ;
                        }
                    }
                }else {
                    StrId = "1" ;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

    }

    public void getUserData(DatabaseReference reference ){

        reference.child(strUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if(user != null) {
                    userName.setText(user.userName);
                    userPhone.setText(user.userPhone);
                    userGender.setText(user.userGender);
                    strUserId = user.userId;

                    SharedPreferences sharedPreferences = getSharedPreferences("user_pref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", user.userName);
                    editor.putString("Phone", user.userPhone);
                    editor.putString("userId", user.userId);
                    editor.apply();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FirebaseSendMessageActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}