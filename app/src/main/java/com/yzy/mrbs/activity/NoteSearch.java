package com.yzy.mrbs.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.model.Note;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class NoteSearch extends BaseUiUser {
    private List<Map<String, Object>> mData;            //本地信息
    private static List<Map<String, Object>> mSData;    //网络信息
    private static List<Note> snotes;                   //JSON字符串中提取的Note列表
    private String s_note_request;                      //从服务器上获取的JSON字符串
    public static String notes[] = new String[]{"记录1", "记录2", "记录3", "记录4", "记录5", "记录6", "记录7", "记录8", "记录9"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_note_search);
        mData = getData();//本地信息
        snotes = getLists();
        mSData = getSData();//网络信息

    }
    @Override
    public void onStart() {
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.app_note_search_list_view);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }

    //获取Note信息列表
    private List<Note> getLists() {
        ///PhalApi/Public/book/?service=Book.getbookinfo&userid=1
        String s_setsign = "http://192.168.1.104:80/PhalApi/Public/book/?service=Book.getbookinfo"+"&userid="+customer.getId();
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
            map.put("note", notes[i]);
            data.add(map);
        }
        return data;
    }

    public class MyAdapter extends BaseAdapter {

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
        public View getView( final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                //可以理解为从vlist获取view  之后把view返回给ListView
                convertView = mInflater.inflate(R.layout.mrbs_activity_note_vlist, null);
                holder.notes = (TextView) convertView.findViewById(R.id.mrbs_activity_note_vlist_title);
                holder.viewBtn = (Button) convertView.findViewById(R.id.mrbs_activity_note_search);
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
        String timemsg = mSData.get(position).get("year").toString() + "年"
                + (mSData.get(position).get("month").toString()) + "月"
                + mSData.get(position).get("day").toString() + "日  "
                + mSData.get(position).get("hour_start").toString() + "时"
                + mSData.get(position).get("minute_start").toString() + "分"
                + "  "
                + "到"
                + "  "
                + mSData.get(position).get("hour_end").toString()+ "时"
                + mSData.get(position).get("minute_end").toString() + "分";
        new AlertDialog.Builder(this)
                .setTitle("记录" + (position+1))
                .setMessage("申请预约的时间：" + mSData.get(position).get("bookaddtime").toString()+ "\n"
                        +"预约申请的会议室："+ mSData.get(position).get("roomname").toString()+"\n"
                        +"预约申请的时间段："+timemsg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
            forward(UiMain.class);
        }
        return super.onKeyDown(keyCode, event);
    }

}
