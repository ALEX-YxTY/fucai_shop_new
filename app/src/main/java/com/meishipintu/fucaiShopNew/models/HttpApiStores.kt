package com.meishipintu.fucaiShopNew.models

import com.meishipintu.fucaiShopNew.models.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

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

    //获取杂货铺奖励详情
    //@param type 1-杂货铺 2-充值中心
    @FormUrlEncoded
    @POST("Home/Payment/getOrderDetail")
    fun getStoreRewardDetail(@Field("id") id: Int, @Field("type") type: Int): Observable<HttpResult<StoreRewardDetail>>

    //添加商户信息
    //上传图片3张
    @Multipart
    @POST("Home/Shop/addShopInfo")
    fun commitShopInfo(@Part("true_name") trueName: RequestBody, @Part("mobile") mobile: RequestBody
                       , @Part("bank_account") bankAccount: RequestBody
                       , @Part("shop_id") shopId: RequestBody, @Part("shop_name") shopName: RequestBody, @Part file1: MultipartBody.Part
                       , @Part file2: MultipartBody.Part, @Part file3: MultipartBody.Part): Observable<HttpResult<AccountInfo>>


    //查询商户账户信息
    //如果
    @FormUrlEncoded
    @POST("Home/Shop/queryShopAccountInfo")
    fun checkAccountInfo(@Field("shop_id") shop_id: String): Observable<HttpResult<AccountInfo>>

    //查询店铺营业员信息
    @FormUrlEncoded
    @POST("http://a.milaipay.com/Merchant/Waiter/getShopWaiters")
    fun getShopWaiters(@Field("shop_id") shop_id: String): Observable<HttpResult<List<WaiterInfo>>>

    //删除营业员接口
    @FormUrlEncoded
    @POST("http://a.milaipay.com/Merchant/Waiter/deleteWaiter")
    fun deleteWaiter(@Field("shop_id") shop_id: String, @Field("uid") uid: String
                     , @Field("waiter_id") waiter_id: Int): Observable<HttpResult<Any>>

    //修改新增营业员
    @FormUrlEncoded
    @POST("http://a.milaipay.com/Merchant/Waiter/updateWaiter")
    fun updateWaiter(@Field("shop_id") shop_id: String,@Field("waiter_id") waiterId: Int
                     ,@Field("nickname") nickname:String, @Field("account") account:String
                     ,@Field("tel") tel:String, @Field("real_psw") realPsw:String
                     , @Field("shop_name") shopName:String) :Observable<HttpResult<Any>>
}