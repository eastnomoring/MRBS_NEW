package com.yzy.mrbs.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseMessage;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.base.C;

import java.util.HashMap;

/**
 * Created by ZhiYuan on 2016/5/22.
 */
public class UiEditText extends BaseUiUser {

    private EditText mEditText;
    private Button mEditSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_edit);

        // 显示软键盘
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        // 界面初始化
        mEditText = (EditText) this.findViewById(R.id.app_edit_text);
        mEditSubmit = (Button) this.findViewById(R.id.app_edit_submit);

        // 处理不同逻辑
        Bundle params = this.getIntent().getExtras();
        final int action = params.getInt("action");

        switch (action) {
            //修改签名逻辑
            case C.action.edittext.CONFIG:
                mEditText.setText(params.getString("value"));
                mEditSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = mEditText.getText().toString();
                        customer.setSign(input); // 更新本地签名
                        HashMap<String, String> urlParams = new HashMap<String, String>();
                        urlParams.put("key", "sign");
                        urlParams.put("val", input);
                        doTaskAsync(C.task.customerEdit, C.api.customerEdit, urlParams);//更新服务器签名
                    }
                });
                break;
            //发表评论逻辑，此功能后续实现
            case C.action.edittext.COMMENT:
                final String blogId = params.getString("blogId");
                mEditSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = mEditText.getText().toString();
                        HashMap<String, String> urlParams = new HashMap<String, String>();
                        urlParams.put("blogId", blogId);
                        urlParams.put("content", input);
                        doTaskAsync(C.task.commentCreate, C.api.commentCreate, urlParams);
                    }
                });
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 异步回掉方法

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        doFinish();
    }

    @Override
    public void onNetworkError (int taskId) {
        super.onNetworkError(taskId);
    }


}
