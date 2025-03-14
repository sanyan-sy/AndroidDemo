package com.example.myapplication2.view.FlowView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;

public class FlowViewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mSearchTv;
    private EditText mEditText;
    private FlowLayout mFlowView;
    private MyFlowView myFlowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_view);
        findViews();
        initListener();
    }

    private void findViews() {
        mSearchTv = findViewById(R.id.activity_flow_view_search_tv);
        mEditText = findViewById(R.id.activity_flow_view_et);
        mFlowView = findViewById(R.id.activity_flow_view_fv);
        myFlowView = findViewById(R.id.activity_flow_view_my_fv);
    }

    private void initListener() {
        mSearchTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mSearchTv){
            addTag();
        }
    }

    private void addTag(){
//        View view = View.inflate(this, R.layout.item_flow_view, null);
        View tagView = LayoutInflater.from(this).inflate(R.layout.item_flow_view, null);
        TextView tagTv = tagView.findViewById(R.id.item_flow_view_tv);
        tagTv.setText(mEditText.getText().toString());
        mFlowView.addView(tagView);

//        View tagView2 = LayoutInflater.from(this).inflate(R.layout.item_flow_view, null);

        TextView tag1 = new TextView(this);
        tag1.setText(mEditText.getText().toString());
        myFlowView.addTag(tag1);
    }
}
