package com.pny.pny67_68.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.BatteryChangeReciever;
import com.pny.pny67_68.ui.wm.DownloadWorker;

import java.util.concurrent.TimeUnit;

public class WorkManagerActivity extends AppCompatActivity {

    WorkManager workManager;

    Button startDownload;
    TextView outputDownload;
    OneTimeWorkRequest oneTimeWorkRequest;
    PeriodicWorkRequest periodicWorkRequest;

    BatteryChangeReciever batteryChangeReciever;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);

        workManager = WorkManager.getInstance();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        oneTimeWorkRequest=
                new OneTimeWorkRequest.
                        Builder(DownloadWorker.class)
                        .setConstraints(constraints).build();

        periodicWorkRequest = new PeriodicWorkRequest
                .Builder(DownloadWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        startDownload = findViewById(R.id.startDownload);
        outputDownload = findViewById(R.id.outputDownload);

        startDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                workManager.enqueue(periodicWorkRequest);

            }
        });

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();
                    outputDownload.append(state.toString() + "\n");
                }
            }
        });

        batteryChangeReciever = new BatteryChangeReciever();
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(batteryChangeReciever,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryChangeReciever);
    }
}