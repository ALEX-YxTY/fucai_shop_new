package com.meishipintu.fucaiShopNew.models;

import android.app.Activity;

public abstract class PostGetTaskWithParam<Params, Result> extends LoadableAsyncTaskWithParam<Params, Void, Result> {

    public PostGetTaskWithParam(
            Activity activity,
            int loadingTip,
            int failTip,
            boolean flagShowFailToast,
            boolean flagShowProgress,
            boolean flagPlayFinishSound) {
        super(activity, loadingTip, failTip, flagShowFailToast, flagShowProgress, flagPlayFinishSound);
    }

    public PostGetTaskWithParam(Activity activity) {
        super(activity);
    }
};
