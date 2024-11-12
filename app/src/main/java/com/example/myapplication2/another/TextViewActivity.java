package com.example.myapplication2.another;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;

public class TextViewActivity extends AppCompatActivity {

    private TextView tvContent;
    private SpannableString spannableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        initView();

//        tvContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "点击了TextView", Toast.LENGTH_SHORT).show();
//            }
//        });
        tvContent.setVisibility(View.INVISIBLE);
    }

    private void initView(){
        tvContent = findViewById(R.id.tv_content);
        String content = "you can click taobao or jingdong";

        // 创建一个SpannableString对象
        spannableString = new SpannableString(content);

        // 设置不同颜色和大小的样式
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        /**
         * RelativeSizeSpan：设置相对大小的字体，即相对于默认文本大小的比例值。你可以通过指定浮点数值来表示相对的大小比例，例如1.5f表示字体的大小为默认大小的1.5倍。
         * AbsoluteSizeSpan：用于设置绝对大小的字体，即以像素（px）为单位的具体大小值。可以通过指定具体的像素值来设置字体的绝对大小。
         */
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);

        /**
         * 标志：
         * SPAN_EXCLUSIVE_EXCLUSIVE：样式不包括范围的起始和结束位置
         * SPAN_INCLUSIVE_EXCLUSIVE：样式包括范围的起始位置，但不包括结束位置
         * SPAN_EXCLUSIVE_INCLUSIVE：样式包括范围的结束位置，但不包括起始位置
         * SPAN_INCLUSIVE_INCLUSIVE： 样式包括范围的起始和结束位置
         */
        spannableString.setSpan(colorSpan, content.indexOf("tao"), content.indexOf("or"), Spanned.SPAN_INCLUSIVE_INCLUSIVE); // 设置前5个字符为红色
        spannableString.setSpan(sizeSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // 设置第7到第12个字符为1.5倍大小

        // 设置下划线和点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // 处理点击事件
                Toast.makeText(getApplicationContext(), "点击了下划线文字", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true); // 设置下划线
            }
        };
        spannableString.setSpan(clickableSpan, content.indexOf("tao"), content.indexOf("or"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置第14到第18个字符具有点击事件和下划线

        // 设置TextView的文本和可点击属性
        tvContent.setText(spannableString);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}