package com.yzy.mrbs.model;

import com.yzy.mrbs.base.BaseModel;

/**
 * Created by ZhiYuan on 2016/5/25.
 */
public class Note extends BaseModel {
    private int roomid;
    private int userid;
    private int year;
    private int month;
    private int day;
    private int hour_start;
    private int minute_start;
    private int hour_end;
    private int minute_end;
    private String roomnote;
    private String phone;
    private String email;
    private int status;
    private String bookaddtime;
    private String roomname;

    public Note() {

    }

    public Note(int roomid, int userid,
                int year, int month, int day, int hour_start, int minute_start, int hour_end, int minute_end,
                String roomnote, String phone, String email, int status, String bookaddtime,String roomname) {
        this.roomid = roomid;
        this.userid = userid;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour_start = hour_start;
        this.minute_start = minute_start;
        this.hour_end = hour_end;
        this.minute_end = minute_end;
        this.roomnote = roomnote;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.bookaddtime = bookaddtime;
        this.roomname = roomname;

    }

    public int getRoomid() {
        return this.roomid;
    }

    public int getUserid() {
        return this.userid;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour_start() {
        return this.hour_start;
    }

    public int getMinute_start() {
        return this.minute_start;
    }

    public int getHour_end() {
        return this.hour_end;
    }

    public int getMinute_end() {
        return this.minute_end;
    }

    public String getRoomnote() {
        return this.roomnote;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public int getStatus() {
        return this.status;
    }

    public String getBookaddtime() {
        return this.bookaddtime;
    }

    public String getRoomname() {
        return this.roomname;
    }

}
