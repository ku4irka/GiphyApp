package com.ku4irka.giphyapp.model;

public interface DataObserver<T> {

    void onResponse(T response);

    void onError(String error);

}