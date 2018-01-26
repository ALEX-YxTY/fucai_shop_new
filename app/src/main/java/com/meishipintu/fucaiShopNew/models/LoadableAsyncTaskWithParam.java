package com.meishipintu.fucaiShopNew.models;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.custom.CustomProgressDialog;
import com.meishipintu.fucaiShopNew.utils.StringUtils;


public abstract class LoadableAsyncTaskWithParam<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected abstract Result doBackgroudJob(Params... param) throws Exception;

    protected abstract void doPostJob(Exception exception, Result result);

    private static final String LOG_TAG = LoadableAsyncTaskWithParam.class.getSimpleName();

    protected CustomProgressDialog dialog;

    protected Exception exception;

    protected Activity activity;

    protected int loadingTip;

    protected int failTip;

    protected boolean flagShowProgress = true;

    protected boolean flagShowFailToast = false;

    protected boolean flagPlayFinishSound = false;

    /**
     * silence mode, no toast, no progress bar, no finish sound
     * 
     * @param activity
     */
    public LoadableAsyncTaskWithParam(Activity activity) {
        this.activity = activity;
        this.flagShowFailToast = false;
        this.flagShowProgress = false;
        this.flagPlayFinishSound = false;
    }

    public LoadableAsyncTaskWithParam(
            Activity activity,
            int loadingTip,
            int failTip,
            boolean flagShowFailToast,
            boolean flagShowProgress,
            boolean flagPlayFinishSound) {
        this.activity = activity;
        this.loadingTip = loadingTip;
        this.failTip = failTip;
        this.flagShowFailToast = flagShowFailToast;
        this.flagShowProgress = flagShowProgress;
        this.flagPlayFinishSound = flagPlayFinishSound;
        if (loadingTip != 0) 
        	this.dialog = new CustomProgressDialog(activity,activity.getString(loadingTip));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (flagShowProgress) {
        	dialog = new CustomProgressDialog(activity, activity.getString(loadingTip));
            dialog.show();
        }
    }

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return doBackgroudJob(params);
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (flagShowProgress) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (flagShowFailToast && exception != null) {
            String errorMsg = StringUtils.isNullOrEmpty(exception.getMessage()) ? "" : exception.getMessage();
            Toast.makeText(activity, activity.getString(failTip, errorMsg), Toast.LENGTH_LONG).show();
        }

        doPostJob(exception, result);
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
