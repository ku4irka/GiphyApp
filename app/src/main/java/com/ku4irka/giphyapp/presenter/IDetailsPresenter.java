package com.ku4irka.giphyapp.presenter;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.view.IDetailsView;

public interface IDetailsPresenter extends Presenter {

    void setView(IDetailsView view);

    void loadMediaDetails(Media media);

    void showMediaDetails();

    void sharePostUrl(String url);
}