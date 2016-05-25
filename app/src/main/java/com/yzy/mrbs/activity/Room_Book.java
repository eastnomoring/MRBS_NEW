package com.yzy.mrbs.activity;

import java.util.Calendar;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;

/**
 * 会议室信息界面
 * Created by ZhiYuan on 2016/5/13.
 */
public class Room_Book extends BaseUiUser {

    private int year;
    private int month;
    private int day;
    private int hour_start;
    private int minute_start;
    private int hour_end;
    private int minute_end;

    private String book_roomid;
    private String book_roomname;
    private EditText book_edit_username;
    private TextView book_text_room;
    private EditText book_edit_phone;
    private EditText book_edit_email;
    private TextView book_text_time;
    private EditText book_edit_note;
    private Button btn_book_comfirm;
    private Button btn_book_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mrbs_activity_room_book);

        book_edit_username = (EditText) this.findViewById(R.id.accountEdittext);
        book_text_room = (TextView) this.findViewById(R.id.roomEdittext);
        book_edit_phone = (EditText) this.findViewById(R.id.phoneEdittext);
        book_edit_email = (EditText) this.findViewById(R.id.emailEdittext);
        book_text_time = (TextView) this.findViewById(R.id.book_time_text);
        book_edit_note = (EditText) this.findViewById(R.id.book_note_edit);


        book_edit_username.setText(customer.getName());
        book_edit_phone.setText(customer.getPhone());
        book_edit_email.setText(customer.getEmail());

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        try {
            book_roomname = bundle.getString("roomname");
            book_roomid = bundle.getString("roomid");

        } catch (Exception e) {
            e.printStackTrace();
        }

        book_text_room.setText(book_roomname);


        //按钮
        btn_book_comfirm = (Button) findViewById(R.id.btn_book_comfirm);
        btn_book_comfirm.setOnClickListener(new ConfirmOnClickListener());
        btn_book_time = (Button) findViewById(R.id.book_time);
        btn_book_time.setOnClickListener(new ButtonTimeOnClickListener());
    }
    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getIntent().getExtras();
        try {
            year = bundle.getInt("book_time_year");
            month = bundle.getInt("book_time_month");
            day = bundle.getInt("book_time_day");
            hour_start = bundle.getInt("book_time_hour_s");
            minute_start = bundle.getInt("book_time_minute_s");
            hour_end = bundle.getInt("book_time_hour_e");
            minute_end = bundle.getInt("book_time_minute_e");
            book_text_time.setText("您的预定时间为：" + year + "年"
                    + (month + 1) + "月" + day + "日  "
                    + hour_start + "时" + minute_start + "分" + "  " + "到" + "  " + hour_end + "时" + minute_end + "分");

            book_roomname = bundle.getString("roomname");
            book_roomid = bundle.getString("roomid");
            book_text_room.setText(book_roomname);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //确认按钮点击事件
    private class ConfirmOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            //点击确认
            String srting_room_book = "您的预订已提交，稍后由会议室管理员确认";
            Bundle mBundle = new Bundle();
//            mBundle.putString("text", "data from Room_Book");//压入数据
            mBundle.putString("title", "消息2");
            mBundle.putString("text", srting_room_book);
            openDialog(mBundle);
        }
    }
    //预约时间按钮点击事件
    private class ButtonTimeOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(Room_Book.this, Room_Book_Time.class);
            Bundle bundle = new Bundle();
            bundle.putString("roomname",book_roomname);
            bundle.putString("roomid",book_roomid);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 其他方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
            forward(UiMain.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
