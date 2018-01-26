package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;


import com.meishipintu.fucaiShopNew.models.bean.Notice;

/**
 * Created by Administrator on 2017/5/31.
 * <p>
 * 主要功能：
 */

public interface NoticeClickListener  {
    void onIgnore(Notice orderdoGrad);
    void onGrad(Notice orderdoGrad);
    void onCall(Notice orderdoGrad);
}
