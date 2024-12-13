package com.example.myapplication2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * 和UI操作相关的工具类
 */

public class UIUtils {

    private static volatile UIUtils uiUtils;

    private static UIUtils getInstance() {
        if (uiUtils == null) {
            synchronized (UIUtils.class) {
                if (uiUtils == null) {
                    uiUtils = new UIUtils();
                }
            }
        }
        return uiUtils;
    }

    public static int dip2px(Context context, int dp){
        return dip2px(context, (float) dp);
    }

    public static int dip2px(Context context, float dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }


    /**
     * View已经在界面上展示了，可以直接获取View的缓存
     * 对View进行量测，布局后生成View的缓存
     * View为固定大小的View，包括TextView,ImageView,LinearLayout,FrameLayout,RelativeLayout等
     * @param view 截取的View,View必须有固定的大小，不然drawingCache返回null
     * @return 生成的Bitmap
     */
    public static Bitmap captureView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        // 重新测量一遍View的宽高
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        // 确定View的位置
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(),
                (int) view.getY() + view.getMeasuredHeight());
        // 生成View宽高一样的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

}
