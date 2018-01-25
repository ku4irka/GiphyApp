package com.ku4irka.giphyapp.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.databinding.FragmentDetailsBinding;
import com.ku4irka.giphyapp.presenter.IDetailsPresenter;
import com.ku4irka.giphyapp.presenter.impl.DetailsPresenter;
import com.ku4irka.giphyapp.util.OnToolbarChangeListener;
import com.ku4irka.giphyapp.view.IDetailsView;

import static com.ku4irka.giphyapp.util.Utils.setVisibility;
import static com.ku4irka.giphyapp.util.Utils.showGifImage;


public class DetailsFragment extends BaseFragment implements IDetailsView {

    private static final String TAG = DetailsFragment.class.getSimpleName();

    private FragmentDetailsBinding mBinding;

    private IDetailsPresenter mPresenter;
    private OnToolbarChangeListener toolbarChangeListener;

    public static DetailsFragment getInstance(Object data) {
        Media media = (Media) data;
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, media);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnToolbarChangeListener) {
            this.toolbarChangeListener = (OnToolbarChangeListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DetailsPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_details,
                container,
                false);

        initViews();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Media media = bundle.getParcelable(TAG);
            mPresenter.loadMediaDetails(media);
            mPresenter.showMediaDetails();
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
    public void onDetach() {
        super.onDetach();
        toolbarChangeListener = null;
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
    public void showMainImage(@NonNull String gifUrl) {
        ImageView imageView = mBinding.ivMain;
        setVisibility(imageView, View.VISIBLE);
        showGifImage(imageView, gifUrl);
    }

    @Override
    public void changeHeightMainImageView(int height) {
        mBinding.ivMain.getLayoutParams().height = height;
        mBinding.ivMain.requestLayout();
    }

    @Override
    public void showUserAvatar(@Nullable String avatarUrl) {
        if (avatarUrl != null) {
            ImageView imageView = mBinding.ivUser;
            setVisibility(imageView, View.VISIBLE);
            showGifImage(imageView, avatarUrl);
        }
    }

    @Override
    public void showDisplayName(@Nullable String displayName) {
        if (displayName != null) {
            setVisibility(mBinding.tvDisplayName, View.VISIBLE);
            mBinding.tvDisplayName.setText(displayName);
        }
    }

    @Override
    public void showUserName(@Nullable String userName) {
        if (userName != null) {
            setVisibility(mBinding.tvUserName, View.VISIBLE);
            mBinding.tvUserName.setText(userName);
        }
    }

    @Override
    public void showPostUrl(@NonNull SpannableString postUrl) {
        setVisibility(mBinding.tvPostUrl, View.VISIBLE);
        mBinding.tvPostUrl.setText(postUrl);
    }

    @Override
    public void changeToolbarTitle(@NonNull String title) {
        toolbarChangeListener.onTitleToolbarChanged(title);
    }

    @Override
    public Context context() {
        return this.getContext();
    }

    private void initViews() {
        toolbarChangeListener.onChangeDisplayHomeAsUpEnabled(true);
        mBinding.tvPostUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mBinding.tvPostUrl.getText().toString();
                mPresenter.sharePostUrl(url);
            }
        });
    }
}
