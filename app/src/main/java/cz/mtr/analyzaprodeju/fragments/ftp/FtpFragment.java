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

import java.text.Normalizer;

import cz.mtr.analyzaprodeju.R;

public class FtpFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = FtpFragment.class.getSimpleName();

    private FtpViewModel mViewModel;
    private Spinner mSpinner;
    private Button mDownloadButton;

    public static FtpFragment newInstance() {
        return new FtpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ftp_fragment, container, false);

        mSpinner = view.findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.stores, android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        mDownloadButton = view.findViewById(R.id.downloadButton);
        mDownloadButton.setOnClickListener(this);
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
        String name = Normalizer.normalize(mSpinner.getSelectedItem().toString(), Normalizer.Form.NFD);
        name = name.replaceAll("[^\\p{ASCII}]", "");

        DownloadAnalysisFtpTask task = new DownloadAnalysisFtpTask(getContext(), mViewModel.convertNameToShortcut(name));
        task.execute();

//        UpdateDatabaseTask updateDatabase = new UpdateDatabaseTask(getContext(), mViewModel);
//        updateDatabase.execute();


    }


}
