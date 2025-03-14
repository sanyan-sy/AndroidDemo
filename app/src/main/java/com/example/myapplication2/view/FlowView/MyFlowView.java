package com.example.myapplication2.view.FlowView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyFlowView extends ViewGroup {
    private int horizontalSpacing = 20; // 标签之间的水平间距
    private int verticalSpacing = 15;  // 标签之间的垂直间距

    public MyFlowView(Context context) {
        super(context);
    }

    public MyFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth = widthSize - getPaddingLeft() - getPaddingRight(); // 可用宽度

        int totalHeight = getPaddingTop(); // 总高度，初始化为顶部 padding
        int currentWidth = 0; // 当前行已使用的宽度，初始化为 0
        int currentLineMaxHeight = 0; // 当前行的最大高度

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue; // 跳过不可见的子View
            }

            // 测量子View，考虑Margin
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 检查是否需要换行
            if (currentWidth + childWidth > availableWidth) {
                totalHeight += currentLineMaxHeight + verticalSpacing; // 换行，累加行高
                currentWidth = 0; // 重置当前行宽度
                currentLineMaxHeight = 0; // 重置当前行最大高度
            }

            currentWidth += childWidth + horizontalSpacing; // 更新当前行宽度
            currentLineMaxHeight = Math.max(currentLineMaxHeight, childHeight); // 更新当前行最大高度
        }

        // 添加最后一行的高度
        totalHeight += currentLineMaxHeight + getPaddingBottom();

        // 设置最终测量的宽高
        setMeasuredDimension(widthSize, resolveSize(totalHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight(); // 可用宽度
        int currentWidth = 0; // 当前行已使用的宽度，初始化为 0
        int currentHeight = getPaddingTop(); // 当前行的起始高度
        int currentLineMaxHeight = 0; // 当前行的最大高度

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue; // 跳过不可见的子View
            }

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 检查是否需要换行
            if (currentWidth + childWidth > availableWidth) {
                currentHeight += currentLineMaxHeight + verticalSpacing; // 换行，增加高度
                currentWidth = 0; // 重置当前行宽度
                currentLineMaxHeight = 0; // 重置当前行最大高度
            }

            // 计算子View的布局位置
            int left = getPaddingLeft() + currentWidth + lp.leftMargin; // 左坐标
            int top = currentHeight + lp.topMargin; // 上坐标
            int right = left + child.getMeasuredWidth(); // 右坐标
            int bottom = top + child.getMeasuredHeight(); // 下坐标

            // 布局子View
            child.layout(left, top, right, bottom);

            // 更新当前行宽度和最大高度
            currentWidth += childWidth + horizontalSpacing;
            currentLineMaxHeight = Math.max(currentLineMaxHeight, childHeight);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        // 默认生成带Margin的LayoutParams
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        // 将传入的LayoutParams转换为MarginLayoutParams
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        // 从XML中读取LayoutParams时，生成带Margin的LayoutParams
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void addTag(View tag) {
        addView(tag);
        requestLayout(); // 请求重新布局
    }
}

