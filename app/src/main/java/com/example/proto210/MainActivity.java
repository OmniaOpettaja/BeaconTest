package com.example.proto210;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.*;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    public static final OkHttpClient client = new OkHttpClient();
    String name;

    EditText kokonimi;

    Button submitButton;

    static Response response = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kokonimi = (EditText) findViewById(R.id.kokonimi);

        submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = kokonimi.getText().toString();
                final TextView myTextView = (TextView) findViewById(R.id.textView);
                Date aika = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(aika);
                myTextView.setText(name + strDate);
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://localhost/lasnaoloseuranta/lasnaoloseuranta.php?teksti=fölsadkjjooAndroid" + name));

                new Thread(new Runnable() {
                    public void run() {
                        // call send message here
                        try {
                            sendGet();
                        } catch (Exception e) {
                            myTextView.setText(name + e);
                        }

                    }
                }).start();


            }
        });
    }
    public static void sendGet() throws Exception {
        Request request = new Request.Builder()
                //.url("https://konejatuotanto.omnia.fi/http.php?testi=Android456")
                .url("http://192.168.8.13/beacon/testi.php?testi=Hienosti menee")
                .addHeader("custom-key", "mkyong")  // add request headers
                .addHeader("User-Agent", "OkHttp Bot")
                .build();

        // try (Response response = httpClient.newCall(request).execute()) {
        try {
            response = client.newCall(request).execute();
            //return response.body().string();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}