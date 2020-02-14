package cz.mtr.analyzaprodeju.fragments.detail;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLargerImage;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.models.datastructures.DisplayableArticle;

public class DetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = DetailFragment.class.getSimpleName();
    private TextView nameTextView, eanTextView, priceTextView, rankTextView, eshopTextView, revenueTextView, salesAmountOneTextView, salesDaysOneTextView,
            salesAmountTwoTextView, salesDaysTwoTextView, lastSaleTextView, storedTextView, daysOfSuppliesTextView, lastDeliveryTextView, supplierTextView,
            releasedTextView, locationsTextView, authorTextView, mReturnsLabelTextView, mOrdersLabelTextView, mDontOrderLabelTextView;
    private FloatingActionButton addFob, returnFob, orderFob;
    private Animation fab_open, fab_close, fab_rotate_anticlock, fab_rotate_clock;
    private EditText ordersEditText;
    private DisplayableArticle selectedArticle;
    private DetailViewModel mViewModel;
    private boolean isOpen = false;
    private ScrollView scrollView2;
    private Button mSaveButton, mBackButton;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public DetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setupTextViews(view);
        addFob = view.findViewById(R.id.addFab);
        returnFob = view.findViewById(R.id.returnFab);
        orderFob = view.findViewById(R.id.orderFab);

        returnFob.setFocusable(false);
        orderFob.setFocusable(false);

        addFob.setOnClickListener(this);
        returnFob.setOnClickListener(this);
        orderFob.setOnClickListener(this);
        mSaveButton = view.findViewById(R.id.saveButtonDetail);
        mSaveButton.setOnClickListener(this);

        mBackButton = view.findViewById(R.id.backButtonDetail);
        mBackButton.setOnClickListener(this);

        scrollView2 = view.findViewById(R.id.scrollView2);
        ordersEditText = view.findViewById(R.id.ordersInput);
        ordersEditText.addTextChangedListener(changeTextWatcher);

        ordersEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);


        mImageView = view.findViewById(R.id.itemPreview);
        mProgressBar = view.findViewById(R.id.imageProgress);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mViewModel.getArticle(DetailFragmentArgs.fromBundle(getArguments()).getArticleAnalys()).observe(getViewLifecycleOwner(), new Observer<DisplayableArticle>() {
            @Override
            public void onChanged(DisplayableArticle displayableArticle) {
                selectedArticle = displayableArticle;
                nameTextView.setText(displayableArticle.getName());
                eanTextView.setText(displayableArticle.getEan());
                priceTextView.setText(displayableArticle.getPrice() + ",- Kč");
                rankTextView.setText(displayableArticle.getRanking() + ".");
                eshopTextView.setText(displayableArticle.getRankingEshop() + ".");
                revenueTextView.setText(displayableArticle.getRevenue() + ",- Kč");
                salesAmountOneTextView.setText(displayableArticle.getSales1() + "ks");
                salesDaysOneTextView.setText("ZA " + displayableArticle.getSales1Days() + " DNŮ");
                salesAmountTwoTextView.setText(displayableArticle.getSales2() + "ks");
                salesDaysTwoTextView.setText("ZA " + displayableArticle.getSales2Days() + " DNŮ");
                lastSaleTextView.setText(displayableArticle.getDateOfLastSale());
                storedTextView.setText(displayableArticle.getStored() + "ks");
                daysOfSuppliesTextView.setText(displayableArticle.getDaysOfSupplies() + " dnů");
                lastDeliveryTextView.setText(displayableArticle.getDateOfLastDelivery());
                supplierTextView.setText(String.format("%s (%s)", displayableArticle.getSupplier(), deliveredAs(displayableArticle)));
                releasedTextView.setText("Vydáno: " + displayableArticle.getReleaseDate());
                locationsTextView.setText(displayableArticle.getLocation());
                authorTextView.setText("Autor: " + displayableArticle.getAuthor());
                mDontOrderLabelTextView.setText(displayableArticle.getDontOrder());
                mViewModel.loadImage(eanTextView.getText().toString(), mImageView, mProgressBar);
            }
        });


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLargerImage dialog = new DialogLargerImage();
                dialog.setCancelable(true);
                dialog.show(getFragmentManager(), "DialogLargerImage");

            }
        });

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fab_rotate_anticlock = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_anticlock);
        fab_rotate_clock = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_clock);

    }


    private String deliveredAs(DisplayableArticle displayableArticle) {
        String name = displayableArticle.getCommision();
        if (name.equals("1514")) {
            return "KOMISE";
        } else if (name.equals("neni")) {
            return "PRODUKT NEBYL V ANALÝZE NALEZEN";
        } else {
            return "PEVNO";
        }
    }


    private void setupTextViews(View view) {
        nameTextView = view.findViewById(R.id.scaperNameTextView);
        eanTextView = view.findViewById(R.id.eanTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        rankTextView = view.findViewById(R.id.rankTextView);
        eshopTextView = view.findViewById(R.id.eshopTextView);
        revenueTextView = view.findViewById(R.id.amountTextView);
        salesAmountOneTextView = view.findViewById(R.id.salesAmountOneTextView);
        salesDaysOneTextView = view.findViewById(R.id.salesDaysOneTextView);
        salesAmountTwoTextView = view.findViewById(R.id.salesAmountTwoTextView);
        salesDaysTwoTextView = view.findViewById(R.id.salesDaysTwoTextView);
        lastSaleTextView = view.findViewById(R.id.lastSaleTextView);
        storedTextView = view.findViewById(R.id.storedTextView);
        daysOfSuppliesTextView = view.findViewById(R.id.daysOfSuppliesTextView);
        lastDeliveryTextView = view.findViewById(R.id.lastDeliveryTextView);
        supplierTextView = view.findViewById(R.id.supplierTextView);
        releasedTextView = view.findViewById(R.id.releasedTextView);
        locationsTextView = view.findViewById(R.id.locationsTextView);
        authorTextView = view.findViewById(R.id.authorTextView);
        mReturnsLabelTextView = view.findViewById(R.id.returnsLabelTextView);
        mOrdersLabelTextView = view.findViewById(R.id.orderLabelTextView);
        mDontOrderLabelTextView = view.findViewById(R.id.dontOrderTextView);
    }


    private boolean isOrder;

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
            case R.id.saveButtonDetail:
                handleSaveButton();
                break;
            case R.id.backButtonDetail:
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
        scrollView2.setVisibility(View.VISIBLE);
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
        isOpen = false;
        scrollView2.setVisibility(View.VISIBLE);
    }

    private void showMoreButtons() {
        addFob.startAnimation(fab_rotate_clock);
        returnFob.startAnimation(fab_open);
        orderFob.startAnimation(fab_open);
        mOrdersLabelTextView.setVisibility(View.VISIBLE);
        mReturnsLabelTextView.setVisibility(View.VISIBLE);
        returnFob.show();
        orderFob.show();
        ordersEditText.getText().clear();
        isOpen = true;
        scrollView2.setVisibility(View.GONE);
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

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


}
