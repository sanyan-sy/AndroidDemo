package com.example.myapplication2.another;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication2.R;


public class EditTextActivity extends AppCompatActivity {

    private EditText etContent;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        etContent = findViewById(R.id.et_content);

//        // 3、使用过滤器
//        InputFilter filter = new InputFilter() {
//            /**
//             *
//             * @param source 输入的文字
//             * @param start 开始位置
//             * @param end 结束位置
//             * @param dest 当前显示的内容
//             * @param dstart 当前开始位置
//             * @param dend 当前结束位置
//             * @return
//             */
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                // 指定要禁止的字符
//                String blockedChar = "!@#$%^&*()";
//                for(int i = start; i < end; i++){
//                    if(blockedChar.contains(String.valueOf(source.charAt(i)))){
//                        // 返回空字符串表示禁止输入该字符
//                        return "";
//                    }
//                }
//                // 允许输入该字符
//                return null;
//            }
//        };
//        etContent.setFilters(new InputFilter[]{filter});

        // 4、使用TextWatcher接口
        etContent.addTextChangedListener(new TextWatcher() {
            // 文本改变之前执行的操作
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 文本改变时执行的操作
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当文本正在改变时执行的操作
                String filteredText = removeUnwantedCharacters(s.toString());
                if (!filteredText.equals(s.toString())) {
                    etContent.setText(filteredText);
                    etContent.setSelection(filteredText.length());
                }
            }

            // 文本改变之后执行的操作
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String removeUnwantedCharacters(String text) {
        // 指定要删除的不想要的字符
        String unwantedCharacters = "!@#$%^&*()";

        // 删除不想要的字符
        StringBuilder filteredText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!unwantedCharacters.contains(String.valueOf(c))) {
                filteredText.append(c);
            }
        }

        return filteredText.toString();
    }

    public void toSetText(View view) {
        // 光标移动到末尾
        String text = "Hello World";
        etContent.setText(text);
        etContent.setSelection(text.length());
    }
}