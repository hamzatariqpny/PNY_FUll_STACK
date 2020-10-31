package com.pny.pny67_68.ui.wm;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadWorker extends Worker {


    private static final String WORK_RESULT = "work_result";

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    // back ground task
    @NonNull
    @Override
    public Result doWork() {

        boolean isSuccess = true;

        downloadImageFromServer();

        if (isSuccess) {
            Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Success").build();
            return Result.success(outputData);
        } else {
            Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Failed").build();
            return Result.failure(outputData);
        }

    }


    public void downloadImageFromServer() {

                for (int i = 0; i <= 100; i++) {

                    Log.d("Download progress", i + "");

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i == 100) {
                        Log.d("Download progress", "Success Completed");
                    }
                }
    }
}
