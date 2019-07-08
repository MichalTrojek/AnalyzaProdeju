package cz.mtr.analyzaprodeju.fragments.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import cz.mtr.analyzaprodeju.R;

public class DialogDeleteFragment extends DialogFragment {
    private static final String TAG = DialogChangeFragment.class.getSimpleName();


    private Button backButton, deleteButton;
    private OnDeleteConfirmed onDeleteConfirmed;

    public interface OnDeleteConfirmed {
        void deleteConfirmed();
    }


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_fragment, container, false);
        backButton = view.findViewById(R.id.backDeleteButton);
        deleteButton = view.findViewById(R.id.deleteAllDialogButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteConfirmed.deleteConfirmed();
                getDialog().dismiss();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDeleteConfirmed = (OnDeleteConfirmed) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }


}
