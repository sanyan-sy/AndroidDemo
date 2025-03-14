package com.example.myapplication2.view.viewStudy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;

public class ViewStudyActivity extends AppCompatActivity {

    private LinearLayout mainLl;
    private TextView testMeasureTv;

    private View testMeasureView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study);

        mainLl = findViewById(R.id.activity_view_study_main);
        testMeasureTv = findViewById(R.id.test_measure_tv);
        testMeasureView = findViewById(R.id.test_measure_view);
//
//        View view = LayoutInflater.from(this).inflate(R.layout.activity_view_study_add_view, null);
//        mainLl.addView(view);
        testMeasureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testData", "testMeasureView.getMeasuredWidth(): " + testMeasureView.getMeasuredWidth());
                Log.d("testData", "testMeasureView.getMeasuredHeight(): " + testMeasureView.getMeasuredHeight());
                Log.d("testData", "testMeasureView.getWidth(): " + testMeasureView.getWidth());
                Log.d("testData", "testMeasureView.getHeight(): " + testMeasureView.getHeight());
            }
        });
    }
}