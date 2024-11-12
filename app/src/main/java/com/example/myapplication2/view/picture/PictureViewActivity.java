package com.example.myapplication2.view.picture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication2.R;

public class PictureViewActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageGlide;
    private ImageView imageShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        initView();

        // 2、使用Glide
        RequestOptions requestOptions = new RequestOptions()
// 设置占位符图片  .placeholder(R.drawable.img)
// 设置错误占位符图片  .error(R.drawable.img)
                .override(500, 500) // 设置图片的尺寸大小
                .centerCrop() // 居中裁剪图片
                .fitCenter()

                .transform(new RoundedCorners(50)); // 设置圆角效果，半径为20

//        https://via.placeholder.com/200x200.png
        Glide.with(this)
                .load(R.drawable.img)
                .apply(requestOptions)
                .into(imageGlide);


        // 3、使用shape
        //imageShape.setImageResource(R.drawable.img);
    }

    private void initView() {
        imageView = findViewById(R.id.iamge_view);
        imageGlide = findViewById(R.id.iamge_glide);
        imageShape = findViewById(R.id.iamge_shape);
    }
}