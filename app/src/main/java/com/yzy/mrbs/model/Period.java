package com.yzy.mrbs.model;

/**
 * Created by ZhiYuan on 2016/5/26.
 */
public class Period {

    int id; //周几 ：1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
    double start,step;
    public Period(int id,double start, double step) {
        super();
        this.id=id;
        this.start = start;
        this.step = step;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public double getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }


}
