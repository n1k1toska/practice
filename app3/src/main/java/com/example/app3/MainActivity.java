package com.example.app3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.net.ssl.SSLContext;

public class MainActivity extends AppCompatActivity {

    private TextView tvDate, tvTime;
    private ImageView ivResult;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        findViewById(R.id.btnDatePicker).setOnClickListener(v -> showDatePicker());

        findViewById(R.id.btnTimePicker).setOnClickListener(v -> showTimePicker());

        findViewById(R.id.btnAlertDialog).setOnClickListener(v -> showAlertDialog());

        findViewById(R.id.btnCustomDialog).setOnClickListener(v -> showCustomDialog());

        findViewById(R.id.btnStartService).setOnClickListener(v ->
                startService(new Intent(MainActivity.this, TimerService.class))
        );
        findViewById(R.id.btnStopService).setOnClickListener(v ->
                stopService(new Intent(MainActivity.this, TimerService.class))
        );
        ivResult = findViewById(R.id.ivResult);
        findViewById(R.id.btnSequential).setOnClickListener(v -> runSequentialTasks());
        findViewById(R.id.btnParallel).setOnClickListener(v -> runParallelTasks());
        findViewById(R.id.btnApi).setOnClickListener(v -> loadImageFromApi());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) ->
                        tvDate.setText("Дата: " + day + "." + (month + 1) + "." + year),
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void showTimePicker() {
        Calendar cal = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hour, minute) ->
                        tvTime.setText("Время: " + hour + ":" + (minute < 10 ? "0" + minute : minute)),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение")
                .setMessage("Перейти на экран результата?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Да", (d, w) -> {
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("source", "AlertDialog");
                    i.putExtra("data", "Пользователь нажал 'Да'");
                    startActivity(i);
                })
                .setNegativeButton("Отмена", (d, w) -> d.dismiss())
                .show();
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        EditText etInput = dialog.findViewById(R.id.etInput);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String text = etInput.getText().toString().trim();
            if (!text.isEmpty()) {
                Intent i = new Intent(MainActivity.this, ResultActivity.class);
                i.putExtra("source", "CustomDialog");
                i.putExtra("data", "Введено: " + text);
                startActivity(i);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Введите текст!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void appendLog(String msg) {
        mainHandler.post(() -> {
            android.widget.TextView tv = findViewById(R.id.tvLog);
            tv.append(msg + "\n");
        });
    }

    private void runSequentialTasks() {
        appendLog("Запуск 3 задач ПОСЛЕДОВАТЕЛЬНО...");

        Data input1 = new Data.Builder().putString("task_name", "Задача_1").build();
        OneTimeWorkRequest work1 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(input1).build();

        Data input2 = new Data.Builder().putString("task_name", "Задача_2").build();
        OneTimeWorkRequest work2 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(input2).build();

        Data input3 = new Data.Builder().putString("task_name", "Задача_3").build();
        OneTimeWorkRequest work3 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(input3).build();

        WorkManager.getInstance(this).beginWith(work1).then(work2).then(work3).enqueue();
    }

    private void runParallelTasks() {
        appendLog("Запуск 2 задач ПАРАЛЛЕЛЬНО...");

        Data inputA = new Data.Builder().putString("task_name", "Параллельная_A").build();
        OneTimeWorkRequest workA = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(inputA).build();

        Data inputB = new Data.Builder().putString("task_name", "Параллельная_B").build();
        OneTimeWorkRequest workB = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(inputB).build();

        List<OneTimeWorkRequest> parallelTasks = Arrays.asList(workA, workB);
        WorkManager.getInstance(this).enqueue(parallelTasks);
    }

    private void loadImageFromApi() {
        appendLog("Загрузка картинки...");
        ivResult.setImageResource(android.R.color.transparent);
        ivResult.setScaleType(ImageView.ScaleType.FIT_CENTER);

        new Thread(() -> {
            int maxRetries = 3;
            boolean success = false;

            for (int attempt = 1; attempt <= maxRetries && !success; attempt++) {
                try {
                    appendLog("Попытка " + attempt + " из " + maxRetries);

                    TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }
                                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                            }
                    };

                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

                    URL apiUrl = new URL("https://dog.ceo/api/breeds/image/random");
                    HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Android-App");
                    conn.setConnectTimeout(60000);
                    conn.setReadTimeout(60000);

                    int responseCode = conn.getResponseCode();
                    appendLog("HTTP код: " + responseCode);

                    if (responseCode != 200) {
                        throw new Exception("HTTP код: " + responseCode);
                    }

                    InputStream is = conn.getInputStream();
                    java.util.Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
                    String response = scanner.hasNext() ? scanner.next() : "";
                    scanner.close();

                    appendLog("Получен JSON");

                    JSONObject obj = new JSONObject(response);
                    String imageUrl = obj.getString("message");
                    appendLog("URL: " + imageUrl);

                    URL imgUrl = new URL(imageUrl);
                    HttpsURLConnection imgConn = (HttpsURLConnection) imgUrl.openConnection();
                    imgConn.setRequestProperty("User-Agent", "Android-App");
                    imgConn.setConnectTimeout(60000);
                    imgConn.setReadTimeout(60000);

                    java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
                    InputStream inputStream = imgConn.getInputStream();
                    byte[] data = new byte[8192];
                    int bytesRead;
                    int totalBytes = 0;

                    while ((bytesRead = inputStream.read(data)) != -1) {
                        buffer.write(data, 0, bytesRead);
                        totalBytes += bytesRead;
                        appendLog("Загружено: " + (totalBytes / 1024) + " KB");
                    }

                    inputStream.close();
                    appendLog("Всего загружено: " + (totalBytes / 1024) + " KB");

                    byte[] imageData = buffer.toByteArray();
                    android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
                    options.inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888;
                    options.inSampleSize = 1;
                    options.inJustDecodeBounds = false;

                    Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

                    if (bitmap == null) {
                        throw new Exception("Не удалось декодировать изображение");
                    }

                    appendLog("Размер: " + bitmap.getWidth() + "x" + bitmap.getHeight());

                    mainHandler.post(() -> {
                        ivResult.setImageBitmap(bitmap);
                        ivResult.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        ivResult.setAdjustViewBounds(true);
                        appendLog("Картинка загружена успешно!");
                        Toast.makeText(MainActivity.this, "Картинка загружена!", Toast.LENGTH_SHORT).show();
                    });

                    success = true;

                } catch (java.net.SocketTimeoutException e) {
                    appendLog("⏱ Таймаут на попытке " + attempt);
                    e.printStackTrace();
                    if (attempt == maxRetries) {
                        mainHandler.post(() -> {
                            appendLog("Все попытки исчерпаны. Таймаут!");
                            Toast.makeText(MainActivity.this, "Таймаут. Попробуйте ещё раз", Toast.LENGTH_LONG).show();
                        });
                    }
                } catch (Exception e) {
                    appendLog("Ошибка на попытке " + attempt + ": " + e.getMessage());
                    e.printStackTrace();
                    if (attempt == maxRetries) {
                        mainHandler.post(() -> {
                            appendLog("Все попытки исчерпаны!");
                            Toast.makeText(MainActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                }

                if (!success && attempt < maxRetries) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}