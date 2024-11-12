package com.example.myapplication2.dispatchevent.slideconflict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication2.R;
import com.example.myapplication2.dispatchevent.slideconflict.adapter.ParentRecyclerViewAdapter;
import com.example.myapplication2.dispatchevent.slideconflict.recyclerview.ParentRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlideConflictActivity extends AppCompatActivity {

    private List<String> dataList = new ArrayList<>();
    private ParentRecyclerView parentRecyclerView;
    private ParentRecyclerViewAdapter parentRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_conflict);

        init();

        parentRecyclerView = findViewById(R.id.parent_recycler_view);

        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);

        // 设置适配器
        parentRecyclerViewAdapter = new ParentRecyclerViewAdapter(dataList);
        parentRecyclerView.setAdapter(parentRecyclerViewAdapter);

    }

    // 初始化数据
    private void init(){
        for(int i = 0; i < 20; i++){
            dataList.add("这是第 " + i + " 个item");
        }
    }

}