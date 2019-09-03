package com.foton.library.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

public class ViewUtils {
    public static <T extends View> T findViewById(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    public static <T extends View> T findViewById(Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }
}
