package com.example.myapplication2.viewpager;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public MyItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    // 设置item四周的边距，效果和margin类似
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = spacing;
        // 如果是第一项，设置顶部间距
        // 返回给定子视图对应的适配器位置
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.top = spacing;
        }
    }
}
