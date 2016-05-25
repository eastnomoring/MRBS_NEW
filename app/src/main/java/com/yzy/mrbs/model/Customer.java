package com.yzy.mrbs.model;

/**
 * Created by ZhiYuan on 2016/4/26.
 */
public class Customer {
    //旧框架使用
    public final static String COL_ID = "id";
    public final static String COL_SID = "sid";
    public final static String COL_NAME = "name";
    public final static String COL_PASS = "pass";
    public final static String COL_SIGN = "sign";
    public final static String COL_FACE = "face";
    public final static String COL_FACEURL = "faceurl";
    public final static String COL_UPTIME = "uptime";

    private String id;            //userid
    private String sid;           //
    private String name;          //username 登录名
    private String pass;         //userpass 用户登录密码
    private String sign;         //用户签名
    private String face;         //用户头像
    private String faceurl;      //用户头像地址
    private String email;         //E-mail
    private String phone;        //手机号



    // default is no login
    private boolean isLogin = false;

    // single instance for login
    static private Customer customer = null;

    static public Customer getInstance() {
        if (Customer.customer == null) {
            Customer.customer = new Customer();
        }
        return Customer.customer;
    }

    public Customer() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFace() {
        return this.face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFaceurl() {
        return this.faceurl;
    }

    public void setFaceurl(String faceurl) {
        this.faceurl = faceurl;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getLogin() {
        return this.isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
