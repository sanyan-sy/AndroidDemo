package com.example.myapplication2.view.TagCloud;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TagCloudView extends ViewGroup {
    private int horizontalSpacing = 20; // 标签之间的水平间距
    private int verticalSpacing = 15;  // 标签之间的垂直间距

    public TagCloudView(Context context) {
        super(context);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec); // 可用宽度
        int totalHeight = getPaddingTop();                // 初始化总高度
        int currentWidth = getPaddingLeft();              // 当前行已使用的宽度

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec); // 测量子控件
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 检查是否需要换行
            if (currentWidth + childWidth + getPaddingRight() > width) {
                currentWidth = getPaddingLeft(); // 换行，重置当前宽度
                totalHeight += childHeight + verticalSpacing; // 增加高度
            }

            currentWidth += childWidth + horizontalSpacing; // 更新当前行宽度
        }

        totalHeight += getPaddingBottom(); // 加上底部内边距
        setMeasuredDimension(width, totalHeight); // 设置控件的最终宽高
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth(); // 控件的总宽度
        int currentWidth = getPaddingLeft(); // 当前行的起始宽度
        int currentHeight = getPaddingTop(); // 当前行的起始高度

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 换行逻辑
            if (currentWidth + childWidth + getPaddingRight() > width) {
                currentWidth = getPaddingLeft(); // 重置当前宽度
                currentHeight += childHeight + verticalSpacing; // 增加高度
            }

            // 布局当前子控件
            child.layout(
                    currentWidth,
                    currentHeight,
                    currentWidth + childWidth,
                    currentHeight + childHeight
            );

            currentWidth += childWidth + horizontalSpacing; // 更新当前行宽度
        }
    }

}

