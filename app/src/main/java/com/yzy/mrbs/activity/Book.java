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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;

/**
 * 预定界面
 * Created by ZhiYuan on 2016/4/20.
 */
public class Book extends BaseUiUser {

    /**
     * Called when the activity is first created.
     */
    private List<Map<String, Object>> mData;
    private int flag;
    public static String title[] = new String[]{"会议室0", "会议室1", "会议室2", "会议室3", "会议室4", "会议室5", "会议室6", "会议室7", "会议室8", "会议室9"};
    public static String info[] = new String[]{"info0", "info1", "info2", "info3", "info4", "info5", "info6", "info7", "info8", "info9",};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book);
        mData = getData();
        ListView listView = (ListView) findViewById(R.id.app_index_list_view);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);

    }

    //获取动态数组数据  可以由其他地方传来(json等)
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

            holder.title.setText((String) mData.get(position).get("title"));
            holder.info.setText((String) mData.get(position).get("info"));
            holder.viewBtn.setTag(position);
            //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    forward(Room_Book.class);
//                    showInfo(position);
                }
            });

            //holder.viewBtn.setOnClickListener(MyListener(position));

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
