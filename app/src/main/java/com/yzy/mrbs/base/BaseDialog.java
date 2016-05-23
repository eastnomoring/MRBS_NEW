package com.yzy.mrbs.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzy.mrbs.R;

/**
 * Created by ZhiYuan on 2016/4/27.
 */
public class BaseDialog {
    private TextView mTextTitle;
    private TextView mTextMessage;
    private Button btn_positive;
    private Button btn_negative;

    private Dialog mDialog;

    public BaseDialog(Context context, Bundle params) {
        //初始化对话框
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.main_dialog);
        mDialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
       //设置显示位置
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        window.setAttributes(wl);
//		window.setGravity(Gravity.CENTER);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.setLayout(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置标题
        mTextTitle = (TextView) mDialog.findViewById(R.id.title);
        mTextTitle.setTextColor(context.getResources().getColor(R.color.gray));
        mTextTitle.setText(params.getString("title"));
        //设置显示文本
        mTextMessage = (TextView) mDialog.findViewById(R.id.cs_main_dialog_text);
        mTextMessage.setTextColor(context.getResources().getColor(R.color.gray));
        mTextMessage.setText(params.getString("text"));
        //设置按钮
        btn_positive = (Button) mDialog.findViewById(R.id.positiveButton);
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_negative = (Button) mDialog.findViewById(R.id.negativeButton);
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        mImageClose = (ImageView) mDialog.findViewById(R.id.cs_main_dialog_close);
//        mImageClose.setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                mDialog.dismiss();
//            }
//        });
    }

    public void show() {
        mDialog.show();
    }
}
