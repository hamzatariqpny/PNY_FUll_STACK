package com.pny.pny67_68.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryChangeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Battery low Broadcast receive
        Toast.makeText(context, "Battery low Alert !", Toast.LENGTH_SHORT).show();
    }

}
