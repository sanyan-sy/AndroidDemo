package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication2.ViewStub.ViewStubActivity;
import com.example.myapplication2.animation.AnimationActivity;
import com.example.myapplication2.another.TextViewActivity;
import com.example.myapplication2.assets.AssetsActivity;
import com.example.myapplication2.channel.ChannelActivity;
import com.example.myapplication2.coordinatorlayout.CoordinatorLayoutActivity;
import com.example.myapplication2.dialog.CustomDialog;
import com.example.myapplication2.dialog.EventBusMsg;
import com.example.myapplication2.dispatchevent.DispatchEventActivity;
import com.example.myapplication2.another.EditTextActivity;
import com.example.myapplication2.drag.DragBaseActivity;
import com.example.myapplication2.service.ServiceActivity;
import com.example.myapplication2.view.ViewActivity;
import com.example.myapplication2.viewpager.ViewPagerFragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "-----onCreate-----");

        setContentView(R.layout.activity_main);

        // 注册EventBus
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "-----onStart-----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "-----onResume-----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "-----onPause-----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "-----onStop-----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "-----onDestroy-----");
        // 解注册EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", "lllll");
        Log.d("MainActivity", "-----onSaveInstanceState-----");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity", savedInstanceState.getString("name"));
        Log.d("MainActivity", "-----onRestoreInstanceState-----");
    }

    public void jumpToViewPager(View view) {
        Intent intent = new Intent(this, ViewPagerFragmentActivity.class);
        startActivity(intent);
    }

    public void jumpToAnimation(View view) {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }

    public void jumpToView(View view) {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    public void jumpToDispatchEvent(View view) {
        Intent intent = new Intent(this, DispatchEventActivity.class);
        startActivity(intent);
    }

    public void jumpToDialog(View view) {
        CustomDialog customDialog = new CustomDialog(MainActivity.this, R.style.DialogStyle);

        customDialog.setListener(new CustomDialog.OnDialogCloseListener() {
            @Override
            public void onDialogClose(String inputText) {
                Log.d("Dialog", "---接口回调-----：" + inputText);
            }
        });

        customDialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusMsg eventBusMsg){
        String inputText = eventBusMsg.getInputText();
        Toast.makeText(this,"输入的内容是：" + inputText, Toast.LENGTH_LONG).show();
        Log.d("Dialog", "---EventBusMsg回调-----：" + inputText);
    }

    public void jumpToService(View view) {
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }

    public void jumpToEditText(View view) {
        Intent intent = new Intent(this, EditTextActivity.class);
        startActivity(intent);
    }

    public void jumpToTextView(View view) {
        Intent intent = new Intent(this, TextViewActivity.class);
        startActivity(intent);
    }

    public void jumpToChannel(View view) {
        Intent intent = new Intent(this, ChannelActivity.class);
        startActivity(intent);
    }

    public void jumpToViewStub(View view) {
        Intent intent = new Intent(this, ViewStubActivity.class);
        startActivity(intent);
    }

    public void jumpToCoordinatorLayout(View view) {
        Intent intent = new Intent(this, CoordinatorLayoutActivity.class);
        startActivity(intent);
    }

    public void jumpToAssets(View view) {
        Intent intent = new Intent(this, AssetsActivity.class);
        startActivity(intent);
    }

    public void jumpToDrag(View view){
        Intent intent = new Intent(this, DragBaseActivity.class);
        startActivity(intent);
    }
}