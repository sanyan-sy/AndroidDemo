package com.example.myapplication2.view.download;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DownloadProgressView extends View {

    private Path path;
    private Paint paint;
    // 进度比例
    private float progressPercentage;
    // 创建矩形对象
    private RectF progressRect;


    public DownloadProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        path = new Path();
        paint = new Paint();
        // 设置画笔颜色
        paint.setColor(Color.BLUE);
        // 设置绘制样式
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        progressPercentage = 0;
        progressRect = new RectF();
    }

    public void setProgressPercentage(float progressPercentage) {
        this.progressPercentage = progressPercentage;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 200;  // 默认宽度为200像素
        int desiredHeight = 100;  // 默认高度为100像素

        // 测量视图的宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        // 测量视图的高度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 裁剪画布
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        path.addRoundRect(rectF, getHeight()/2, getHeight()/2, Path.Direction.CW);
        canvas.clipPath(path);

        // 背景颜色
        /*tip：如果在裁剪之前就给画布设置颜色的话，裁剪是没有效果的
        * 因为在裁剪之前就已经给画布设置了颜色，所以即使裁剪路径之外的区域应该是透明的，但它仍然会显示之前已经设置的颜色*/
        canvas.drawColor(Color.WHITE);

        // 根据进度比例设置宽度
        float width = getWidth() * (float)(progressPercentage / 100);
        progressRect.set(0, 0, width, getHeight());

        canvas.drawRoundRect(progressRect, getHeight()/2, getHeight()/2, paint);


    }

}


//public class DownloadProgressView extends View {
//    private float progress;  // 下载进度
//    private Paint paint;  // 绘制进度的画笔
//
//    public DownloadProgressView(Context context) {
//        super(context);
//        init();
//    }
//
//    public DownloadProgressView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public DownloadProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        paint = new Paint();
//        paint.setColor(Color.BLUE);
//        paint.setStyle(Paint.Style.FILL);
//    }
//
//    public void setProgress(float progress) {
//        this.progress = progress;
//        invalidate();  // 通知视图进行重绘
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        // 绘制背景
//        canvas.drawColor(Color.WHITE);
//
//        // 绘制进度条
//        float width = getWidth() * progress;
//        canvas.drawRect(0, 0, width, getHeight(), paint);
//    }
//}
