package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 * <p>
 * 主要功能：
 */

public class XcxCouponQuery implements Serializable {
    private ArrayList<XcxCouponDetail> data;
    private int person_num;

    public XcxCouponQuery() {
    }

    public ArrayList<XcxCouponDetail> getData() {
        return data;
    }

    public void setData(ArrayList<XcxCouponDetail> data) {
        this.data = data;
    }

    public int getPerson_num() {
        return person_num;
    }

    public void setPerson_num(int person_num) {
        this.person_num = person_num;
    }

    @Override
    public String toString() {
        return "XcxCouponQuery{" +
                "data=" + data +
                ", person_num=" + person_num +
                '}';
    }
}
