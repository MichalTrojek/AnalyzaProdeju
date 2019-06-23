package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cz.mtr.analyzaprodeju.R;


public class BottomFragment extends Fragment implements View.OnTouchListener {
    private Button scanButton;
    private BarcodeFragment barcodeFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        scanButton = (Button) view.findViewById(R.id.scan_button);
        scanButton.setOnTouchListener(this);
        return view;
    }


    public static BottomFragment newInstance() {
        BottomFragment fragment = new BottomFragment();
        return fragment;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            scanButton.setBackgroundResource(R.drawable.selectedscanbutton);
            slideDownScannerFragment();
            slideDownScanButton();
            slideDownHomeFragment();
            return true;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }
        return false;
    }


    private void slideDownScannerFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_left);
        barcodeFragment = new BarcodeFragment();
        ft.add(R.id.center_container, barcodeFragment, "BARCODE_FRAGMENT");
        ft.commit();

    }

    private void slideDownScanButton() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom);
        ft.remove(this).commit();
    }

    private void slideDownHomeFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom);
        Fragment homeFragment = getActivity().getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
        ft.remove(homeFragment);
        ft.commit();
    }


}
