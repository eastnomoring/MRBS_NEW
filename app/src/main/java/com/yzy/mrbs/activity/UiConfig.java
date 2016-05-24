package com.yzy.mrbs.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseHandler;
import com.yzy.mrbs.base.BaseMessage;
import com.yzy.mrbs.base.BaseTask;
import com.yzy.mrbs.base.BaseUi;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.base.BaseUser;
import com.yzy.mrbs.base.C;
import com.yzy.mrbs.list.SimpleList;
import com.yzy.mrbs.model.Config;
import com.yzy.mrbs.model.Customer;
import com.yzy.mrbs.phalapi.PhalapiHttpUtil;
import com.yzy.mrbs.util.AppCache;
import com.yzy.mrbs.util.AppUtil;
import com.yzy.mrbs.util.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 用户配置界面
 * Created by ZhiYuan on 2016/5/17.
 */
public class UiConfig extends BaseUiUser {
    private ListView listConfig;
    private ImageView faceImage;
    private String faceImageUrl;
    private String s_getinfo_request;
    private String sign = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_config);

        // 设置界面消息处理器
        this.setHandler(new ConfigHandler(this));

        // 获取配置功能列表
        listConfig = (ListView) findViewById(R.id.app_config_list_main);

//        //更新用户信息
//        update_user_info();

    }
    @Override
    public void onStart() {
        super.onStart();


        // 列表参数准备
        final ArrayList<Config> dataList = new ArrayList<Config>();
        dataList.add(new Config(getResources().getString(R.string.config_face), customer.getFace()));
        dataList.add(new Config(getResources().getString(R.string.config_sign), customer.getSign()));

        String[] from = {Config.COL_NAME};
        int[] to = {R.id.tpl_list_menu_text_name};
        //使用SimpleList列表
        listConfig.setAdapter(new SimpleList(this, AppUtil.dataToList(dataList, from), R.layout.tpl_list_menu, from, to));
        listConfig.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // 修改头像逻辑
                if (pos == 0) {
                    overlay(UiSetFace.class);
                    // 修改签名逻辑
                } else {
                    Bundle data = new Bundle();
                    data.putInt("action", C.action.edittext.CONFIG);
                    data.putString("value", dataList.get(pos).getValue());
                    doEditText(data);
                }
            }
        });

        // 获取用户信息 适用于旧服务器框架
//        HashMap<String, String> cvParams = new HashMap<String, String>();
//        cvParams.put("customerId", customer.getId());//使用用户ID 联网 在 服务端查看用户信息接口 获取用户信息
//        this.doTaskAsync(C.task.customerView, C.api.customerView, cvParams);


        // 获取用户信息 适用于新服务器框架
        customer.getId();
//        String s_setsign = "http://192.168.1.103:80/PhalApi/Public/user/?service=User.getuserinfo"+"&userid=" +customer.getId();
        String s_setsign = "http://115.28.193.57:80/PhalApi/Public/user/?service=User.getuserinfo"+"&userid=" +customer.getId();
        try {
            s_getinfo_request = PhalapiHttpUtil.getRequest(s_setsign);
        } catch (Exception e) {
            e.printStackTrace();
            toast("网络连接失败，未更新您的签名");
        }
        try {
            JSONObject jsonObj = new JSONObject(s_getinfo_request);
            JSONObject jsonObj_data = new JSONObject(jsonObj.getString("data"));
            sign = jsonObj_data.getString("sign");
            BaseUser.setSimpleInfo(
                    jsonObj_data.getString("userid"),
                    jsonObj_data.getString("username"),
                    jsonObj_data.getString("userpass"),
                    sign,
                    jsonObj_data.getString("email"),
                    jsonObj_data.getString("phone")
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        //更新用户信息
        update_user_info();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////                 旧方法适用于Hush Framework框架服务端             //////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *  异步回调方法（获取网络请求后才会被调用）
     * @param taskId
     * @param message
     */
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.customerView:
                try {
                    final Customer customer = (Customer) message.getResult("Customer");
                    TextView textTop = (TextView) this.findViewById(R.id.tpl_list_info_text_top);
                    TextView textBottom = (TextView) this.findViewById(R.id.tpl_list_info_text_bottom);
                    textTop.setText(customer.getSign());//用户签名
                    textBottom.setText(UIUtil.getCustomerInfo(this, customer));//用户信息
                    // 异步加载头像
                    faceImage = (ImageView) this.findViewById(R.id.tpl_list_info_image_face);
                    faceImageUrl = customer.getFaceurl();
                    loadImage(faceImageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                break;
        }
    }

    @Override
    public void onNetworkError (int taskId) {
        super.onNetworkError(taskId);
    }
    /**
     * 内部类
     */
    private class ConfigHandler extends BaseHandler {
        public ConfigHandler(BaseUi ui) {
            super(ui);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case BaseTask.LOAD_IMAGE:
                        Bitmap face = AppCache.getImage(faceImageUrl);
                        faceImage.setImageBitmap(face);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ui.toast(e.getMessage());
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
            forward(UiMain.class);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 从本地更新用户签名
     */
    public void update_user_info(){
        TextView textTop = (TextView) this.findViewById(R.id.tpl_list_info_text_top);
        TextView textBottom = (TextView) this.findViewById(R.id.tpl_list_info_text_bottom);
        textTop.setText(sign);//用户签名
        textBottom.setText(UIUtil.getCustomerInfo(this, customer));//用户信息
    }

}
