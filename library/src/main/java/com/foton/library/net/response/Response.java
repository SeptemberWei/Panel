package com.foton.library.net.response;


public class Response<T> {

    //    private boolean status; // 返回的code
    private T result; // 具体的数据结果
    private String message; // message 可用来返回接口的说明
    private int code;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
