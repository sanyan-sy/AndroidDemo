package com.example.myapplication2.service.broadcast;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

public class DownloadService3 extends Service {

    private LocalBroadcastManager broadcastManager;
    private int downloadId;
    // 在下进度百分比值
    private int downloadProgress = -1;
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(){
            DownloadService3.this.startDownload();
        }

        public void pauseDownload(){
            DownloadService3.this.pauseDownload();
        }

        public int getDownloadProgress(){
            return DownloadService3.this.getDownloadProgress();
        }
    }

    public DownloadService3() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service3", "-----Service3：onCreate-----");
        // 获取本地广播管理器对象实例
        broadcastManager = LocalBroadcastManager.getInstance(this);
        // 初始化FileDownloader
        FileDownloader.setup(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service3", "-----Service3：onStartCommand-----");
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service3", "-----Service3：onBind-----");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service3", "-----Service3：onDestroy-----");
        // 暂停下载
        pauseDownload();

        // 保存下载进度
        if(downloadProgress != -1){
            // 继续下载了才更新，否则不更新值
            saveDownloadProgress(downloadProgress);
        }

    }

    public int getDownloadProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences("DownloadProgress", MODE_PRIVATE);
        return sharedPreferences.getInt("progress", 0);
    }

    private void saveDownloadProgress(int progress) {
        SharedPreferences.Editor editor = getSharedPreferences("DownloadProgress", MODE_PRIVATE).edit();
        editor.putInt("progress", progress);
        editor.apply();
    }

    // 开启下载
    private void startDownload() {
//        RemoteViews views;
//        PendingIntent intent;
        // 创建下载任务
        BaseDownloadTask task = FileDownloader.getImpl().create("https://redirector.gvt1.com/edgedl/android/studio/install/2021.1.1.11/android-studio-2021.1.1.11-windows.exe")  // 要下载文件的URL
                .setPath(getFilesDir().getPath() + "/android-studio-2021.1.1.11-windows.exe")    // 保存地址

                // 设置进度回调
                // 设置下载任务的监听器，该监听器将在下载过程中接收各种回调事件，例如下载准备中、下载进度、下载完成、下载暂停、下载出错等。
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        // 下载准备中回调
                        Log.d("Service2", "-----下载准备中-----");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        // 下载过程中回调
                        /**
                         * 下载进度更新：
                         * sofarBytes：已下载的字节数
                         * totalBytes：总字节数
                         */
//                        int progress = (int) (soFarBytes / totalBytes  * 100.0 );   //计算下载进度的百分比值
                        downloadProgress = (int) (soFarBytes  * 100.0 / totalBytes);
                        // 下载进度更新的时候发送广播
                        sendProgressBroadcast(downloadProgress);

                        Log.d("Service3", "-----下载进度：" + downloadProgress);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        // 下载完成
                        Log.d("Service3", "-----完成下载-----");
                        // 下载完成后停止服务
                        stopSelf();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        // 下载暂停
                        Log.d("Service3", "-----暂停下载-----");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        // 下载出错
                        Log.d("Service3", "-----下载出错-----");
                        // 下载出错后停止服务
                        stopSelf();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        // 下载警告
                        Log.d("Service3", "-----下载出现警告-----");
                    }
                });

        // 启动下载任务并返回一个下载任务的标识
        downloadId = task.start();
    }

    // 停止下载
    private void pauseDownload() {
        // 暂停下载任务
        // 会调用FileDownloadListener中的pause
        FileDownloader.getImpl().pause(downloadId);
    }

    // 发送广播
    private void sendProgressBroadcast(int progress){
        Intent intent = new Intent("com.example.myapplication2.DOWNLOAD_PROGRESS");
        intent.putExtra("downloadProgress", progress);
        broadcastManager.sendBroadcast(intent);
    }
}












//    private DownloadProgressListener downloadProgressListener;
//    public void setDownloadProgressListener(DownloadProgressListener listener){
//        this.downloadProgressListener = listener;
//    }
//    public interface DownloadProgressListener {
//        void onProgressUpdate(int progress);
//    }