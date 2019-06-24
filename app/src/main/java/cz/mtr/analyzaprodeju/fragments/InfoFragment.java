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

    private TextView nameTextView, eanTextView, priceTextView, rankTextView, eshopTextView, salesTextView, salesAmountOneTextView, salesDaysOneTextView, salesAmountTwoTextView, salesDaysTwoTextView, lastSaleTextView,
            storedTextView, daysOfSuppliesTextView, lastDeliveryTextView, supplierTextView, releasedTextView, locationsTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        nameTextView.setText("Veselí");


        eanTextView = (TextView) view.findViewById(R.id.eanTextView);
        eanTextView.setText("9788026418573");


        priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        priceTextView.setText("199,- kč");


        rankTextView = (TextView) view.findViewById(R.id.rankTextView);
        rankTextView.setText("45");


        eshopTextView = (TextView) view.findViewById(R.id.eshopTextView);
        eshopTextView.setText("31");


        salesTextView = (TextView) view.findViewById(R.id.salesTextView);
        salesTextView.setText("34 000,- kč");

        salesAmountOneTextView = (TextView) view.findViewById(R.id.salesAmountOneTextView);
        salesAmountOneTextView.setText("45 ks");

        salesDaysOneTextView = (TextView) view.findViewById(R.id.salesDaysOneTextView);
        salesDaysOneTextView.setText("Za 31 dní");

        salesAmountTwoTextView = (TextView) view.findViewById(R.id.salesAmountTwoTextView);
        salesAmountTwoTextView.setText("433 ks");

        salesDaysTwoTextView = (TextView) view.findViewById(R.id.salesDaysTwoTextView);
        salesDaysTwoTextView.setText("Za 157 dní");


        lastSaleTextView = (TextView) view.findViewById(R.id.lastSaleTextView);
        lastSaleTextView.setText("17.06.2019");

        storedTextView = (TextView) view.findViewById(R.id.storedTextView);
        storedTextView.setText("357");

        daysOfSuppliesTextView = (TextView) view.findViewById(R.id.daysOfSuppliesTextView);
        daysOfSuppliesTextView.setText("35 dní");

        lastDeliveryTextView = (TextView) view.findViewById(R.id.lastDeliveryTextView);
        lastDeliveryTextView.setText("12.01.2019");

        supplierTextView = (TextView) view.findViewById(R.id.supplierTextView);
        supplierTextView.setText("EUROMEDIA");

        releasedTextView = (TextView) view.findViewById(R.id.releasedTextView);
        releasedTextView.setText("11.07.2019");

        locationsTextView = (TextView) view.findViewById(R.id.locationsTextView);
        locationsTextView.setText("005, 101, 102, 803, P02, P04, V13 ");

        return view;
    }


}
