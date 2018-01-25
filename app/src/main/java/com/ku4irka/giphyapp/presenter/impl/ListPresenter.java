package com.ku4irka.giphyapp.presenter.impl;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.app.AppApplication;
import com.ku4irka.giphyapp.model.DataObserver;
import com.ku4irka.giphyapp.model.IModel;
import com.ku4irka.giphyapp.model.Model;
import com.ku4irka.giphyapp.presenter.IListPresenter;
import com.ku4irka.giphyapp.view.IListDataView;

import java.util.List;

import static com.ku4irka.giphyapp.app.Const.DETAILS_SCREEN;

public class ListPresenter implements IListPresenter {

    private IListDataView view;
    private IModel model;

    public ListPresenter() {
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
    public void setView(IListDataView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        this.loadDataList();
        model.getTrendingData();
    }

    @Override
    public void onItemClicked(Media media) {
        navigateTo(DETAILS_SCREEN, media);
    }

    private void loadDataList() {
        model = new Model<>(new DataObserver<List<Media>>() {
            @Override
            public void onResponse(List<Media> response) {
                view.renderDataList(response);
                view.hideLoading();
            }

            @Override
            public void onError(String error) {
                view.showError(error);
                view.hideLoading();
            }
        });
    }

    private void navigateTo(String screenKey, Object data) {
        AppApplication.getInstance().getRouter().navigateTo(screenKey, data);
    }
}