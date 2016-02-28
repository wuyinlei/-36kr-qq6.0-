package com.wuyinlei.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {

    private Button btn_back;
    private TextView top_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setStatusBar();
        initializes();
    }

    private void initializes() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        top_text = (TextView) findViewById(R.id.top_bar_text);
        top_text.setText("关于我们");
    }
}
