package com.example.myapplication2.animation.PropertyAnimation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication2.R;

public class PropertyAnimationActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    private AnimatorSet animatorSet;
    private ObjectAnimator animator2;
    private ValueAnimator animator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        button1 = findViewById(R.id.bt_property_animation1);
        button2 = findViewById(R.id.bt_property_animation2);
        button3 = findViewById(R.id.bt_property_animation3);
    }


    public void toPropertyAnimation1(View view) {
//        toPropertyAnimation2(view);
//        toPropertyAnimation3(view);

        animatorSet = new AnimatorSet();
        // 同时播放
        animatorSet.play(animator3).with(animator2);
//        // 按指定顺序播放动画
//        animatorSet.playSequentially(animator3, animator2);

//        // 同时播放多个动画
//        animatorSet.playTogether(animator3, animator2);

        animatorSet.setDuration(2000);
        animatorSet.start();

//        ObjectAnimator animator = ObjectAnimator.ofFloat(myButton, "rotation", 0, 200);
//        animator.setDuration(2000);
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.d("Property", "--------value:---------" + animation.getAnimatedValue());
//            }
//        });
//
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                Log.d("Property", "--------动画开始---------");
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Log.d("Property", "--------动画结束---------");
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                Log.d("Property", "--------onAnimationCancel---------");
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                Log.d("Property", "--------onAnimationRepeat---------");
//            }
//        });
//
//        animator.start();
    }

    public void toPropertyAnimation2(View view) {
        animator2 = ObjectAnimator.ofFloat(button2,"translationX",0,200);
        animator2.setDuration(2000);
        animator2.start();
    }

    public void toPropertyAnimation3(View view) {
        animator3 = ValueAnimator.ofFloat(0,200);
        animator3.setDuration(2000);

        // 设置更新监听器
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();

                button3.setTranslationY(value);
            }
        });

        // 启动动画
        animator3.start();
    }
}