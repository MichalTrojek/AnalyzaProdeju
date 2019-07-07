package cz.mtr.analyzaprodeju.fragments.printer;

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


public class PrinterDialog extends DialogFragment {


    public interface OnPrintClicked {
        void returnsClicked();

        void ordersClicked();
    }

    private static final String TAG = PrinterDialog.class.getSimpleName();


    private Button returnsButton, ordersButton;
    private OnPrintClicked mOnPrintClicked;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_print_fragment, container, false);
        returnsButton = view.findViewById(R.id.printReturnsButton);
        ordersButton = view.findViewById(R.id.printOrdersButton);

        returnsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPrintClicked.returnsClicked();
                getDialog().dismiss();
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPrintClicked.ordersClicked();
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnPrintClicked = (OnPrintClicked) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

}
