package com.yzy.mrbs.util;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.yzy.mrbs.R;

/**
 * Created by ZhiYuan on 2016/5/22.
 */
public class AppFilter {

    public static Spanned getHtml (String text) {
        return Html.fromHtml(text);//将字符串进行HTML格式化
    }

    /* used by list classes */
    public static void setHtml (TextView tv, String text) {
        if (tv.getId() == R.id.tpl_list_blog_text_content ||
                tv.getId() == R.id.tpl_list_blog_text_comment
                ) {
            tv.setText(AppFilter.getHtml(text));
        } else {
            tv.setText(text);
        }
    }
}
