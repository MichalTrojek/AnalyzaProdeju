package cz.mtr.analyzaprodeju.fragments.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.Normalizer;
import java.util.ArrayList;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.repository.room.ItemFts;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private TextView mSearchInput;
    private RecyclerView mRecyclerView;
    private SearchItemAdapter mAdapter;
    private ArrayList<ItemFts> mDataSet = new ArrayList<>();


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        mSearchInput = view.findViewById(R.id.searchInput);
        mRecyclerView = view.findViewById(R.id.recyclerViewSearch);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchItemAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                mDataSet.clear();
                String normalizedInput = Normalizer.normalize(editable.toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
                mViewModel.searchByName(normalizedInput);
                mDataSet.addAll(mViewModel.searchByName(normalizedInput));
                mAdapter.setSearchItems(mDataSet);

            }
        });
    }

}
