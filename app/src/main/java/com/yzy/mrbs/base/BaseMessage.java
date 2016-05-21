package com.yzy.mrbs.base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yzy.mrbs.util.AppUtil;
/**
 * 基础消息类
 * 此类中包含对JSON消息中的数据集合字段“result”的处理逻辑
 * Created by ZhiYuan on 2016/4/26.
 */
public class BaseMessage {

    private String code;
    private String message;
    private String resultSrc;
    private Map<String, BaseModel> resultMap;
    private Map<String, ArrayList<? extends BaseModel>> resultList;

    public BaseMessage () {
        this.resultMap = new HashMap<String, BaseModel>();
        this.resultList = new HashMap<String, ArrayList<? extends BaseModel>>();
    }

    @Override
    public String toString () {
        return code + " | " + message + " | " + resultSrc;
    }

    public String getCode () {
        return this.code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getMessage () {
        return this.message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    /**
     * 获取象型数据
     * @return
     */
    public String getResult () {
        return this.resultSrc;
    }
    /**
     * 获取通过setResult方法解析得到的单个对象型数据
     * @param modelName
     * @return
     * @throws Exception
     */
    public Object getResult (String modelName) throws Exception {
        Object model = this.resultMap.get(modelName);
        // 返回空模型异常
        if (model == null) {
            throw new Exception("Message data is empty");
        }
        return model;
    }

    /**
     * 用法和getResult一样
     * @param modelName
     * @return
     * @throws Exception
     */
    public ArrayList<? extends BaseModel> getResultList (String modelName) throws Exception {
        ArrayList<? extends BaseModel> modelList = this.resultList.get(modelName);
        // 返回空数据异常
        if (modelList == null || modelList.size() == 0) {
            throw new Exception("Message data list is empty");
        }
        return modelList;
    }
    /**
     * 消息数据的解析逻辑
     * 首先，记录下参数传入的JSON消息中的result数据集的字符源码，并保存到类属性resultSrc中。
     * 然后，使用JSONObject对象解析result数据集的最外层数据
     * 接着，使用JSONObject对象的迭代器Iterator模式遍历并解析result数据集中中的每一个模型数据，模型
     * 名称通过getModelName方法获取，而模型数据则根据不同的数据类型来做分别处理
     * 如果模型数据是单个对象，则使用json2model方法直接转化成对应的单个模型对象，并存储到类属性resultMap中
     * 但如果是个对象数组，则需要使用JSONArray来对对象数组遍历并转化，得到模型对象数组，再存储到类属性resultList中去
     * @param result
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setResult (String result) throws Exception {
        this.resultSrc = result;
        if (result.length() > 0) {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(result);
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                // 获取模型名、类名以及模型数据
                String jsonKey = it.next();
                String modelName = getModelName(jsonKey);
                String modelClassName = "com.yzy.mrbs.model." + modelName;
                JSONArray modelJsonArray = jsonObject.optJSONArray(jsonKey);
                // 模型数据为对象（JSONObject）的情况
                if (modelJsonArray == null) {
                    JSONObject modelJsonObject = jsonObject.optJSONObject(jsonKey);
                    if (modelJsonObject == null) {
                        throw new Exception("Message result is invalid");
                    }
                    this.resultMap.put(modelName, json2model(modelClassName, modelJsonObject));
                    // 模型数据为数组（JSONArray）的情况
                } else {
                    ArrayList<BaseModel> modelList = new ArrayList<BaseModel>();
                    for (int i = 0; i < modelJsonArray.length(); i++) {
                        JSONObject modelJsonObject = modelJsonArray.optJSONObject(i);
                        modelList.add(json2model(modelClassName, modelJsonObject));
                    }
                    this.resultList.put(modelName, modelList);
                }
            }
        }
    }

    /**
     * 用于模型对象的转化
     * 首先使用Class类的forName方法来动态创建制定模型的对象，然后使用JAVA对象的反射（Reflection）特性
     * 来给模型对象动态注入属性值，最后返回组成完成的模型对象
     * @param modelClassName
     * @param modelJsonObject
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private BaseModel json2model (String modelClassName, JSONObject modelJsonObject) throws Exception  {
        // auto-load model class
        BaseModel modelObj = (BaseModel) Class.forName(modelClassName).newInstance();
        Class<? extends BaseModel> modelClass = modelObj.getClass();
        // auto-setting model fields
        Iterator<String> it = modelJsonObject.keys();
        while (it.hasNext()) {
            String varField = it.next();
            String varValue = modelJsonObject.getString(varField);
            Field field = modelClass.getDeclaredField(varField);
            field.setAccessible(true); // have private to be accessable
            field.set(modelObj, varValue);
        }
        return modelObj;
    }

    private String getModelName (String str) {
        String[] strArr = str.split("\\W");
        if (strArr.length > 0) {
            str = strArr[0];
        }
        return AppUtil.ucfirst(str);
    }
}
