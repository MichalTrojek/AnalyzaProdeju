package cz.mtr.analyzaprodeju.fragments.ranking;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cz.mtr.analyzaprodeju.Interfaces.OnItemClickListener;
import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.ranking.adapter.RankingItemAdapter;
import cz.mtr.analyzaprodeju.fragments.ranking.item.RankingItem;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class RankingFragment extends Fragment implements OnItemClickListener {
    private static final String TAG = RankingFragment.class.getSimpleName();
    private RankingViewModel mViewModel;
    private RecyclerView mStoreRecyclerView;
    private RankingItemAdapter mAdapter;
    private TextView mInfoTextView;


    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
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

        mViewModel = ViewModelProviders.of(this).get(RankingViewModel.class);
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

        mViewModel.getArticleAnalysis().observe(getViewLifecycleOwner(), new Observer<SharedArticle>() {
            @Override
            public void onChanged(SharedArticle sharedArticle) {
                try {
                    RankingFragmentDirections.RankingToDetail action = RankingFragmentDirections.rankingToDetail(sharedArticle);
                    if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.rankingFragment) {
                        Navigation.findNavController(getView()).navigate(action);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        mViewModel.sendPosition(position);
    }


}
