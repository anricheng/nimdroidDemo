package cn.chou.aric.baselibrary.model;


public class BaseResponse<T> {
    int code;
    String message;
    T result;

    public int getStatusCode() {
        return code;
    }

    public T getPayload() {
        return result;
    }
}
