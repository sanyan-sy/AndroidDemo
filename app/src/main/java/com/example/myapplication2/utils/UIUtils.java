package com.example.myapplication2.utils;

import android.content.Context;

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

}
