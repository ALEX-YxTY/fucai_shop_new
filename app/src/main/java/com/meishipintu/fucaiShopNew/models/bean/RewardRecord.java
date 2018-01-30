package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 * <p>
 * 主要功能：
 */

public class RewardRecord implements Serializable {

    private int recharge_count;
    private double recharge_sum;
    private List<RewardData> data;

    public RewardRecord() {
    }

    public int getRecharge_count() {
        return recharge_count;
    }

    public void setRecharge_count(int recharge_count) {
        this.recharge_count = recharge_count;
    }

    public double getRecharge_sum() {
        return recharge_sum;
    }

    public void setRecharge_sum(double recharge_sum) {
        this.recharge_sum = recharge_sum;
    }

    public List<RewardData> getData() {
        return data;
    }

    public void setData(List<RewardData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RewardRecord{" +
                "recharge_count=" + recharge_count +
                ", recharge_sum=" + recharge_sum +
                ", data=" + data.toString() +
                '}';
    }
}
