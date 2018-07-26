package com.meishipintu.fucaiShopNew.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.WaiterClickListener

/**
 * Created by Administrator on 2018/6/11.
 *
 * 主要功能：
 */
class CustomChooseDialog(private val ctx:Context, themeId:Int, val type:Int):Dialog(ctx,themeId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (type == 2) {
            val listener: ChooseDialogListener = ctx as ChooseDialogListener
            setContentView(R.layout.dialog_orange_choose2)
            findViewById<Button>(R.id.cancel).setOnClickListener{
                listener.onCancelListener()
                this.dismiss()
            }
            findViewById<Button>(R.id.ok).setOnClickListener{
                listener.onOkListener(type)
                this.dismiss()
            }
        } else {
            setContentView(R.layout.dialog_orange_choose1)
            findViewById<Button>(R.id.cancel).setOnClickListener{
                this.dismiss()
            }
        }
        window!!.setBackgroundDrawableResource(R.color.transparent)

    }
}