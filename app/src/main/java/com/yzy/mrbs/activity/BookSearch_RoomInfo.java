package com.yzy.mrbs.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.model.Note;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhiYuan on 2016/5/26.
 */
public class BookSearch_RoomInfo extends BaseUiUser {

    private String roomid;
    private String roomname;


    private static List<Map<String, Object>> mSData;    //网络信息
    private static List<Note> snotes;                   //JSON字符串中提取的Note列表
    private String s_search_request;                      //从服务器上获取的JSON字符串



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book_search_room_info);
        //接收从BookSearch传来的参数
        Bundle bundle = this.getIntent().getExtras();
        roomname = bundle.getString("roomname");
        roomid = bundle.getString("roomid");
        snotes = getLists();
    }
    //获取Note信息列表
    private List<Note> getLists() {
//        String s_setsign = "http://192.168.1.104:80/PhalApi/Public/book/?service=Book.getbookinfo"+"&userid="+customer.getId();
        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/book/?service=Book.getroominfo"+"&roomid="+roomid;
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
                mlists.add(new Note(roomid, userid, year,month,day,hour_start,minute_start,hour_end,
                        minute_end,roomnote,phone,email,status,bookaddtime,roomname));
//                toast("结束数据处理");
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
