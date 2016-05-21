package com.yzy.mrbs.activity;

import java.util.Calendar;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUi;

/**
 * 会议室信息界面
 * Created by ZhiYuan on 2016/5/13.
 */
public class Room_Book extends BaseUi {

    // 定义5个记录当前时间的变量
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
        setContentView(R.layout.mrbs_activity_room_book);
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
                Room_Book.this.year = year;
                Room_Book.this.month = month;
                Room_Book.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);
            }
        });
        // 为TimePicker指定监听器
        timePicker_start.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minuteOfDay) {
                Room_Book.this.hour_start = hourOfDay;
                Room_Book.this.minute_start = minuteOfDay;
                // 显示当前日期、时间
                showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);

            }
        });
        // 为TimePicker指定监听器
        timePicker_end.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minuteOfDay) {
                Room_Book.this.hour_end = hourOfDay;
                Room_Book.this.minute_end = minuteOfDay;
                // 显示当前日期、时间
                showDate(year, month, day, hour_start, minute_start, hour_end, minute_end);

            }
        });
    }

    private class ConfirmOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            //点击确认
            Bundle mBundle = new Bundle();
            mBundle.putString("text", "data from Room_Book");//压入数据
            openDialog(mBundle);

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
}
