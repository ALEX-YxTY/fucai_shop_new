package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/17.
 * <p>
 * 主要功能：
 */

public class RewardData implements Serializable {
    private String mobile;
    private double make_money;  //盈利-应该已经乘以分成比例了
    private int pay_from;       //1-支付宝
    private int pay_mobile_from;    //1-移动 2-联通 3-电信
    private int m;          //m-流量值
    private int type;       //1-话费 2-流量
    private String create_time;
    private int id;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RewardData() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getMake_money() {
        return make_money;
    }

    public void setMake_money(double make_money) {
        this.make_money = make_money;
    }

    public int getPay_from() {
        return pay_from;
    }

    public void setPay_from(int pay_from) {
        this.pay_from = pay_from;
    }

    public int getPay_mobile_from() {
        return pay_mobile_from;
    }

    public void setPay_mobile_from(int pay_mobile_from) {
        this.pay_mobile_from = pay_mobile_from;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RewardDetail{" +
                "mobile='" + mobile + '\'' +
                ", make_money=" + make_money +
                ", pay_from=" + pay_from +
                ", pay_mobile_from=" + pay_mobile_from +
                ", m=" + m +
                ", type=" + type +
                ", create_time='" + create_time + '\'' +
                ", id=" + id +
                '}';
    }
}
