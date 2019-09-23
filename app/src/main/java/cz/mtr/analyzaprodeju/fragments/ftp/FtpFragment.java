package cz.mtr.analyzaprodeju.fragments.ftp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import cz.mtr.analyzaprodeju.R;

public class FtpFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = FtpFragment.class.getSimpleName();

    private FtpViewModel mViewModel;
    private Spinner mSpinner;
    private Button mDownloadButton;
    private TextInputEditText mPasswordEditText;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ftp_fragment, container, false);

        mSpinner = view.findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.stores, android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        mPasswordEditText = view.findViewById(R.id.passwordInput);
        mDownloadButton = view.findViewById(R.id.downloadButton);
        mDownloadButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FtpViewModel.class);
        mViewModel.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mPasswordEditText.setText(s);
            }
        });

        mViewModel.getIndexOfLastSelectedItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mSpinner.setSelection(integer);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        adapterView.getAdapter().getItem(pos);
        mViewModel.setIndexOfLastSelectedItem(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {
        if (mPasswordEditText.length() != 0) {
            mViewModel.onDownloadDataClick(mSpinner.getSelectedItem().toString(), mPasswordEditText.getText().toString(), this.getFragmentManager());
            clearBackStack();
            Navigation.findNavController(getView()).navigate(R.id.homeFragment);

        } else {
            Toast toast = Toast.makeText(getContext(), "Není vložené heslo", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void clearBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

}
