package com.ku4irka.giphyapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;

public interface IDetailsView extends LoadDataView {

    void showMainImage(@NonNull String gifUrl);

    void changeHeightMainImageView(int height);

    void showUserAvatar(@Nullable String avatarUrl);

    void showDisplayName(@Nullable String displayName);

    void showUserName(@Nullable String userName);

    void showPostUrl(@NonNull SpannableString postUrl);

    void changeToolbarTitle(@NonNull String title);

    Context context();
}