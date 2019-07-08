package cz.mtr.analyzaprodeju.fragments.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import cz.mtr.analyzaprodeju.R;

public class DialogLoadingFragment extends DialogFragment {

    private static final String TAG = DialogChangeFragment.class.getSimpleName();


    private ProgressBar progress;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        progress = view.findViewById(R.id.progressBar);
        return view;
    }
}
