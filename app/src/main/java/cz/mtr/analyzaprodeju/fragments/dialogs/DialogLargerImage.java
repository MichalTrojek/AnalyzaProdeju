package cz.mtr.analyzaprodeju.fragments.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.models.Model;


public class DialogLargerImage extends DialogFragment {
    private static final String TAG = DialogLargerImage.class.getSimpleName();


    private ImageView mImageView;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_larger_image, container, false);

        mImageView = view.findViewById(R.id.largeImageView);
//        ImageDisplay display = new ImageDisplay(Model.getInstance().getLargeImageLink(), mImageView);
//        display.show();



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.get().load(Model.getInstance().getLargeImageLink()).into(mImageView);
    }

}
