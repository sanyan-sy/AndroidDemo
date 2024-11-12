package com.example.myapplication2.viewpager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.bean.Fruit;
import com.example.myapplication2.viewpager.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ListViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView tvContent;

    private ListView mListView;
    // 传入ListView中的数据 （这个可以到时候从Activity中传进来 ---> Activity向Fragment中传递数据）
    private List<Fruit> fruitList;
    private ListViewAdapter listViewAdapter;

    public ListViewFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListViewFragment newInstance(String param1, String param2) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        initData();
    }

    private void initData() {
        // 在方法中初始化 （每次调用都重新初始化）
        fruitList = new ArrayList<>();

        // Fruit fruit = new Fruit(); 定义在外部最后add的都是同一个对象
        Random random = new Random();
        int count = random.nextInt(20) + 1;
        for(int i = 0; i < count; i++){
            Fruit fruit = new Fruit();
            fruit.setName("水果" + i);
            fruit.setImageId(R.mipmap.ic_launcher);
            fruitList.add(fruit);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvContent = view.findViewById(R.id.tv_content);
        mListView = view.findViewById(R.id.list_view);

        tvContent.setText(mParam1);

        listViewAdapter = new ListViewAdapter(view.getContext(), fruitList);
        mListView.setAdapter(listViewAdapter);
    }
}