package com.meishipintu.fucaiShopNew.models;

import android.app.Activity;

public abstract class PostGetTask<Result> extends LoadableAsyncTask<Void, Void, Result> {

    public PostGetTask(
            Activity activity,
            int loadingTip,
            int failTip,
            boolean flagShowFailToast,
            boolean flagShowProgress,
            boolean flagPlayFinishSound) {
        super(activity, loadingTip, failTip, flagShowFailToast, flagShowProgress, flagPlayFinishSound);
    }

    public PostGetTask(Activity activity) {
        super(activity);
    }
}
