package com.ku4irka.giphyapp.util;

import android.support.annotation.Nullable;


public interface OnToolbarChangeListener {

    void onTitleToolbarChanged(@Nullable String title);

    void onChangeDisplayHomeAsUpEnabled(boolean state);

}