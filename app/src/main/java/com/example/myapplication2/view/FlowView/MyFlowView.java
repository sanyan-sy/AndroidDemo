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

    /**
     * 负责 测量控件及其子控件的大小，即为每个子控件分配宽高空间，并确定整个 ViewGroup 的宽高。
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     *
     */
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

    /**
     * 定位控件及其子控件的位置（确定每个控件在父容器中的具体坐标），即根据测量结果，将每个子控件放到具体的坐标位置上
     * @param changed This is a new size or position for this view
     * @param l Left position, relative to parent
     * @param t Top position, relative to parent
     * @param r Right position, relative to parent
     * @param b Bottom position, relative to parent
     *
     * onLayout 方法会被系统自动调用，前提是控件已经完成了 测量（onMeasure）
     */
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

            // 布局当前子控件（每个子控件的位置由这四个值决定，坐标值是相对于父控件左上角的）
            child.layout(
                    currentWidth,                           // 左坐标
                    currentHeight,                          // 上坐标
                    currentWidth + childWidth,              // 右坐标
                    currentHeight + childHeight             // 下坐标
            );

            currentWidth += childWidth + horizontalSpacing; // 更新当前行宽度
        }
    }

    public void addTag(View tag){
        addView(tag);
        requestLayout();
    }

}

