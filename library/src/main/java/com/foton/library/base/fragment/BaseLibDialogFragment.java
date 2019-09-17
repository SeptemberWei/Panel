package com.foton.library.base.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.foton.library.annotation.ContentView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLibDialogFragment extends DialogFragment {
    private Unbinder mUnbinder;
    public String FLAG = getClass().getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(injectLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isNeedFullScreen()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            super.onActivityCreated(savedInstanceState);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        } else {
            super.onActivityCreated(savedInstanceState);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isNeedFullScreen()) {
            setSystemUIVisible(false);
            getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getDialog().getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getDialog().getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    public String getFlag() {
        return FLAG;
    }

    public int injectLayout() {
        Class<? extends Fragment> aClass = getClass();
        ContentView contentView = aClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            return layoutId;
        }
        return -1;
    }

    public abstract void init();

    public abstract boolean isNeedFullScreen();


}
