package com.ku4irka.giphyapp.presenter;

import com.ku4irka.giphyapp.view.IMainView;

public interface IMainPresenter extends Presenter {

    void setView(IMainView view);

    void initializeNavigation();

    void backPressed(int countFragments);

}