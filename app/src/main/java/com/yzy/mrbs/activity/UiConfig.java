package com.yzy.mrbs.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseHandler;
import com.yzy.mrbs.base.BaseMessage;
import com.yzy.mrbs.base.BaseTask;
import com.yzy.mrbs.base.BaseUi;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.base.C;
import com.yzy.mrbs.list.SimpleList;
import com.yzy.mrbs.model.Config;
import com.yzy.mrbs.model.Customer;
import com.yzy.mrbs.util.AppCache;
import com.yzy.mrbs.util.AppUtil;
import com.yzy.mrbs.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用户配置界面
 * Created by ZhiYuan on 2016/5/17.
 */
public class UiConfig extends BaseUiUser {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_config);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
