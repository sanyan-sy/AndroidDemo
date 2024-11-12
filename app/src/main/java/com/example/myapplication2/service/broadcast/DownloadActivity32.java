package com.example.myapplication2.service.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.view.download.DownloadProgressView;

public class DownloadActivity32 extends AppCompatActivity {

    private DownloadProgressView downloadProgressView;
    private TextView tvContent;

    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private LocalReciver localReciver;

    private DownloadService3.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            System.out.println("onServiceConnected: " + iBinder);
            Log.d("Service3", "-----onServiceConnected-----");
            downloadBinder = (DownloadService3.DownloadBinder) iBinder;

            int progress = downloadBinder.getDownloadProgress();
            Log.d("Service3", "-----onServiceConnected : progress-----" + progress);
            downloadProgressView.setProgressPercentage(progress);
            tvContent.setText("下载进度：" + String.format("%d%%", progress));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download31);

        tvContent = findViewById(R.id.tv_content);
        downloadProgressView = findViewById(R.id.download_progress);

        // 开启服务
        Intent intent = new Intent(this, DownloadService3.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        // 接收广播
        broadcastManager = LocalBroadcastManager.getInstance(this);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.myapplication2.DOWNLOAD_PROGRESS");

        localReciver = new LocalReciver();

        // 注册本地广播
        broadcastManager.registerReceiver(localReciver, intentFilter);
        Log.d("Service3", "-----activity：onCreate-----");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 发送广播请求当前下载进度
        Log.d("Service3", "-----activity：onStart-----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Service3", "-----activity：onResume-----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册
        broadcastManager.unregisterReceiver(localReciver);
    }

    public void toStratDownload(View view) {

        System.out.println("toStratDownload: " + downloadBinder);
        if (downloadBinder != null){
            // 开始下载
            downloadBinder.startDownload();
        }
    }

    public void toPauseDownload(View view) {
        if (downloadBinder != null){
            // 暂停下载
            downloadBinder.pauseDownload();
        }
    }

    class LocalReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if("com.example.myapplication2.DOWNLOAD_PROGRESS".equals(intent.getAction())){
                int progress = intent.getIntExtra("downloadProgress", 0);
                Log.d("Service3", "-----广播接收: " + progress);
                downloadProgressView.setProgressPercentage(progress);
                tvContent.setText("下载进度：" + String.format("%d%%", progress));
            }
        }
    }
}