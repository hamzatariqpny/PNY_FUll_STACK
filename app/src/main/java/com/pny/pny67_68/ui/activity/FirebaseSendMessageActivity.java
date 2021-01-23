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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.Movies;
import com.pny.pny67_68.repository.model.User;

public class FirebaseSendMessageActivity extends AppCompatActivity {

    EditText userName , userPhone , userGender;
    String strName , strPhone , strGender , strUid , StrId , strUserId = "";
    Button  updateUserData;
    Button  addNewUser;
    Button Logout;
    String token;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

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
        mAdView = findViewById(R.id.adView);

        strUid = getSharedPreferences("user_pref",MODE_PRIVATE).getString("uid","");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {


            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(FirebaseSendMessageActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        DatabaseReference reference = firebaseDatabase.getReference("user");

        getUserData(reference);

        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("user_pref",MODE_PRIVATE);

                strName = userName.getText().toString();
                strPhone = userPhone.getText().toString();
                strGender = userGender.getText().toString();

                User user = new User();

                String userId = sharedPreferences.getString("userId","");

                if(userId.isEmpty()){
                    user.setUserId(StrId);
                }else {
                    user.setUserId(userId);
                }

                user.setUserName(strName);
                user.setUserGender(strGender);
                user.setUserPhone(strPhone);
                user.setToken(token);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", strName);
                editor.putString("Phone", strPhone);
                editor.putString("userId", user.userId);
                editor.putString("token", user.token);
                editor.apply();

                reference.child(strUid).setValue(user);

                startActivity(new Intent(FirebaseSendMessageActivity.this, UsersActivity.class));

            }
        });


        updateUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                clearUserPrefs();

                firebaseAuth.signOut();

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null){
                   finish();
                }
            }
        });


        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                startActivity(new Intent(FirebaseSendMessageActivity.this, UsersActivity.class));
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

    public void clearUserPrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
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
                    subscribeToToken(user.userPhone);
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



    public void subscribeToToken(String token){
        FirebaseMessaging.getInstance().subscribeToTopic(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("subscribeToToken",task.toString());
            }
        });
    }



}