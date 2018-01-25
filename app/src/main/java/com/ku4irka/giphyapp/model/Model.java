package com.ku4irka.giphyapp.model;

import android.util.Log;

import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.network.api.CompletionHandler;
import com.giphy.sdk.core.network.api.GPHApi;
import com.giphy.sdk.core.network.response.ListMediaResponse;
import com.ku4irka.giphyapp.app.AppApplication;
import com.ku4irka.giphyapp.app.Const;

public class Model<T> implements IModel {

    private GPHApi gphClient;
    private DataObserver<T> observer;

    public Model(DataObserver<T> observer) {
        this.observer = observer;
        gphClient = AppApplication.getInstance().getGphClient();
    }


    // Trending Gifs
    @Override
    public void getTrendingData() {
        gphClient.trending(
                MediaType.gif,
                null,
                null,
                null,
                new CompletionHandler<ListMediaResponse>() {
            @Override
            public void onComplete(ListMediaResponse result, Throwable e) {
                if (result == null) {
                    // Do what you want to do with the error
                    observer.onError(Const.EMPTY_RESPONSE);
                } else {
                    if (result.getData() != null) {
                        for (Media gif : result.getData()) {
                            Log.v("getTrendingData", gif.getId());
                        }
                        observer.onResponse((T) result.getData());
                    } else {
                        Log.e("getTrendingData error", "No results found");
                        observer.onError(Const.NO_RESULTS_FOUND);
                    }
                }
            }
        });
    }
}