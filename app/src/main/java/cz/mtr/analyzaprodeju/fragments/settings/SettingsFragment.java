package cz.mtr.analyzaprodeju.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        mIpTextView = view.findViewById(R.id.ip_textView);
        saveButton = view.findViewById(R.id.save_ip_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setIpAddress(mInputIpAdress.getText().toString());
                Navigation.findNavController(getView()).navigate(R.id.homeFragment);
            }
        });


        backButton = view.findViewById(R.id.back_ip_button);
        backButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.homeFragment));


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);


        mViewModel.getIpAdress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mIpTextView.setText(s);
            }
        });

    }

}
