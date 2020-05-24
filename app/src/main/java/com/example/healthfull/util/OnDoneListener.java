package com.example.healthfull.util;

public interface OnDoneListener<T> {
    void onSuccess(T object);
    void onFailure(String message);
}
