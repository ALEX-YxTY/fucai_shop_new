
package com.meishipintu.fucaiShopNew.models;

@SuppressWarnings("serial")
public class HttpStatusCodeException extends Exception {

    private int httpStatusCode = -1;

    public HttpStatusCodeException(int httpErrorCode) {
        super(String.format("网络请求失败, 代码%d", httpErrorCode));
        this.httpStatusCode = httpErrorCode;
    }

    public int getHttpErrorCode() {
        return httpStatusCode;
    }

    @Override
    public String toString() {
        return "httpStatusCode = " + httpStatusCode;
    }

}
