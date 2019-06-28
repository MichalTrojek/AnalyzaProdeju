package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.ViewModel.BarcodeViewModel;
import info.androidhive.barcode.BarcodeReader;

public class BarcodeFragment extends Fragment implements BarcodeReader.BarcodeReaderListener {
    private static final String TAG = BarcodeFragment.class.getSimpleName();

    private BarcodeReader mBarcodeReader;
    private FloatingActionButton mStopScanningButton;
    private BarcodeViewModel mViewModel;


    public BarcodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mViewModel = ViewModelProviders.of(this).get(BarcodeViewModel.class);
        mViewModel.getStatus().observe(this, new Observer<BarcodeViewModel.Status>() {
            @Override
            public void onChanged(BarcodeViewModel.Status status) {


                switch (status) {
                    case NOT_FOUND:
                        Toast.makeText(getActivity(), "EAN NENALEZEN", Toast.LENGTH_SHORT).show();
                        break;
                    case DATABASE:
                        goToNotFoundFragment();
                        break;
                    case ANALYSIS:
                        goToDetailFragment();
                        break;

                }
            }
        });

    }

    private void goToNotFoundFragment() {
        try {
            BarcodeFragmentDirections.BarcodeToNotFound action = BarcodeFragmentDirections.barcodeToNotFound(mViewModel.getArticleDb());
            if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.barcodeFragment) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void goToDetailFragment() {
        try {
            BarcodeFragmentDirections.BarcodeToDetail action = BarcodeFragmentDirections.barcodeToDetail(mViewModel.getmArticleAnalysis());
            if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.barcodeFragment) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barcode, container, false);
        mBarcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        mBarcodeReader.setListener(this);
        mStopScanningButton = (FloatingActionButton) view.findViewById(R.id.stopScanningButton);
        mStopScanningButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.barcodeToHome, null));
        return view;
    }

    @Override
    public void onScanned(final Barcode barcode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mViewModel.findArticle(barcode.displayValue);


            }
        });
    }


    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Log.e(TAG, "onScanError: " + errorMessage);
    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }


}