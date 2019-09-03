package com.foton.library.net.response;

import com.foton.library.net.Exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<T>> {

    @Override
    public ObservableSource<T> apply(Throwable throwable) throws Exception {
        return Observable.error(CustomException.handleException(throwable));
    }
}