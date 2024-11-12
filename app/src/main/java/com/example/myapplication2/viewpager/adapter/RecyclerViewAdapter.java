package com.example.myapplication2.viewpager.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;
import com.example.myapplication2.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;

    private List<Fruit> mList = new ArrayList<>();
    // 头部个数
    private int headerCount = 1;
    // 底部个数
    private int footerCount = 1;

    private int onCreateViewHolderCount = 0;
    private int onBindViewHolderCount = 0;

    public RecyclerViewAdapter(List<Fruit> fruitList) {
        mList.addAll(fruitList);
    }

    public int getContentItemCount(){
        return mList.size();
    }


    // 根据位置返回对应item的类型
    @Override
    public int getItemViewType(int position) {
        // 假设只有一个头部和尾部
        if (headerCount != 0 && position < headerCount) {
            return ITEM_TYPE_HEADER;
        } else if (footerCount != 0 && position >= (headerCount + getContentItemCount())) {
            return ITEM_TYPE_BOTTOM;
        } else {
            return ITEM_TYPE_CONTENT;
        }
//        return mList.get(position).type;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("RecyclerViewAdapter", "--- onCreateViewHolder --- " + (++onCreateViewHolderCount));

        if(viewType == ITEM_TYPE_HEADER){
            // 加载头部布局
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false);
            return new HeaderViewHolder(headerView);
        }else if(viewType == ITEM_TYPE_CONTENT){
            // 加载子项布局
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_itme, parent, false);
            return new ItemViewHolder(itemView);
        }else{
            // 加载底部布局
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);
            return new FooterViewHolder(footerView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d("TAG", "--- position --- " + position + " : " + (++onBindViewHolderCount));
        Log.d("TAG", "--- holder --- " + holder.toString());

        if(holder instanceof HeaderViewHolder){

        }else if(holder instanceof FooterViewHolder){

        }else if(holder instanceof ItemViewHolder){
            Log.d("TAG", "--- Fruit --- " + mList.get(position - headerCount).toString());
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            itemViewHolder.imageView.setImageResource(mList.get(position - headerCount).getImageId());
            itemViewHolder.textView.setText(mList.get(position - headerCount).getName());
        }

    }

    @Override
    public int getItemCount() {

        return getContentItemCount() + headerCount + footerCount;
    }

    // 中间内容ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fruit_image);
            textView = itemView.findViewById(R.id.fruit_name);
        }
    }

    // 头部ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView headerImage;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerImage = itemView.findViewById(R.id.header_Image);
        }
    }

    // 尾部ViewHolder
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            footerButton = itemView.findViewById(R.id.footer_bt);
        }
    }
}