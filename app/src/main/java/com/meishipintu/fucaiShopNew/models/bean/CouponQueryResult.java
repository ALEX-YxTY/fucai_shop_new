package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 * <p>
 * 主要功能：
 */

public class CouponQueryResult implements Serializable {
    private String count_mobile;
    private String totla_money;
    private List<CouponVerifyed> data;

    public CouponQueryResult() {
    }

    public String getCount_mobile() {
        return count_mobile;
    }

    public void setCount_mobile(String count_mobile) {
        this.count_mobile = count_mobile;
    }

    public String getTotla_money() {
        return totla_money;
    }

    public void setTotla_money(String totla_money) {
        this.totla_money = totla_money;
    }

    public List<CouponVerifyed> getData() {
        return data;
    }

    public void setData(List<CouponVerifyed> data) {
        this.data = data;
    }
}
