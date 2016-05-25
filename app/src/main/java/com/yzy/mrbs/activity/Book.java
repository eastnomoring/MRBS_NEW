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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.model.Room;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 预定界面
 * Created by ZhiYuan on 2016/4/20.
 */
public class Book extends BaseUiUser {

    /**
     * Called when the activity is first created.
     */
    private List<Map<String, Object>> mData;   //本地信息
    private static List<Map<String, Object>> mSData;    //网络信息
    private static List<Room> rooms;                  //JSON字符串中提取的Room列表
    private String s_book_request;            //从服务器上获取的JSON字符串
    public static String title[] = new String[]{"会议室0", "会议室1", "会议室2", "会议室3", "会议室4", "会议室5", "会议室6", "会议室7", "会议室8", "会议室9"};
    public static String info[] = new String[]{"info0", "info1", "info2", "info3", "info4", "info5", "info6", "info7", "info8", "info9",};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book);
        ListView listView = (ListView) findViewById(R.id.app_index_list_view);
        mData = getData();  //本地信息
        rooms = getLists();
        mSData = getSData();//网络信息
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);

    }
    //获取Room信息列表
    private List<Room> getLists() {
        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/room/?service=Room.getroomlist";
        List<Room> mlists = new ArrayList<Room>();
        try {
            s_book_request = PhalapiHttpUtil.getRequest(s_setsign);
        } catch (Exception e) {
            e.printStackTrace();
            toast("网络连接失败");
        }
        try {
            JSONObject jsonObj = new JSONObject(s_book_request);
            JSONArray array = new JSONArray(jsonObj.getString("data"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                int roomid = item.getInt("roomid");
                String roomname = item.getString("roomname");
                String roominfo = item.getString("roominfo");
                int roomface = item.getInt("roomface");
                String roomfaceurl = item.getString("roomfaceurl");
                mlists.add(new Room(roomid, roomname, roominfo,roomface,roomfaceurl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mlists;
    }

    //获取数组数据  本地信息
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < title.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", title[i]);
            map.put("info", info[i]);
            list.add(map);
        }
        return list;
    }
    //获取动态数组数据  由JSON传来
    private List<Map<String, Object>> getSData() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rooms.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", rooms.get(i).getId());
            map.put("name", rooms.get(i).getName());
            map.put("info", rooms.get(i).getInfo());
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
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
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
                convertView = mInflater.inflate(R.layout.mrbs_activity_book_vlist, null);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.info = (TextView) convertView.findViewById(R.id.info);
                holder.viewBtn = (Button) convertView.findViewById(R.id.view_btn);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (mSData == null) {
                holder.title.setText((String) mData.get(position).get("title"));
                holder.info.setText((String) mData.get(position).get("info"));
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
                    holder.info.setText((String) mSData.get(position).get("info"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.viewBtn.setTag(position);
                //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
                holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //转到预订界面Room_Book,并传递参数
                        Intent intent = new Intent(Book.this, Room_Book.class);
                        //用Bundle携带数据
                        Bundle bundle = new Bundle();
                        //传递roomid、roomname
                        bundle.putString("roomid", mSData.get(position).get("id").toString());
                        bundle.putString("roomname", mSData.get(position).get("name").toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
//                        showInfo(position);
                    }
                });

            }
            return convertView;
        }
    }
    //****************************************第二种方法，高手一般都用此种方法,具体原因，我还不清楚,有待研究

    //		public View getView(int position, View convertView, ViewGroup parent) {
//			 ViewHolder holder = null;
//			 MyListener myListener=null;
//			if (convertView == null) {
//
//				holder=new ViewHolder();
//
//				//可以理解为从vlist获取view  之后把view返回给ListView
//				 myListener=new MyListener(position);
//
//				convertView = mInflater.inflate(R.layout.vlist, null);
//				holder.title = (TextView)convertView.findViewById(R.id.title);
//				holder.info = (TextView)convertView.findViewById(R.id.info);
//				holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
//				convertView.setTag(holder);
//			}else {
//				holder = (ViewHolder)convertView.getTag();
//			}
//
//			holder.title.setText((String)mData.get(position).get("title"));
//			holder.info.setText((String)mData.get(position).get("info"));
//			holder.viewBtn.setTag(position);
//			//给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
//			holder.viewBtn.setOnClickListener( myListener);
//
//			//holder.viewBtn.setOnClickListener(MyListener(position));
//
//			return convertView;
//		}
//	}
//
//	 private class MyListener implements OnClickListener{
//	        int mPosition;
//	        public MyListener(int inPosition){
//	            mPosition= inPosition;
//	        }
//	        @Override
//	        public void onClick(View v) {
//	            // TODO Auto-generated method stub
//	            Toast.makeText(ListViewActivity.this, title[mPosition], Toast.LENGTH_SHORT).show();
//	        }
//
//	    }
//
//
    /**
     *  提取出来方便点
     */
    public final class ViewHolder {
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }
    /**
     * 测试用
     * @param position
     */
    public void showInfo(int position) {

        ImageView img = new ImageView(Book.this);
        img.setImageResource(R.drawable.b_book);
        new AlertDialog.Builder(this).setView(img)
                .setTitle("详情" + position)
                .setMessage("会议室：" + title[position] + "  信息:" + info[position])
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
