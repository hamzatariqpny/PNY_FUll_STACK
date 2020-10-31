package com.pny.pny67_68.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.db.AppDataBase;
import com.pny.pny67_68.ui.db.Contact;

import java.net.URI;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {


    EditText numberEdt, nameEdt;
    Button createContact, readContact, updateContact, deleteContact;

    AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // reference to the database
        appDataBase = AppDataBase.getAppDataBase(this);

        numberEdt = findViewById(R.id.addContactNumber);
        nameEdt = findViewById(R.id.addContactName);
        createContact = findViewById(R.id.createContact);
        readContact = findViewById(R.id.readContact);
        updateContact = findViewById(R.id.updateContact);
        deleteContact = findViewById(R.id.deleteContact);


        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDataBase.contactDao().insertContacts(getContact());
                Toast.makeText(DatabaseActivity.this, "Contact Added", Toast.LENGTH_SHORT).show();
            }
        });


        readContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DatabaseActivity.this, RecyclerViewActvity.class));
            }
        });


        updateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    // return Contact entity object
    public Contact getContact() {

        String strContactName = nameEdt.getText().toString();
        String strContactNumber = numberEdt.getText().toString();

        Contact contact = new Contact();
        contact.contactName = strContactName;
        contact.contactNumber = strContactNumber;

        return contact;


    }
}