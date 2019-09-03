package com.foton.library.base.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

public abstract class AbstractPresenter<T> implements Presenter<T> {

    private WeakReference<T> mViewModel;

    @Override
    public void setViewModel(T viewModel) {
        mViewModel = new WeakReference<>(viewModel);
    }

    public T viewModel() {
        return mViewModel.get();
    }

    @Override
    public void stop() {
        if (mViewModel != null) {
            mViewModel.clear();
            mViewModel = null;
        }
    }
}
