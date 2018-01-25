package com.ku4irka.giphyapp.presenter.impl;

import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.app.AppApplication;
import com.ku4irka.giphyapp.presenter.IMainPresenter;
import com.ku4irka.giphyapp.view.IMainView;

import static com.ku4irka.giphyapp.app.Const.LIST_SCREEN;
import static com.ku4irka.giphyapp.app.Const.TWO_THOUSAND;

public class MainPresenter implements IMainPresenter {

    private static long backPressed;

    private IMainView view;

    public MainPresenter() {
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
    public void setView(IMainView view) {
        this.view = view;
    }

    @Override
    public void initializeNavigation() {
        this.navigateTo(LIST_SCREEN);
    }

    @Override
    public void backPressed(int countFragments) {
        if (countFragments == 1) {
            backPressed();
        } else {
            view.backPressed();
        }
    }

    private void backPressed() {
        if (backPressed + TWO_THOUSAND > System.currentTimeMillis()) {
            view.exit();
        } else {
            view.showError(view.context().getString(R.string.pressAgain));
        }
        backPressed = System.currentTimeMillis();
    }

    private void navigateTo(String screenKey) {
        AppApplication.getInstance().getRouter().navigateTo(screenKey);
    }
}