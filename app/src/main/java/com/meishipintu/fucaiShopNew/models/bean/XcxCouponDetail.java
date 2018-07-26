package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/11.
 * <p>
 * 主要功能：
 */

public class XcxCouponDetail implements Serializable {

    private String coupon_sn;
    private String redball_name;
    private String waiter_name;
    private int status;
    private String create_time;
    private String use_time;

    public XcxCouponDetail() {
    }

    public String getCoupon_sn() {
        return coupon_sn;
    }

    public void setCoupon_sn(String coupon_sn) {
        this.coupon_sn = coupon_sn;
    }

    public String getRedball_name() {
        return redball_name;
    }

    public void setRedball_name(String redball_name) {
        this.redball_name = redball_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    @Override
    public String toString() {
        return "XcxCouponDetail{" +
                "coupon_sn='" + coupon_sn + '\'' +
                ", redball_name='" + redball_name + '\'' +
                ", waiter_name='" + waiter_name + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", use_time=" + use_time +
                '}';
    }
}
