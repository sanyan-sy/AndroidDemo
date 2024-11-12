package com.example.myapplication2.coordinatorlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    
    private List<String> dataList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        
        initData();

        recyclerView = findViewById(R.id.recycle_view);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        
        adapter = new MyRecyclerViewAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        for(int i = 0; i < 20; i++){
            dataList.add("这是第 " + i + " 个item");
        }
    }
}