package com.example.myapplication2.assets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication2.R;

import java.io.IOException;
import java.io.InputStream;

public class AssetsActivity extends AppCompatActivity {

    private ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);

        imageView = findViewById(R.id.image_view);

        // 获取AssetManager对象
        AssetManager assetManager = getAssets();


        try {
            // 获取指定路径下的所有文件和子目录名称
            String[] fileList = assetManager.list("picture");
            for(String fileName : fileList){
                Log.d("Asset", fileName);
            }


            /*加载图片资源*/
            // 打开一个指定文件名的输入流
            /**
             * InputStream open(String fileName, int accessMode); 以指定模式打开文件
             * ACCESS_UNKNOWN : 未指定具体的读取模式
             * ACCESS_RANDOM : 随机读取
             * ACCESS_STREAMING : 顺序读取
             * ACCESS_BUFFER : 缓存读取
             */
            InputStream inputStream = assetManager.open("picture/img.png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}