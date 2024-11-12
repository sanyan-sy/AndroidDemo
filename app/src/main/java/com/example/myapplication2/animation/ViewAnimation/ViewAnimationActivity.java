package com.example.myapplication2.animation.ViewAnimation;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;

public class ViewAnimationActivity extends AppCompatActivity {

    private Button myButton;
    private Button myButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        myButton = findViewById(R.id.bt_view_animation);
        myButton2 = findViewById(R.id.bt_view_animation2);
    }

    public void toViewAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);

        // 平移
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
        animation1.setDuration(2000);
        // 渐变
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        animation1.setDuration(2000);
        // 缩放
        Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        animation1.setDuration(2000);

        animationSet.addAnimation(animation1);
        animationSet.addAnimation(animation3);
//        animationSet.addAnimation(animation2);


        myButton.startAnimation(animationSet);

        /*组合动画*/
//        AnimationSet animationSet = new AnimationSet(true);
//
//        // 平移动画
//        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
//        translateAnimation.setDuration(1000);
//        animationSet.addAnimation(translateAnimation);
//
//        // 缩放动画
//        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
//        scaleAnimation.setDuration(1000);
//        animationSet.addAnimation(scaleAnimation);
//
//        // 渐变动画
//        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
//        rotateAnimation.setDuration(1000);
//        animationSet.addAnimation(rotateAnimation);
//
//        myButton.setAnimation(animationSet);
//        animationSet.start();
    }

    public void toViewAnimation2(View view) {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.set_animation);
        myButton2.startAnimation(animation);
    }
}