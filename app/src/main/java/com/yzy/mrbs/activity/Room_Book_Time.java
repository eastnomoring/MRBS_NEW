package com.yzy.mrbs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZhiYuan on 2016/5/25.
 */
public class Room_Book_Time extends BaseUiUser {
    private String book_roomid;
    private String book_roomname;
    private int minute_end_t;
    private int minute_start_t;

    // 定义记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int hour_start;
    private int minute_start;
    private int hour_end;
    private int minute_end;
    private Button room_book_btn_confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_room_book_time);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        TimePicker timePicker_start = (TimePicker) findViewById(R.id.timePicker_start);
        TimePicker timePicker_end = (TimePicker) findViewById(R.id.timePicker_end);
        room_book_btn_confirm = (Button) findViewById(R.id.room_book_ok);
        room_book_btn_confirm.setOnClickListener(new ConfirmOnClickListener());
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                Room_Book_Time.this.year = year;
                Room_Book_Time.this.month = month;
                Room_Book_Time.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////       旧方法，时间间隔为1分钟     ////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
//        // 为TimePicker指定监听器
//        timePicker_start.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//
//            @Override
//            public void onTimeChanged(TimePicker view
//                    , int hourOfDay, int minute) {
//                Room_Book_Time.this.hour_start = hourOfDay;
//                Room_Book_Time.this.minute_start = minute;
//                // 显示当前日期、时间
//                showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);
//
//            }
//        });
//        timePicker_end.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//
//            @Override
//            public void onTimeChanged(TimePicker view
//                    , int hourOfDay, int minute) {
//                Room_Book_Time.this.hour_end = hourOfDay;
//                Room_Book_Time.this.minute_end = minute;
//                // 显示当前日期、时间
//                showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);
//
//            }
//        });
        /////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////       新方法，时间间隔为15分钟     ////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        timePicker_start.setOnTimeChangedListener(mStartTimeChangedListener);
        timePicker_end.setOnTimeChangedListener(mEndTimeChangedListener);

        //接收从会议室列表传来的参数
        Bundle bundle = this.getIntent().getExtras();
        book_roomname = bundle.getString("roomname");
        book_roomid = bundle.getString("roomid");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////       新方法，时间间隔为15分钟     ////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    private TimePicker.OnTimeChangedListener mStartTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    updateDisplay_1(view, minute_start_t, hourOfDay, minute);
                }
            };
    //    private void updateDisplay(TimePicker timePicker, Date date, int hourOfDay, int minute) {
    private void updateDisplay_1(TimePicker timePicker, int minute_start_t, int hourOfDay, int minute) {
        // do calculation of next time
        int nextMinute = 0;
        if (minute >= 45 && minute <= 59)
            nextMinute = 45;
        else if (minute >= 30)
            nextMinute = 30;
        else if (minute >= 15)
            nextMinute = 15;
        else if (minute > 0)
            nextMinute = 0;
        else {
            nextMinute = 45;
        }
        // remove ontimechangedlistener to prevent stackoverflow/infinite loop
        timePicker.setOnTimeChangedListener(mNullTimeChangedListener);

        // set minute
        timePicker.setCurrentMinute(nextMinute);

        // hook up ontimechangedlistener again
        timePicker.setOnTimeChangedListener(mStartTimeChangedListener);

        // update the date variable for use elsewhere in code
//        minute_end_t = nextMinute;
//        String mg = Integer.toString(minute_end_t);
//        toast(mg);
        Room_Book_Time.this.hour_start = hourOfDay;
        Room_Book_Time.this.minute_start = nextMinute;
        // 显示当前日期、时间
        showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);
    }

    private TimePicker.OnTimeChangedListener mEndTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    updateDisplay(view, minute_end_t, hourOfDay, minute);
                }
            };
    private TimePicker.OnTimeChangedListener mNullTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                }
            };

    //    private void updateDisplay(TimePicker timePicker, Date date, int hourOfDay, int minute) {
    private void updateDisplay(TimePicker timePicker, int minute_end_t, int hourOfDay, int minute) {

        // do calculation of next time
        int nextMinute = 0;
        if (minute >= 45 && minute <= 59)
            nextMinute = 45;
        else if (minute >= 30)
            nextMinute = 30;
        else if (minute >= 15)
            nextMinute = 15;
        else if (minute > 0)
            nextMinute = 0;
        else {
            nextMinute = 45;
        }

        // remove ontimechangedlistener to prevent stackoverflow/infinite loop
        timePicker.setOnTimeChangedListener(mNullTimeChangedListener);

        // set minute
        timePicker.setCurrentMinute(nextMinute);

        // hook up ontimechangedlistener again
        timePicker.setOnTimeChangedListener(mEndTimeChangedListener);

        // update the date variable for use elsewhere in code
//        minute_end_t = nextMinute;
//        String mg = Integer.toString(minute_end_t);
//        toast(mg);
        Room_Book_Time.this.hour_end = hourOfDay;
        Room_Book_Time.this.minute_end = nextMinute;
        // 显示当前日期、时间
        showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);
    }

    private class ConfirmOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if(hour_start > hour_end){
                toast(" 您设置的时间有误，开始时间大于结束时间，提交后将不会被通过！！！ ");
            }
            Intent intent = new Intent(Room_Book_Time.this, Room_Book.class);
            //点击确认
            Bundle mBundle = new Bundle();
            mBundle.putInt("book_time_year", year);
            mBundle.putInt("book_time_month", month);
            mBundle.putInt("book_time_day", day);
            mBundle.putInt("book_time_hour_s", hour_start);
            mBundle.putInt("book_time_minute_s", minute_start);
            mBundle.putInt("book_time_hour_e", hour_end);
            mBundle.putInt("book_time_minute_e", minute_end);
            mBundle.putString("roomname", book_roomname);
            mBundle.putString("roomid", book_roomid);
            intent.putExtras(mBundle);
            startActivity(intent);
            doFinish();
        }
    }

    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month
            , int day, int hour_s, int minute_s, int hour_e, int minute_e) {
        EditText show = (EditText) findViewById(R.id.show);
        show.setText("您的预定时间为：" + year + "年"
                + (month + 1) + "月" + day + "日  "
                + hour_s + "时" + minute_s + "分" + "  " + "到" + "  " + hour_e + "时" + minute_e + "分");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 其他方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(Room_Book_Time.this, Room_Book.class);
            //点击确认
            Bundle mBundle = new Bundle();
            mBundle.putString("roomname", book_roomname);
            mBundle.putString("roomid", book_roomid);
            intent.putExtras(mBundle);
            startActivity(intent);
            this.finish();
//            forward(Room_Book.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
