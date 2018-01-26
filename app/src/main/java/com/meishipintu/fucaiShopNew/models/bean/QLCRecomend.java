package com.meishipintu.fucaiShopNew.models.bean;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/21.
 * <p>
 * 主要功能：
 */

public class QLCRecomend extends RecoInfo {

    private String[] balls;
    private String[] redResult;
    private String specialBall;

    public String[] getBalls() {
        return balls;
    }

    public void setBalls(String[] balls) {
        this.balls = balls;
    }

    public String[] getRedResult() {
        return redResult;
    }

    public void setRedResult(String[] redResult) {
        this.redResult = redResult;
    }

    public String getSpecialBall() {
        return specialBall;
    }

    public void setSpecialBall(String specialBall) {
        this.specialBall = specialBall;
    }

    @Override
    public String toString() {
        return "QLCRecomend{" +
                super.toString() +
                "balls=" + Arrays.toString(balls) +
                ", redResult=" + Arrays.toString(redResult) +
                ", specialBall='" + specialBall + '\'' +
                '}';
    }
}
