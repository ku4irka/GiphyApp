package com.ku4irka.giphyapp.presenter;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.view.IListDataView;

public interface IListPresenter extends Presenter {

    void setView(IListDataView view);

    void initialize();

    void onItemClicked(Media media);

}