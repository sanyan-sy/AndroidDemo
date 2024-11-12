package com.example.myapplication2.view.picture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class RoundImageView extends AppCompatImageView {

    private Paint paint;
    private Path clipPath;
    private float radius;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        clipPath = new Path();
        // 设置圆角半径
        radius = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
            /*绘制圆角路径*/
            // 创建矩形对象
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            // 重置裁剪路径（清除之前的裁剪路径，确保裁剪路径是空的）
            clipPath.reset();
            // 将矩形路径添加到裁剪路径clipPath中 Path.Direction.CW：顺时针方向创建路径
            clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
            // 将画布按裁剪路进行径裁剪
            canvas.clipPath(clipPath);
            super.onDraw(canvas);

    }
}
