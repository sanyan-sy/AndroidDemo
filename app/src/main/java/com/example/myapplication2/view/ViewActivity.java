package com.example.myapplication2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication2.R;
import com.example.myapplication2.view.FlowView.FlowViewActivity;
import com.example.myapplication2.view.circular.CustomRippleViewActivity;
import com.example.myapplication2.view.circular2.CircularActivity;
import com.example.myapplication2.view.download.DownloadProgressActivity;
import com.example.myapplication2.view.longpress.LongPressActivity;
import com.example.myapplication2.view.longpress.LongPressView;
import com.example.myapplication2.view.picture.PictureViewActivity;
import com.example.myapplication2.view.viewStudy.ViewStudyActivity;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
    }

    public void ToPictureView(View view) {
        Intent intent = new Intent(this, PictureViewActivity.class);
        startActivity(intent);
    }

    public void jumpToRipple(View view) {
        Intent intent = new Intent(this, CustomRippleViewActivity.class);
        startActivity(intent);
    }

    public void jumpToLongPress(View view) {
        Intent intent = new Intent(this, LongPressActivity.class);
        startActivity(intent);
    }

    public void jumpToDownloadProgress(View view) {
        Intent intent = new Intent(this, DownloadProgressActivity.class);
        startActivity(intent);
    }

    public void jumpToCircular2(View view) {
        Intent intent = new Intent(this, CircularActivity.class);
        startActivity(intent);
    }

    public void jumpFlowView(View view) {
        Intent intent = new Intent(this, FlowViewActivity.class);
        startActivity(intent);
    }

    public void jumpViewTest(View view) {
        Intent intent = new Intent(this, ViewStudyActivity.class);
        startActivity(intent);
    }
}