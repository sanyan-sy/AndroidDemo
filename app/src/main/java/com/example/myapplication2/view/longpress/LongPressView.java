package com.example.myapplication2.view.longpress;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LongPressView extends View {

    // 圆圈半径
    private float radius;
    private Paint paint;

    private boolean isLongPress;

    // 长按开始时间
    private long longPressStartTime;
    // 长按位置
    private float touchX;
    private float touchY;

    // 可以延迟执行的代码块
    private Runnable longPressRunable = new Runnable() {
        @Override
        public void run() {
            if(System.currentTimeMillis() - longPressStartTime >= 2000){
                isLongPress = true;
                invalidate();
            }
        }
    };

    public LongPressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        // 启用抗锯齿属性，是边缘更加平滑，但是会增加绘制的计算负担，降低绘制性能
        paint.setAntiAlias(true);
        radius = 50;
        isLongPress = false;
        longPressStartTime = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸事件坐标
        Log.d("LongPress", "-----------event.getX------------:" + event.getX() + " || " + getX());
        Log.d("LongPress", "-----------event.getY------------:" + event.getY() + " || " + getY());

        // 存储触摸事件坐标
        touchX = event.getX();
        touchY = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            // 如果是按下事件
            isLongPress = false;
            // 设置开始时间为系统当前时间
            longPressStartTime = System.currentTimeMillis();
            // 延迟两秒执行longPressRunnable
            /*1、使用 postDelayed() 方法延迟执行一个 Runnable 对象时，
            Android 系统会将该任务添加到一个消息队列中。
            消息队列按照任务的延迟时间进行排序，以便按照预定的时间顺序依次执行任务
            2、消息队列是与主线程相关联的，因此 removeCallbacks(longPressRunnable)
            方法只会影响主线程消息队列中的任务，而不会影响其他线程的任务队列。*/
            postDelayed(longPressRunable, 2000);
        }else {
            removeCallbacks(longPressRunable);
            // 长按事件成功触发后再将isLongPress设为false
            if(isLongPress){
                isLongPress = false;
                /*虽然在取消长按事件后 CircleView 的绘制可能已经被取消了，
                但调用 invalidate() 可以确保界面状态的一致性，以及在需要时重新绘制其他可能受影响的视图。
                因此，使用 invalidate() 是为了保持界面的一致性和正确性，尽管在某些情况下可能看起来多余。*/
                invalidate();
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("LongPress", "-----------getX------------:" + getX());
        Log.d("LongPress", "-----------getY------------:" + getY());
        // 获取当前视图的宽度和高度
        Log.d("LongPress", "-----------getWidth------------:" + getWidth());
        Log.d("LongPress", "-----------getHeigh------------:" + getHeight());

        if(isLongPress){
            canvas.drawCircle(touchX, touchY, radius, paint);

            // 设置视图缩放和旋转的中心坐标 （默认情况下缩放和旋转的中心点是视图的左上角）
            setPivotX(touchX);
            setPivotY(touchY);

//            setPivotX(getWidth());
//            setPivotY(getHeight());

            // 设置动画（同时控制多个属性的动画）
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX",1f,1.2f,1f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY",1f,1.2f,1f);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY);
            animator.setDuration(500);

            animator.start();
        }

    }
}
