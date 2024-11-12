package com.example.myapplication2.dispatchevent.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class MyChlidView extends AppCompatButton {
    public MyChlidView(Context context) {
        super(context);
    }

    public MyChlidView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChlidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("test", "-----chlid:onTouchEvent----- " + event.getAction());
            return false;
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.d("test", "-----chlid:onTouchEvent-----: " + event.getAction());
            performClick();
            return false;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("test", "-----chlid:onTouchEvent-----: " + event.getAction());
            return false;
        }
        return true;
    }

    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.d("test", "-----chlid-----: " + event.getAction());
//        return true;
//    }
}
