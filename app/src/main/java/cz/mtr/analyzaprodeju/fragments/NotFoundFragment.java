package cz.mtr.analyzaprodeju.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class NotFoundFragment extends Fragment {

    private NotFoundViewModel mViewModel;
    private TextView mNameTextView, mEanTextView, mPriceTextView;


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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotFoundViewModel.class);
        mViewModel.getArticleDb(NotFoundFragmentArgs.fromBundle(getArguments()).getArticleDb()).observe(getViewLifecycleOwner(), new Observer<SharedArticle>() {
            @Override
            public void onChanged(SharedArticle article) {
                mNameTextView.setText("Název: " + article.getName());
                mEanTextView.setText("EAN: " + article.getEan());
                mPriceTextView.setText("CENA: " + article.getPrice() + ",- Kč");
            }
        });
    }


}
