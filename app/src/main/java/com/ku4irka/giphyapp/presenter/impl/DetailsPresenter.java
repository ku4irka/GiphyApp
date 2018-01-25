package com.ku4irka.giphyapp.presenter.impl;

import android.text.SpannableString;
import android.util.Log;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.presenter.IDetailsPresenter;
import com.ku4irka.giphyapp.util.Utils;
import com.ku4irka.giphyapp.view.IDetailsView;

public class DetailsPresenter implements IDetailsPresenter {

    private static final String TAG = DetailsPresenter.class.getSimpleName();

    private IDetailsView view;
    private Media mMedia;

    private String mToolbarTitle;

    public DetailsPresenter() {
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        view = null;
    }

    @Override
    public void setView(IDetailsView view) {
        this.view = view;
    }

    @Override
    public void loadMediaDetails(Media media) {
        this.mMedia = media;
    }

    @Override
    public void showMediaDetails() {
        if (mMedia != null) {
            showMainImage();
            showUserAvatar();
            showDisplayName();
            showUserName();
            showUrl();
            changeToolbarTitle();
        }
    }

    @Override
    public void sharePostUrl(String url) {
        String subject = view.context().getString(R.string.app_name);
        Utils.shareIntent(view.context(), subject, url);
    }

    private void showMainImage() {
        try {
            String originalGif = mMedia.getImages().getOriginal().getGifUrl();
            if (originalGif != null) {
                changeHeightMainImage();
                view.showMainImage(originalGif);
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "showMainImage", e);
        }
    }

    private void changeHeightMainImage() {
        try {
            int width = mMedia.getImages().getOriginal().getWidth();
            int height = mMedia.getImages().getOriginal().getHeight();
            height = Utils.calculateHeight(
                    width,
                    height,
                    1,
                    0,
                    1);
            view.changeHeightMainImageView(height);
        } catch (NullPointerException e) {
            Log.d(TAG, "changeHeightMainImage", e);
        }
    }

    private void showUserAvatar() {
        try {
            String avatarUrl = mMedia.getUser().getAvatarUrl();
            view.showUserAvatar(avatarUrl);
        } catch (NullPointerException e) {
            Log.d(TAG, "showUserAvatar", e);
        }
    }

    private void showDisplayName() {
        try {
            mToolbarTitle = mMedia.getUser().getDisplayName();
            view.showDisplayName(mToolbarTitle);
        } catch (NullPointerException e) {
            Log.d(TAG, "showDisplayName", e);
        }
    }

    private void showUserName() {
        try {
            String userName = mMedia.getUser().getUsername();
            view.showUserName(userName);
        } catch (NullPointerException e) {
            Log.d(TAG, "showUserName", e);
        }
    }

    private void showUrl() {
        String postUrlStr = mMedia.getUrl();
        if (postUrlStr != null) {
            SpannableString postUrl = Utils.getUnderlineText(postUrlStr);
            view.showPostUrl(postUrl);
        }
    }

    private void changeToolbarTitle() {
        String title;
        if (mToolbarTitle != null) {
            title = mToolbarTitle;
        } else {
            title = view.context().getString(R.string.app_name);
        }
        view.changeToolbarTitle(title);
    }
}