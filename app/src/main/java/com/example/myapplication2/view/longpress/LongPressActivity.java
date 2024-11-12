package com.example.myapplication2.view.longpress;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication2.R;

public class LongPressActivity extends AppCompatActivity {

    private LongPressView longPressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_press);

        longPressView = findViewById(R.id.long_press);
        longPressView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(LongPressActivity.this, "Long press triggered", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}