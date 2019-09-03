package com.foton.library.net.progress;


import android.content.Context;

import com.foton.library.net.Exception.ApiException;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProgressSubscriber<T> implements ProgressListener, Observer<T> {
    private ProgressDialogHandler mProgressDialogHandler;

    private SubscriberOnNextListener subscriberOnNextListener;
    private Disposable mDisposable;

    public ProgressSubscriber(Context context, SubscriberOnNextListener<T> subscriberOnNextListener) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mProgressDialogHandler = new ProgressDialogHandler(new WeakReference<>(context).get(), this, true);
    }

    private void show() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismiss() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onProgressCancle() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        show();
    }

    @Override
    public void onNext(T value) {
        dismiss();
        subscriberOnNextListener.onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        ApiException ex
                = (ApiException) e;
        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_ERROR_DIALOG, ex.getDisplayMessage()).sendToTarget();
    }

    @Override
    public void onComplete() {
        dismiss();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
