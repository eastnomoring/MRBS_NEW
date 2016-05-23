package com.yzy.mrbs.activity;


import android.os.Bundle;

import android.view.KeyEvent;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;


/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class BookSearch extends BaseUiUser {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book_search);

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
            forward(UiMain.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
