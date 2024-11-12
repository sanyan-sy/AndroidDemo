package com.example.myapplication2.viewpager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Fruit> mList = new ArrayList<>();
    private int getViewCount = 0;

    public ListViewAdapter() {
    }

    public ListViewAdapter(Context context, List<Fruit> fruitList){
        this.context = context;
        // =之后mList和fruitList指向同一个内存地址
        // this.mList = fruitList;
        this.mList.addAll(fruitList);
    }

    public int getGetViewCount(){
        return getViewCount;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("ListViewAdapter", "--- position: getViewCount --- " + position + " : " + (++getViewCount) );
        Log.d("ListViewAdapter", "--- convertView --- " + convertView );

        ViewHolder viewHolder;
        if(convertView == null){
            View view = LayoutInflater.from(context).inflate(R.layout.fruit_itme, parent, false);
            convertView = view;
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.fruit_image);
            viewHolder.textView = convertView.findViewById(R.id.fruit_name);
            // 将ViewHolder存储在convertView中
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Log.d("ListViewAdapter", "--- convertViewED --- " + convertView );

        // 设置数据
        viewHolder.imageView.setImageResource(mList.get(position).getImageId());
        viewHolder.textView.setText(mList.get(position).getName());
        return convertView;
    }


    public void refreshData(List<Fruit> fruitList){

        mList.clear();
        mList.addAll(fruitList);
        Log.d("ListViewAdapter", "----refreshData:fruitList----" + fruitList);
        Log.d("ListViewAdapter", "----refreshData:mList----" + mList);
        notifyDataSetChanged();
    }


    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
