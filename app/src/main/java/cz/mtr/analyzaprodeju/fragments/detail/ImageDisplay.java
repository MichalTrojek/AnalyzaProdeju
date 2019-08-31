package cz.mtr.analyzaprodeju.fragments.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.models.Model;

public class ImageDisplay {

    private String mUrl;
    private ImageView mImageView;
    private ProgressBar mProgress;

    public ImageDisplay(String url, ImageView imageView, ProgressBar progressBar) {
        this.mUrl = url;
        this.mImageView = imageView;
        this.mProgress = progressBar;
    }


    public void show() {
//        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load(Model.getInstance().getLargeImageLink()).placeholder(R.drawable.notfound).error(R.drawable.notfound).noFade().fetch(new Callback() {
            @Override
            public void onSuccess() {
                mProgress.setVisibility(View.GONE);
                Picasso.get().load(mUrl).error(R.drawable.notfound).into(mImageView);
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(mUrl).error(R.drawable.notfound).into(mImageView);
                mProgress.setVisibility(View.GONE);
            }
        });
    }


}
