package com.meishipintu.fucaiShopNew.models

import com.meishipintu.fucaiShopNew.models.bean.AccountInfo
import com.meishipintu.fucaiShopNew.models.bean.HttpResult
import com.meishipintu.fucaiShopNew.models.bean.RewardDetail
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
interface HttpApiStores {

    //获取奖励详情
    //@param type 1-杂货铺 2-充值中心
    @FormUrlEncoded
    @POST("Home/Payment/getOrderDetail")
    fun getRewardDetail(@Field("id") id: Int, @Field("type") type: Int): Observable<HttpResult<RewardDetail>>

    //添加商户信息
    //上传图片


    //查询商户账户信息
    //如果
    @FormUrlEncoded
    @POST("Home/Shop/queryShopAccountInfo")
    fun checkAccountInfo(@Field("shop_id") shop_id:String):Observable<HttpResult<AccountInfo>>
}