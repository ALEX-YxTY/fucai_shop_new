package com.meishipintu.fucaiShopNew.models.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2018/1/30.
 *
 * 主要功能：
 */
data class HttpResult<T> (
        @SerializedName("status") var status:Int
        ,@SerializedName("msg") var msg:String
        ,@SerializedName("data") var data:T)