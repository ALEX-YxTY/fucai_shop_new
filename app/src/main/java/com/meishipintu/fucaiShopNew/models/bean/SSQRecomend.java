package com.meishipintu.fucaiShopNew.models.bean;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/21.
 * <p>
 * 主要功能：
 */

public class SSQRecomend extends RecoInfo {

    private String[] redBalls;
    private String[] blueBalls;
    private String[] redResult;
    private String blueResult;

    public String[] getRedBalls() {
        return redBalls;
    }

    public void setRedBalls(String[] redBalls) {
        this.redBalls = redBalls;
    }

    public String[] getBlueBalls() {
        return blueBalls;
    }

    public void setBlueBalls(String[] blueBalls) {
        this.blueBalls = blueBalls;
    }

    public String[] getRedResult() {
        return redResult;
    }

    public void setRedResult(String[] redResult) {
        this.redResult = redResult;
    }

    public String getBlueResult() {
        return blueResult;
    }

    public void setBlueResult(String blueResult) {
        this.blueResult = blueResult;
    }

    @Override
    public String toString() {
        return "SSQRecomend{" +
                super.toString() +
                "redBalls=" + Arrays.toString(redBalls) +
                ", blueBalls=" + Arrays.toString(blueBalls) +
                ", redResult=" + Arrays.toString(redResult) +
                ", blueResult='" + blueResult + '\'' +
                '}';
    }
}
