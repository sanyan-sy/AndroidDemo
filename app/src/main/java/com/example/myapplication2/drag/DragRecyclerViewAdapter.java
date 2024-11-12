package com.example.myapplication2.drag;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * 描述：拖拽demo列表adapter
 */
public class DragRecyclerViewAdapter extends RecyclerView.Adapter<DragRecyclerViewAdapter.ViewHolder> implements ItemDragSwipeListener {

    private Context mContext;
    private List<String> mDataList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contentTv;
        private View shadowView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.item_drag_tv);
            shadowView = itemView.findViewById(R.id.item_drag_test_shadow_view);
        }
    }

    public DragRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (mDataList == null || position < 0 || position >= mDataList.size()) {
                return;
            }
            holder.contentTv.setText(mDataList.get(position));
            holder.contentTv.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View v) {
                    // 传参，同一个window或者activity里面，用传参的方式就能实现数据交互
                    ClipData clipData = ClipData.newPlainText("", "");
                    // 拖拽背景，拖拽时跟随手势移动显示的View（DragShadowBuilder会给传入的view绘制一个拖拽的阴影，view的清晰度和透明度会有变化）
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(holder.shadowView);


//                    // 加载自定义拖拽阴影布局
//                    View dragShadowView = LayoutInflater.from(mContext).inflate(R.layout.layout_drag_float_view, null);
//                    // 创建自定义 DragShadowBuilder
//                    CustomDragShadowBuilder shadowBuilder = new CustomDragShadowBuilder(new View(mContext));

                    v.startDragAndDrop(clipData, shadowBuilder, new ArrayList<>(), 0);
                    return true;
                }
            });


            holder.contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了" + mDataList.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class CustomDragShadowBuilder extends View.DragShadowBuilder {
        private final View shadowView;

        public CustomDragShadowBuilder(View view) {
            super(view);
            this.shadowView = view;
        }

        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            shadowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width = shadowView.getMeasuredWidth();
            int height = shadowView.getMeasuredHeight();
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            shadowView.layout(0, 0, shadowView.getMeasuredWidth(), shadowView.getMeasuredHeight());
            shadowView.draw(canvas);
        }
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void refreshData(List<String> dataList) {
        try {
            if (dataList == null) {
                return;
            }
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        try {
            Collections.swap(mDataList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemDelete(int position) {

    }

    /**
     * 添加工具
     */
    public void notifyToolAdd(int position, String content) {
        try {
            if (position > mDataList.size()) {
                return;
            }
            mDataList.add(position, content);
            notifyItemInserted(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除工具
     */
    public void notifyToolRemove(int position) {
        try {
            if (position >= mDataList.size()) {
                return;
            }
            mDataList.remove(position);
            notifyItemRemoved(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个随机位置
     */
    public int getRandomPosition() {
        return (int) (Math.random() * mDataList.size());
    }
}
