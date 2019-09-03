package com.foton.library.net.response;

import com.foton.library.net.Exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ResponseFunction <T> implements Function<Response<T>, ObservableSource<T>> {

    @Override
    public ObservableSource<T> apply(Response<T> tResponse) throws Exception {
        boolean status = tResponse.getStatus();
        String message = tResponse.getMessage();
        int code = tResponse.getReturnCode();
        if (code == 0) {
            return Observable.just(tResponse.getData());
        } else {
            return Observable.error(new ApiException(code, message));
        }
    }
}
