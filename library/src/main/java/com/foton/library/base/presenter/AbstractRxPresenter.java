package com.foton.library.base.presenter;

import com.foton.library.net.schedulers.SchedulerProvider;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbstractRxPresenter<T> extends AbstractPresenter<T> {
    final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleProvider<ActivityEvent> provider;

    public AbstractRxPresenter() {
    }

    public AbstractRxPresenter(LifecycleProvider<ActivityEvent> provider) {
        this.provider = provider;
    }

    public LifecycleProvider<ActivityEvent> getProvider() {
        return provider;
    }

    public void setProvider(LifecycleProvider<ActivityEvent> provider) {
        this.provider = provider;
    }


    @Override
    public void stop() {
        super.stop();
        clearSubscription();
    }

    public <T> ObservableTransformer<T, T> combind() {
        return upstream -> upstream.compose(getProvider().bindToLifecycle()).compose(SchedulerProvider.getInstance().applySchedulers());
    }

    protected void addSubscription(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    protected void clearSubscription() {
        mCompositeDisposable.clear();
    }
}
