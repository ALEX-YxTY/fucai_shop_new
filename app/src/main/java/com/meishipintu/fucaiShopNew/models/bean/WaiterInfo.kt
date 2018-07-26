package com.meishipintu.fucaiShopNew.models.bean

import android.provider.ContactsContract
import java.io.Serializable

/**
 * Created by Administrator on 2018/6/11.
 *
 * 主要功能：
 */
data class WaiterInfo(val id: Int, val uid: String, val account: String, val nickname: String, val mobile: String
                      , val password: String, val real_psw: String, val shop_id: Int, val type: Int, val status: Int
                      , val shop_name: String) : Serializable