package cz.mtr.analyzaprodeju.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import cz.mtr.analyzaprodeju.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private EditText mInputIpAdress;
    private TextView mIpTextView;
    private Button saveButton, backButton;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);


        mInputIpAdress = view.findViewById(R.id.ip_adress_input);
        saveButton = view.findViewById(R.id.save_ip_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mInputIpAdress.getText().toString().isEmpty()) {
                    if (mViewModel.validateIpAddress(mInputIpAdress.getText().toString())) {
                        mViewModel.setIpAddress(mInputIpAdress.getText().toString());
                        Navigation.findNavController(getView()).navigate(R.id.homeFragment);
                    } else {
                        Toast.makeText(getActivity(), "Vložena IP adresa ve špatném formátu.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Není zadaná IP adresa", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);


        mViewModel.getIpAdress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mInputIpAdress.setHint("IP adresa: " + s);
            }
        });

    }

}
