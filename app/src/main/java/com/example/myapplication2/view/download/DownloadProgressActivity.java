package com.example.myapplication2.view.download;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication2.R;

public class DownloadProgressActivity extends AppCompatActivity {

    private DownloadProgressView downloadProgressView;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_progress);

        downloadProgressView = findViewById(R.id.download_progress);
        tvContent = findViewById(R.id.tv_content);

    }

    public void toDownload(View view) {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);

        // 设置更新监听器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                Log.d("Download", "------value------:" + value);
                downloadProgressView.setProgressPercentage(value);

                int percentage = (int)(value * 100);
                tvContent.setText("下载进度：" + String.format("%d%%", percentage));
            }
        });

        // 启动动画
        animator.start();
    }
}