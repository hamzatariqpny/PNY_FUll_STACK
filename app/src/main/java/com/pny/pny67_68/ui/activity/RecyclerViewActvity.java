package com.pny.pny67_68.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.ContactAdapter;
import com.pny.pny67_68.ui.ContactModel;
import com.pny.pny67_68.ui.db.AppDataBase;
import com.pny.pny67_68.ui.db.Contact;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActvity extends AppCompatActivity {


    ArrayList<ContactModel> contactModels = new ArrayList<>();
    RecyclerView contactRV;
    ContactAdapter contactAdapter;

    AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_actvity);


        // reference to the database
        appDataBase = AppDataBase.getAppDataBase(this);

        List<Contact> contacts = appDataBase.contactDao().getAll();


        contactRV = findViewById(R.id.contactRV);
        contactRV.setLayoutManager(new LinearLayoutManager(this));

        contactAdapter  = new ContactAdapter(this,contacts);

        contactRV.setAdapter(contactAdapter);


    }



}