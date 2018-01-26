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

    public RewardByMonth() {
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
}
