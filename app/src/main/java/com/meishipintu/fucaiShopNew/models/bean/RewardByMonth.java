package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/16.
 * <p>
 * 主要功能：
 */

public class RewardByMonth implements Serializable {
    private double money_this_month;
    private double money_last_month;
    private double goods_this_money_money;
    private double goods_before_month_money;

    public RewardByMonth() {
    }

    public double getGoods_this_money_money() {
        return goods_this_money_money;
    }

    public void setGoods_this_money_money(double goods_this_money_money) {
        this.goods_this_money_money = goods_this_money_money;
    }

    public double getGoods_before_month_money() {
        return goods_before_month_money;
    }

    public void setGoods_before_month_money(double goods_before_month_money) {
        this.goods_before_month_money = goods_before_month_money;
    }

    public double getMoney_this_month() {
        return money_this_month;
    }

    public void setMoney_this_month(double money_this_month) {
        this.money_this_month = money_this_month;
    }

    public double getMoney_last_month() {
        return money_last_month;
    }

    public void setMoney_last_month(double money_last_month) {
        this.money_last_month = money_last_month;
    }

    @Override
    public String toString() {
        return "RewardByMonth{" +
                "money_this_month=" + money_this_month +
                ", money_last_month=" + money_last_month +
                ", goods_this_money_money=" + goods_this_money_money +
                ", goods_before_month_money=" + goods_before_month_money +
                '}';
    }
}
