package com.example.myapplication2.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.viewpager.adapter.ViewPagerAapter;
import com.example.myapplication2.drag.DragFragment;
import com.example.myapplication2.viewpager.fragment.ListViewFragment;
import com.example.myapplication2.viewpager.fragment.RecyclerViewFragment;
import com.example.myapplication2.viewpager.fragment.XRecyclerViewFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentActivity extends AppCompatActivity {

    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private String title[] = {"fg1", "fg2", "fg3", "fg4"};
    private ViewPagerAapter viewPagerAapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);

        viewPager = findViewById(R.id.vp);
        magicIndicator = findViewById(R.id.mi);

        initData();

        viewPagerAapter = new ViewPagerAapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAapter);
        
        // 初始化Magicindicator
        initMagicindicator();

        // 设置页面滑动监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 页面滚动时的逻辑处理
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // 页面选中发生变化时的逻辑处理
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ViewPagerFragmentActivity.this,"你滑动到了Fragment" + (position + 1), Toast.LENGTH_SHORT).show();

                magicIndicator.onPageSelected(position);
            }

            // 页面滚动状态发生变化时被调用
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initMagicindicator() {

        magicIndicator.setBackgroundColor(Color.YELLOW);

        // 创建导航栏
        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdjustMode(true);  // 调节位置，等分
//        commonNavigator.setRightPadding(50);

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            // 返回标题数量
            @Override
            public int getCount() {
                return title == null ? 0 : title.length;
            }

            // 创建标题视图（标题视图：负责显示指示器的每个标题，可以根据选中状态进行相应设置）
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                //设置Magicindicator的一种标题模式， 标题模式有很多种，这是最基本的一种
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                // 设置标题文本
                simplePagerTitleView.setText(title[index]);
                //设置被选中状态下的item颜色
                simplePagerTitleView.setSelectedColor(Color.RED);
                //设置未被选中状态下的item颜色
                simplePagerTitleView.setNormalColor(Color.BLACK);
                // 设置item被点击时候的响应事件
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 点击标题时切换到对应的页面
                        viewPager.setCurrentItem(index);
//                        viewPager.setCurrentItem();
                    }
                });
                return simplePagerTitleView;
            }

            // 创建指示器视图
            // 指示器视图：用于指示当前选中的标题位置，起到了标识当前选中项的作用，它可以随着页面的滑动或选中状态的变化进行相应的位置或样式的调整。
            @Override
            public IPagerIndicator getIndicator(Context context) {
                //设置标题指示器，也有多种,可不选，即没有标题指示器。
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4")
                        , Color.parseColor("#76b0ff"), Color.parseColor("#c683fe")
                        , Color.parseColor("#76b0ff"), Color.parseColor("#c683fe")
                        , Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;

//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY); // 设置模式为精确模式
//                indicator.setLineWidth(20); // 设置指示器的线宽
//                indicator.setLineHeight(2); // 设置指示器的线高
//                indicator.setRoundRadius(1); // 设置指示器的圆角半径
//                indicator.setColors(Color.BLACK); // 设置指示器的颜色
//                return indicator;
            }
        });

        // 绑定导航栏
        magicIndicator.setNavigator(commonNavigator);
        // 将magicIndicator与viewpager进行绑定
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void initData() {
        fragmentList = new ArrayList<>();

        ListViewFragment listViewFragment = ListViewFragment.newInstance("这是第1个ListViewFragment", "");
        RecyclerViewFragment recyclerViewFragment = RecyclerViewFragment.newInstance("这是第1个RecyclerViewFragment", "");
        XRecyclerViewFragment xRecyclerViewFragment = new XRecyclerViewFragment();
        DragFragment dragFragment = new DragFragment();

        fragmentList.add(listViewFragment);
        fragmentList.add(recyclerViewFragment);
        fragmentList.add(xRecyclerViewFragment);
        fragmentList.add(dragFragment);
    }
}