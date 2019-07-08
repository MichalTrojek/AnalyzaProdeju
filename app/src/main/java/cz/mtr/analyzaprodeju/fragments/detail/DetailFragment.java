package cz.mtr.analyzaprodeju.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.models.datastructures.DisplayableArticle;

public class DetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = DetailFragment.class.getSimpleName();
    private TextView nameTextView, eanTextView, priceTextView, rankTextView, eshopTextView, revenueTextView, salesAmountOneTextView, salesDaysOneTextView, salesAmountTwoTextView, salesDaysTwoTextView, lastSaleTextView,
            storedTextView, daysOfSuppliesTextView, lastDeliveryTextView, supplierTextView, releasedTextView, locationsTextView;
    private FloatingActionButton addFob, returnFob, orderFob;
    private Animation fab_open, fab_close, fab_rotate_anticlock, fab_rotate_clock;
    private EditText ordersEditText, returnsEditText;
    private DisplayableArticle selectedArticle;
    private DetailViewModel mViewModel;
    private boolean isOpen = false;
    private ScrollView scrollView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setupTextViews(view);
        addFob = view.findViewById(R.id.addFab);
        returnFob = view.findViewById(R.id.returnFab);
        orderFob = view.findViewById(R.id.orderFab);
        addFob.setOnClickListener(this);
        returnFob.setOnClickListener(this);
        orderFob.setOnClickListener(this);

        scrollView2 = view.findViewById(R.id.scrollView2);
        ordersEditText = view.findViewById(R.id.ordersInput);
        returnsEditText = view.findViewById(R.id.returnsInput);
        ordersEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        returnsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
                rankTextView.setText(displayableArticle.getRanking());
                eshopTextView.setText(displayableArticle.getRankingEshop());
                revenueTextView.setText(displayableArticle.getRevenue() + ",- Kč");
                salesAmountOneTextView.setText(displayableArticle.getSales1() + "ks");
                salesDaysOneTextView.setText("Za " + displayableArticle.getSales1Days() + " dnů");
                salesAmountTwoTextView.setText(displayableArticle.getSales2() + "ks");
                salesDaysTwoTextView.setText("Za " + displayableArticle.getSales2Days() + " dnů");
                lastSaleTextView.setText(displayableArticle.getDateOfLastSale());
                storedTextView.setText(displayableArticle.getStored() + "ks");
                daysOfSuppliesTextView.setText(displayableArticle.getDaysOfSupplies() + " dnů");
                lastDeliveryTextView.setText(displayableArticle.getDateOfLastDelivery());
                supplierTextView.setText(String.format("%s (%s)", displayableArticle.getSupplier(), deliveredAs(displayableArticle)));
                releasedTextView.setText(displayableArticle.getReleaseDate());
                locationsTextView.setText(displayableArticle.getLocation());
            }
        });

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fab_rotate_anticlock = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_anticlock);
        fab_rotate_clock = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_clock);

    }

    private String deliveredAs(DisplayableArticle displayableArticle) {
        if (displayableArticle.getCommision().equals("1514")) {
            return "Komise";
        } else {
            return "Pevno";
        }
    }


    private void setupTextViews(View view) {
        nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        eanTextView = (TextView) view.findViewById(R.id.eanTextView);
        priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        rankTextView = (TextView) view.findViewById(R.id.rankTextView);
        eshopTextView = (TextView) view.findViewById(R.id.eshopTextView);
        revenueTextView = (TextView) view.findViewById(R.id.revenueTextView);
        salesAmountOneTextView = (TextView) view.findViewById(R.id.salesAmountOneTextView);
        salesDaysOneTextView = (TextView) view.findViewById(R.id.salesDaysOneTextView);
        salesAmountTwoTextView = (TextView) view.findViewById(R.id.salesAmountTwoTextView);
        salesDaysTwoTextView = (TextView) view.findViewById(R.id.salesDaysTwoTextView);
        lastSaleTextView = (TextView) view.findViewById(R.id.lastSaleTextView);
        storedTextView = (TextView) view.findViewById(R.id.storedTextView);
        daysOfSuppliesTextView = (TextView) view.findViewById(R.id.daysOfSuppliesTextView);
        lastDeliveryTextView = (TextView) view.findViewById(R.id.lastDeliveryTextView);
        supplierTextView = (TextView) view.findViewById(R.id.supplierTextView);
        releasedTextView = (TextView) view.findViewById(R.id.releasedTextView);
        locationsTextView = (TextView) view.findViewById(R.id.locationsTextView);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addFab:
                handleAddClick();
                break;
            case R.id.orderFab:
                handleOrdersClick();
                break;
            case R.id.returnFab:
                handleReturnsClick();
                break;
        }

    }

    private void handleAddClick() {
        if (isOpen) {
            close();
        } else {
            open();
        }
    }

    private void close() {
        addFob.startAnimation(fab_rotate_anticlock);
        returnFob.startAnimation(fab_close);
        orderFob.startAnimation(fab_close);
        returnFob.setClickable(false);
        orderFob.setClickable(false);
        ordersEditText.setVisibility(View.INVISIBLE);
        returnsEditText.setVisibility(View.INVISIBLE);
        isOpen = false;
        scrollView2.setVisibility(View.VISIBLE);
        Model.getInstance().saveOrdersAndReturns();
    }

    private void open() {
        addFob.startAnimation(fab_rotate_clock);
        returnFob.startAnimation(fab_open);
        orderFob.startAnimation(fab_open);
        returnFob.setClickable(true);
        orderFob.setClickable(true);
        ordersEditText.setVisibility(View.VISIBLE);
        returnsEditText.setVisibility(View.VISIBLE);
        ordersEditText.getText().clear();
        returnsEditText.getText().clear();
        isOpen = true;
        scrollView2.setVisibility(View.INVISIBLE);

    }

    private void handleOrdersClick() {
        mViewModel.saveArticleAndAmountOrders(selectedArticle, ordersEditText.getText().toString());
        close();
        closeKeyboard();
    }

    private void handleReturnsClick() {
        mViewModel.saveArticleAndAmountReturns(selectedArticle, returnsEditText.getText().toString());
        closeKeyboard();
        close();
    }

    private void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


}
