package com.meishipintu.fucaiShopNew.views.fragments


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub

import com.meishipintu.fucaiShopNew.Cookies
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.models.HttpApiClinet
import com.meishipintu.fucaiShopNew.views.InfomationCommitActivity

import butterknife.OnClick
import com.meishipintu.fucaiShopNew.models.HttpApiStores
import io.reactivex.disposables.CompositeDisposable

/**
 * A simple [Fragment] subclass.
 */
class F0_AddInfoFrag : Fragment() {

    private var stubReview: ViewStub? = null
    private val httpApiStores: HttpApiStores by lazy { HttpApiClinet.retrofit() }
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.frag_add_info, container, false)
        stubReview = view.findViewById(R.id.stub_review)
        if (Cookies.isCommitInfo(Cookies.getShopId())) {
            //已提交,待审核
            stubReview?.inflate()
        }
        view.findViewById<View>(R.id.bt_add_info).setOnClickListener {
            startActivityForResult(Intent(activity, InfomationCommitActivity::class.java), COMMIT_INFO)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COMMIT_INFO && resultCode == RESULT_OK) {
            stubReview!!.inflate()
        }
    }

    companion object {

        private val COMMIT_INFO = 1

        private var instance: F0_AddInfoFrag? = null

        fun createInstance(): F0_AddInfoFrag {
            if (instance == null) {
                instance = F0_AddInfoFrag()
            }
            return instance as F0_AddInfoFrag
        }
    }
}
