package com.example.myapplication2.view.Ripple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class RippleView extends View {
    private static final int DEFAULT_COLOR = Color.BLUE;
    private static final int DEFAULT_DURATION = 500;

    private List<Ripple> rippleList;
    private Paint paint;
    private int rippleColor;
    private int animationDuration;

    public RippleView(Context context) {
        super(context);
        init();
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        rippleList = new ArrayList<>();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        rippleColor = DEFAULT_COLOR;
        animationDuration = DEFAULT_DURATION;
        paint.setColor(rippleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Ripple ripple : rippleList) {
            int alpha = ripple.getAlpha();
            int radius = ripple.getRadius();
            paint.setAlpha(alpha);
            canvas.drawCircle(ripple.getX(), ripple.getY(), radius, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            createRipple(x, y);
            invalidate();
        }
        return true;
    }

    public void createRipple(float x, float y) {
        final Ripple ripple = new Ripple(x, y);
        rippleList.add(ripple);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                ripple.setScale(scale);
                invalidate();
            }
        });
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
        private float x;
        private float y;
        private float scale;
        private int alpha;

        public Ripple(float x, float y) {
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

        public int getRadius() {
            return (int) (scale * 100);
        }

        public int getAlpha() {
            return alpha;
        }

        public void setScale(float scale) {
            this.scale = scale;
            this.alpha = (int) (255 * (1 - scale));
        }
    }
}