package com.pny.pny67_68.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.ContactAdapter;
import com.pny.pny67_68.repository.model.ContactModel;
import com.pny.pny67_68.repository.db.AppDataBase;
import com.pny.pny67_68.repository.db.Contact;
import com.pny.pny67_68.ui.contactDb.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActvity extends AppCompatActivity {


    ArrayList<ContactModel> contactModels = new ArrayList<>();
    RecyclerView contactRV;
    ContactAdapter contactAdapter;

    ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_actvity);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        // reference to the database
        contactViewModel.initDB(this);

        contactRV = findViewById(R.id.contactRV);
        contactRV.setLayoutManager(new LinearLayoutManager(this));

        ContactViewModel.ContactsLiveData.observe(this,new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
               // contactAdapter  = new ContactAdapter(RecyclerViewActvity.this,contacts);
                contactRV.setAdapter(contactAdapter);
            }
        });



        contactViewModel.getContact();

    }



}