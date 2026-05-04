package com.example.app3;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class TimerService extends Service {
    private static final int COUNTDOWN_SECONDS = 10;
    private int remainingSeconds = COUNTDOWN_SECONDS;
    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Сервис создан", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Сервис запущен", Toast.LENGTH_SHORT).show();
        remainingSeconds = COUNTDOWN_SECONDS;

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingSeconds > 0) {
                    android.util.Log.d("TimerService", "Осталось секунд: " + remainingSeconds);
                    remainingSeconds--;
                    handler.postDelayed(this, 1000);
                } else {
                    Toast.makeText(TimerService.this, "Таймер завершён!", Toast.LENGTH_LONG).show();
                    stopSelf();
                }
            }
        };
        handler.post(timerRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }
        Toast.makeText(this, "Сервис остановлен", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
