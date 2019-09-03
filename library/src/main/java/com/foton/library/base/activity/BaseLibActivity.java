package com.foton.library.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foton.library.R;
import com.foton.library.base.BaseInterface;
import com.foton.library.base.presenter.AbstractRxPresenter;
import com.foton.library.utils.ViewUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLibActivity<P extends AbstractRxPresenter> extends RxAppCompatActivity implements BaseInterface {
    Unbinder unbinder;

    public FragmentManager fragmentManager;

    public FragmentTransaction fragmentTransaction;

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout();
        unbinder = ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.setProvider(this);
        }
        onBaseCreate(savedInstanceState);
    }

    protected P createPresenter() {
        return null;
    }

    private void setContentLayout() {
        if (getLayoutRoot() <= 0 && getLayoutContent() > 0) {
            setContentView(getLayoutContent());
        } else if (getLayoutRoot() > 0 && getLayoutContent() > 0) {
            setContentView(getLayoutRoot());
            ViewGroup viewGroup = getView(R.id.root);
            View child = getLayoutInflater().inflate(getLayoutContent(), null);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
            if (!coverTitle()) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams.addRule(RelativeLayout.BELOW, R.id.layout_title);
            }
            viewGroup.addView(child, lp);
        }
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        init();
    }

    @Override
    public int getLayoutRoot() {
        return R.layout.activity_root;
    }

    @Override
    public Object getAppTitle() {
        return null;
    }

    @Override
    public boolean coverTitle() {
        return false;
    }

    public void setTitle(@StringRes int strRes) {
        TextView textView = getView(R.id.titleTv);
        if (null != textView) {
            textView.setText(strRes);
        }
    }

    public void setTitle(String str) {
        TextView textView = getView(R.id.titleTv);
        if (null != textView) {
            textView.setText(str);
        }
    }

    public void showLeftRightButton(boolean isShow, boolean isLeft) {
        Button button = getView(isLeft ? R.id.backBtn : R.id.rightBtn);
        if (null != button) {
            button.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setAppBackground(@ColorInt int res) {
        RelativeLayout layout = getView(R.id.appBackground);
        layout.setBackgroundColor(res);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.stop();
        }
    }

    public <T extends View> T getView(int id) {
        return ViewUtils.findViewById(this, id);
    }

    public void showNoneView(boolean isShow) {
        if (isShow) {
            View view = getView(R.id.noneView);
            view.setVisibility(View.VISIBLE);
        }
    }

    public void onLeftClick(View view) {
        View view1 = getWindow().peekDecorView();
        if (view1 != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
        supportFinishAfterTransition();
    }

    public void onRightClick(View view) {
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

}
