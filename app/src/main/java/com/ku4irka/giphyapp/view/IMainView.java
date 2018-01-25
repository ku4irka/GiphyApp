package com.ku4irka.giphyapp.view;

import android.content.Context;

public interface IMainView extends LoadDataView {

    void backPressed();

    void exit();

    Context context();
}