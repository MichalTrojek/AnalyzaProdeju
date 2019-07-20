package cz.mtr.analyzaprodeju.fragments.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import cz.mtr.analyzaprodeju.R;

public class DialogDownloadDatabase extends DialogFragment {


    private ProgressBar mProgressBar;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_download_database, container, false);

        mProgressBar = view.findViewById(R.id.progressBar3);

        return view;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }
}
