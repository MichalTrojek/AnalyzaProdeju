package cz.mtr.analyzaprodeju.fragments.ftp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import cz.mtr.analyzaprodeju.R;

public class FtpFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = FtpFragment.class.getSimpleName();

    private FtpViewModel mViewModel;
    private Spinner spinner;
    private Button downloadButton;

    public static FtpFragment newInstance() {
        return new FtpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ftp_fragment, container, false);

        spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.stores, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        downloadButton = view.findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(this);

//        spinner.setSelection(5);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(FtpViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        adapterView.getAdapter().getItem(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {


//        Toast.makeText(getContext(), spinner.getSelectedItem() + "", Toast.LENGTH_SHORT).show();

        UpdateDatabaseTask updateDatabase = new UpdateDatabaseTask(getContext(), mViewModel);
        updateDatabase.execute();


    }


}
