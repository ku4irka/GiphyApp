package com.ku4irka.giphyapp.app;

import android.app.Application;

import com.giphy.sdk.core.network.api.GPHApi;
import com.giphy.sdk.core.network.api.GPHApiClient;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class AppApplication extends Application {

    private static AppApplication sInstance;
    private Cicerone<Router> mCicerone;
    private GPHApi gphClient;

    public static AppApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mCicerone = Cicerone.create();
        gphClient = new GPHApiClient(Const.API_KEY);
    }

    public NavigatorHolder getNavigatorHolder() {
        return mCicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return mCicerone.getRouter();
    }

    public GPHApi getGphClient() {
        return gphClient;
    }
}