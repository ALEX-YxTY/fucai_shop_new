package com.meishipintu.fucaiShopNew.models.bean

/**
 * Created by Administrator on 2018/1/30.
 *
 * 主要功能：
 * @param status 0-审核未通过 1-审核通过
 */
data class AccountInfo(val id: Int, val true_name: String, val mobile: String, val bank_account: String
                       , val shop_id: String, val status: Int)
