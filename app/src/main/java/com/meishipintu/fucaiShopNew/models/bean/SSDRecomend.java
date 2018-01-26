package com.meishipintu.fucaiShopNew.models.bean;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/21.
 * <p>
 * 主要功能：
 */

public class SSDRecomend extends RecoInfo {

    private String[][] balls;
    private String[] result;

    public String[][] getBalls() {
        return balls;
    }

    public void setBalls(String[][] balls) {
        this.balls = balls;
    }

    public String[] getResult() {
        return result;
    }

    public void setResult(String[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SSDRecomend{" +
                super.toString() +
                "balls=" + Arrays.toString(balls) +
                ", result=" + Arrays.toString(result) +
                '}';
    }
}
