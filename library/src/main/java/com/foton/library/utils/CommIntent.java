package com.foton.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

public class CommIntent {
    private Activity activity;
    private Intent intent = new Intent();

    public CommIntent(Activity activity, Class<?> clasz) {
        this.activity = activity;
        intent.setClass(activity, clasz);
    }

    public static CommIntent getInstance(Activity activity, Class<?> clasz) {
        return new CommIntent(activity, clasz);
    }

    public void start(boolean isFinish) {
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    public void startForResult(int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    public CommIntent with(String key, Object object) {
        if (!TextUtils.isEmpty(key) && null != object) {
            if (object instanceof Integer) {
                intent.putExtra(key, (Integer) object);
            } else if (object instanceof String) {
                intent.putExtra(key, String.valueOf(object));
            } else if (object instanceof Boolean) {
                intent.putExtra(key, (Boolean) object);
            } else if (object instanceof Float) {
                intent.putExtra(key, (Float) object);
            } else if (object instanceof Byte) {
                intent.putExtra(key, (Byte) object);
            } else if (object instanceof Long) {
                intent.putExtra(key, (Long) object);
            } else if (object instanceof Double) {
                intent.putExtra(key, (Double) object);
            } else if (object instanceof CharSequence) {
                intent.putExtra(key, (CharSequence) object);
            } else if (object instanceof Bundle) {
                intent.putExtra(key, (Bundle) object);
            } else if (object instanceof Serializable) {
                intent.putExtra(key, (Serializable) object);
            } else if (object instanceof Parcelable) {
                intent.putExtra(key, (Parcelable) object);
            } else if (object instanceof String[]) {
                intent.putExtra(key, (String[]) object);
            }
        }
        return this;
    }
}
