package com.ozan.moviest.binding;

import android.databinding.BindingAdapter;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ozan.moviest.helper.Controller;

public class BindAdapter {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, Boolean show) {
        if (show) {
            view.setVisibility(view.VISIBLE);
        } else {
            view.setVisibility(view.GONE);
        }
    }

    @BindingAdapter(value = {"loadImage"})
    public static void loadImage(RoundedImageView imageView, String url) {
        Glide.with(Controller.currentContext).applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load("https://image.tmdb.org/t/p/w342/$url")
                .into(imageView);
    }
}
