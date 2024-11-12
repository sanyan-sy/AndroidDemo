package com.example.myapplication2.dispatchevent.moveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class ChildView extends AppCompatButton {

    public ChildView(Context context) {
        super(context);
        init();
    }

    public ChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "你点击了按钮", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("Dispatch", "-----View:Down-----");
            Log.d("Dispatch", "-----View:getRawX-----" + event.getRawX());
            Log.d("Dispatch", "-----View:getRawY-----" + event.getRawY());
            getParent().requestDisallowInterceptTouchEvent(true);
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.d("Dispatch", "-----View：Up-----");
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你点击了按钮", Toast.LENGTH_SHORT).show();
                }
            });
//            performClick();
        }

        return true;
    }

    // 不处理，调用父view的onTouchEvent
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        return false;
//    }
}

















//public class ChildView extends AppCompatButton {
//
//    private Paint paint;
//
//    private float diffX = 0;
//    private float diffY = 0;
//
//    // 移动后新坐标
//    private float newX = 0;
//    private float newY = 0;
//
//    // 屏幕宽度和高度
//    private int screeWidth = 0;
//    private int screeHight = 0;
//
//
//    public ChildView(Context context) {
//        super(context);
//
//        init();
//    }
//
//    public ChildView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//
//        init();
//    }
//
//    private void init(){
//        paint = new Paint();
//        paint.setColor(Color.BLUE);
//        paint.setStyle(Paint.Style.FILL);
//
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.d("Dispatch", "-----Click-----: " + "你点击了按钮");
//                Toast.makeText(getContext(), "Button clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
////            Log.d("MyView", "------event.get()-----:" + event.getX() + ", " +event.getY());
////            Log.d("MyView", "------event.getRaw()-----:" + event.getRawX() + ", " +event.getRawY());
////            Log.d("MyView", "------get()-----:" + getX() + ", " +  getY());
////            Log.d("MyView", "------getHight()-----:" + getHeight());
////            Log.d("MyView", "------getWight()-----:" + getWidth());
////            Log.d("MyView", "------screeHight-----:" + screeHight);
////            Log.d("MyView", "------screeWidth-----:" + screeWidth);
//
//            Log.d("Dispatch", "-----View：Down-----");
////            Toast.makeText(this.getContext(), "你点击了按钮", Toast.LENGTH_SHORT).show();
//            diffX = event.getRawX() - getX();
//            diffY = event.getRawY() - getY();
//            return true;
//
//        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
//            Log.d("Dispatch", "-----View：Move-----");
//
//            newX = event.getRawX() - diffX;
//            newY = event.getRawY() - diffY;
//
//            if(newX < 0){
//                newX = 0;
//            }else if(newX + getWidth() > screeWidth){
//                newX = screeWidth - getWidth();
//            }
//
//            if(newY < 0){
//                newY = 0;
//            }else if(newY + getHeight() > screeHight){
//                newY = screeHight - getHeight();
//            }
//
//            // 移动差值+原x y坐标
//            setX(event.getRawX() - diffX);
//            setY(event.getRawY() - diffY);
////            invalidate();
//            return true;
//        }else if (event.getAction()  == MotionEvent.ACTION_UP){
//            Log.d("Dispatch", "-----View：Up-----");
//            return true;
//        }
//
//        return super.onTouchEvent(event);
//    }
//
//
//
//    //    @Override
////    public boolean dispatchTouchEvent(MotionEvent event) {
////        if(event.getAction() == MotionEvent.ACTION_MOVE){
////            return false;
////        }
////        return super.dispatchTouchEvent(event);
////    }
//}
