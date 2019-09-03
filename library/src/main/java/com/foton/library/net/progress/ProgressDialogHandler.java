package com.foton.library.net.progress;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.foton.library.R;
import com.foton.library.ui.view.LoadingDialog;

import java.lang.ref.WeakReference;

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    public static final int SHOW_ERROR_DIALOG = 3;

    private LoadingDialog mLoadingView;

    private WeakReference<Context> mContext;

    private ProgressListener progressListener;

    private boolean cancelable;

    public ProgressDialogHandler(Context context, ProgressListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.mContext = new WeakReference<>(context);
        this.progressListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingDialog(mContext.get());

            mLoadingView.setCancelable(cancelable);

            if (cancelable) {
                mLoadingView.setOnDismissListener(dialog -> progressListener.onProgressCancle());
            }
            if (!mLoadingView.isShowing()) {
                mLoadingView.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (mLoadingView != null) {
            mLoadingView.dismiss();
            mLoadingView = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
            case SHOW_ERROR_DIALOG:
                dismissProgressDialog();
                showError(msg.obj);
                break;
        }
    }

    private void showError(Object object) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext.get());
        builder.setTitle(mContext.get().getString(R.string.notice));
        builder.setMessage(object.toString());
        builder.setNegativeButton(mContext.get().getString(R.string.confirm), null);
        builder.show();
    }

}
