package com.example.myapplication2.view.FlowView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication2.R;

/**
 * FlowLayout is much more like a {@link android.widget.LinearLayout}, but it can automatically
 * separate the widgets wrapped in it into multiple lines just like the water flow.
 * <p>
 * Inspired by {@see http://hzqtc.github.io/2013/12/android-custom-layout-flowlayout.html}
 *
 * @author liangfeizc {@see http://www.liangfeizc.com}
 */
public class FlowLayout extends ConstraintLayout {

    private static final int DEFAULT_HORIZONTAL_SPACING = 5;
    private static final int DEFAULT_VERTICAL_SPACING = 5;
    private static final int DEFAULT_MAX_LINES = 5;

    private int mVerticalSpacing;
    private int mHorizontalSpacing;
    private int maxLines;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_horizontal_spacing, DEFAULT_HORIZONTAL_SPACING);
            mVerticalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_vertical_spacing, DEFAULT_VERTICAL_SPACING);
            maxLines = a.getInteger(R.styleable.FlowLayout_max_lines, DEFAULT_MAX_LINES);
        } finally {
            a.recycle();
        }
    }


    public void setHorizontalSpacing(int pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(int pixelSize) {
        mVerticalSpacing = pixelSize;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int myWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;
        int currentLine = 0;
        // Measure each child and put the child to the right of previous child
        // if there's enough room for it, otherwise, wrap the line and put the child to next line.
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                try {
                    measureChild(child, widthMeasureSpec, heightMeasureSpec);
                } catch (Exception e) {
                    e.getMessage();
                }

            } else {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                if (currentLine >= maxLines - 1 ) {
                    break;
                }
                childLeft = paddingLeft + childWidth + mHorizontalSpacing;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
                currentLine++;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;

        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;
        int currentLine = 0;
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                if (currentLine >= maxLines) {
                    break;
                }
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
                currentLine++;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }
}
