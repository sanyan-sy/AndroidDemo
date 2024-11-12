package com.example.myapplication2.view.circular;

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
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

public class CustomRippleView extends View {
    // 默认波纹的颜色
    private static final int DEFAULT_COLOR = Color.BLUE;
    // 默认动画持续时间（500毫秒）
    private static final int DEFAULT_DURATION = 500;

    // 存储所有波纹效果的列表
    private List<Ripple> rippleList;
    // 绘制波纹的画笔
    private Paint paint;
    // 存储波纹颜色
    private int rippleColor;
    // 存储动画持续时间
    private int animationDuration;

    public CustomRippleView(Context context) {
        super(context);
        init();
    }

    public CustomRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    // 初始化变量
    private void init() {
        rippleList = new ArrayList<>();
        paint = new Paint();
        // 设置画笔绘制样式
        // Paint.Style.STROKE：绘制的图形只有边框线而没有填充
        paint.setStyle(Paint.Style.STROKE);
        // 设置画笔线条宽度（像素）
        paint.setStrokeWidth(3);
        // 从资源文件中获取波纹颜色
        rippleColor = getResources().getColor(R.color.rippleColor);
        // 设置画笔绘制的颜色
        paint.setColor(rippleColor);
        // 设置默认动画时长
        animationDuration = DEFAULT_DURATION;
    }

    // 绘制所有波纹的效果（每当需要重新绘制视图时，系统会自动调用该方法）
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 遍历list中的每个波纹对象，获取透明度和半径，并使用画笔绘制对应的空心圆
        for (Ripple ripple : rippleList) {
            int alpha = ripple.getAlpha();
            int radius = ripple.getRadius();
            paint.setAlpha(alpha);
            // 在画布上绘制圆形 （Android中每个视图都关联一个Canvas对象）
            /*Canvas 是一个绘图工具，它只提供了绘制的功能，
            并不负责处理用户交互或响应事件。在绘制时，
            你可以使用 Canvas 来绘制视觉效果，但如果需要与用户交互，
            还需要结合其他的事件处理机制，如触摸事件监听器等。*/
            canvas.drawCircle(ripple.getX(), ripple.getY(), radius, paint);
        }
    }

    // 处理触摸事件，在点击位置创建波纹效果
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 按下时
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            createRipple(x, y);
            // 请求重新绘制视图
            invalidate();
        }
        return true;
    }

    // 创建波纹对象并启动动画
    public void createRipple(float x, float y) {
        final Ripple ripple = new Ripple(x, y);
        rippleList.add(ripple);

        // 创建值动画对象（随着动画的执行，系统会根据时间的推移计算当前动画值）
        ValueAnimator animator = ValueAnimator.ofFloat(0, 3);
        // 设置动画持续时间
        animator.setDuration(animationDuration);
        // 设置动画的插值器
        // AccelerateInterpolator：加速插值器，使得动画在开始时速度较慢，然后逐渐加速。
        animator.setInterpolator(new AccelerateInterpolator());

        // 每次动画值更新时触发回调
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                ripple.setScale(scale);
                invalidate();
            }
        });

        // 动画结束后进行回调
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rippleList.remove(ripple);
                invalidate();
            }
        });
        animator.start();
    }

    private static class Ripple {
        // 波纹中心坐标
        private float x;
        private float y;
        // 缩放比例  0-1   0:缩小为不可见  1：原始尺寸
        private float scale;
        // 透明度 0-255，根据缩放比例动态计算
        private int alpha;

        public Ripple(float x, float y) {
            // 初始中心坐标
            this.x = x;
            this.y = y;
            this.scale = 0;
            this.alpha = 255;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        // 获取半径
        public int getRadius() {
            return (int) (scale * 100);
        }

        public int getAlpha() {
            return alpha;
        }

        // 设置缩放比例，透明度根据缩放比例动态计算
        public void setScale(float scale) {
            this.scale = scale;
            this.alpha = (int) ((255/3) * (3 - scale));
        }
    }
}

