package com.ku4irka.giphyapp.view;

import android.support.annotation.Nullable;

public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showError(@Nullable String message);
}