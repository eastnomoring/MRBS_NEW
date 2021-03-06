package com.yzy.mrbs.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.base.BaseUser;
import com.yzy.mrbs.model.Note;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by ZhiYuan on 2016/5/29.
 */
public class Admin extends BaseUiUser {

    private List<Map<String, Object>> mData;            //本地信息
    private static List<Map<String, Object>> mSData;    //网络信息
    private static List<Note> snotes;                   //JSON字符串中提取的Note列表
    private String s_note_request;                      //从服务器上获取的JSON字符串
    private String s_admin_request;                      //从服务器上获取的JSON字符串
    public static String notes[] = new String[]{"记录1", "记录2", "记录3", "记录4", "记录5", "记录6", "记录7", "记录8", "记录9"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_admin);
        mData = getData();//本地信息
        snotes = getLists();
        mSData = getSData();//网络信息

    }

    @Override
    public void onStart() {
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.app_admin_list_view);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }

    //获取Note信息列表
    private List<Note> getLists() {
//        String s_setsign = "http://192.168.1.104:80/PhalApi/Public/book/?service=Book.getbookinfo"+"&userid="+customer.getId();
        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/book/?service=Book.getallbookinfo";
        List<Note> mlists = new ArrayList<Note>();
        try {
            s_note_request = PhalapiHttpUtil.getRequest(s_setsign);
        } catch (Exception e) {
            e.printStackTrace();
            toast("网络连接失败");
        }
        try {
            JSONObject jsonObj = new JSONObject(s_note_request);
//            toast(jsonObj.getString("msg"));
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

//                toast("结束数据处理");
            }
        } catch (Exception e) {
            e.printStackTrace();
            toast("数据处理失败");
        }
        return mlists;
    }

    //获取数组数据  本地信息
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < notes.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("note", notes[i]);
            list.add(map);
        }
        return list;
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
            map.put("note", "记录" + (i + 1));
            data.add(map);
        }
        return data;
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {   //制定包含的选项个数
            // TODO Auto-generated method stub
            if (mSData == null) {
                return mData.size();
            } else {
                return mSData.size();
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        //****************************************final方法
        // 注意原本getView方法中的int position变量是非final的，现在改为final
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                //可以理解为从vlist获取view  之后把view返回给ListView
                convertView = mInflater.inflate(R.layout.mrbs_activity_admin_vlist, null);
                holder.notes = (TextView) convertView.findViewById(R.id.mrbs_activity_admin_vlist_title);
                holder.viewBtn = (Button) convertView.findViewById(R.id.mrbs_activity_admin);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                holder.notes.setText((String) mSData.get(position).get("note"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //holder.viewBtn.setOnClickListener(MyListener(position));
            holder.viewBtn.setTag(position);
            //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    toast(mSData.get(position).get("note").toString());
                    showInfo(position);
                }
            });
            return convertView;
        }
    }

    //提取出来方便点
    public final class ViewHolder {
        public TextView notes;
        public Button viewBtn;
    }

    public void showInfo(int position) {
        final String url = "&roomid=" + mSData.get(position).get("roomid").toString() +
                "&userid=" + mSData.get(position).get("userid").toString() +
                "&year=" + mSData.get(position).get("year").toString() +
                "&month=" + mSData.get(position).get("month").toString() +
                "&day=" + mSData.get(position).get("day").toString() +
                "&hour_start=" + mSData.get(position).get("hour_start").toString() +
                "&minute_start=" + mSData.get(position).get("minute_start").toString() +
                "&hour_end=" + mSData.get(position).get("hour_end").toString() +
                "&minute_end=" + mSData.get(position).get("minute_end").toString();
        String timemsg = mSData.get(position).get("year").toString() + "年"
                + mSData.get(position).get("month").toString() + "月"
                + mSData.get(position).get("day").toString() + "日  "
                + mSData.get(position).get("hour_start").toString() + "时"
                + mSData.get(position).get("minute_start").toString() + "分"
                + "  "
                + "到"
                + "  "
                + mSData.get(position).get("hour_end").toString() + "时"
                + mSData.get(position).get("minute_end").toString() + "分";
        new AlertDialog.Builder(this)
                .setTitle("记录" + (position + 1))
                .setMessage("预约人ID：" + mSData.get(position).get("userid").toString() + "\n"
                        + "申请预约的时间：" + paserTime(mSData.get(position).get("bookaddtime").toString()) + "\n"
                        + "预约申请的会议室：" + mSData.get(position).get("roomname").toString() + "\n"
                        + "预约申请的时间段：" + timemsg + "\n"
                        + "预约状态：" + mSData.get(position).get("status").toString())
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //http://127.0.0.1/PhalApi/Public/book/?service=Book.setbookstatus&roomid=1&userid=3&year=2016&month=6&day=3&hour_start=9&minute_start=0&hour_end=12&minute_end=30
                        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/book/?service=Book.setbookstatus" + url;
                        try {
                            s_admin_request = PhalapiHttpUtil.getRequest(s_setsign);
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("网络连接失败");
                        }
                        try {
                            JSONObject jsonObj = new JSONObject(s_admin_request);
//                            toast(jsonObj.getString("msg"));
                            if (jsonObj.getInt("ret") == 200) {
                                toast("修改会议室预约状态成功");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("数据处理失败");
                        }


                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * 将服务器时间转化为北京时间
     *
     * @param time
     * @return
     */
    public String paserTime(String time) {
        int i_time = Integer.parseInt(time);
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = format.format(new Date(i_time * 1000L));
        return times;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
