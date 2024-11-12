package com.example.myapplication2.ViewStub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.R;

public class ViewStubActivity extends AppCompatActivity {

//    private boolean isCreateText = false;
//    private boolean isCreateImage = false;
    private ViewStub stubText;
    private ViewStub stubImage;

    private View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);

        stubText = findViewById(R.id.view_stu_text);
        stubImage = findViewById(R.id.view_stu_image);
    }

    public void toShowText(View view) {
        // ViewStub的setVisibility 成View.VISIBLE或INVISIBLE如果是首次使用，
        // 都会自动inflate其指向的布局文件，并替换ViewStub本身，
        // 再次使用则是相 当于对其指向的布局文件设置可见性。
        stubText.setVisibility(View.VISIBLE);
//        view1 = stubText.inflate();   这里也不能直接这样写，可以加一个flat用来判断
//        TextView textView = view1.findViewById(R.id.text_view_stub);
        TextView textView = findViewById(R.id.text_view_stub);   // 这样的话它会从view树的根开始巴拉巴拉（也算一个优化吧）
        textView.setText("啦啦啦啦啦啦啦");
    }

    public void toShowImage(View view) {
        stubImage.setVisibility(View.VISIBLE);
        ImageView imageView = findViewById(R.id.image_view_stub);
        imageView.setImageResource(R.drawable.img);
    }

    public void toCloseText(View view) {
        stubText.setVisibility(View.GONE);
    }

    public void toCloseImage(View view) {
        stubImage.setVisibility(View.GONE);
    }
}