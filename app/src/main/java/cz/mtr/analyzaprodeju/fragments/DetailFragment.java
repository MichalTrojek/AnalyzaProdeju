package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.ViewModel.DetailViewModel;
import cz.mtr.analyzaprodeju.room.Article;

public class DetailFragment extends Fragment {
    private static final String TAG = DetailFragment.class.getSimpleName();
    private TextView nameTextView, eanTextView, priceTextView, rankTextView, eshopTextView, salesTextView, salesAmountOneTextView, salesDaysOneTextView, salesAmountTwoTextView, salesDaysTwoTextView, lastSaleTextView,
            storedTextView, daysOfSuppliesTextView, lastDeliveryTextView, supplierTextView, releasedTextView, locationsTextView;

    private DetailViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setupTextViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mViewModel.getArticle(DetailFragmentArgs.fromBundle(getArguments()).getMessage()).observe(getViewLifecycleOwner(), new Observer<Article>() {
            @Override
            public void onChanged(Article article) {
                eanTextView.setText(article.getEan());
                nameTextView.setText(article.getName());
                priceTextView.setText(article.getPrice());
            }
        });
    }


    private void setupTextViews(View view) {
        nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        eanTextView = (TextView) view.findViewById(R.id.eanTextView);
        priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        rankTextView = (TextView) view.findViewById(R.id.rankTextView);
        eshopTextView = (TextView) view.findViewById(R.id.eshopTextView);
        salesTextView = (TextView) view.findViewById(R.id.salesTextView);
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

}
