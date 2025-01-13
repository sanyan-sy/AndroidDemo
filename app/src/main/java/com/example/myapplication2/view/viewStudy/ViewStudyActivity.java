package com.example.myapplication2.view.viewStudy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;

public class ViewStudyActivity extends AppCompatActivity {

    private LinearLayout mainLl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study);

        mainLl = findViewById(R.id.activity_view_study_main);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_view_study_add_view, null);
        mainLl.addView(view);
    }
}