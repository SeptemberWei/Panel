package com.foton.library.base.presenter;

public interface Presenter<T> {

    void setViewModel(T viewModel);

    void start();

    void stop();
}
