package com.yzy.mrbs.activity;
import com.yzy.mrbs.R;

import com.yzy.mrbs.base.BaseMessage;
import com.yzy.mrbs.base.BaseService;
import com.yzy.mrbs.base.BaseUi;
import com.yzy.mrbs.base.BaseUser;
import com.yzy.mrbs.base.C;
import com.yzy.mrbs.model.Customer;
import com.yzy.mrbs.service.NoticeService;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;



import java.util.HashMap;


/**
 * 登录界面
 * Created by ZhiYuan on 2016/4/21.
 */
public class Login extends BaseUi {
    private Button btn_login;
    private EditText username_edit;
    private EditText password_edit;
    private CheckBox mCheckBox;
    private SharedPreferences settings;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check if login
        if (BaseUser.isLogin()) {
            this.forward(UiMain.class);
        }
        // set view after check login
        setContentView(R.layout.mrbs_activity_login);
        //控件对象初始化
        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        mCheckBox = (CheckBox) this.findViewById(R.id.app_login_check_remember);
        btn_login = (Button) findViewById(R.id.signin_button);
        btn_login.setOnClickListener(new LoginOnClickListener());

        //获取记住的账号和密码
        settings = getPreferences(Context.MODE_PRIVATE);
        if (settings.getBoolean("remember", false)) {
            mCheckBox.setChecked(true);
            username_edit.setText(settings.getString("username", ""));
            password_edit.setText(settings.getString("password", ""));
        }
        // 记住密码复选框
        mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                if (mCheckBox.isChecked()) {
                    editor.putBoolean("remember", true);
                    editor.putString("username", username_edit.getText().toString());
                    editor.putString("password", password_edit.getText().toString());
                } else {
                    editor.putBoolean("remember", false);
                    editor.putString("username", "");
                    editor.putString("password", "");
                }
                editor.commit();//关闭editor
            }
        });
    }
    /**
     * btn_login控件的点击事件，执行doTaskLogin方法
     */
    private class LoginOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if(username_edit.length() == 0|| password_edit.length()== 0) {
                toast("请输入用户名密码");
            }
//            doTaskLogin();
            forward(UiMain.class);
//            forward(HttpClientTest.class);
        }
    }
    /**
     *使用 BaseUi中的doTaskAsync方法来发送异步请求到服务端的登陆接口进行登陆操作
     */
    private void doTaskLogin(){
        app.setLong(System.currentTimeMillis());
        //输入不为空时进行网络请求
        if(username_edit.length() > 0&& password_edit.length() > 0) {
            HashMap<String,String> urlParams = new HashMap<String,String>();
            urlParams.put("name",username_edit.getText().toString());
            urlParams.put("pass",password_edit.getText().toString());
            try{
                this.doTaskAsync(C.task.login,C.api.login,urlParams);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    //////////////////异步回掉方法（在获取到网络请求之后才会被调用）/////////////////////
    /**
     * 用于接受和处理异步请求结果的回掉方法，与doTaskAsync方法对应
     * 此方法会从服务器端接口返回的JSON消息中解析出Customer对象
     */
    public void onTaskComplete(int taskId,BaseMessage message){
        super.onTaskComplete(taskId,message);
        switch (taskId) {
            case C.task.login:
                Customer customer = null;
                // 登录逻辑
                try {
                    customer = (Customer) message.getResult("Customer");
                    // 登录成功
                    if (customer.getName() != null) {
                        BaseUser.setCustomer(customer);
                        BaseUser.setLogin(true);
                        // 登录失败
                    } else {
                        BaseUser.setCustomer(customer); // set sid
                        BaseUser.setLogin(false);
                        toast(this.getString(R.string.msg_loginfail));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                // login complete
                long startTime = app.getLong();
                long loginTime = System.currentTimeMillis() - startTime;
                Log.w("LoginTime", Long.toString(loginTime));
                // 切换至首页
                if (BaseUser.isLogin()) {
                    // 启动 NoticeService
                    BaseService.start(this, NoticeService.class);
                    // 跳转至应用首页
                    forward(UiMain.class);
                }
                break;
        }
    }
    /**
     * 网络失败时执行的回掉方法
     * 默认弹出网络失败提示，提示文字的常量名为C.err.network
     * 可根据需要重写
     */
    public void onNetworkError (int taskId) {
        super.onNetworkError(taskId);
    }
    /**
     * 在登陆界面中按后退按钮就关闭程序
     * 其中doFinish方法在BaseUi中定义
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
