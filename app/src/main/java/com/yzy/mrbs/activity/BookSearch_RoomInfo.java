package com.yzy.mrbs.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.model.Note;
import com.yzy.mrbs.model.Period;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhiYuan on 2016/5/26.
 */
public class BookSearch_RoomInfo extends BaseUiUser {
    String Monday[];
    String Sunday[];

    private String roomid;
    private String roomname;


    private static List<Map<String, Object>> mSData;    //网络信息
    private static List<Note> snotes;                   //JSON字符串中提取的Note列表
    private String s_search_request;                    //从服务器上获取的JSON字符串
    private List<Period> periods;

    LinearLayout weekPanels[] = new LinearLayout[7];
    List periodData[] = new ArrayList[7];
    int itemHeight;
    int marTop, marLeft;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book_search_room_info);

        //接收从BookSearch传来的参数
        Bundle bundle = this.getIntent().getExtras();
        roomname = bundle.getString("roomname");
        roomid = bundle.getString("roomid");

        //获取此会议室的预订情况Note
        snotes = getLists();

        //获取本地日期
        Calendar cal_1 = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        cal_1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        Log.i("本周一日期", df1.format(cal_1.getTime()));
        Calendar cal_2 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        cal_2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal_2.add(Calendar.WEEK_OF_YEAR, 1);
        Log.i("本周末日期", df2.format(cal_2.getTime()));


        //从Note列表中筛选出日期在本周的预约
        Monday = df1.format(cal_1.getTime()).split("-");
        Log.i("本周一年", Monday[0]);
        Log.i("本周一月", Monday[1]);
        Log.i("本周一日", Monday[2]);
        Sunday = df2.format(cal_2.getTime()).split("-");
        Log.i("本周日年", Sunday[0]);
        Log.i("本周日月", Sunday[1]);
        Log.i("本周日日", Sunday[2]);

        //数据
        periods = getData();
        getperiodData();

        itemHeight = getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
        marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
        marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);


        for (int i = 0; i < weekPanels.length; i++) {
            weekPanels[i] = (LinearLayout) findViewById(R.id.weekPanel_1 + i);
//            initWeekPanel(weekPanels[i], courseData[i]);
            initWeekPanel(weekPanels[i], periodData[i]);
        }


    }

    //获取动态数组数据  由JSON传来
    private List<Map<String, Object>> getSData() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < snotes.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roomid", snotes.get(i).getRoomid());
            map.put("userid", snotes.get(i).getUserid());
            map.put("year", snotes.get(i).getYear());
            map.put("month", snotes.get(i).getMonth());
            map.put("day", snotes.get(i).getDay());
            map.put("hour_start", snotes.get(i).getHour_start());
            map.put("minute_start", snotes.get(i).getMinute_start());
            map.put("hour_end", snotes.get(i).getHour_end());
            map.put("minute_end", snotes.get(i).getMinute_end());
            map.put("roomnote", snotes.get(i).getRoomnote());
            map.put("phone", snotes.get(i).getPhone());
            map.put("email", snotes.get(i).getEmail());
            map.put("status", snotes.get(i).getStatus());
            map.put("bookaddtime", snotes.get(i).getBookaddtime());
            map.put("roomname", snotes.get(i).getRoomname());
            data.add(map);
        }
        return data;
    }

    private List<Period> getData() {
        List<Period> list = new ArrayList<Period>();
        for (int i = 0; i < snotes.size(); i++) {
            int id = -1;
            double start = 0.0;
            double step = 0.0;
            Note item = snotes.get(i);
            int year = item.getYear();
            int month = item.getMonth();
            int day = item.getDay();
            int hour_start = item.getHour_start();
            int minute_start = item.getMinute_start();
            int hour_end = item.getHour_end();
            int minute_end = item.getMinute_end();
            if (year >= Integer.parseInt(Monday[0]) &&
                    year <= Integer.parseInt(Sunday[0]) &&
                    month >= Integer.parseInt(Monday[1]) &&
                    month <= Integer.parseInt(Sunday[1]) &&
                    day >= Integer.parseInt(Monday[2]) &&
                    day <= Integer.parseInt(Sunday[2])
                    ) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                id = cal.get(Calendar.DAY_OF_WEEK);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
                if ((hour_start - 8) >= 0 && hour_end >= hour_start) {
                    start = (hour_start - 8) + (minute_start / 60);
                    step = (60 - minute_start) / 60 + (hour_end - hour_start - 1) + (minute_end / 60);
                }
            }
            list.add(new Period(id, start, step));
        }
        return list;
    }
    public void getperiodData(){

        List<Period> list0 = new ArrayList<Period>();
        List<Period> list1 = new ArrayList<Period>();
        List<Period> list2 = new ArrayList<Period>();
        List<Period> list3 = new ArrayList<Period>();
        List<Period> list4 = new ArrayList<Period>();
        List<Period> list5 = new ArrayList<Period>();
        List<Period> list6 = new ArrayList<Period>();
        for (int i = 0; i < periods.size(); i++) {
            Period item = periods.get(i);
            int id = item.getId();
            if (id == 2) {
                list0.add(item);
            } else if (id == 3) {
                list1.add(item);
            } else if (id == 4) {
                list2.add(item);

            } else if (id == 5) {
                list3.add(item);

            } else if (id == 6) {
                list4.add(item);

            } else if (id == 7) {
                list5.add(item);

            } else if (id == 1) {
                list6.add(item);
            } else if (id == -1) {
                return;
            }

        }
        periodData[0]=list0;
        periodData[1]=list1;
        periodData[2]=list2;
        periodData[3]=list3;
        periodData[4]=list4;
        periodData[5]=list5;
        periodData[6]=list6;

    }

    //    public void getData(){
//        List<Period>list1=new ArrayList<Period>();
//        Period c1 =new Period("软件工程","A402", 1, 4, "典韦", "1002");
//        list1.add(c1);
//        list1.add(new Period("C语言", "A101", 6, 3, "甘宁", "1001"));
//        courseData[0]=list1;
//
//        List<Period>list2=new ArrayList<Period>();
//        list2.add(new Period("计算机组成原理", "A106", 6, 3, "马超", "1001"));
//        courseData[1]=list2;
//
//        List<Period>list3=new ArrayList<Period>();
//        list3.add(new Period("数据库原理", "A105", 2, 3, "孙权", "1008"));
//        list3.add(new Period("计算机网络", "A405", 6, 2, "司马懿", "1009"));
//        list3.add(new Period("电影赏析", "A112", 9, 2, "诸葛亮", "1039"));
//        courseData[2]=list3;
//
//        List<Period>list4=new ArrayList<Period>();
//        list4.add(new Period("数据结构", "A223", 1, 3, "刘备", "1012"));
//        list4.add(new Period("操作系统", "A405", 6, 3, "曹操", "1014"));
//        courseData[3]=list4;
//
//        List<Period>list5=new ArrayList<Period>();
//        list5.add(new Period("Android开发","C120",1,4,"黄盖","1250"));
//        list5.add(new Period("游戏设计原理","C120",8,4,"陆逊","1251"));
//        courseData[4]=list5;
//    }
    public void initWeekPanel(LinearLayout ll, List<Period> data) {
        if (ll == null || data == null || data.size() < 1) {
            toast("近一周此会议室没有预约");
            return;
        }
        Log.i("Msg", "初始化面板");
        Period pre = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            Period c = data.get(i);
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    (int) (itemHeight * c.getStep() + marTop * (c.getStep() - 1)));
            if (i > 0) {
                lp.setMargins(marLeft, (int) (c.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop, 0, 0);
            } else {
                lp.setMargins(marLeft, (int) (c.getStart() - 1) * (itemHeight + marTop) + marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
//            tv.setText(c.getId() + "\n" + c.getStart() + "\n" + c.getStep() + "\n");
//            tv.setBackgroundColor(getResources().getColor(R.color.classIndex));
            tv.setBackground(getResources().getDrawable(R.drawable.background_login));
            ll.addView(tv);
            pre = c;
        }
    }

    //获取Note信息列表
    private List<Note> getLists() {
//        String s_setsign = "http://192.168.1.104:80/PhalApi/Public/book/?service=Book.getbookinfo"+"&userid="+customer.getId();
        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/book/?service=Book.getroominfo" + "&roomid=" + roomid;
        List<Note> mlists = new ArrayList<Note>();
        try {
            s_search_request = PhalapiHttpUtil.getRequest(s_setsign);
        } catch (Exception e) {
            e.printStackTrace();
            toast("网络连接失败");
        }
        try {
            JSONObject jsonObj = new JSONObject(s_search_request);
            toast(jsonObj.getString("msg"));
            JSONArray array = new JSONArray(jsonObj.getString("data"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                int roomid = item.getInt("roomid");
                int userid = item.getInt("userid");
                int year = item.getInt("year");
                int month = item.getInt("month");
                int day = item.getInt("day");
                int hour_start = item.getInt("hour_start");
                int minute_start = item.getInt("minute_start");
                int hour_end = item.getInt("hour_end");
                int minute_end = item.getInt("minute_end");
                String roomnote = item.getString("roomnote");
                String phone = item.getString("phone");
                String email = item.getString("email");
                int status = item.getInt("status");
                String bookaddtime = item.getString("bookaddtime");
                String roomname = item.getString("roomname");
                mlists.add(new Note(roomid, userid, year, month, day, hour_start, minute_start, hour_end,
                        minute_end, roomnote, phone, email, status, bookaddtime, roomname));
            }
        } catch (Exception e) {
            e.printStackTrace();
            toast("数据处理失败");
        }
        return mlists;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            forward(BookSearch.class);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
