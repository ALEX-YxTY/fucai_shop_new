package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 * <p>
 * 主要功能：
 */

public class RewardRecord implements Serializable {

    private int cz_center_recharge_count;
    private double cz_center_recharge_sum;
    private int goods_recharge_count;
    private double goods_recharge_sum;
    private List<RewardData> data;

    public RewardRecord() {
    }

    public List<RewardData> getData() {
        return data;
    }

    public void setData(List<RewardData> data) {
        this.data = data;
    }

    public int getCz_center_recharge_count() {
        return cz_center_recharge_count;
    }

    public void setCz_center_recharge_count(int cz_center_recharge_count) {
        this.cz_center_recharge_count = cz_center_recharge_count;
    }

    public double getCz_center_recharge_sum() {
        return cz_center_recharge_sum;
    }

    public void setCz_center_recharge_sum(double cz_center_recharge_sum) {
        this.cz_center_recharge_sum = cz_center_recharge_sum;
    }

    public int getGoods_recharge_count() {
        return goods_recharge_count;
    }

    public void setGoods_recharge_count(int goods_recharge_count) {
        this.goods_recharge_count = goods_recharge_count;
    }

    public double getGoods_recharge_sum() {
        return goods_recharge_sum;
    }

    public void setGoods_recharge_sum(double goods_recharge_sum) {
        this.goods_recharge_sum = goods_recharge_sum;
    }

    @Override
    public String toString() {
        return "RewardRecord{" +
                "cz_center_recharge_count=" + cz_center_recharge_count +
                ", cz_center_recharge_sum=" + cz_center_recharge_sum +
                ", goods_recharge_count=" + goods_recharge_count +
                ", goods_recharge_sum=" + goods_recharge_sum +
                ", data=" + data +
                '}';
    }
}
