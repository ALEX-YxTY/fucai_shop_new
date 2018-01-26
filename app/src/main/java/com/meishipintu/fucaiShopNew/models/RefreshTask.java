package com.meishipintu.fucaiShopNew.models;

import android.app.Activity;

import com.meishipintu.fucaiShopNew.R;


public abstract class RefreshTask extends PostGetTask<Integer> {

    public abstract int doGetNetRefresh() throws Exception;

    public abstract void doOnRefreshFinish();

    public abstract void doRequeryCursor();

    public RefreshTask(Activity activityConext) {
        super(activityConext, R.string.loading, R.string.fail_refresh, false, false, false);
    }

    @Override
    protected Integer doBackgroudJob() throws Exception {
        return doGetNetRefresh();
    }

    @Override
    protected void doPostJob(Exception exception, Integer result) {
        if (exception == null) {
            doRequeryCursor();
        }

        doOnRefreshFinish();
    }
}