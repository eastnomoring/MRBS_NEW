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

    /**
     * 获取用户信息方法
     * @param ctx
     * @param customer
     * @return
     */
    public static String getCustomerInfo (Context ctx, Customer customer) {
        Resources resources = ctx.getResources();
        StringBuffer sb = new StringBuffer();
        sb.append(resources.getString(R.string.info_username));
        sb.append("  |  ");
        sb.append(customer.getName()+"\n");
        sb.append(resources.getString(R.string.info_phone));
        sb.append("  |  ");
        sb.append(customer.getPhone()+"\n");
        sb.append(resources.getString(R.string.info_email));
        sb.append("  |  ");
        sb.append(customer.getEmail());
        return sb.toString();
    }
}
