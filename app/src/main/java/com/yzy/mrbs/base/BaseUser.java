package com.yzy.mrbs.base;

import com.yzy.mrbs.model.Customer;

/**
 * Created by ZhiYuan on 2016/5/12.
 */
public class BaseUser {
    static public boolean isLogin () {
        Customer customer = Customer.getInstance();
        if (customer.getLogin() == true) {
            return true;
        }
        return false;
    }

    static public void setLogin (Boolean status) {
        Customer customer = Customer.getInstance();
        customer.setLogin(status);
    }

    static public void setCustomer (Customer mc) {
        Customer customer = Customer.getInstance();
        customer.setId(mc.getId());
        customer.setSid(mc.getSid());
        customer.setName(mc.getName());
        customer.setSign(mc.getSign());
        customer.setFace(mc.getFace());
    }
    static public void setBaesInfo (String id,String name,String pass) {
        Customer customer = Customer.getInstance();
        customer.setId(id);
        customer.setName(name);
        customer.setPass(pass);
    }

    static public void setSimpleInfo (String id,String name,String pass,String sign,String email,String phone) {
        Customer customer = Customer.getInstance();
        customer.setId(id);
        customer.setName(name);
        customer.setPass(pass);
        customer.setSign(sign);
        customer.setEmail(email);
        customer.setPhone(phone);

    }

    static public Customer getCustomer () {
        return Customer.getInstance();
    }
}
