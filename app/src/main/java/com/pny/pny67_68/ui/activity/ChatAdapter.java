package com.pny.pny67_68.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.pny.pny67_68.repository.model.Chat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Activity activity;
    List<Chat> contactModels;
    AppDataBase appDataBase;
    SharedPreferences sharedPreferences;

    int VIEW_TYPE_SENDER = 0;
    int VIEW_TYPE_RECIEVER = 1;


    public ChatAdapter(Activity activity, List<Chat> contactModels) {
        this.activity = activity;
        this.contactModels = contactModels;
        sharedPreferences = activity.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        appDataBase = AppDataBase.getAppDataBase(activity);
    }

    @Override
    public int getItemViewType(int position) {
        if (contactModels.get(position).fromMessageId.equals(sharedPreferences.getString("userId", ""))) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_RECIEVER;
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_SENDER){
            view = LayoutInflater.from(activity).inflate(R.layout.item_chat_sender, parent, false);
        }else {
            view = LayoutInflater.from(activity).inflate(R.layout.item_chat_reciever, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, final int position) {

        Chat chat = contactModels.get(position);

        holder.senderName.setText(chat.fromMessageName);
        holder.senderTxt.setText(chat.txtMessage);

    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {

        ImageView contactImage;
        TextView senderName;
        TextView senderTxt;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contactImage);
            senderName = itemView.findViewById(R.id.senderName);
            senderTxt = itemView.findViewById(R.id.senderTxt);

        }
    }

}
