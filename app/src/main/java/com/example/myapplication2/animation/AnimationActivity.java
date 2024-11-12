package com.example.myapplication2.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication2.R;
import com.example.myapplication2.animation.PropertyAnimation.PropertyAnimationActivity;
import com.example.myapplication2.animation.ViewAnimation.ViewAnimationActivity;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    public void jumpToViewAnimation(View view) {
        Intent intent = new Intent(this, ViewAnimationActivity.class);
        startActivity(intent);
    }

    public void jumpToPropertyAnimation(View view) {
        Intent intent = new Intent(this, PropertyAnimationActivity.class);
        startActivity(intent);
    }
}