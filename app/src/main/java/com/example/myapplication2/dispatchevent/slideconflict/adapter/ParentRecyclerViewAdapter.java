package com.example.myapplication2.dispatchevent.slideconflict.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;
import com.example.myapplication2.dispatchevent.slideconflict.recyclerview.ChildRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataList = new ArrayList<>();


    public ParentRecyclerViewAdapter() {
    }

    public ParentRecyclerViewAdapter(List<String> dataList) {
        this.dataList.addAll(dataList);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout2, parent, false);
            return new ChlidViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).tvContent.setText(dataList.get(position - 1));
        }else if(holder instanceof ChlidViewHolder){
            ChlidViewHolder chlidViewHolder = (ChlidViewHolder)holder;
            // 创建布局管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(chlidViewHolder.itemView.getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            chlidViewHolder.childRecyclerView.setLayoutManager(layoutManager);

            // 创建适配器
            ChlidRecyclerViewAdapter chlidRecyclerViewAdapter = new ChlidRecyclerViewAdapter(dataList);
            chlidViewHolder.childRecyclerView.setAdapter(chlidRecyclerViewAdapter);

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvContent;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    class ChlidViewHolder extends RecyclerView.ViewHolder{

        ChildRecyclerView childRecyclerView;

        public ChlidViewHolder(@NonNull View itemView) {
            super(itemView);

            childRecyclerView = itemView.findViewById(R.id.chlid_recycler_view);
        }
    }

}
