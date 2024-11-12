package com.example.myapplication2.drag;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;
import com.example.myapplication2.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lx
 * 描述：拖拽demo 测试上传GitHub 测试上传GitHub2
 */
public class DragFragment extends Fragment {

    private static final String TAG = "DragFragment";
    private static final int MAXITEMCOUNT = 15;

    private RelativeLayout rootView;

    /**
     * 第一个RecyclerView
     */
    private RecyclerView mFirstRv;
    private DragRecyclerViewAdapter mFirstAdapter;

    private View innerView;

    /**
     * 第二个RecyclerView
     */
    private RecyclerView mSecondRv;
    private DragRecyclerViewAdapter mSecondAdapter;

    private List<String> mDataList = new ArrayList<>();

    /**
     * 用于拖拽的view
     */
    private View dragFloatView;

    private TextView addItemTv;
    private TextView removeItemTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        findViews(view);
        dragFloatView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_drag_float_view, null);
        initRecyclerView();
        initListener();
    }

    private void initData() {
        try {
            // 生成一个1到MAXITEMCOUNT之间的随机数整数
//            Random random = new Random();
//            int count = random.nextInt(MAXITEMCOUNT) + 1;
            for (int i = 0; i < 4; i++) {
                mDataList.add("item" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findViews(View view) {
        try {
            rootView = view.findViewById(R.id.fragment_drag_inner_root_view);
            mFirstRv = view.findViewById(R.id.fragment_drag_inner_first_rv);
            innerView = view.findViewById(R.id.fragment_drag_inner_view);
            mSecondRv = view.findViewById(R.id.fragment_drag_inner_second_rv);
            addItemTv = view.findViewById(R.id.fragment_drag_add_item_tv);
            removeItemTv = view.findViewById(R.id.fragment_drag_remove_item_tv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        try {
            // 初始化第一个RecyclerView
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mFirstRv.setLayoutManager(linearLayoutManager);
            mFirstAdapter = new DragRecyclerViewAdapter(getContext());
            mFirstRv.setAdapter(mFirstAdapter);
            mFirstAdapter.refreshData(mDataList);


            // 初始化第二个RecyclerView
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            mSecondRv.setLayoutManager(gridLayoutManager);
            mSecondAdapter = new DragRecyclerViewAdapter(getContext());

//            ItemTouchHelper.Callback callback = new SimpleDragSwipeCallBack(mSecondAdapter);
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//            itemTouchHelper.attachToRecyclerView(mSecondRv);

            mSecondRv.setAdapter(mSecondAdapter);
            mSecondAdapter.refreshData(mDataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        try {
//            innerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(getActivity(), "点击内部view", Toast.LENGTH_SHORT).show();
////                    mSecondAdapter.notifyToolAdd(1, "新加");
////                    mSecondAdapter.notifyToolRemove(2);
//
//                }
//            });

//            innerView.setOnDragListener(new InnerViewDragListener());
            rootView.setOnDragListener(new RootViewDragListener());

            addItemTv.setOnClickListener(v -> {
//                int position = mFirstAdapter.getRandomPosition();
//                mFirstAdapter.notifyToolAdd(position, "新加" + position);

                testShowDragFloatView();

            });

            removeItemTv.setOnClickListener(v -> {
//                mFirstAdapter.notifyToolRemove(mFirstAdapter.getRandomPosition());

                View dragFloatView2 = LayoutInflater.from(getActivity()).inflate(R.layout.layout_drag_float_view, null);
                View solidView = dragFloatView2.findViewById(R.id.layout_drag_float_view_view);
                solidView.setBackgroundColor(Color.RED);
                if (dragFloatView2.getParent() != null) {
                    ((ViewGroup) dragFloatView2.getParent()).removeView(dragFloatView2);
                }
                rootView.addView(dragFloatView2);
                dragFloatView.setY(UIUtils.dip2px(getActivity(), 5));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：innerView的拖拽监听
     * <p>
     * event.getX() 和 event.getY() 获取的是触摸事件相对于触发该事件的视图左上角的坐标。具体来说：
     * event.getX() 返回触摸点相对于视图左边缘的水平距离。
     * event.getY() 返回触摸点相对于视图上边缘的垂直距离。
     * 这些坐标是相对于视图本身的局部坐标，而不是相对于整个屏幕的全局坐标
     * <p>
     * event.getX() 和 event.getY() 获取的是用户手指在拖拽阴影中按下的位置相对于 innerView 左上角的坐标。
     * <p>
     * getLocationOnScreen(location) 方法获取到的 x 和 y 坐标是视图左上角相对于整个屏幕左上角的横纵距离（绝对坐标）
     * <p>
     * <p>
     * `onDrag()` 方法会在以下几种拖拽事件发生时被调用：
     * <p>
     * 1. `DragEvent.ACTION_DRAG_STARTED` - 拖拽操作开始时。
     * 2. `DragEvent.ACTION_DRAG_ENDED` - 拖拽操作结束时。
     * 3. `DragEvent.ACTION_DRAG_ENTERED` - 拖拽的视图进入监听视图时。
     * 4. `DragEvent.ACTION_DRAG_EXITED` - 拖拽的视图离开监听视图时。
     * 5. `DragEvent.ACTION_DRAG_LOCATION` - 拖拽的视图在监听视图中移动时。
     * 6. `DragEvent.ACTION_DROP` - 拖拽的视图被释放时。
     * <p>
     * 这些事件会触发 `onDrag()` 方法，并传递相应的 `DragEvent` 对象。
     * <p>
     * rootLocation = new int[2];
     * rootView.getLocationOnScreen(rootLocation);
     * 调用 rootView 的 getLocationOnScreen 方法，这会将根视图左上角的 X 和 Y 坐标填充到 rootLocation 数组中。
     * <p>
     * 计算触摸坐标：
     * int touchX = (int) event.getX() + rootLocation[0];
     * int touchY = (int) event.getY() + rootLocation[1];
     * 通过将从 DragEvent 获取的本地触摸坐标 (event.getX() 和 event.getY()) 加上存储在 rootLocation 中的根视图屏幕坐标，计算出触摸点的绝对屏幕坐标
     */
    class InnerViewDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            //v 永远是设置该监听的view，这里即fl_blue
            String simpleName = v.getClass().getSimpleName();
            Log.d(TAG, "onDrag: ");
            Log.w(TAG, "view name:" + simpleName);
            Log.w(TAG, "getX()：" + event.getX());
            Log.w(TAG, "getY()：" + event.getY());

            //获取事件
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "开始拖拽");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "结束拖拽");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(TAG, "拖拽的view进入监听的view时");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(TAG, "拖拽的view离开监听的view时");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    float x = event.getX();
                    float y = event.getY();
//                    long l = SystemClock.currentThreadTimeMillis();
                    Log.d(TAG, "拖拽的view在监听view中的位置:x =" + x + ",y=" + y);
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "释放拖拽的view");
                    break;
            }
            //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
            return true;
        }
    }

    /**
     * 触摸点的坐标
     */
    private int touchX;
    private int touchY;

    /**
     * 描述：rootView的拖拽监听
     */
    class RootViewDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            //v 永远是设置该监听的view，这里即fl_blue
            String simpleName = v.getClass().getSimpleName();
//            Log.d(TAG, "onDrag: ");
//            Log.w(TAG, "view name:" + simpleName);
//            Log.w(TAG, "getX()：" + event.getX());
//            Log.w(TAG, "getY()：" + event.getY());

            // 触摸点相对于视图左上角的坐标（触摸点的相对坐标）
            int x = (int) event.getX();
            int y = (int) event.getY();

            // 视图相较于屏幕的坐标
            int[] rootLocation = new int[2];
            rootView.getLocationOnScreen(rootLocation);

            // 触摸点的坐标（触摸点的绝对坐标）
//            touchX = (int) event.getX() + rootLocation[0];
//            touchY = (int) event.getY() + rootLocation[1];
            touchX = (int) event.getX();
            touchY = (int) event.getY();

            Log.w(TAG, "根视图左上角x、y坐标:  X =" + rootLocation[0] + ", Y=" + rootLocation[1]);
            Log.w(TAG, "触摸点的绝对坐标:touchX =" + touchX + ",touchY=" + touchY);

            //获取事件
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "开始拖拽");
                    showDragFloatView();
//                    testShowDragFloatView();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "结束拖拽");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(TAG, "拖拽的view进入监听的view时");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(TAG, "拖拽的view离开监听的view时");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    updateDragViewPosition();
                    Log.d(TAG, "拖拽的view在监听view中的位置:x =" + x + ",y=" + y);
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "释放拖拽的view");
                    break;
            }
            //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
            return true;
        }
    }

    private void testShowDragFloatView() {
        if (dragFloatView.getParent() != null) {
            ((ViewGroup) dragFloatView.getParent()).removeView(dragFloatView);
        }
        rootView.addView(dragFloatView);
//        dragFloatView.setX(UIUtils.dip2px(getActivity(), 100));
//        dragFloatView.setY(UIUtils.dip2px(getActivity(), 100));
    }

    /**
     * 显示跟手拖拽的view
     */
    private void showDragFloatView() {
        try {
            if (dragFloatView == null) {
                dragFloatView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_drag_float_view, null);
                dragFloatView.setLayoutParams(new ViewGroup.LayoutParams(
                        UIUtils.dip2px(getContext(), 70),
                        UIUtils.dip2px(getContext(), 50)));
            }

            // 确保 dragFloatView 的宽度和高度已测量
            dragFloatView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width = dragFloatView.getMeasuredWidth();
            int height = dragFloatView.getMeasuredHeight();

            Log.d(TAG, "showDragFloatView: dragFloatView.getWidth() = " + dragFloatView.getMeasuredWidth() + "  dragFloatView.getHeight() = " + dragFloatView.getMeasuredHeight());
//            dragFloatView.setX(touchX - width / 2);
//            dragFloatView.setY(touchY - height/2);
            rootView.addView(dragFloatView);
            dragFloatView.setX(touchX - width / 2);
            dragFloatView.setY(touchY - height / 2);
//            updateDragViewPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新拖拽view的位置
     */
    private void updateDragViewPosition() {
        dragFloatView.setX(touchX - dragFloatView.getWidth() / 2);
        dragFloatView.setY(touchY - dragFloatView.getHeight() / 2);
    }
}
