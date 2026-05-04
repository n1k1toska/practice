package com.example.app3;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import javax.xml.transform.Result;

public class MyWorker extends Worker {
    public static final String TAG = "WorkManagerLog";
    private String taskName;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        taskName = workerParams.getInputData().getString("task_name");
        if (taskName == null) {
            taskName = "Unknown";
        }
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Начало: " + taskName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Завершено: " + taskName);
        return Result.success();
    }
}
