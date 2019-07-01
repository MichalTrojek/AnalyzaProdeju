package cz.mtr.analyzaprodeju.fragments.ranking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.ranking.adapter.ItemAdapter;

public class StoreRankingFragment extends Fragment {

    private StoreRankingViewModel mViewModel;
    private RecyclerView mStoreRecyclerView;
    private ItemAdapter mAdapter;
    private TextView mInfoTextView;


    public static StoreRankingFragment newInstance() {
        return new StoreRankingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storeranking_fragment, container, false);
        mStoreRecyclerView = view.findViewById(R.id.storeRecyclerView);
        mInfoTextView = view.findViewById(R.id.infoTextView);
        mStoreRecyclerView.setHasFixedSize(true);
        mStoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ItemAdapter();
        mStoreRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StoreRankingViewModel.class);
        mAdapter.setRankingItems(mViewModel.getAllItems());
        mInfoTextView.setText(mViewModel.getInfo());
    }

}
