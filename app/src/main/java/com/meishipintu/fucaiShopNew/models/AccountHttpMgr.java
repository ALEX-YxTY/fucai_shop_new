package com.meishipintu.fucaiShopNew.models;


import com.meishipintu.fucaiShopNew.utils.MessageDigestGenerator;

import org.json.JSONObject;

public class AccountHttpMgr {
    private static AccountHttpMgr instance = new AccountHttpMgr();

    public static AccountHttpMgr getInstance() {
        return instance;
    }

    public JSONObject register(String tel, String vCode, String pwd, String sid, String nickName) throws Exception {
        JSONObject jsonObject = null;
        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("mobile", tel);
        jsonObjectParam.put("verify", vCode);
        jsonObjectParam.put("password", MessageDigestGenerator.generateHash("SHA-256", pwd));
        jsonObjectParam.put("shopCode", sid);
        jsonObjectParam.put("nickname", nickName);

        jsonObject = HttpMgr.getInstance().postJson(ServerUrlConstants.getRegUrl(), jsonObjectParam, true);
        return jsonObject;
    }
    
    public JSONObject login(String tel, String pwd) throws Exception {
    	JSONObject jsonObject = null;
        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("mobile", tel);
        jsonObjectParam.put("password", MessageDigestGenerator.generateHash("SHA-256", pwd));
        jsonObject = HttpMgr.getInstance().postJson(ServerUrlConstants.getLoginUrl(), jsonObjectParam, false);
        return jsonObject;
    }
    
    public JSONObject resetPwd(String tel, String vCode, String newPwd) throws Exception {
    	JSONObject jsonObject = null;
        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("mobile", tel);
        jsonObjectParam.put("verify", vCode);
        jsonObjectParam.put("password", MessageDigestGenerator.generateHash("SHA-256", newPwd));
        jsonObject = HttpMgr.getInstance().postJson(ServerUrlConstants.getResetPwdUrl(), jsonObjectParam, false);
        return jsonObject;
    }
    
    public JSONObject changePwd(String uid, String token, String tel, String oldPwd, String newPwd) throws Exception {
    	JSONObject jsonObject = null;
        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("uid", uid);
        jsonObjectParam.put("token", token);
        jsonObjectParam.put("oldpwd", MessageDigestGenerator.generateHash("SHA-256", oldPwd));
        jsonObjectParam.put("newpwd", MessageDigestGenerator.generateHash("SHA-256", newPwd));
        jsonObject = HttpMgr.getInstance().postJson(ServerUrlConstants.getChgPwdUrl(), jsonObjectParam, false);
        return jsonObject;
    }
    
    public JSONObject getVCode(String tel) throws Exception {
    	JSONObject jsonObject = null;
        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("mobile", tel);
        jsonObject = HttpMgr.getInstance().postJson(ServerUrlConstants.getSendVCodeUrl(), jsonObjectParam, false);
        return jsonObject;
    }

    public JSONObject bindShop(String shopCode, String uid, String token) throws Exception {
    	JSONObject jsonObject = null;
        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("uid", uid);
        jsonObjectParam.put("token", token);
        jsonObjectParam.put("shopCode", shopCode);
        jsonObject = HttpMgr.getInstance().postJson(ServerUrlConstants.getBindShopUrl(), jsonObjectParam, false);
        return jsonObject;
    }

}
