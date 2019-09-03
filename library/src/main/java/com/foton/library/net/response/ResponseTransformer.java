package com.foton.library.net.response;


import io.reactivex.ObservableTransformer;

public class ResponseTransformer {

    public <T> ObservableTransformer<Response<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }
}
