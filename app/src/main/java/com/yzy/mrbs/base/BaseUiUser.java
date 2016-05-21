package com.yzy.mrbs.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.yzy.mrbs.R;
import com.yzy.mrbs.activity.Book;
import com.yzy.mrbs.activity.BookSearch;
import com.yzy.mrbs.activity.HttpClientTest;
import com.yzy.mrbs.activity.Login;
import com.yzy.mrbs.activity.NoteSearch;
import com.yzy.mrbs.activity.UiConfig;
import com.yzy.mrbs.model.Customer;

/**
 * (带菜单)
 * Created by ZhiYuan on 2016/5/18.
 */
public class BaseUiUser extends BaseUi {

    private final int MENU_APP_LOGOUT = 1;
    private final int MENU_APP_ABOUT = 2;

    protected static Customer customer = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        if (!BaseUser.isLogin()) {
//            this.forward(Login.class);
//            this.onStop();
//        } else {
//            customer = BaseUser.getCustomer();
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.bindMainTab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_APP_LOGOUT, 0, R.string.menu_app_logout).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        menu.add(0, MENU_APP_ABOUT, 0, R.string.menu_app_about).setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case MENU_APP_LOGOUT: {
                doLogout(); // do logout first
                forward(Login.class);
                break;
            }
            case MENU_APP_ABOUT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.menu_app_about);
                String appName = this.getString(R.string.app_name);
                String appVersion = this.getString(R.string.app_version);
                builder.setMessage(appName + " " + appVersion + "\n" + "    " + "制作：姚志远");
                builder.setIcon(R.drawable.face);
                builder.setPositiveButton(R.string.btn_cancel, null);
                builder.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void bindMainTab() {
        ImageButton bTabBook = (ImageButton) findViewById(R.id.main_tab_1);
        ImageButton bTabBookSearch = (ImageButton) findViewById(R.id.main_tab_2);
        ImageButton bTabNoteSearch = (ImageButton) findViewById(R.id.main_tab_3);
        ImageButton bTabConf = (ImageButton) findViewById(R.id.main_tab_4);
        if (bTabBook != null && bTabBookSearch != null && bTabNoteSearch != null && bTabConf != null) {
            View.OnClickListener mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.main_tab_1:
                            forward(Book.class);//会议室预约
                            break;
                        case R.id.main_tab_2:
//                            forward(BookSearch.class);//预约查询
                            forward(HttpClientTest.class);
                            break;
                        case R.id.main_tab_3:
                            forward(NoteSearch.class);//记录查询
                            break;
                        case R.id.main_tab_4:
                            forward(UiConfig.class);//个人信息
                            break;
                    }
                }
            };
            bTabBook.setOnClickListener(mOnClickListener);
            bTabBookSearch.setOnClickListener(mOnClickListener);
            bTabNoteSearch.setOnClickListener(mOnClickListener);
            bTabConf.setOnClickListener(mOnClickListener);
        }
    }
}
