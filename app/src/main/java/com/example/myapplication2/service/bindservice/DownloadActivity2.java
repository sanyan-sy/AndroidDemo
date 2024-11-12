package com.example.myapplication2.service.bindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.myapplication2.R;
import com.example.myapplication2.service.unbindservice.DownloadService;
import com.example.myapplication2.view.download.DownloadProgressView;

public class DownloadActivity2 extends AppCompatActivity {

    // 将活动与服务进行绑定
    private DownloadService2.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        // 活动与服务成功绑定时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d("Service2", iBinder.toString());
            // 获取DownloadBinder实例 （之后就能调用里面的方法了）
            downloadBinder = (DownloadService2.DownloadBinder) iBinder;
        }

        // 活动与服务解除绑定时调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download2);

// 绑定服务
        Intent intent = new Intent(this, DownloadService2.class);
        /**
         * 第三个参数：标志位
         * BIND_AUTO_CREATE：在活动和服务进行绑定后自动创建服务（会使onCreate执行，但onStartCommand不会执行）
         */
        bindService(intent, connection, BIND_AUTO_CREATE);
        Log.d("Service2", "-------activity：onCreate-----");
    }

    public void toStartDownload(View view) {

        Log.d("Service2", String.valueOf(downloadBinder));
        if(downloadBinder != null){
            Log.d("Service2", "-----开始下载-----");
            downloadBinder.startDownload();
        }
    }

    public void toStopDownload(View view) {
        if (downloadBinder != null){
            Log.d("Service2", "-----暂停下载-----");
            downloadBinder.pauseDownload();
        }
    }
}