package com.example.myapplication2.view.circular2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CircularView extends View {

    private Paint paint;

    // 存储每次点击创建的圆
    private List<Circle> circleList = new ArrayList<>();

    // 点击位置
    private float touchX;
    private float touchY;

    // 比例
    private float percentage;

    public CircularView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);

        percentage = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("CircularView", "-------你点击了屏幕-------");
            // 点击后创建一个圆对象加入集合中
            Circle circle = new Circle(touchX, touchY);
            circleList.add(circle);

            // 启动动画
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(2000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    percentage = (float) animation.getAnimatedValue();
                    Log.d("CircularView", "-----scale----：" + percentage);
                    circle.setPercentage(percentage);
                    invalidate();
                }
            });

            // 动画结束后移除circle
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    circleList.remove(circle);
                }
            });
            animator.start();
//            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(Circle circle : circleList){
            paint.setAlpha((int) (255 * (1 - circle.getPercentage())));
            canvas.drawCircle(circle.getTouchX(), circle.getTouchY(), 200 * circle.getPercentage(), paint);
        }

//        if(isPress){
//            paint.setAlpha((int) (255 * (1 - scale)));
//            canvas.drawCircle(touchX, touchY, 200 * scale, paint);
//        }

    }

    private class Circle{
        float touchX;
        float touchY;

        float percentage;

        public Circle(float touchX, float touchY) {
            this.touchX = touchX;
            this.touchY = touchY;
            percentage = 0;
        }

        public float getTouchX() {
            return touchX;
        }

        public void setTouchX(float touchX) {
            this.touchX = touchX;
        }

        public float getTouchY() {
            return touchY;
        }

        public void setTouchY(float touchY) {
            this.touchY = touchY;
        }

        public float getPercentage() {
            return percentage;
        }

        public void setPercentage(float percentage) {
            this.percentage = percentage;
        }
    }
}
