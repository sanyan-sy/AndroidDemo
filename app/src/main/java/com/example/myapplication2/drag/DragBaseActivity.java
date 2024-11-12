package com.example.myapplication2.drag;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication2.R;

/**
 * @author Administrator
 * 描述：拖拽demo
 */
public class DragBaseActivity extends AppCompatActivity {

    private FrameLayout container;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_base);
        findViews();
    }

    private void findViews() {
        container = findViewById(R.id.activity_drag_base_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_drag_base_container, new DragFragment()).commit();
    }
}
