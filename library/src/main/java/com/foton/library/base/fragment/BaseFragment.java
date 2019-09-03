package com.foton.library.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foton.library.base.BaseInterface;
import com.foton.library.base.activity.BaseLibActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment implements BaseInterface {
    private Unbinder mUnbinder;
    public BaseLibActivity baseLibActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutContent(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseLibActivity = (BaseLibActivity) getActivity();
        onBaseCreate(savedInstanceState);
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onHidden(hidden);
    }

    public  abstract void onHidden(boolean hidden);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public boolean coverTitle() {
        return false;
    }

    @Override
    public int getLayoutRoot() {
        return 0;
    }

    @Override
    public Object getAppTitle() {
        return null;
    }


}
