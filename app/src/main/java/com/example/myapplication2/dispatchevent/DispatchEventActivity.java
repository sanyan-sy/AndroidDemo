package com.example.myapplication2.dispatchevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication2.R;
import com.example.myapplication2.dispatchevent.moveview.MyViewActivity;
import com.example.myapplication2.dispatchevent.slideconflict.SlideConflictActivity;
import com.example.myapplication2.dispatchevent.test.TestActivity;

public class DispatchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_event);
    }

    public void jumpToMyView(View view) {
        Intent intent = new Intent(this, MyViewActivity.class);
        startActivity(intent);
    }

    public void jumpToSlideConflict(View view) {
        Intent intent = new Intent(this, SlideConflictActivity.class);
        startActivity(intent);
    }

    public void jumpToTest(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

}