package com.meishipintu.fucaiShopNew.models;

@SuppressWarnings("serial")
public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {
        super("连接服务器错误! 请检查网络配置!");
    }

}
