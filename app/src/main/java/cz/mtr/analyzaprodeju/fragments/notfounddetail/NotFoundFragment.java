package cz.mtr.analyzaprodeju.fragments.notfounddetail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class NotFoundFragment extends Fragment implements View.OnClickListener {

    private NotFoundViewModel mViewModel;
    private TextView mNameTextView, mEanTextView, mPriceTextView, textView, mOrdersLabelTextView, mReturnsLabelTextView;
    private FloatingActionButton addFob, returnFob, orderFob;
    private Animation fab_open, fab_close, fab_rotate_anticlock, fab_rotate_clock;
    private EditText ordersEditText;
    private boolean isOpen = false;
    private SharedArticle selectedArticle;
    private Button mBackButton, mSaveButton;
    private CardView mInfoCardView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notfound, container, false);
        mNameTextView = view.findViewById(R.id.scaperNameTextView);
        mEanTextView = view.findViewById(R.id.eanTextView);

        mPriceTextView = view.findViewById(R.id.priceTextView);
        addFob = view.findViewById(R.id.addFab);
        returnFob = view.findViewById(R.id.returnFab);
        orderFob = view.findViewById(R.id.orderFab);
        addFob.setOnClickListener(this);
        returnFob.setOnClickListener(this);
        orderFob.setOnClickListener(this);

        ordersEditText = view.findViewById(R.id.ordersInput);
        ordersEditText.addTextChangedListener(changeTextWatcher);

        ordersEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);


        mBackButton = view.findViewById(R.id.backButtonNotFound);
        mBackButton.setOnClickListener(this);

        mSaveButton = view.findViewById(R.id.saveButtonNotFound);
        mSaveButton.setOnClickListener(this);

        mInfoCardView = view.findViewById(R.id.infoCardViewNotFound);

        textView = view.findViewById(R.id.textView);
        mOrdersLabelTextView = view.findViewById(R.id.textView11);
        mReturnsLabelTextView = view.findViewById(R.id.TextView7);


        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotFoundViewModel.class);
        mViewModel.getArticleDb(NotFoundFragmentArgs.fromBundle(getArguments()).getArticleDb()).observe(getViewLifecycleOwner(), new Observer<SharedArticle>() {
            @Override
            public void onChanged(SharedArticle article) {
                selectedArticle = article;
                mNameTextView.setText("Název: " + article.getName());
                mEanTextView.setText("EAN: " + article.getEan());
                mPriceTextView.setText("CENA: " + article.getPrice() + ",- Kč");
            }
        });

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fab_rotate_anticlock = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_anticlock);
        fab_rotate_clock = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_clock);
    }

    boolean isOrder;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addFab:
                handleAddClick();
                break;
            case R.id.orderFab:
                handleOrdersAndReturnsClick();
                isOrder = true;
                break;
            case R.id.returnFab:
                handleOrdersAndReturnsClick();
                isOrder = false;
                break;
            case R.id.saveButtonNotFound:
                handleSaveButton();
                break;
            case R.id.backButtonNotFound:
                showDefaultState();
                break;
        }

    }

    private void handleSaveButton() {
        if (isOrder) {
            mViewModel.saveArticleAndAmountOrders(selectedArticle, ordersEditText.getText().toString().trim());
        } else {
            mViewModel.saveArticleAndAmountReturns(selectedArticle, ordersEditText.getText().toString().trim());
        }
        Toast.makeText(getContext(), "Uloženo", Toast.LENGTH_SHORT).show();
        Model.getInstance().saveOrdersAndReturns();
        showDefaultState();
    }

    private void showDefaultState() {
        ordersEditText.setVisibility(View.GONE);
        mBackButton.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        mInfoCardView.setVisibility(View.VISIBLE);
        closeKeyboard();
        addFob.show();
    }

    private void handleOrdersAndReturnsClick() {

        showAmountInputAndSaveButton();

    }

    private void showAmountInputAndSaveButton() {
        hideAddButtons();
        mSaveButton.setVisibility(View.VISIBLE);
        mBackButton.setVisibility(View.VISIBLE);
        ordersEditText.setVisibility(View.VISIBLE);


    }

    private void hideAddButtons() {
        addFob.startAnimation(fab_rotate_anticlock);
        returnFob.startAnimation(fab_close);
        orderFob.startAnimation(fab_close);
        ordersEditText.setVisibility(View.GONE);
        mOrdersLabelTextView.setVisibility(View.GONE);
        mReturnsLabelTextView.setVisibility(View.GONE);
        addFob.hide();
        isOpen = false;
    }


    private void handleAddClick() {
        if (isOpen) {
            hideMoreButtons();
        } else {
            showMoreButtons();
        }
    }

    private void hideMoreButtons() {
        addFob.startAnimation(fab_rotate_anticlock);
        returnFob.startAnimation(fab_close);
        orderFob.startAnimation(fab_close);
        ordersEditText.setVisibility(View.GONE);
        mOrdersLabelTextView.setVisibility(View.GONE);
        mReturnsLabelTextView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        mInfoCardView.setVisibility(View.VISIBLE);
        isOpen = false;

    }

    private void showMoreButtons() {
        addFob.startAnimation(fab_rotate_clock);
        returnFob.startAnimation(fab_open);
        orderFob.startAnimation(fab_open);

        returnFob.show();
        orderFob.show();
        ordersEditText.getText().clear();
        isOpen = true;
        mOrdersLabelTextView.setVisibility(View.VISIBLE);
        mReturnsLabelTextView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        mInfoCardView.setVisibility(View.GONE);
    }


    private void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private TextWatcher changeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amountInput = ordersEditText.getText().toString().trim();
            mSaveButton.setEnabled(!amountInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
