package com.example.myapplication2.service.bindservice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.myapplication2.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * 绑定式服务
 */
public class DownloadService2 extends Service {
    private static final String CHANNEL_ID = "download_channel";

    // 通过NotificationId匹配，可以将多个进度通知更新到指定的通知
    private static final int NOTIFICATION_ID = 1;

//    private static final String DOWNLOAD_URL = "https://via.placeholder.com/200x200.png";  // 下载文件的URL
//    private static final String SAVE_PATH = "/path/to/save/file.zip";  // 文件保存路径

    private int downloadId;

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(){
            DownloadService2.this.startDownload();
        }

        public void pauseDownload(){
            DownloadService2.this.pauseDownload();
        }
    }

    public DownloadService2() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service2", "-----Service2：onCreate-----");
        // 初始化FileDownloader
        FileDownloader.setup(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service2", "-----Service2：onStartCommand-----");
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service2", "-----Service2：onBind-----");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service2", "-----Service2：onDestroy-----");
        pauseDownload();
    }

    // 开启下载
    /**
     * 通知实现的关键类：
     * NotificationManager 通知管理器，用来发起、更新、删除通知 notify
     * NotificationChannel 通知渠道，8.0及以上配置渠道以及优先级
     * NotificationCompat.Builder 通知构造器，用来配置通知的布局显示以及操作相关
     */
    private void startDownload() {
        createNotificationChannel();

//        RemoteViews views;
//        PendingIntent intent;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img)// 小图标
                .setContentTitle("文件下载")// 标题
                .setContentText("开始下载文件...")           // 文本
                // 配置进度
                /*
                max 最大值
                progress 当前进度
                indeterminate false表示确定的进度，比如100，true表示不确定的进度，会一直显示进度动画，直到更新状态完成，或删除通知
                 */
                .setProgress(100, 0, false)
                .setOngoing(true);    // 将通知设置为持续进行

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 发起通知
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

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
                        int progress = (int) (soFarBytes  * 100.0 / totalBytes);
                        Log.d("Service2", "-----下载进度：" + progress);
                        updateNotificationProgress(notificationBuilder, progress);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        // 下载完成
                        notificationBuilder.setContentText("下载完成")
                                .setProgress(0, 0, false)
                                .setOngoing(false);
                        // 下载完成再发通知
                        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

                        Log.d("Service", "-----完成下载-----");
                        // 下载完成后停止服务
                        stopSelf();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        // 下载暂停
                        Log.d("Service", "-----暂停下载-----");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        // 下载出错
                        Log.d("Service", "-----下载出错-----");
                        // 下载出错后停止服务
                        stopSelf();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        // 下载警告
                        Log.d("Service", "-----下载出现警告-----");
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

    // 适配Android8.0以上，创建通知渠道
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "下载通知";
            int importance = NotificationManager.IMPORTANCE_LOW;
            String description = "显示下载进度";

            //创建通道对象
            /**
             * 通道ID
             * 通道名称
             * 通道重要级别
             */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            // 通道描述信息（描述信息通常不会直接显示给用户，但某些设备和 Android 版本的设置界面中可能会显示通道的描述）
            channel.setDescription(description);
            // 是否在桌面显示角标
            channel.setShowBadge(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotificationProgress(NotificationCompat.Builder builder, int progress) {
        // 1、更新进度
        /*
        max 最大值
        progress 当前进度
        indeterminate false表示确定的进度，比如100，true表示不确定的进度，会一直显示进度动画，直到更新状态完成，或删除通知
         */
        builder.setProgress(100, progress, false)
                .setContentText("正在下载文件：" + progress + "%");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 2、下载完成
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}




























//    // 开始下载
//    private void startDownload() {
//        BaseDownloadTask task = FileDownloader.getImpl().create(DOWNLOAD_URL)
//                .setPath(getFilesDir().getPath() + "/200x200.png")
//                .setListener(new FileDownloadListener() {
//                    @Override
//                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        // 下载准备中
//                    }
//
//                    @Override
//                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        // 下载进度回调
//                        int progress = (int) (soFarBytes * 100.0 / totalBytes);
//                        // 更新 UI 或显示下载进度
//                    }
//
//                    @Override
//                    protected void completed(BaseDownloadTask task) {
//                        // 下载完成
//                    }
//
//                    @Override
//                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        // 下载暂停
//                    }
//
//                    @Override
//                    protected void error(BaseDownloadTask task, Throwable e) {
//                        // 下载出错
//                    }
//
//                    @Override
//                    protected void warn(BaseDownloadTask task) {
//                        // 下载警告
//                    }
//                });
//
//        downloadId = task.start();
//    }
//
//    // 停止下载
//    private void pauseDownload() {
//        FileDownloader.getImpl().pause(downloadId);
//    }

