package com.foton.library.base.activity;

import android.app.Activity;
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
import com.foton.library.annotation.ContentView;
import com.foton.library.annotation.PresentInstance;
import com.foton.library.annotation.Title;
import com.foton.library.annotation.TitleType;
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

    @Override
    protected void onStart() {
        super.onStart();
        lazyLoad();
    }

    private void setContentLayout() {
        if (getLayoutRoot() <= 0 && injectLayout() > 0) {
            setContentView(injectLayout());
        } else if (getLayoutRoot() > 0 && injectLayout() > 0) {
            setContentView(getLayoutRoot());
            ViewGroup viewGroup = getView(R.id.root);
            View child = getLayoutInflater().inflate(injectLayout(), null);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
            if (!coverTitle()) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams.addRule(RelativeLayout.BELOW, R.id.layout_title);
            }
            viewGroup.addView(child, lp);
        }
    }

    private int injectLayout() {
        Class<? extends Activity> aClass = getClass();
        ContentView contentView = aClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            return layoutId;
        }
        return -1;
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        setPageTitle();
        init();
    }

    /**
     * if don`t need to show the title bar ,pls return 0;
     *
     * @return
     */
    @Override
    public int getLayoutRoot() {
        Class<? extends Activity> aClass = getClass();
        ContentView contentView = aClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int titleLayout = contentView.titleLayout();
            if (titleLayout != -1) {
                return titleLayout;
            }
            TitleType titleType = contentView.titleType();
            if (titleType == TitleType.None) {
                return 0;
            }
        }
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

    private void setPageTitle() {
        try {
            Class<? extends Activity> aClass = getClass();
            Title annotationTitle = aClass.getAnnotation(Title.class);
            if (annotationTitle != null) {
                int value = annotationTitle.value();
                TextView textView = getView(R.id.titleTv);
                if (null != textView) {
                    textView.setText(value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void setPageTitle(@StringRes int strRes) {
        TextView textView = getView(R.id.titleTv);
        if (null != textView) {
            textView.setText(strRes);
        }
    }

    protected void setPageTitle(String str) {
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

    /**
     * create presenter for current context
     *
     * @return
     */
    protected P createPresenter() {
        Class<? extends Activity> aClass = getClass();
        PresentInstance presentInstance = aClass.getAnnotation(PresentInstance.class);
        if (presentInstance != null) {
            Class<P> claz = presentInstance.value();
            try {
                P instance = claz.newInstance();
                return instance;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * load data from service
     */
    protected void lazyLoad() {

    }
}
