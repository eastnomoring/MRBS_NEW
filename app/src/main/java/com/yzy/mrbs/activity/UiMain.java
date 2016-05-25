package com.yzy.mrbs.activity;

import android.os.Bundle;
import android.view.KeyEvent;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.base.BaseUser;
import com.yzy.mrbs.base.C;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONObject;

/**
 * Created by ZhiYuan on 2016/5/17.
 */
public class UiMain extends BaseUiUser {
    private String s_uimain_request;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_uimain);
        // 获取用户信息 适用于新服务器框架
        customer.getId();
//        String s_setsign = "http://192.168.1.103:80/PhalApi/Public/user/?service=User.getuserinfo"+"&userid=" +customer.getId();
        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/user/?service=User.getuserinfo"+"&userid=" +customer.getId();
        try {
            s_uimain_request = PhalapiHttpUtil.getRequest(s_setsign);
        } catch (Exception e) {
            e.printStackTrace();
            toast("网络连接失败，未更新您的签名");
        }
        try {
            JSONObject jsonObj = new JSONObject(s_uimain_request);
            JSONObject jsonObj_data = new JSONObject(jsonObj.getString("data"));
            BaseUser.setSimpleInfo(
                    jsonObj_data.getString("userid"),
                    jsonObj_data.getString("username"),
                    jsonObj_data.getString("userpass"),
                    jsonObj_data.getString("sign"),
                    jsonObj_data.getString("email"),
                    jsonObj_data.getString("phone")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStart(){
        super.onStart();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // other methods

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
