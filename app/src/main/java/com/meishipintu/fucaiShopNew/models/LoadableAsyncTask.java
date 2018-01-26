package com.meishipintu.fucaiShopNew.models;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.custom.CustomProgressDialog;
import com.meishipintu.fucaiShopNew.utils.StringUtils;


public abstract class LoadableAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected abstract Result doBackgroudJob() throws Exception;

    protected abstract void doPostJob(Exception exception, Result result);

    private static final String LOG_TAG = LoadableAsyncTask.class.getSimpleName();

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
    public LoadableAsyncTask(Activity activity) {
        this.activity = activity;
        this.flagShowFailToast = false;
        this.flagShowProgress = false;
        this.flagPlayFinishSound = false;
    }

    public LoadableAsyncTask(
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
//        if (loadingTip != 0) 
//        	this.dialog = new CustomProgressDialog(activity,activity.getString(loadingTip));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (flagShowProgress) {
            dialog = new CustomProgressDialog(activity, activity.getString(loadingTip));
            dialog.show();
            Log.i("test", "pb is showing");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (flagShowProgress) {
            try {
                dialog.dismiss();
                Log.i("test", "pb is dismissing");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     
    }

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return doBackgroudJob();
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
            Log.d("LoadableAsyncTask", e.getMessage());
            if(e.getMessage().equals("连接服务器错误! 请检查网络配置!"))
            {
            	showToast();
            } 
        }
        return null;
    }
    
    
    private Handler mHandler;
    private void showToast()
    {
    	Looper lop=Looper.getMainLooper();
    	mHandler=new Handler(lop);
    	mHandler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast toast=Toast.makeText(activity, "网络断线了，请检查网络连接", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}});
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (flagShowProgress) {
            try {
                dialog.dismiss();
                Log.i("test", "pb is dismissing");

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
