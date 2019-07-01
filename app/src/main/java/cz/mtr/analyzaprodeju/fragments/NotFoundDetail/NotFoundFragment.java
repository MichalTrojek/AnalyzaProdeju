package cz.mtr.analyzaprodeju.fragments.NotFoundDetail;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class NotFoundFragment extends Fragment implements View.OnClickListener {

    private NotFoundViewModel mViewModel;
    private TextView mNameTextView, mEanTextView, mPriceTextView;
    private FloatingActionButton addFob, returnFob, orderFob;
    private Animation fab_open, fab_close, fab_rotate_anticlock, fab_rotate_clock;
    private EditText ordersEditText, returnsEditText;
    private boolean isOpen = false;
    private SharedArticle selectedArticle;

    public static NotFoundFragment newInstance() {
        return new NotFoundFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.not_found_fragment, container, false);
        mNameTextView = view.findViewById(R.id.nameTextView);
        mEanTextView = view.findViewById(R.id.eanTextView);
        mPriceTextView = view.findViewById(R.id.priceTextView);
        addFob = view.findViewById(R.id.addFab);
        returnFob = view.findViewById(R.id.returnFab);
        orderFob = view.findViewById(R.id.orderFab);
        addFob.setOnClickListener(this);
        returnFob.setOnClickListener(this);
        orderFob.setOnClickListener(this);

        ordersEditText = view.findViewById(R.id.ordersInput);
        returnsEditText = view.findViewById(R.id.returnsInput);
        ordersEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        returnsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return view;
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
