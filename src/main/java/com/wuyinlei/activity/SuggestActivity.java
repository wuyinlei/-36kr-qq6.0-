package com.wuyinlei.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyinlei.DefineView;
import com.wuyinlei.adapter.SuggestTypeAdapter;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuggestActivity extends BaseActivity implements DefineView{

    private ImageView itemarrow;
    private EditText etsuggest;
    private EditText etphone;
    private Button btnsubmit;
    private TextView tv_type;
    private TextView top_bar_text;
    private Button btn_back;

    private String suggest_text;
    private String contact_way;
    private String[]types = new String[]{"新需求建议","功能建议","界面建议","操作问题","质量问题","投诉","其他"};


    private PopupWindow typeWindow;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        setStatusBar();
        initView();
        initValidata();
        bindData();
        initListener();

    }


    @Override
    public void initView() {
        itemarrow = (ImageView) findViewById(R.id.item_arrow);
        etsuggest = (EditText) findViewById(R.id.et_suggest);
        etphone = (EditText) findViewById(R.id.et_phone);
        btnsubmit = (Button) findViewById(R.id.btn_submmit);
        tv_type = (TextView) findViewById(R.id.tv_type);
        top_bar_text = (TextView) findViewById(R.id.top_bar_text);
        top_bar_text.setText("意见反馈");
        btn_back = (Button) findViewById(R.id.btn_back);

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submmitData();
            }
        });

        itemarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPopupWindow() {
        if (typeWindow == null){
            typeWindow = new PopupWindow(SuggestActivity.this);
            typeWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            typeWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

            View contentView = LayoutInflater.from(SuggestActivity.this)
                    .inflate(R.layout.suggest_type_window_layout, null);
            ListView lv_type = (ListView) contentView.findViewById(R.id.lv_suggest_type_window);
            SuggestTypeAdapter adapter = new SuggestTypeAdapter(SuggestActivity.this,types);
            lv_type.setAdapter(adapter);
            lv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tv_type.setText(types[((int) id)]);
                    type = types[((int) id)];
                    if (typeWindow != null) {
                        typeWindow.dismiss();
                    }

                }
            });

            typeWindow.setContentView(contentView);
            typeWindow.setFocusable(true);
            typeWindow.setOutsideTouchable(true);
            typeWindow.setBackgroundDrawable(new ColorDrawable(0));
            typeWindow.showAsDropDown(tv_type);
        } else {
            if (typeWindow.isShowing()){
                typeWindow.dismiss();
            } else {
                //在那个的下面
                typeWindow.showAsDropDown(tv_type);
            }
        }
    }

    /**
     * 提交数据
     */
    private void submmitData() {
        if (TextUtils.isEmpty(etsuggest.getText().toString())) {
            Toast.makeText(SuggestActivity.this, "输入的建议不能为空", Toast.LENGTH_SHORT).show();
        }

        checkPhone(etphone.getText().toString());

        Toast.makeText(SuggestActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

        HashMap<String,String> params = new HashMap<String, String>();

        params.put("suggest",suggest_text);
        params.put("phone", contact_way);
        params.put("type", tv_type.getText().toString());

               /* OkHttpManager.postAsync("url", params, new OkHttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Toast.makeText(SuggestActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void checkPhone(String phone) {
        /**
         * 正则表达式，用来判断手机号码是否符合
         */
        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(phone);
        if (!m.matches()) {
            Toast.makeText(this, "您输入的手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void bindData() {
        suggest_text = etsuggest.getText().toString();
        contact_way = etphone.getText().toString();
    }
}
