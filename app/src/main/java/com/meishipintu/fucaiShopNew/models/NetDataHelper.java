package com.meishipintu.fucaiShopNew.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meishipintu.fucaiShopNew.RxBus;
import com.meishipintu.fucaiShopNew.models.bean.BusMessage;
import com.meishipintu.fucaiShopNew.models.bean.CouponQueryResult;
import com.meishipintu.fucaiShopNew.models.bean.CouponVerifyed;
import com.meishipintu.fucaiShopNew.models.bean.Notice;
import com.meishipintu.fucaiShopNew.models.bean.OrderdoGrad;
import com.meishipintu.fucaiShopNew.models.bean.QLCRecomend;
import com.meishipintu.fucaiShopNew.models.bean.RecoInfo;
import com.meishipintu.fucaiShopNew.models.bean.RewardByMonth;
import com.meishipintu.fucaiShopNew.models.bean.RewardRecord;
import com.meishipintu.fucaiShopNew.models.bean.SSDRecomend;
import com.meishipintu.fucaiShopNew.models.bean.SSQRecomend;
import com.meishipintu.fucaiShopNew.models.bean.VersionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetDataHelper {
    private static final String TAG = "NJFUCAI-Dao";
    private static NetDataHelper dataHelper;
    private static RequestQueue mQueue;
    private static Gson gson;
    private Context context;
    public static String domain = "http://fucai.milaipay.com/Home/Index/";


    private NetDataHelper(Context context) {
        mQueue = Volley.newRequestQueue(context);
        gson = new Gson();
        this.context = context;
    }

    public static synchronized NetDataHelper getInstance(Context context) {
        if (dataHelper == null)
            dataHelper = new NetDataHelper(context.getApplicationContext());
        return dataHelper;
    }

    public void getGrabOrders(int page, String mid, final NetCallBack<List<Notice>> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", String.valueOf(page));
        map.put("mid", mid);
        Log.d("getGrabOrdersPost", map.toString());
        NormalPostRequest request = new NormalPostRequest(domain + "getGrabOrders", new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("getGrabOrders", response.toString());
                try {
                    int status = response.getInt("status");
                    if (status == 1) {
                        List<Notice> list = gson.fromJson(response.getJSONArray("data").toString(),
                                new TypeToken<List<Notice>>() {
                                }.getType());
                        callBack.onSuccess(list);
                    } else {
                        callBack.onError(response.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error.toString());
            }
        }, map);
        mQueue.add(request);
    }

    public void doGrab(String mid, String oid, final NetCallBack<String> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mid", mid);
        map.put("oid", oid);
        Log.d("doGrab", map.toString());
        NormalPostRequest request = new NormalPostRequest(domain + "doGrab", new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("doGrab", response.toString());
                try {
                    int status = response.getInt("status");
                    if (status == -1) {
                        callBack.onError(response.getString("msg"));
                        return;
                    }
                    callBack.onSuccess(response.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error.toString());
            }
        }, map);
        mQueue.add(request);
    }

    public void getShopOrder(String mid, int status, final NetCallBack<List<OrderdoGrad>> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mid", mid);
        map.put("status", String.valueOf(status));
        Log.d("getShopOrder", map.toString());
        NormalPostRequest request = new NormalPostRequest(domain + "getShopOrder", new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("getShopOrder", response.toString());
                try {
                    int status = response.getInt("status");

                    List<OrderdoGrad> list = gson.fromJson(response.getJSONArray("data").toString(),
                            new TypeToken<List<OrderdoGrad>>() {
                            }.getType());
                    callBack.onSuccess(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error.toString());
            }
        }, map);
        mQueue.add(request);
    }

    //忽略订单
    public void doIgnore(String oid, String mid, final NetCallBack<String> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mid", mid);
        map.put("oid", oid);
        Log.d("doGrab", map.toString());
        NormalPostRequest request = new NormalPostRequest(domain + "doIgnore", new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("doGrab", response.toString());
                try {
                    int status = response.getInt("status");
                    if (status == -1) {
                        callBack.onError(response.getString("msg"));
                        return;
                    }
                    callBack.onSuccess(response.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error.toString());
            }
        }, map);
        mQueue.add(request);
    }

    //自动更新用获取版本号
    public void getVersion(final NetCallBack<VersionInfo> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "1");
        map.put("type_key", "2");
        Log.d("getVersion", map + "");
        NormalPostRequest request = new NormalPostRequest(domain + "getVersion", new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("getVersionresult", response.toString());
                try {
                    int status = response.getInt("status");
                    if (status == 1) {
                        List<VersionInfo> list = gson.fromJson(response.getJSONArray("data").toString(),
                                new TypeToken<List<VersionInfo>>() {
                                }.getType());
                        callBack.onSuccess(list.get(0));
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, map);
        mQueue.add(request);
    }

    /**
     * 获取路线任务信息接口
     * <p>
     * http://fucai.milaipay.com/Home/Index/userRoadInfo
     * POST uid = `uid`
     *
     * @return data:RoadTask[]
     * msg
     * status:1/-1
     */
    public void postRoadTask(String uid, String mid, final NetCallBack<String> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", uid);
        map.put("mid", mid);
        NormalPostRequest request = new NormalPostRequest(domain + "RoadTask", new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("test", "postRoadTask" + response.toString());
                try {
                    callBack.onSuccess(response.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onError(e.getMessage());
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error.toString());
                Log.i("test", error.toString());
            }
        }, map);
        mQueue.add(request);
    }

    //获取商家双色球荐号情况
    public void getSSQRecommendationByShop(String shopId,
                                           final NetCallBack<List<RecoInfo>> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Pet/getShopPetInfo"
                , new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "response:" + response.toString());
                    if (response.getInt("status") == 1) {
                        List<RecoInfo> infoList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        int totalWin = 0;
                        int totalNum = 0;
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject info = data.getJSONObject(i);
                            SSQRecomend recommend = new SSQRecomend();
                            recommend.setType(1);
                            recommend.setPeriods(info.getString("periods"));
                            recommend.setRedBalls(info.getString("red_ball").split(","));
                            recommend.setBlueBalls(info.getString("blue_ball").split(","));
                            recommend.setIsSet(info.getInt("is_sett"));
                            if (recommend.getIsSet() == 1) {
                                int money = info.getJSONObject("winner").getInt("money");
                                recommend.setMoney(money);
                                totalWin += money;
                                totalNum += 1;
                                recommend.setRedResult(info.getJSONObject("bet").getJSONObject("detail")
                                        .getString("KJ_Z_NUM").split(" "));
                                recommend.setBlueResult(info.getJSONObject("bet").getJSONObject("detail")
                                        .getString("KJ_T_NUM"));
                            }
                            infoList.add(recommend);
                        }
                        if (totalNum > 0) {
                            RxBus.getDefault().send(new BusMessage(100, (totalWin * 100 / (totalNum * 1260)) + "%"));
                        }
                        callBack.onSuccess(infoList);
                    } else {
                        callBack.onError(response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error:" + e.getMessage());
                    callBack.onError("获取商家推荐号码失败");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("网络连接错误，请稍后重试");
            }
        }, map);
        mQueue.add(request);
    }

    //获取商家3D荐号情况
    public void get3DRecommendationByShop(String shopId
            , final NetCallBack<List<RecoInfo>> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Threed/getShopBetInfo"
                , new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "response:" + response.toString());
                    if (response.getInt("status") == 1) {
                        List<RecoInfo> infoList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        int totalWin = 0;
                        int totalNum = 0;
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject info = data.getJSONObject(i);
                            SSDRecomend recommend = new SSDRecomend();
                            recommend.setType(2);
                            recommend.setPeriods(info.getString("periods"));
                            recommend.setIsSet(info.getInt("is_sett"));
                            String[] debetBalls = info.getString("ball").split("],");
                            String[][] yourBall = new String[3][];
                            yourBall[0] = debetBalls[0].substring(1).split(",");
                            yourBall[1] = debetBalls[1].substring(1).split(",");
                            yourBall[2] = debetBalls[2].substring(1, debetBalls[2].length() - 1).split(",");
                            recommend.setBalls(yourBall);
                            if (recommend.getIsSet() == 1) {
                                int money = info.getInt("money");
                                recommend.setMoney(money);
                                recommend.setResult(info.getString("result").split(","));
                                totalWin += money;
                                totalNum += 1;
                            }
                            infoList.add(recommend);
                        }
                        if (totalNum > 0) {
                            RxBus.getDefault().send(new BusMessage(100, (totalWin * 100 / (totalNum * 16)) + "%"));
                        }
                        callBack.onSuccess(infoList);
                    } else {
                        callBack.onError(response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error:" + e.getMessage());
                    callBack.onError("获取商家推荐号码失败");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("网络连接错误，请稍后重试");
            }
        }, map);
        mQueue.add(request);
    }

    //获取商家七乐彩荐号情况
    public void getQLCRecommendationByShop(String shopId
            , final NetCallBack<List<RecoInfo>> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/sevenl/getShopBetInfo"
                , new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "response:" + response.toString());
                    if (response.getInt("status") == 1) {
                        List<RecoInfo> infoList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        int totalWin = 0;
                        int totalNum = 0;
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject info = data.getJSONObject(i);
                            QLCRecomend recommend = new QLCRecomend();
                            recommend.setType(3);
                            recommend.setPeriods(info.getString("periods"));
                            recommend.setIsSet(info.getInt("is_sett"));
                            recommend.setBalls(info.getString("red_ball").split(","));
                            if (recommend.getIsSet() == 1) {
                                int money = info.getInt("money");
                                recommend.setMoney(money);
                                totalWin += money;
                                totalNum += 1;
                                recommend.setRedResult(info.getString("red_list").split(","));
                                recommend.setSpecialBall(info.getString("special_ball"));
                            }
                            infoList.add(recommend);
                        }
                        if (totalNum > 0) {
                            RxBus.getDefault().send(new BusMessage(100, (totalWin * 100 / (totalNum * 240)) + "%"));
                        }
                        callBack.onSuccess(infoList);
                    } else {
                        callBack.onError(response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error:" + e.getMessage());
                    callBack.onError("获取商家推荐号码失败");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("网络连接错误，请稍后重试");
            }
        }, map);
        mQueue.add(request);
    }

    //获取通知
    public void getNotices(String uid, final NetCallBack<List<Notice>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        NormalPostRequest request = new NormalPostRequest(domain + "pushInfo",
                new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject arg0) {
                        Log.d("getNotices", arg0.toString());
                        int succeed;
                        try {
                            succeed = arg0.getInt("status");
                            if (succeed == 1) {
                                List<Notice> list = gson.fromJson(arg0.getJSONArray("data").toString(),
                                        new TypeToken<List<Notice>>() {
                                        }.getType());
                                callback.onSuccess(list);
                            } else {
                                callback.onError(arg0.getString("msg"));
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                callback.onError(arg0.toString());
            }

        }, map);
        mQueue.add(request);
    }

    //获取系统通知
    public void getSysNotices(String type, final NetCallBack<List<Notice>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("flag", "2");       //福彩社区店消息flag
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Common/getPushList",
                new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject arg0) {
                        Log.d("getSysNotices", arg0.toString());
                        int succeed;
                        try {
                            succeed = arg0.getInt("status");
                            if (succeed == 1) {
                                List<Notice> noticeList = new ArrayList<>();
                                JSONArray dataArray = arg0.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataNotice = dataArray.getJSONObject(i);
                                    Notice notice = new Notice();
                                    notice.setId(dataNotice.getString("id"));
                                    notice.setPush_content(dataNotice.getString("content"));
                                    notice.setPush_time(dataNotice.getString("time"));
                                    notice.setPush_title(dataNotice.getString("title"));
                                    notice.setPush_type(dataNotice.getString("type"));
                                    notice.setPush_show(dataNotice.getString("show"));
                                    noticeList.add(notice);
//                                    Log.d("debug", notice.toString());
                                }
                                callback.onSuccess(noticeList);
                            } else {
                                callback.onError(arg0.getString("msg"));
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                callback.onError(arg0.toString());
            }

        }, map);
        mQueue.add(request);
    }

    //获取验券详情
    public void getCouponQuery(int type, String shopId, long startTime, long endTime, final NetCallBack<CouponQueryResult> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        map.put("start_time", startTime + "");
        map.put("end_time", endTime + "");
        if (type > 0) {
            map.put("type", type + "");
        }
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Index/getShopCoupon_new",
                new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject arg0) {
                        Log.d("getCouponQuery", arg0.toString());
                        int succeed;
                        try {
                            succeed = arg0.getInt("status");
                            if (succeed == 1) {
                                CouponQueryResult result = new CouponQueryResult();
                                result.setCount_mobile(arg0.getString("count_mobile"));
                                result.setTotla_money(arg0.getString("total_money"));
                                List<CouponVerifyed> couponVerifyedList = new ArrayList<>();
                                JSONArray dataArray = arg0.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataItem = dataArray.getJSONObject(i);
                                    CouponVerifyed coupon = new CouponVerifyed();
                                    coupon.setMobile(dataItem.getString("mobile"));
                                    coupon.setName(dataItem.getString("name"));
                                    coupon.setValue(dataItem.getString("value"));
                                    coupon.setUse_time(dataItem.getString("use_time"));
                                    couponVerifyedList.add(coupon);
                                }
                                result.setData(couponVerifyedList);
                                callBack.onSuccess(result);
                            } else {
                                callBack.onError(arg0.getString("msg"));
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError("获取验券信息失败，请稍后重试");
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                callBack.onError("获取验券信息失败，请稍后重试");
            }

        }, map);
        mQueue.add(request);
    }

    //获取用户信息审核情况
    //返回 -1 尚未提交  0- 审核未通过  1- 审核通过
    public void isUserInfoChecked(String shopId, final NetCallBack<Integer> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Shop/queryShopAccountInfo",
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("queryShopAccountInfo", response.toString());
                        int succeed;
                        try {
                            succeed = response.getInt("status");
                            if (succeed == 1) {
                                JSONObject data = response.getJSONObject("data");
                                if (!data.has("status")) {
                                    //尚未提交
                                    callBack.onSuccess(-1);
                                } else {
                                    callBack.onSuccess(data.getInt("status"));
                                }

                            } else {
                                callBack.onError(response.getString("msg"));
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError("获取商户审核信息失败，请稍后重试");
                        }
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("获取商户审核信息失败，请稍后重试");
            }
        }, map);
        mQueue.add(request);
    }

    //获取奖励信息
    public void getRewardDetail(String shopId, String startTime, String endTime, int type, int page
            , final NetCallBack<RewardRecord> callBack){
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("type", type + "");
        map.put("page", page + "");
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Shop/getShopGetMoneyList",
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getCouponQuery", response.toString());
                        try {
                            int succeed = response.getInt("status");
                            if (succeed == 1) {
                                String data = response.getString("data");
                                Gson gson = new Gson();
                                RewardRecord rewardRecord = gson.fromJson(data, RewardRecord.class);
                                callBack.onSuccess(rewardRecord);
                            } else {
                                callBack.onError(response.getString("msg"));
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError("获取商户奖励信息失败，请稍后重试");
                        }
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("获取商户奖励信息失败，请稍后重试");
            }
        }, map);
        mQueue.add(request);
    }

    //获取本月及上月奖励金
    public void getRewardByMonth(String shopId, final NetCallBack<RewardByMonth> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", shopId);
        NormalPostRequest request = new NormalPostRequest("http://fucai.milaipay.com/Home/Shop/getThisMonthAndLastMonthMoney",
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int succeed = response.getInt("status");
                            if (succeed == 1) {
                                JSONObject data = response.getJSONObject("data");
                                RewardByMonth rewardByMonth = new RewardByMonth();
                                rewardByMonth.setMoney_this_month(data.getDouble("this_month_money"));
                                rewardByMonth.setMoney_last_month(data.getDouble("before_month_money"));
                                callBack.onSuccess(rewardByMonth);
                            } else {
                                callBack.onError(response.getString("msg"));
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError("获取商户奖励信息失败，请稍后重试");
                        }
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("获取商户奖励信息失败，请稍后重试");
            }
        }, map);
        mQueue.add(request);
    }
}
