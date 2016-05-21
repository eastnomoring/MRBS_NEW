package com.yzy.mrbs.base;

import android.content.Context;

import com.yzy.mrbs.util.AppClient;
import com.yzy.mrbs.util.HttpUtil;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZhiYuan on 2016/4/27.
 */
public class BaseTaskPool {
    // 线程池对象
    static private ExecutorService taskPool;

    // 界面上下文对象
    private Context context;

    //初始化任务线程池
    public BaseTaskPool (BaseUi ui) {
        this.context = ui.getContext();
        taskPool = Executors.newCachedThreadPool();
    }
    /**
     * 创建异步远程任务方法（含参数）
     */
    public void addTask (int taskId, String taskUrl, HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        try {
            taskPool.execute(new TaskThread(context, taskUrl, taskArgs, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }
    /**
     * 创建异步远程任务方法（不含参数）
     */
    public void addTask (int taskId, String taskUrl, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        try {
            taskPool.execute(new TaskThread(context, taskUrl, null, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }
    /**
     * 创建自定义异步任务
     */
    public void addTask (int taskId, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        try {
            taskPool.execute(new TaskThread(context, null, null, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }
    /**
     * 任务逻辑线程类
     */
    private class TaskThread implements Runnable {
        private Context context;
        private String taskUrl;
        private HashMap<String, String> taskArgs;
        private BaseTask baseTask;
        private int delayTime = 0;
        public TaskThread(Context context, String taskUrl, HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime) {
            this.context = context;
            this.taskUrl = taskUrl;
            this.taskArgs = taskArgs;
            this.baseTask = baseTask;
            this.delayTime = delayTime;
        }
        @Override
        public void run() {
            try {
                baseTask.onStart();
                String httpResult = null;
                // 设置任务延时
                if (this.delayTime > 0) {
                    Thread.sleep(this.delayTime);
                }
                try {
                    // 远程任务
                    if (this.taskUrl != null) {
                        // 初始化appclient
                        AppClient client = new AppClient(this.taskUrl);
                        if (HttpUtil.WAP_INT == HttpUtil.getNetType(context)) {
                            client.useWap();
                        }
                        if (taskArgs == null) {
                            httpResult = client.get();// Get请求
                        } else {
                            httpResult = client.post(this.taskArgs); // Post请求
                        }
                    }
                    // 远程任务处理
                    if (httpResult != null) {
                        baseTask.onComplete(httpResult);
                        // 本地任务处理
                    } else {
                        baseTask.onComplete();
                    }
                } catch (Exception e) {
                    baseTask.onError(e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    baseTask.onStop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
