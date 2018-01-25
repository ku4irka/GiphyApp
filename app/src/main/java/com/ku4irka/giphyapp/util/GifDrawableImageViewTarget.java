package com.ku4irka.giphyapp.util;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

public class GifDrawableImageViewTarget extends ImageViewTarget<GifDrawable> {

    private int mLoopCount = GifDrawable.LOOP_FOREVER;

    public GifDrawableImageViewTarget(ImageView view, int loopCount) {
        super(view);
        mLoopCount = loopCount;
    }

    public GifDrawableImageViewTarget(ImageView view, int loopCount, boolean waitForLayout) {
        super(view, waitForLayout);
        mLoopCount = loopCount;
    }

    @Override
    protected void setResource(@Nullable GifDrawable resource) {
        if (resource != null) {
            resource.setLoopCount(mLoopCount);
        }
        view.setImageDrawable(resource);
    }
}