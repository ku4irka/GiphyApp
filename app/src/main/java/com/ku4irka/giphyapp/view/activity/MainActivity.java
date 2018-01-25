package com.ku4irka.giphyapp.view.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.databinding.ActivityMainBinding;
import com.ku4irka.giphyapp.presenter.IMainPresenter;
import com.ku4irka.giphyapp.presenter.impl.MainPresenter;
import com.ku4irka.giphyapp.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView {

    private ActivityMainBinding mBinding;

    private IMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initPresenter();

        if (savedInstanceState == null) {
            mPresenter.initializeNavigation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onBackPressed() {
        int countOfFragments = getSupportFragmentManager().getBackStackEntryCount();
        mPresenter.backPressed(countOfFragments);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@Nullable String message) {
        if (message != null) {
            showToastMessage(message);
        }
    }

    @Override
    public void backPressed() {
        super.onBackPressed();
    }

    @Override
    public void exit() {
        finish();
    }

    @Override
    public Context context() {
        return this.getApplicationContext();
    }

    private void initPresenter() {
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
    }
}
