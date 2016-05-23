package com.yzy.mrbs.activity;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class NoteSearch extends BaseUiUser {

    private List<Map<String, Object>> mData;
    public static String notes[] = new String[]{"记录0", "记录1", "记录2", "记录3", "记录4", "记录5", "记录6", "记录7", "记录8", "记录9"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_note_search);
        mData = getData();
        ListView listView = (ListView) findViewById(R.id.app_note_search_list_view);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);

    }

    //获取动态数组数据  可以由其他地方传来(json等)
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < notes.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("note", notes[i]);
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

                convertView = mInflater.inflate(R.layout.mrbs_activity_note_vlist, null);
                holder.notes = (TextView) convertView.findViewById(R.id.mrbs_activity_note_vlist_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.notes.setText((String) mData.get(position).get("note"));
            //holder.viewBtn.setOnClickListener(MyListener(position));

            return convertView;
        }
    }

    //提取出来方便点
    public final class ViewHolder {
        public TextView notes;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
            forward(UiMain.class);
        }
        return super.onKeyDown(keyCode, event);
    }

}
