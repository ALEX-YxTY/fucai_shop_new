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
    private String create_time;
    private int pay_type_from; //1-充值中心 2-杂货铺
    private int id; //订单id

    public RewardData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getPay_type_from() {
        return pay_type_from;
    }

    public void setPay_type_from(int pay_type_from) {
        this.pay_type_from = pay_type_from;
    }

    @Override
    public String toString() {
        return "RewardData{" +
                "mobile='" + mobile + '\'' +
                ", make_money=" + make_money +
                ", create_time='" + create_time + '\'' +
                ", pay_type_from=" + pay_type_from +
                ", id=" + id +
                '}';
    }
}
