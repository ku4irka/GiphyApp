package com.ku4irka.giphyapp.view;

import android.content.Context;

import com.giphy.sdk.core.models.Media;

import java.util.List;

public interface IListDataView extends LoadDataView {

    void renderDataList(List<Media> mediaList);

    Context context();
}