package com.meishipintu.fucaiShopNew.utils

import android.content.Context
import android.content.pm.PackageInfo

/**
 * Created by Administrator on 2018/1/24.
 *
 * 主要功能：
 */
object PackageUtils {
    //获取包信息
    fun getPackageInfo(context: Context): PackageInfo? {
        try {
            val manager = context.packageManager
            return manager.getPackageInfo(context.packageName, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}