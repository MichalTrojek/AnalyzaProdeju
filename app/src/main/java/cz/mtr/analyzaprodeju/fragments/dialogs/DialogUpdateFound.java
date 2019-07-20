package cz.mtr.analyzaprodeju.fragments.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cz.mtr.analyzaprodeju.MainActivity;
import cz.mtr.analyzaprodeju.R;

public class DialogUpdateFound extends DialogFragment {

    private Button mStartButton, mBackButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_found_fragment, container, false);


        mStartButton = view.findViewById(R.id.startButton);
        mBackButton = view.findViewById(R.id.backButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).createDownloadingDatabaseDialog();
                dismiss();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
