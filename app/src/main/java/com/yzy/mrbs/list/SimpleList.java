package com.yzy.mrbs.list;

import android.content.Context;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.yzy.mrbs.util.AppFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class SimpleList extends SimpleAdapter {

    public SimpleList(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public void setViewText(TextView v, String text) {
        AppFilter.setHtml(v, text);
    }
}
