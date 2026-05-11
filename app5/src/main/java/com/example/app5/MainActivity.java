package com.example.app5;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.animation.ObjectAnimator;
import java.io.IOException;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "practice6_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private TextView tvAudioStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWebView();

        tvAudioStatus = findViewById(R.id.tvAudioStatus);
        Button btnPlayPause = findViewById(R.id.btnPlayPause);
        initMediaPlayer();

        btnPlayPause.setOnClickListener(v -> toggleAudio());

        ImageView ivAnim = findViewById(R.id.ivAnim);
        findViewById(R.id.btnRotate).setOnClickListener(v -> animateRotate(ivAnim));
        findViewById(R.id.btnMove).setOnClickListener(v -> animateMove(ivAnim));
        findViewById(R.id.btnScale).setOnClickListener(v -> animateScale(ivAnim));

        checkNotificationPermission();
        findViewById(R.id.btnNotifyNormal).setOnClickListener(v -> showNormalNotification());
        findViewById(R.id.btnNotifyDelayed).setOnClickListener(v -> showDelayedNotification());

        EditText etProviderInput = findViewById(R.id.etProviderInput);
        TextView tvResult = findViewById(R.id.tvProviderResult);

        findViewById(R.id.btnAddProvider).setOnClickListener(v -> {
            String name = etProviderInput.getText().toString();
            if (name.isEmpty()) return;

            ContentValues values = new ContentValues();
            values.put("name", name);

            Uri newUri = getContentResolver().insert(MyItemsProvider.CONTENT_URI, values);

            if (newUri != null) {
                tvResult.setText("Добавлено в Провайдер: " + newUri.toString());
                etProviderInput.setText("");
            }
        });

        findViewById(R.id.btnGetProvider).setOnClickListener(v -> {
            Cursor cursor = getContentResolver().query(MyItemsProvider.CONTENT_URI, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                StringBuilder sb = new StringBuilder("Данные из Провайдера:\n");
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    sb.append("- ID: ").append(id).append(", Name: ").append(name).append("\n");
                } while (cursor.moveToNext());
                cursor.close();
                tvResult.setText(sb.toString());
            } else {
                tvResult.setText("Провайдер пуст. Добавьте элемент.");
                if (cursor != null) cursor.close();
            }
        });

        findViewById(R.id.btnSaveJson).setOnClickListener(v -> {
            try {
                Gson gson = new Gson();
                Item item = new Item(1, "Тестовый объект для JSON");
                String jsonString = gson.toJson(item);

                FileOutputStream fos = openFileOutput("data.json", MODE_PRIVATE);
                fos.write(jsonString.getBytes());
                fos.close();

                tvResult.setText("JSON сохранен в файл:\n" + jsonString);
            } catch (Exception e) {
                tvResult.setText("Ошибка сохранения JSON: " + e.getMessage());
                e.printStackTrace();
            }
        });

        findViewById(R.id.btnLoadJson).setOnClickListener(v -> {
            try {
                java.io.File file = new java.io.File(getFilesDir(), "data.json");
                if (!file.exists()) {
                    tvResult.setText("Файл data.json не найден!\nСначала сохраните JSON.");
                    return;
                }

                FileInputStream fis = openFileInput("data.json");
                java.io.InputStreamReader isr = new java.io.InputStreamReader(fis);
                java.io.BufferedReader reader = new java.io.BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                fis.close();

                String jsonString = sb.toString();

                if (jsonString.trim().isEmpty()) {
                    tvResult.setText("Файл пустой!");
                    return;
                }

                Gson gson = new Gson();
                Item loadedItem = gson.fromJson(jsonString, Item.class);

                tvResult.setText("Загруженный объект:\nID: " + loadedItem.id + "\nName: " + loadedItem.name);
            } catch (Exception e) {
                tvResult.setText("Ошибка чтения JSON: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void setupWebView() {
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        findViewById(R.id.btnLoadWeb).setOnClickListener(v ->
                webView.loadUrl("https://google.com")
        );
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> tvAudioStatus.setText("Статус: Готов к воспроизведению"));
        } catch (IOException e) {
            e.printStackTrace();
            tvAudioStatus.setText("Ошибка инициализации аудио");
        }
    }

    private void toggleAudio() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.pause();
                tvAudioStatus.setText("Пауза");
            } else {
                mediaPlayer.start();
                tvAudioStatus.setText("Играет...");
            }
            isPlaying = !isPlaying;
        }
    }

    private void animateRotate(View view) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        rotation.setDuration(2000);
        rotation.start();
    }

    private void animateMove(View view) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationX", 0f, 300f);
        translation.setDuration(1000);
        translation.start();
    }

    private void animateScale(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 2f);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);
        scaleX.start();
        scaleY.start();
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение на уведомления получено", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Practice 6 Channel";
            String description = "Канал для уведомлений из практики";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNormalNotification() {
        createChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Уведомление")
                .setContentText("Это обычное уведомление из Практики 6.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build());
    }

    private void showDelayedNotification() {
        Toast.makeText(this, "Уведомление придет через 10 секунд", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,
                AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long futureInMillis = System.currentTimeMillis() + 10000;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}