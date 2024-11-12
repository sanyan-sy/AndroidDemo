package com.example.myapplication2.drag;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleDragSwipeCallBack extends ItemTouchHelper.Callback {

    private ItemDragSwipeListener mAdapter;

    public SimpleDragSwipeCallBack(ItemDragSwipeListener listener) {
        // 注入
        this.mAdapter = listener;
    }

    /**
     * 确定拖拽、滑动支持的方向
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 允许上下拖拽
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        // 禁用滑动
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖拽交换事件
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        try {
            if (mAdapter == null) {
                return false;
            }
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            Log.d("testDrag", "onMove(拖拽交换事件): " + viewHolder.getAdapterPosition() + " ----- " + target.getAdapterPosition());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 暂时不处理滑动操作，so不实现
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 启用长按拖拽
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 禁用滑动
        return false;
    }

//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        super.onSelectedChanged(viewHolder, actionState);
//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
//            viewHolder.itemView.setAlpha(0.7f); // 拖拽时改变item的透明度
//        }
//    }
//
//    @Override
//    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//        viewHolder.itemView.setAlpha(1.0f); // 拖拽结束后恢复透明度
//    }
}
