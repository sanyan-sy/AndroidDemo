package com.example.myapplication2.channel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication2.BuildConfig;
import com.example.myapplication2.R;

public class ChannelActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        imageView = findViewById(R.id.imageView);


        // 根据渠道标识设置对应的图片资源
        if (BuildConfig.CHANNEL_ID.equals("channelA")) {
            // 渠道A的逻辑代码
            imageView.setImageResource(R.drawable.img1);
        } else if (BuildConfig.CHANNEL_ID.equals("channelB")) {
            // 渠道B的逻辑代码
            imageView.setImageResource(R.drawable.img2);
        } else {
            // 默认情况下的逻辑代码
            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
    }
}