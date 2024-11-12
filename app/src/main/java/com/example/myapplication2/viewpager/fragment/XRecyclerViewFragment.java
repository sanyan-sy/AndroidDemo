package com.example.myapplication2.viewpager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication2.R;
import com.example.myapplication2.bean.Fruit;
import com.example.myapplication2.viewpager.MyItemDecoration;
import com.example.myapplication2.viewpager.adapter.XRecyclerViewAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class XRecyclerViewFragment extends Fragment {

    private XRecyclerView xRecyclerView;
    private XRecyclerViewAdapter xRecyclerViewAdapter;

    private ExecutorService executorService;

    // 用来从子线程向主线程传递数据
    private Handler handler;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public XRecyclerViewFragment() {

    }

    public static XRecyclerViewFragment newInstance(String param1, String param2) {
        XRecyclerViewFragment fragment = new XRecyclerViewFragment();
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

        /* 手动创建线程池
        1、corePoolSize线程池的核心线程数
        2、maximumPoolSize能容纳的最大线程数
        3、keepAliveTime空闲线程存活时间  1秒
        4、unit 存活的时间单位
        5、workQueue 存放提交但未执行任务的队列
        6、threadFactory 创建线程的工厂类
        7、handler 等待队列满后的拒绝策略
         */
        executorService = new ThreadPoolExecutor(2, 10,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_x_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        xRecyclerView = view.findViewById(R.id.xrecycler_view);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                // 根据what的值来区分不同的消息
                if(msg.what == 0){
                    // 刷新数据
                    xRecyclerViewAdapter.refreshData((List<Fruit>)msg.obj);
                    // 停止刷新动画
                    xRecyclerView.refreshComplete();
                }else if(msg.what ==1){
                    // 加载更多数据
                    xRecyclerViewAdapter.addData((List<Fruit>)msg.obj);
                    // 停止加载动画
                    xRecyclerView.loadMoreComplete();
                }
            }
        };

        // 创建并设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);

        //创建并设置适配器
        xRecyclerViewAdapter = new XRecyclerViewAdapter(getData());
        xRecyclerView.setAdapter(xRecyclerViewAdapter);

        // 设置item之间的间距
        MyItemDecoration itemDecoration = new MyItemDecoration(15);
        xRecyclerView.addItemDecoration(itemDecoration);

        // 设置下拉刷新和上拉加载样式
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);


        // 设置下拉刷新和上拉加载的监听器
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            // 下拉刷新
            @Override
            public void onRefresh() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 模拟请求网络耗时
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 将数据传回主线程中刷新UI
                        Message message = Message.obtain(); // 从消息池中获取Message对象，可提高系统性能和效率
                        message.what = 0;
                        message.obj = getData();
                        handler.sendMessage(message);

                    }
                });

                /*
                Handler().postDelayed()方法，可以通过将Runnable的代码放在主线程的消息队列中，
                实现在延迟时间结束后在主线程中执行代码的效果。

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        xRecyclerViewAdapter.refreshData(fruitList);

                        // 停止刷新动画
                        xRecyclerView.refreshComplete();
                    }
                }, 3000);
                 */


            }

            // 上拉加载
            @Override
            public void onLoadMore() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 模拟请求网络耗时
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 将数据传回主线程中刷新UI
                        Message message = Message.obtain(); // 从消息池中获取Message对象，可提高系统性能和效率
                        message.what = 1;
                        message.obj = getData();
                        handler.sendMessage(message);

                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private List<Fruit> getData() {
        List<Fruit> dataList = new ArrayList<>();

        // Fruit fruit = new Fruit(); 定义在外部最后add的都是同一个对象
        Random random = new Random();
        int count = random.nextInt(10) + 1;
        for (int i = 0; i < count; i++) {
            Fruit fruit = new Fruit();
            fruit.setName("水果" + i);
            fruit.setImageId(R.mipmap.ic_launcher);
            /*
            fruit.type = i  == 0 ? 1:i==count - 1 ?3:2;
            根据变量i的值来判断fruit.type的取值。如果i是 0，则fruit.type等于 1；如果i是count - 1，则fruit.type等于 3；否则，fruit.type等于 2。
             */
            dataList.add(fruit);
        }
        return dataList;
    }
}