package com.yzy.mrbs.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUi;
import com.yzy.mrbs.base.BaseUser;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONObject;


/**
 * 注册界面
 * Created by ZhiYuan on 2016/4/21.
 */
public class Register extends BaseUi {
    private String s_register_request;
    private String msg;
    private EditText username_edit;
    private EditText password_edit;
    private EditText phone_edit;
    private EditText email_edit;
    private Button btn_register;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set view after check login
        setContentView(R.layout.mrbs_activity_register);
        username_edit = (EditText) findViewById(R.id.accountEdittext);
        password_edit = (EditText) findViewById(R.id.pwdEdittext);
        phone_edit = (EditText) findViewById(R.id.phoneEdittext);
        email_edit = (EditText) findViewById(R.id.emailEdittext);
        btn_register = (Button) findViewById(R.id.btn_register_2);
        btn_register.setOnClickListener(new RegisterOnClickListener());
    }
    /**
     * btn_register控件的点击事件
     */
    private class RegisterOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            //
            if (username_edit.length() == 0 || password_edit.length() == 0|| phone_edit.length() == 0) {
                toast("用户名、密码、手机号均为必填项");
            }
            String s_setsign = "http://192.168.1.103:80/PhalApi/Public/user/?service=User.useradd"
                    +"&username=" +username_edit.getText().toString()
                    +"&userpass="+password_edit.getText().toString()
                    +"&phone="+phone_edit.getText().toString();
            try {
                s_register_request = PhalapiHttpUtil.getRequest(s_setsign);
            } catch (Exception e) {
                e.printStackTrace();
                toast("网络连接失败");
            }
            try {
                JSONObject jsonObj = new JSONObject(s_register_request);
                msg = jsonObj.getString("msg");
                if (jsonObj.getString("data")!="[]"&& msg!= null) {
                    toast(msg);
                }
                if (jsonObj.getInt("data") >= 1) {
                    toast("注册成功！");
                    forward(Login.class);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
