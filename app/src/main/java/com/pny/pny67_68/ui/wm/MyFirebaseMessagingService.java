package com.pny.pny67_68.ui.wm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.activity.ChatActivity;

// all messages will Receive  here .
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage != null){
            Log.d("messageTitle",remoteMessage.getData().get("title"));
            Log.d("messageBody",remoteMessage.getData().get("message"));

            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("message");
            createNotificationChannel(body,title,remoteMessage);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    private void createNotificationChannel(
             String messageBody,
             String title,
             RemoteMessage remoteMessage
    ) {
        String  CHANNEL_ID = "0011";
        String CHANNEL_NAME = "PNY_MESSAGE_CHANNEL";

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            manager.createNotificationChannel(channel);
        }

        // click of notificaiton

        String recieverID = remoteMessage.getData().get("recieverID");
        String recieverName = remoteMessage.getData().get("recieverName");
        String recieverPhone = remoteMessage.getData().get("recieverPhone");

        Intent intent = new Intent(MyFirebaseMessagingService.this, ChatActivity.class);
        intent.putExtra("recieverID",recieverID);
        intent.putExtra("recieverName",recieverName);
        intent.putExtra("recieverPhone",recieverPhone);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        ); // Create an Intent for the activity you want to start


        Notification notification = new
                NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setContentIntent(resultPendingIntent)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;

        manager.notify(1, notification);
    }
}

