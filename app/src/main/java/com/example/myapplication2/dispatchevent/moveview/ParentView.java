package com.example.myapplication2.dispatchevent.moveview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ParentView extends LinearLayout {

    // 移动后新坐标
    private float newX = 0;
    private float newY = 0;

    // 边界宽度和高度
    private int screeWidth = 0;
    private int screeHight = 0;



    // 开始的位置
    private float lastX = 0;
    private float lastY = 0;

    public ParentView(Context context) {
        super(context);

    }

    public ParentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }
    // 获取导航栏高度
    public int getNavigationBarHeight() {
        int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = getContext().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return getContext().getResources().getDimensionPixelSize(resourceId);
        } else{
            return 0;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screeWidth = getResources().getDisplayMetrics().widthPixels;
        screeHight = getResources().getDisplayMetrics().heightPixels - getStatusBarHeight() - getNavigationBarHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        定义成局部变量滑动的时候会重置为0
//        float diffX = 0;
//        float diffY = 0;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("MyView", "------event.get()-----:" + event.getX() + ", " +event.getY());
            Log.d("MyView", "------event.getRaw()-----:" + event.getRawX() + ", " +event.getRawY());
            Log.d("MyView", "------get()-----:" + getX() + ", " +  getY());
            Log.d("MyView", "------getHight()-----:" + getHeight());
            Log.d("MyView", "------getWight()-----:" + getWidth());
            Log.d("MyView", "------screeHight-----:" + screeHight);
            Log.d("MyView", "------screeWidth-----:" + screeWidth);
            Log.d("MyView", "------getMeasuredHeight-----:" + getMeasuredHeight());
            Log.d("MyView", "------getMeasuredWidth-----:" + getMeasuredWidth());
            Log.d("Dispatch", "-----ViewGroup：Down-----");
            Log.d("Dispatch", "-----ViewGroup:getRawX-----" + event.getRawX());
            Log.d("Dispatch", "-----ViewGroup:getRawY-----" + event.getRawY());
            Log.d("Dispatch", "-----Down: X-----" + getX());
            Log.d("Dispatch", "-----Down: Y-----" + getY());


            lastX = event.getRawX();
            lastY = event.getRawY();

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("Dispatch", "-----ViewGroup：Move-----");

            Log.d("move", "getRawX-before" + event.getRawX());
            Log.d("move", "getRawY-before" + event.getRawY());

            newX = event.getRawX()-lastX +getX();
            newY = event.getRawY() - lastY + getY();

            if(newX < 0){
                newX = 0;
            }else if(newX + getWidth() > screeWidth){
                newX = screeWidth - getWidth();
            }

            if(newY < 0){
                newY = 0;
            }else if(newY + getHeight() > screeHight){
                newY = screeHight - getHeight();
            }

            // 移动差值+原x y坐标
            setX(newX);
            setY(newY);
            lastX = event.getRawX();
            lastY = event.getRawY();
            Log.d("move", "getRawX-after" + event.getRawX());
            Log.d("move", "getRawY-after" + event.getRawY());
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.d("Dispatch", "-----ViewGroup：Up-----");
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }

        lastX = ev.getRawX();
        lastY = ev.getRawY();

        return false;
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        // 返回true表示事件直接被消费了，不会再将事件传给onTouchEvent，事件只传到了这里就直接返回true了
//        Log.d("Dispatch", "------dispatchTouchEvent：return true" + ev.getAction());
//        return true;
//    }


    //    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//
//        rect.set(0, 0, getWidth(), getHeight());
//        canvas.drawRect(rect, paint);
//    }
}
