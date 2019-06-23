package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cz.mtr.analyzaprodeju.R;

public class InfoFragment extends Fragment {
    private static final String TAG = InfoFragment.class.getSimpleName();

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("INFO FRAGMENT");
        return view;
    }


}
