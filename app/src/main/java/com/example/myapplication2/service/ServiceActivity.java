package com.example.myapplication2.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication2.R;
import com.example.myapplication2.service.bindservice.DownloadActivity2;
import com.example.myapplication2.service.broadcast.DownloadActivity31;
import com.example.myapplication2.service.broadcast.DownloadActivity32;
import com.example.myapplication2.service.unbindservice.DownloadActivity;
import com.example.myapplication2.service.unbindservice.DownloadService;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void jumpToUnBindService(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void jumpToBindService(View view) {
        Intent intent = new Intent(this, DownloadActivity2.class);
        startActivity(intent);
    }

    public void jumpToBroadcast(View view) {
        Intent intent = new Intent(this, DownloadActivity31.class);
        startActivity(intent);
    }

    public void jumpToBroadcast2(View view) {
        Intent intent = new Intent(this, DownloadActivity32.class);
        startActivity(intent);
    }
}