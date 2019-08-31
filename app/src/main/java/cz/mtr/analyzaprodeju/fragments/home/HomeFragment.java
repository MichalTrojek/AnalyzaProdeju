package cz.mtr.analyzaprodeju.fragments.home;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.github.zagum.switchicon.SwitchIconView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.barcode.BarcodeViewModel;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private FloatingActionButton mScanButton;
    private TextInputLayout mEanInputLayout;
    private TextInputEditText mEanInputEditText;
    private HomeFragmentViewModel mViewModel;
    private BottomAppBar mBottomAppBar;
    private ImageButton mButtonName;
    private SwitchIconView switchIconView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupViews(view);
        setupViewModel();
        setEanInputListenerToHandleEnterKey();


        switchIconView = view.findViewById(R.id.switchIconView);
        switchIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchIconView.switchState();
                if(switchIconView.isIconEnabled()){
                    showSearchByEan();
                } else {
                    hideSearchByEan();
                }
            }
        });

        mButtonName = view.findViewById(R.id.byNameButton);
        mButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByName();
            }
        });
        mBottomAppBar = view.findViewById(R.id.bottomAppBar);

        return view;
    }


    private void setupViews(View view) {
        mScanButton = view.findViewById(R.id.scan_button);
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onScanButtonClicked();
            }
        });

        mEanInputLayout = view.findViewById(R.id.eanInput);
        mEanInputEditText = view.findViewById(R.id.eanEditText);

    }

    private void onScanButtonClicked() {
        if (Build.MODEL.toLowerCase().equals("pda")) {
            Toast.makeText(getContext(), "Na tomto zařízení je tato funkce vypnutá. Použíjte funkci hledat podle eanu a pak skenujte tlačítkem na zařízení.", Toast.LENGTH_LONG).show();
        } else {
            Navigation.createNavigateOnClickListener(R.id.homeToBarcode, null);
        }
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
    public void onStart(){
        super.onStart();
        if(switchIconView.isIconEnabled()){
            showSearchByEan();
        }
    }


    private void showSearchByEan() {
        mEanInputLayout.setFocusable(true);
        mEanInputLayout.requestFocus();
        mEanInputLayout.setVisibility(View.VISIBLE);
    }

    private void hideSearchByEan() {
        mEanInputLayout.requestFocus();
        mEanInputLayout.setVisibility(View.GONE);
    }

    private void searchByName() {
        moveToSearchFragment();
    }

    private void moveToSearchFragment(){
            NavHostFragment.findNavController(this).navigate(R.id.toSearch);
    }


    private void setEanInputListenerToHandleEnterKey() {
        mEanInputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyevent) {
                return handleEnter(keyCode, keyevent);
            }
        });
    }

    private boolean handleEnter(int keyCode, KeyEvent keyevent) {
        if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            mViewModel.findArticle(mEanInputEditText.getText().toString());
            mEanInputEditText.setText("");
            return true;
        }
        return false;
    }

}
