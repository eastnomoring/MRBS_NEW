package com.yzy.mrbs.activity;

import android.os.Bundle;
import android.view.KeyEvent;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;

/**
 * Created by ZhiYuan on 2016/5/17.
 */
public class UiMain extends BaseUiUser {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_uimain);
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
