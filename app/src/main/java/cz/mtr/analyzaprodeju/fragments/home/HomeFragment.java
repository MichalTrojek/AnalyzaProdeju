package cz.mtr.analyzaprodeju.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.barcode.BarcodeViewModel;

public class HomeFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private Switch mSwitch;
    private FloatingActionButton scanButton;
    private TextInputLayout eanInput;
    private TextInputEditText eanEditText;
    private HomeFragmentViewModel mViewModel;
    private MultiStateToggleButton toggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        setupViewModel();
        setupToggle(view);
        setEanInputListenerToHandleEnterKey();
        return view;
    }

    private void findViews(View view) {
        scanButton = view.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.homeToBarcode, null));
        eanInput = view.findViewById(R.id.eanInput);
        eanEditText = view.findViewById(R.id.eanEditText);
    }


    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
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


    private void setupToggle(View view) {
        toggle = view.findViewById(R.id.mstb_multi_id);
        toggle.setElements(R.array.search_options, 1);
        toggle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                switch (position) {
                    case 0:
                        searchByEan();
                        break;
                    case 1:
                        searchWithScanner();
                        break;
                    case 2:
                        searchByName();
                        break;
                }
            }
        });
    }


    private void goToNotFoundFragment() {
        try {
            HomeFragmentDirections.HomeToNotFound action = HomeFragmentDirections.homeToNotFound(mViewModel.getArticleDb());
            if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.homeFragment) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goToDetailFragment() {
        try {
            HomeFragmentDirections.HomeToDetail action = HomeFragmentDirections.homeToDetail(mViewModel.getArticleAnalysis());
            if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.homeFragment) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            searchByEan();
        } else {
            searchWithScanner();
        }
    }

    private void searchWithScanner() {
        scanButton.show();
        eanInput.setVisibility(View.GONE);
    }

    private void searchByEan() {
        scanButton.hide();
        eanInput.setFocusable(true);
        eanInput.requestFocus();
        eanInput.setVisibility(View.VISIBLE);
//        mSwitch.setFocusable(false);
    }

    private void searchByName() {
        Toast.makeText(getContext(), "NAME SEARHC", Toast.LENGTH_SHORT).show();
    }


    private void setEanInputListenerToHandleEnterKey() {
        eanEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyevent) {
                return handleEnter(keyCode, keyevent);
            }
        });
    }

    private boolean handleEnter(int keyCode, KeyEvent keyevent) {
        if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            mViewModel.findArticle(eanEditText.getText().toString());
            eanEditText.setText("");
            return true;
        }
        return false;
    }

}
