package com.example.myapplication2.viewpager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.bean.Fruit;
import com.example.myapplication2.viewpager.MyItemDecoration;
import com.example.myapplication2.viewpager.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 非第三方库实现RecyclerView的头部和底部
 */
public class RecyclerViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvContent;

    // 传入recyclerviewAdapter中的数据
    private List<Fruit> fruitList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public RecyclerViewFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerViewFragment newInstance(String param1, String param2) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvContent = view.findViewById(R.id.tv_content);
        recyclerView = view.findViewById(R.id.recycle_view);

        tvContent.setText(mParam1);

        // 创建并设置布局管理器
//        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // 创建并设置适配器
        recyclerViewAdapter = new RecyclerViewAdapter(fruitList);
        recyclerView.setAdapter(recyclerViewAdapter);

        // 设置item之间的间距
        MyItemDecoration itemDecoration = new MyItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
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
            /*
            fruit.type = i  == 0 ? 1:i==count - 1 ?3:2;
            根据变量i的值来判断fruit.type的取值。如果i是 0，则fruit.type等于 1；如果i是count - 1，则fruit.type等于 3；否则，fruit.type等于 2。
             */
            fruitList.add(fruit);
        }
    }
}