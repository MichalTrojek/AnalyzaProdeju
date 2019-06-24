package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cz.mtr.analyzaprodeju.R;
import info.androidhive.barcode.BarcodeReader;

public class BarcodeFragment extends Fragment implements BarcodeReader.BarcodeReaderListener {
    private static final String TAG = BarcodeFragment.class.getSimpleName();

    private BarcodeReader barcodeReader;
    private FloatingActionButton stopScanningButton;


    public BarcodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barcode, container, false);
        barcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        barcodeReader.setListener(this);
        stopScanningButton = (FloatingActionButton) view.findViewById(R.id.stopScanningButton);
//        stopScanningButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.barcodeToHome, null));


        stopScanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.barcodeToInfo);
            }
        });
        return view;
    }

    @Override
    public void onScanned(final Barcode barcode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Barcosde: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
//                Navigation.findNavController(getView()).navigate(R.id.barcodeToInfo);
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