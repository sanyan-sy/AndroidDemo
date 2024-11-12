package com.example.myapplication2.dispatchevent.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyParentView extends LinearLayout {

    private OnClickListener clickListener;

    public MyParentView(Context context) {
        super(context);
    }

    public MyParentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("test", "-----parent:dispatchTouchEvent-----: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("test", "-----parent:onTouchEvent----- " + event.getAction());
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("test", "-----parent:onTouchEvent-----: " + event.getAction());
            return false;
        }else if(event.getAction() == MotionEvent.ACTION_UP && clickListener != null){
            Log.d("test", "-----parent:onTouchEvent-----: " + event.getAction());
            clickListener.onClick(this);
            return false;
        }
        return true;
    }

    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
