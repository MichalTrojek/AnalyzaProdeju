package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cz.mtr.analyzaprodeju.R;
import info.androidhive.barcode.BarcodeReader;

public class BarcodeFragment extends Fragment implements BarcodeReader.BarcodeReaderListener, View.OnClickListener {
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
        stopScanningButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onScanned(final Barcode barcode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getActivity(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                slideScannerUp();
            }
        });
    }

    private void slideScannerUp() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_top);
        ft.remove(BarcodeFragment.this);
        ft.commit();
    }

    private void showInfoFragment() {

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

    @Override
    public void onClick(View view) {
        slideScannerUp();
        showHomeFragment();
        SlideInBottomFragment();
    }

    private void SlideInBottomFragment() {
        BottomFragment fragment = BottomFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_left, R.anim.enter_from_bottom, R.anim.exit_to_left); //the second pair of animations is there so back button shows animations.
        transaction.add(R.id.bottom_container, fragment, "BOTTOM_FRAGMENT").commit();
    }

    private void showHomeFragment() {
        HomeFragment fragment = HomeFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_left, R.anim.enter_from_bottom, R.anim.exit_to_left); //the second pair of animations is there so back button shows animations.
        transaction.add(R.id.center_container, fragment, "HOME_FRAGMENT").commit();
    }


}