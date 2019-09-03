package com.foton.library.callback;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.foton.library.helper.ActivityStackHelper;

public class AppLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "AppLifecycleCallback";

    private static final int APP_STATUS_UNKNOWN = -1;
    private static final int APP_STATUS_LIVE = 0;

    private int mAppStatus = APP_STATUS_UNKNOWN;

    private boolean mIsForground = true;
    private int mAppCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityStackHelper.getInstance().addActivity(activity);

//        if (mAppStatus == APP_STATUS_UNKNOWN) {
//            mAppStatus = APP_STATUS_LIVE;
//            startLauncherActivity(activity);
//        }

        if (savedInstanceState != null && savedInstanceState.getBoolean("saveStateKey", false)) {
            Log.e(TAG, "localTime --> " + savedInstanceState.getLong("localTime"));
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mAppCount++;
        if (!mIsForground) {
            mIsForground = true;
            Log.e(TAG, "app into forground");
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        mAppCount--;
        if (!isForgroundAppValue()) {
            mIsForground = false;
            Log.d(TAG, "app into background ");

//            Intent intent = new Intent(LifecycleBroadcastReceiver.ActionBack);
//            activity.sendBroadcast(intent);//发送标准广播
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // 弱引用持有当前 Activity 实例
        ActivityStackHelper.getInstance().setCurrentActivity(activity);
        // Activity 页面栈方式
        ActivityStackHelper.getInstance().setTopActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        outState.putBoolean("saveStateKey", true);
        outState.putLong("localTime", System.currentTimeMillis());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStackHelper.getInstance().removeActivity(activity);
    }

    private boolean isForgroundAppValue() {
        return mAppCount > 0;
    }

    private static void startLauncherActivity(Activity activity) {
        try {
            Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            String launcherClassName = launchIntent.getComponent().getClassName();
            String className = activity.getComponentName().getClassName();

            if (TextUtils.isEmpty(launcherClassName) || launcherClassName.equals(className)) {
                return;
            }

            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(launchIntent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
