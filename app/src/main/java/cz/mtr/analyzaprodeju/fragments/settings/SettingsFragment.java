package cz.mtr.analyzaprodeju.fragments.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import cz.mtr.analyzaprodeju.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private TextInputEditText mInputIpAddress;
    private Button saveButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        mInputIpAddress = view.findViewById(R.id.eanEditText);


        saveButton = view.findViewById(R.id.save_ip_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mInputIpAddress.getText().toString().isEmpty()) {
                    if (mViewModel.validateIpAddress(mInputIpAddress.getText().toString())) {
                        mViewModel.setIpAddress(mInputIpAddress.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Byla vložena IP adresa ve špatném formátu.", Toast.LENGTH_SHORT).show();
                    }
                }

                hideKeyboard(mInputIpAddress);
                Navigation.findNavController(getView()).navigate(R.id.toHome);

            }

        });


        return view;
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);


        mViewModel.getIpAddress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mInputIpAddress.setText(s);
            }
        });


    }

}
