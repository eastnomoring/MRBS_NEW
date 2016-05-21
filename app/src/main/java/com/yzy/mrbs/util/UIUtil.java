package com.yzy.mrbs.util;

import android.content.Context;
import android.content.res.Resources;

import com.yzy.mrbs.R;
import com.yzy.mrbs.model.Customer;

/**
 * 获取用户信息
 * Created by ZhiYuan on 2016/5/18.
 */
public class UIUtil {

    // tag for log
//	private static String TAG = UIUtil.class.getSimpleName();

    public static String getCustomerInfo (Context ctx, Customer customer) {
        Resources resources = ctx.getResources();
        StringBuffer sb = new StringBuffer();
        sb.append(resources.getString(R.string.customer_blog));
        sb.append(" ");
        sb.append(customer.getBlogcount());
        sb.append(" | ");
        sb.append(resources.getString(R.string.customer_fans));
        sb.append(" ");
        sb.append(customer.getFanscount());
        return sb.toString();
    }
}