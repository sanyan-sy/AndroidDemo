package com.example.myapplication2.dispatchevent.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication2.R;

public class TestActivity extends AppCompatActivity {

    private MyParentView myParentView;
    private MyChlidView myChlidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        myParentView = findViewById(R.id.my_parent_view);
        myChlidView = findViewById(R.id.my_child_view);

        myParentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("test", "-----parent:onTouch-----: " + event.getAction());
                return false;
            }
        });

        myParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "-----parent:onClick-----: ");
            }
        });


        myChlidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "-----chlid:onClick-----: ");
            }
        });
    }
}