package com.yzy.mrbs.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.yzy.mrbs.R;
import com.yzy.mrbs.util.AppCache;
import com.yzy.mrbs.util.AppUtil;

import java.util.HashMap;

/**
 *
 *
 * 界面控制器基类
 *
 * Created by ZhiYuan on 2016/4/27.
 */
public class BaseUi extends Activity {

    protected BaseApp app;
    protected BaseHandler handler;
    protected BaseTaskPool taskPool;
    protected boolean showLoadBar = false;
    protected boolean showDebugMsg = true;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // debug memory
        debugMemory("onCreate");
        // async task handler
        this.handler = new BaseHandler(this);
        // init task pool
        this.taskPool = new BaseTaskPool(this);
        // init application
        this.app = (BaseApp) this.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // debug memory
        debugMemory("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // debug memory
        debugMemory("onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        // debug memory
        debugMemory("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        // debug memory
        debugMemory("onStop");
    }

    /**
     * 消息提示框组件的调用方法
     * @param msg
     */
    public void toast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 在当前界面之上覆盖目标界面
     * @param classObj
     */
    public void overlay (Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    /**
     * 在当前界面之上覆盖目标界面
     * @param classObj
     * @param params
     */
    public void overlay (Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
    }

    /**
     * 切换当前界面至目标界面
     * @param classObj
     */
    public void forward (Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.finish();
    }

    /**
     * 切换当前界面至目标界面
     * @param classObj
     * @param params
     */
    public void forward (Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(params);
        this.startActivity(intent);
        this.finish();
    }

    /**
     * 获取当前界面的上下文对象
     * @return
     */
    public Context getContext () {
        return this;
    }

    /**
     * 获取当前界面的消息处理器类Handler
     * @return
     */
    public BaseHandler getHandler () {
        return this.handler;
    }

    /**
     * 设置消息处理器类Handler，可用于设置自定义的消息处理器
     * @param handler
     */
    public void setHandler (BaseHandler handler) {
        this.handler = handler;
    }

    /**
     *获取对应的模板对象
     * @return
     */
    public LayoutInflater getLayout () {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 根据ID获取对应的模板对象
     * @param layoutId
     * @return
     */
    public View getLayout (int layoutId) {
        return getLayout().inflate(layoutId, null);
    }

    /**
     * 根据ID获取对应的模板对象
     * @param layoutId
     * @param itemId
     * @return
     */
    public View getLayout (int layoutId, int itemId) {
        return getLayout(layoutId).findViewById(itemId);
    }

    /**
     *获取异步任务池
     * @return
     */
    public BaseTaskPool getTaskPool () {
        return this.taskPool;
    }
    
    /**
     *  显示加载进度条
     */
    public void showLoadBar () {
        this.findViewById(R.id.main_load_bar).setVisibility(View.VISIBLE);
        this.findViewById(R.id.main_load_bar).bringToFront();
        showLoadBar = true;
    }

    /**
     * 隐藏加载进度条
     */
    public void hideLoadBar () {
        if (showLoadBar) {
            this.findViewById(R.id.main_load_bar).setVisibility(View.GONE);
            showLoadBar = false;
        }
    }

    /**
     * 快速打开Dialog窗口
     * @param params
     */
    public void openDialog(Bundle params) {
        new BaseDialog(this, params).show();
    }


    /**
     * 快速加载远程图片
     * @param url
     */
    public void loadImage (final String url) {
        taskPool.addTask(0, new BaseTask(){
            @Override
            public void onComplete(){
                AppCache.getCachedImage(getContext(), url);
                sendMessage(BaseTask.LOAD_IMAGE);
            }
        }, 0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // logic method

    /**
     * 结束当前界面
     */
    public void doFinish () {
        this.finish();
    }

    /**
     * 用户注销
     */
    public void doLogout () {
        BaseUser.setLogin(false);
    }

    /**
     * 编辑文本
     */
    public void doEditText () {
        Intent intent = new Intent();
        intent.setAction(C.intent.action.EDITTEXT);
        this.startActivity(intent);
    }

    /**
     * 编辑文本
     * @param data
     */
    public void doEditText (Bundle data) {
        Intent intent = new Intent();
        intent.setAction(C.intent.action.EDITTEXT);
        intent.putExtras(data);
        this.startActivity(intent);
    }

    /**
     * 发送消息，常与消息处理器类Handler配合使用
     * @param what
     */
    public void sendMessage (int what) {
        Message m = new Message();
        m.what = what;
        handler.sendMessage(m);
    }

    /**
     * 发送消息，常与消息处理器类Handler配合使用
     * @param what
     * @param data
     */
    public void sendMessage (int what, String data) {
        Bundle b = new Bundle();
        b.putString("data", data);
        Message m = new Message();
        m.what = what;
        m.setData(b);
        handler.sendMessage(m);
    }

    /**
     * 发送消息，常与消息处理器类Handler配合使用
     * @param what
     * @param taskId
     * @param data
     */
    public void sendMessage (int what, int taskId, String data) {
        Bundle b = new Bundle();
        b.putInt("task", taskId);
        b.putString("data", data);
        Message m = new Message();
        m.what = what;
        m.setData(b);
        handler.sendMessage(m);
    }

    /**
     * 创建新的异步任务
     * @param taskId
     * @param delayTime
     */
    public void doTaskAsync (int taskId, int delayTime) {
        taskPool.addTask(taskId, new BaseTask(){
            @Override
            public void onComplete () {
                sendMessage(BaseTask.TASK_COMPLETE, this.getId(), null);
            }
            @Override
            public void onError (String error) {
                sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
            }
        }, delayTime);
    }

    /**
     * 创建新的异步任务
     * @param taskId
     * @param baseTask
     * @param delayTime
     */
    public void doTaskAsync (int taskId, BaseTask baseTask, int delayTime) {
        taskPool.addTask(taskId, baseTask, delayTime);
    }

    /**
     * 创建新的异步任务
     * @param taskId
     * @param taskUrl
     */
    public void doTaskAsync(int taskId, String taskUrl) {
//        showLoadBar();
        taskPool.addTask(taskId, taskUrl, new BaseTask() {
            @Override
            public void onComplete(String httpResult) {
                sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
            }

            @Override
            public void onError(String error) {
                sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
            }
        }, 0);
    }

    /**
     * 创建新的异步任务
     * @param taskId
     * @param taskUrl
     * @param taskArgs
     */
    public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//        showLoadBar();
        taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask(){
            @Override
            public void onComplete (String httpResult) {
                sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
            }
            @Override
            public void onError (String error) {
                sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
            }
        }, 0);
    }

    /**
     * 异步任务完成后的回掉方法
     * @param taskId
     * @param message
     */
    public void onTaskComplete (int taskId, BaseMessage message) {

    }

    /**
     * 异步任务完成后的回掉方法
     * @param taskId
     */
    public void onTaskComplete (int taskId) {

    }

    /**
     * 网络异常回掉方法
     * @param taskId
     */
    public void onNetworkError (int taskId) {
        toast(C.err.network);
    }

    /**
     * 获取当前占用内存
     */
    public void debugMemory (String tag) {
        if (this.showDebugMsg) {
            Log.w(this.getClass().getSimpleName(), tag+":"+ AppUtil.getUsedMemory());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // common classes

    public class BitmapViewBinder implements SimpleAdapter.ViewBinder {
        //
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if ((view instanceof ImageView) & (data instanceof Bitmap)) {
                ImageView iv = (ImageView) view;
                Bitmap bm = (Bitmap) data;
                iv.setImageBitmap(bm);
                return true;
            }
            return false;
        }
    }
}