package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pny.pny67_68.R;

public class FirebaseAuthActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    TextView loggedInEmail;
    Button registerUser;
    Button loginUser;

    String strEmail, strPassword;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        registerUser = findViewById(R.id.registerUser);
        loginUser = findViewById(R.id.loginUser);
        loggedInEmail = findViewById(R.id.loggedInEmail);


        firebaseAuth = FirebaseAuth.getInstance();


        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = userEmail.getText().toString();
                strPassword = userPassword.getText().toString();

                if (isFieldNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(FirebaseAuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Log.d("FirebaseAuthActivity", "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                    }else {
                                        Toast.makeText(FirebaseAuthActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strEmail = userEmail.getText().toString();
                strPassword = userPassword.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(strEmail,strPassword)
                        .addOnCompleteListener(FirebaseAuthActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Log.d("FirebaseAuthActivity", "signInWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if(user != null){
                                        loggedInEmail.setText(user.getEmail());
                                        String uid = user.getUid();

                                        SharedPreferences sharedPreferences = getSharedPreferences("user_pref",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("uid", uid);
                                        editor.putString("email", user.getEmail());
                                        editor.apply();

                                        Intent intent = new Intent(FirebaseAuthActivity.this,FirebaseSendMessageActivity.class);
                                        intent.putExtra("uid",uid);
                                        startActivity(intent);

                                    }
                                }else {
                                    Toast.makeText(FirebaseAuthActivity.this, "Invalid user name and password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            Intent intent = new Intent(FirebaseAuthActivity.this,FirebaseSendMessageActivity.class);
            intent.putExtra("uid",user.getUid());
            finish();
            startActivity(intent);
        }else {
            loggedInEmail.setText("No user Logged In");
        }
    }

    public boolean isFieldNotEmpty() {

        String message = "";
        boolean isFieldNotEmpty;

        if (strEmail.isEmpty()) {
            message = "Email is Empty";
            isFieldNotEmpty = false;
        } else if (strPassword.isEmpty()) {
            isFieldNotEmpty = false;
            message = "Password is Empty";
        } else {
            isFieldNotEmpty = true;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        return isFieldNotEmpty;

    }
}