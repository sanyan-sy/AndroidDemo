package com.example.myapplication2.viewpager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication2.R;
import com.example.myapplication2.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

public class XRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Fruit> dataList = new ArrayList<>();
    private static final int ONE_ITEM = 1;
    private static final int TWO_ITEM = 2;

    public XRecyclerViewAdapter(){
    }

    public XRecyclerViewAdapter(List<Fruit> fruitList){
        dataList.addAll(fruitList);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType % 2 == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_itme, parent, false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item2, parent, false);
            return new MyViewHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder)holder).textView.setText(dataList.get(position).getName());

            // 使用Glide加载图片
            Glide.with(holder.itemView)
                    .load(R.drawable.img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(((MyViewHolder)holder).imageView);
                    // 设置圆角
                    // .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))  radius是圆角半径，以像素为单位
        }else {
            ((MyViewHolder2)holder).textView.setText(dataList.get(position).getName());
            ((MyViewHolder2)holder).imageView.setImageResource(dataList.get(position).getImageId());
            ((MyViewHolder2)holder).imageView2.setImageResource(dataList.get(position).getImageId());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // 刷新数据
    public void refreshData(List<Fruit> fruitList){
        dataList.clear();
        dataList.addAll(fruitList);
        notifyDataSetChanged();
    }

    // 加载更多数据
    public void addData(List<Fruit> fruitList){
        dataList.addAll(fruitList);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fruit_name);
            imageView = itemView.findViewById(R.id.fruit_image);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        ImageView imageView2;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fruit_name);
            imageView = itemView.findViewById(R.id.fruit_image);
            imageView2 = itemView.findViewById(R.id.fruit_image2);
        }
    }
}
