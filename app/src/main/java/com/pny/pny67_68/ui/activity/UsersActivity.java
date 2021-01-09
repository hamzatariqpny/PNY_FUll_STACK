package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.ContactModel;
import com.pny.pny67_68.repository.model.User;
import com.pny.pny67_68.ui.ContactAdapter;
import com.pny.pny67_68.ui.contactDb.ContactViewModel;

import java.util.ArrayList;
import java.util.Map;

public class UsersActivity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;

    DatabaseReference reference;

    RecyclerView contactRV;
    ContactAdapter contactAdapter;
    Button addUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        addUser = findViewById(R.id.addUser);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("user");

        contactRV = findViewById(R.id.contactRV);
        contactRV.setLayoutManager(new LinearLayoutManager(this));

        getUserData();

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersActivity.this, FirebaseAuthActivity.class));
            }
        });

    }

    public void getUserData() {

        SharedPreferences sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<User> userArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);

                    if(!userId.equals(user.userId)){
                        userArrayList.add(user);
                    }

                }

                contactAdapter = new ContactAdapter(UsersActivity.this, userArrayList);
                contactRV.setAdapter(contactAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UsersActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}