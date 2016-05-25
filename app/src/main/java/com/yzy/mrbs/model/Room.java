package com.yzy.mrbs.model;

import com.yzy.mrbs.base.BaseModel;

/**
 * Created by ZhiYuan on 2016/5/21.
 */
public class Room extends BaseModel {
    private int roomid;
    private String roomname;
    private String roominfo;
    private int roomface;
    private String roomfaceurl;


    public Room(){

    }
    public Room(int roomid,String roomname,String roominfo,int roomface,String roomfaceurl){
        this.roomid = roomid;
        this.roomname = roomname;
        this.roominfo = roominfo;
        this.roomface = roomface;
        this.roomfaceurl = roomfaceurl;
    }
    public int getId () {
        return this.roomid;
    }

    public void setId (int id) {
        this.roomid = id;
    }

    public int getFace () {
        return this.roomface;
    }

    public void setFace (int face) {
        this.roomface = face;
    }


    public String getInfo () {
        return this.roominfo;
    }

    public void setInfo (String info) {
        this.roominfo = info;
    }

    public String getName () {
        return this.roomname;
    }

    public void setName (String name) {
        this.roomname = name;
    }
    public String getFaceurl () {
        return this.roomfaceurl;
    }

    public void setFaceurl (String url) {
        this.roomfaceurl = url;
    }



}
