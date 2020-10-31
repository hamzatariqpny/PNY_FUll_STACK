package com.pny.pny67_68.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.db.AppDataBase;
import com.pny.pny67_68.ui.db.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactViewHolder> {

    Activity activity;
    List<Contact> contactModels;
    AppDataBase appDataBase;

    public ContactAdapter(Activity activity, List<Contact> contactModels) {
        this.activity = activity;
        this.contactModels = contactModels;
        appDataBase =  AppDataBase.getAppDataBase(activity);
    }

    // Step 2
    // to convert XML into java object
    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_contact,parent,false);
        return new contactViewHolder(view);
    }

    // Step 3
    // to bind data to the java object
    // position = 0
    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, final int position) {

        final Contact contactModel = contactModels.get(position);

        holder.contactName.setText(contactModel.contactName);
        holder.contactNumber.setText(contactModel.contactNumber);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //appDataBase.contactDao().updateContact(getEditContact(contactModel));
                //activity.startActivity(new Intent(activity,));
                appDataBase.contactDao().deleteContact(getEditContact(contactModel));
                contactModels = appDataBase.contactDao().getAll();
                notifyDataSetChanged();

            }
        });

    }

    // to set the number if rows .
    // Step 1
    // 10 0-9
    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    class contactViewHolder extends RecyclerView.ViewHolder{

        ImageView contactImage;
        TextView contactName;
        TextView contactNumber;

        public contactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contactImage);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);

        }
    }


    // return Contact entity object
    public Contact getEditContact(Contact oldcontact) {

        oldcontact.contactName = oldcontact.contactName+ " Edited";
        oldcontact.contactNumber = oldcontact.contactNumber+ " Edited";

        return oldcontact;

    }


}
