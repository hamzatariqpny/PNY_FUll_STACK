package com.pny.pny67_68.ui.contactDb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.activity.RecyclerViewActvity;
import com.pny.pny67_68.repository.db.AppDataBase;
import com.pny.pny67_68.repository.db.Contact;

public class DatabaseActivity extends AppCompatActivity {


    EditText numberEdt, nameEdt;
    Button createContact, readContact, updateContact, deleteContact;

    ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.initDB(this);

        numberEdt = findViewById(R.id.addContactNumber);
        nameEdt = findViewById(R.id.addContactName);
        createContact = findViewById(R.id.createContact);
        readContact = findViewById(R.id.readContact);
        updateContact = findViewById(R.id.updateContact);
        deleteContact = findViewById(R.id.deleteContact);


        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(DatabaseActivity.this, "Contact Added", Toast.LENGTH_SHORT).show();

                String strContactName = nameEdt.getText().toString();
                String strContactNumber = numberEdt.getText().toString();

                contactViewModel.InsertContact(strContactName,strContactNumber);

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


}