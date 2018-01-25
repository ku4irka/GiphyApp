package com.ku4irka.giphyapp.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.app.AppApplication;

import static com.ku4irka.giphyapp.app.Const.MAX_COUNT_OF_REPEAT_ANIMATION;

public final class Utils {

    private Utils() {}

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(float dp) {
        return Math.round(
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dp,
                        AppApplication.getInstance().getResources().getDisplayMetrics()
                )
        );
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = AppApplication.getInstance()
                .getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int calculateHeight(int widthPicPx,
                                      int heightPicPx,
                                      int numberElementsOnScreen,
                                      int padding,
                                      double scaling) {
        DisplayMetrics displayMetrics = AppApplication.getInstance()
                .getResources().getDisplayMetrics();

        int screenWidthPx = (int)
                ((float) displayMetrics.widthPixels / (float) numberElementsOnScreen);

        screenWidthPx *= scaling;
        screenWidthPx -= padding;

        int heightPx = (int) (((float) screenWidthPx / (float) widthPicPx) * (float) heightPicPx);
        return heightPx;
    }

    public static SpannableString getUnderlineText(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(
                new UnderlineSpan(),
                0,
                text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static void shareIntent(Context context, String subject, String text) {
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, text);

        context.startActivity(Intent.createChooser(i, context.getString(R.string.share)));
    }

    public static void setVisibility(View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    public static void showGifImage(@NonNull ImageView imageView, @NonNull String imageSrc) {
        GlideApp.with(AppApplication.getInstance().getApplicationContext())
                .asGif()
                .load(imageSrc)
                .fitCenter()
                .error(new ColorDrawable(Color.CYAN))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(new GifDrawableImageViewTarget(imageView, MAX_COUNT_OF_REPEAT_ANIMATION));
    }
}