package com.meishipintu.fucaiShopNew.models;

@SuppressWarnings("serial")
public class ResultCodeException extends Exception {

    public static final int RESPONSE_USER_NAME_DUPLICATE = -10;
    public static final int RESPONSE_EMAIL_DUPLICATE = -13;
    public static final int RESPONSE_PERMISSION_DENIED = -14;    
    public static final int ERROR_USER_NAME_OR_PASSWORD = -19;
    
    private int resultErrorCode = Integer.MIN_VALUE;

    private String errorMsg = "";

    public ResultCodeException(int resultErrorCode) {
        switch (resultErrorCode) {
        case ERROR_USER_NAME_OR_PASSWORD:
            errorMsg = "用户名或密码错误";
            break;
        case RESPONSE_EMAIL_DUPLICATE:
            errorMsg = "邮箱已被注册";
            break;
        case RESPONSE_USER_NAME_DUPLICATE:
            errorMsg = "用户名已被注册";
            break;
        case RESPONSE_PERMISSION_DENIED:
            errorMsg = "访问出错啦! 您无权查看! 先关注TA吧!";
            break;
        default:
            errorMsg = String.format("服务器请求出错! 代码  %d", resultErrorCode);
            break;
        }
        this.resultErrorCode = resultErrorCode;
    }
    
    public ResultCodeException(int resultErrorCode, String msg) { 
        errorMsg = msg;
        this.resultErrorCode = resultErrorCode;
    }

    public int getHttpErrorCode() {
        return resultErrorCode;
    }

    @Override
    public String toString() {
        return "resultErrorCode = " + resultErrorCode;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
