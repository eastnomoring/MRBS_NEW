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
import com.yzy.mrbs.base.BaseUser;
import com.yzy.mrbs.base.C;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

/**
 * Created by ZhiYuan on 2016/5/22.
 */
public class UiEditText extends BaseUiUser {

    private EditText mEditText;
    private Button mEditSubmit;
    private String s_setsign_request;

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
                        //旧框架更新网络签名
//                        HashMap<String, String> urlParams = new HashMap<String, String>();
//                        urlParams.put("key", "sign");
//                        urlParams.put("val", input);
//                        doTaskAsync(C.task.customerEdit, C.api.customerEdit, urlParams);//更新服务器签名
                        //新框架更新网络签名  逻辑
                        customer.getId();
                        String s_setsign = "http://192.168.1.103:80/PhalApi/Public/user/?service=User.setusersign"+"&userid=" +customer.getId()+"&sign="+input;
                        try {
                            s_setsign_request = PhalapiHttpUtil.getRequest(s_setsign);
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("网络连接失败，未更新您的签名");
                        }
                        try {
                            JSONObject jsonObj = new JSONObject(s_setsign_request);
                            if (jsonObj.getString("data")== "1"){
                                toast("更新成功");
                            }
                            if(jsonObj.getString("data")== "0"){
                                toast("您的签名同之前一样，无需更新");
                            }
                            if(jsonObj.getString("data")== "false"){
                                toast("服务器未响应");
                            }
                            toast("恭喜您，更新签名成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
