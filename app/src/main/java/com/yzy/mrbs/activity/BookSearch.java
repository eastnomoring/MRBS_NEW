package com.yzy.mrbs.activity;


import android.content.Context;
import android.content.Intent;
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
import com.yzy.mrbs.model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class BookSearch extends Book {
    private List<Map<String, Object>> mData;   //本地信息
    private static List<Map<String, Object>> mSData;    //网络信息
    private static List<Room> rooms;                  //JSON字符串中提取的Room列表
    private String s_book_request;            //从服务器上获取的JSON字符串
    public static String title[] = new String[]{"会议室1", "会议室2", "会议室3", "会议室4", "会议室5", "会议室6", "会议室7", "会议室8", "会议室9"};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book_search);
        ListView listView = (ListView) findViewById(R.id.app_book_search_list_view);
        mData = getData();  //本地信息
        rooms = getLists();
        mSData = getSData();//网络信息
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        if (mSData != null) {
            toast("从服务器获取会议室列表");
        }
    }

    //获取数组数据  本地信息
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < title.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", title[i]);
            list.add(map);
        }
        return list;
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {  //制定包含的选项个数
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
                convertView = mInflater.inflate(R.layout.mrbs_activity_book_search_vlist, null);
                holder.title = (TextView) convertView.findViewById(R.id.mrbs_activity_book_search_vlist_title);
                holder.viewBtn = (Button) convertView.findViewById(R.id.mrbs_activity_book_search);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (mSData == null) {
                holder.title.setText((String) mData.get(position).get("title"));
                holder.viewBtn.setTag(position);
                //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
                holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //forward(Room_Book.class);
                        //showInfo(position);
                    }
                });
                //holder.viewBtn.setOnClickListener(MyListener(position));
            } else {
                try {
                    holder.title.setText((String) mSData.get(position).get("name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.viewBtn.setTag(position);
                //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
                holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(BookSearch.this, BookSearch_RoomInfo.class);
//                        //用Bundle携带数据
//                        Bundle bundle = new Bundle();
//                        //传递roomid、roomname
//                        bundle.putString("roomid", mSData.get(position).get("id").toString());
//                        bundle.putString("roomname", mSData.get(position).get("name").toString());
//                        intent.putExtras(bundle);
//                        startActivity(intent);

                    }
                });

            }
            return convertView;
        }
    }

    public final class ViewHolder {
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
            forward(UiMain.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
