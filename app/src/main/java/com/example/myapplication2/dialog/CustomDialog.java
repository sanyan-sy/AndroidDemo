package com.example.myapplication2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.myapplication2.R;

import org.greenrobot.eventbus.EventBus;

public class CustomDialog extends Dialog {
    private Button btIntent;
    private EditText editText;

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

//    public CustomDialog(Context context) {
//        super(context, R.style.DialogStyle);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        editText = findViewById(R.id.et_content);
        btIntent = findViewById(R.id.bt_intent);

        btIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                getContext().startActivity(intent);
            }
        });

        // 设置对话框的宽度和位置
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            // 将对话框的位置设为底部
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }

        // 可以通过按下返回键或点击对话框外部取消对话框
        setCancelable(false);
        // 可通过点击对话框外部取消对话框
        setCanceledOnTouchOutside(true);


//        setOnShowListener();
//        setOnDismissListener();
//        setOnCancelListener();
//        setOnKeyListener();

    }

    // 被观察者
    @Override
    public void onBackPressed() {
        String inputText = editText.getText().toString();

        // 1、接口回调
        if(listener != null){
            listener.onDialogClose(inputText);
        }

        // 2、使用EventBus发送事件
        EventBus.getDefault().post(new EventBusMsg(inputText));
        dismiss();
    }



    private OnDialogCloseListener listener;

    // 提供公共方法初始化接口
    public void setListener(OnDialogCloseListener listener) {
        this.listener = listener;
    }

    public interface OnDialogCloseListener {
        // 监听方法
        void onDialogClose(String inputText);
    }
}

