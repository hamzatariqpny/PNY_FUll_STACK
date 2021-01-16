package com.pny.pny67_68.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.db.AppDataBase;
import com.pny.pny67_68.repository.db.Contact;
import com.pny.pny67_68.repository.model.User;
import com.pny.pny67_68.ui.activity.ChatActivity;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactViewHolder> {

    Activity activity;
    List<User> contactModels;
    AppDataBase appDataBase;

    public ContactAdapter(Activity activity, List<User> contactModels) {
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

        final User user = contactModels.get(position);

        holder.contactName.setText(user.userName);
        holder.contactNumber.setText(user.userPhone);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("recieverID",user.userId);
                intent.putExtra("recieverName",user.userName);
                intent.putExtra("recieverPhone",user.userPhone);
                activity.startActivity(intent);
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
