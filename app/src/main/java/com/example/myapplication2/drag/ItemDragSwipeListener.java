package com.example.myapplication2.drag;

/**
 * item拖拽、滑动监听
 */
public interface ItemDragSwipeListener {

    /**
     * 拖拽item（两个item交换位置）
     * @param fromPosition 第一个item位置
     * @param toPosition   第二个item位置
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * 删除item
     * @param position 要删除的item的位置
     */
    void onItemDelete(int position);

}
