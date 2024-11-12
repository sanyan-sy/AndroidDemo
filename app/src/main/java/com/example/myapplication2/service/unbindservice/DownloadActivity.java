package com.example.myapplication2.service.unbindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication2.R;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
    }

    public void toStartService(View view) {
        Intent startIntent = new Intent(this, DownloadService.class);
        startService(startIntent);
    }

    public void toStopService(View view) {
        Intent stopIntent = new Intent(this, DownloadService.class);
        stopService(stopIntent);
    }
}