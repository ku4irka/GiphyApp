package com.ku4irka.giphyapp.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.databinding.FragmentListBinding;
import com.ku4irka.giphyapp.presenter.IListPresenter;
import com.ku4irka.giphyapp.presenter.impl.ListPresenter;
import com.ku4irka.giphyapp.util.OnToolbarChangeListener;
import com.ku4irka.giphyapp.view.IListDataView;
import com.ku4irka.giphyapp.view.adapters.RecyclerDataAdapter;

import java.util.List;

import static com.ku4irka.giphyapp.app.Const.SPAN_COUNT_LIST;


public class ListFragment extends BaseFragment implements IListDataView {

    private static final String TAG = ListFragment.class.getSimpleName();

    private FragmentListBinding mBinding;

    private IListPresenter mPresenter;
    private OnToolbarChangeListener toolbarChangeListener;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerDataAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static ListFragment getInstance() {
        return new ListFragment();
    }

    public ListFragment() {
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
        mPresenter = new ListPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_list,
                container,
                false);
        initView();
        setupRecyclerView();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        this.loadDataList();
        this.showLoading();
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
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        toolbarChangeListener = null;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(@Nullable String message) {
        if (message != null) {
            showToastMessage(message);
        }
    }

    @Override
    public void renderDataList(@Nullable List<Media> mediaList) {
        if (mediaList != null) {
            mAdapter.setData(mediaList);
        }
    }

    @Override
    public Context context() {
        return this.getContext();
    }

    /**
     * Load all data.
     */
    private void loadDataList() {
        mPresenter.initialize();
    }

    private void refreshData() {
        loadDataList();
    }

    private void setupRecyclerView() {
        mAdapter.setOnItemClickListener(itemClickListener);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
//        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initView() {
        toolbarChangeListener.onTitleToolbarChanged(getString(R.string.trending_now));
        toolbarChangeListener.onChangeDisplayHomeAsUpEnabled(false);
        mRecyclerView = mBinding.recyclerView;
        mGridLayoutManager = new GridLayoutManager(
                getContext(),
                SPAN_COUNT_LIST
        );
        mAdapter = new RecyclerDataAdapter();
        mSwipeRefreshLayout = mBinding.swipeRefreshLayout;
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private RecyclerDataAdapter.OnItemClickListener itemClickListener =
            new RecyclerDataAdapter.OnItemClickListener() {
                @Override
                public void onClick(Media media) {
                    if (mPresenter != null) {
                        mPresenter.onItemClicked(media);
                    }
                }
            };
}
