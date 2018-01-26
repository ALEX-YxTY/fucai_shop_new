package com.meishipintu.fucaiShopNew.models;

import android.app.Activity;

public abstract class LoadMoreTask extends PostGetTask<Integer> {

    public LoadMoreTask(
            Activity activity,
            int loadingTip,
            int failTip,
            boolean flagShowFailToast,
            boolean flagShowProgress,
            boolean flagPlayFinishSound) {
        super(activity, loadingTip, failTip, flagShowFailToast, flagShowProgress, flagPlayFinishSound);
    }

    public LoadMoreTask(Activity activity) {
        super(activity);
    }

    protected abstract int doGetNetLoadMore() throws Exception;

    protected abstract void doUpdateFootView();

    protected abstract void doRequeryCursor();

    protected void relocateLocation() {
    }

    @Override
    protected Integer doBackgroudJob() throws Exception {
        return doGetNetLoadMore();
    }

    @Override
    protected void doPostJob(Exception exception, Integer result) {

        doRequeryCursor();
        doUpdateFootView();
    }
}