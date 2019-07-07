package cz.mtr.analyzaprodeju.fragments.display.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import cz.mtr.analyzaprodeju.R;


public class FragmentChangeDialog extends DialogFragment {
    private static final String TAG = FragmentChangeDialog.class.getSimpleName();

    public interface OnInputSelected {
        void sendAmount(String amount);

        void deleteItem();
    }

    public OnInputSelected mOnInputSelected;


    private Button changeButton, deleteButton;
    private EditText changeAmountEditText;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_change_fragment, container, false);
        changeButton = view.findViewById(R.id.changeButton);
        changeButton.setEnabled(false);
        deleteButton = view.findViewById(R.id.deleteButton);
        changeAmountEditText = view.findViewById(R.id.inputChangeAmount);
        changeAmountEditText.addTextChangedListener(changeTextWatcher);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnInputSelected.deleteItem();
                getDialog().dismiss();
            }
        });


        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnInputSelected.sendAmount(changeAmountEditText.getText().toString());
                hideKeyboard(changeAmountEditText);
                getDialog().dismiss();
            }
        });


        return view;
    }


    private TextWatcher changeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amountInput = changeAmountEditText.getText().toString().trim();
            changeButton.setEnabled(!amountInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }


}
