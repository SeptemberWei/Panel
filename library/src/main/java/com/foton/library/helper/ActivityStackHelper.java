package com.foton.library.helper;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class ActivityStackHelper {
    private Stack<Activity> mActivities;

    private WeakReference<Activity> mCurrentActivityWeakRef;

    private ActivityStackHelper() {

    }

    private static class InstanceHolder {
        private static final ActivityStackHelper sInstance = new ActivityStackHelper();
    }

    public static ActivityStackHelper getInstance() {
        return InstanceHolder.sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (mCurrentActivityWeakRef != null) {
            currentActivity = mCurrentActivityWeakRef.get();
        }

        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    public void addActivity(Activity activity) {
        if (mActivities == null) {
            mActivities = new Stack<Activity>();
        }

        if (mActivities.search(activity) == -1) {
            mActivities.push(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (mActivities != null && mActivities.size() > 0) {
            mActivities.remove(activity);
        }
    }

    public void setTopActivity(Activity activity) {
        if (mActivities != null && mActivities.size() > 0) {
            if (mActivities.search(activity) == -1) {
                mActivities.push(activity);
                return;
            }

            int location = mActivities.search(activity);
            if (location != 1) {
                mActivities.remove(activity);
                mActivities.push(activity);
            }
        }
    }

    public Activity getTopActivity() {
        if (mActivities != null && mActivities.size() > 0) {
            return mActivities.peek();
        }

        return null;
    }

    public boolean isTopActivity(Activity activity) {
        return activity.equals(mActivities.peek());
    }

    public void finishTopActivity() {
        if (mActivities != null && mActivities.size() > 0) {
            Activity activity = mActivities.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void finishAllActivity() {
        if (mActivities != null && mActivities.size() > 0) {
            while (!mActivities.empty()) {
                Activity activity = mActivities.pop();
                if (activity != null) {
                    activity.finish();
                }
            }

            mActivities.clear();
            mActivities = null;
        }
    }

    public void finishAllActivityWithOut(Class claz) {
        if (mActivities != null && mActivities.size() > 0) {
            for (Activity activity : mActivities) {
                if (activity.getClass() != claz) {
                    activity.finish();
                }
            }
        }
    }

    public Stack<Activity> getActivityStack() {
        return mActivities;
    }
}