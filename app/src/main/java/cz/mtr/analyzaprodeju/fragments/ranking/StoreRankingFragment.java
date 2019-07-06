package cz.mtr.analyzaprodeju.fragments.ranking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.ranking.adapter.RankingItemAdapter;
import cz.mtr.analyzaprodeju.fragments.ranking.other.RankingItem;

public class StoreRankingFragment extends Fragment implements RankingItemAdapter.OnItemClickListener {

    private StoreRankingViewModel mViewModel;
    private RecyclerView mStoreRecyclerView;
    private RankingItemAdapter mAdapter;
    private TextView mInfoTextView;


    public static StoreRankingFragment newInstance() {
        return new StoreRankingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storeranking_fragment, container, false);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        mStoreRecyclerView = view.findViewById(R.id.storeRecyclerView);
        mInfoTextView = view.findViewById(R.id.infoTextView);
        mStoreRecyclerView.setHasFixedSize(true);
        mStoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RankingItemAdapter(this);
        mStoreRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(StoreRankingViewModel.class);
        mViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<RankingItem>>() {
            @Override
            public void onChanged(List<RankingItem> rankingItems) {
                mAdapter.setRankingItems(rankingItems);
            }
        });
        mViewModel.getInfo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mInfoTextView.setText(s);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "ean " + mViewModel.getAllItems().getValue().get(position).getEan(), Toast.LENGTH_SHORT).show();
    }
}
