package cz.mtr.analyzaprodeju.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;

import cz.mtr.analyzaprodeju.Interfaces.OnItemClickListener;
import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.repository.room.ItemFts;

public class SearchFragment extends Fragment implements OnItemClickListener {


    private static final String TAG = SearchFragment.class.getSimpleName();
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
        mAdapter = new SearchItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mViewModel.getStatus().observe(this, new Observer<SearchViewModel.SearchStatus>() {
            @Override
            public void onChanged(SearchViewModel.SearchStatus searchStatus) {
                hideKeyboard(mSearchInput);
                switch (searchStatus) {
                    case DATABASE:
                        goToNotFoundFragment();
                        break;
                    case ANALYSIS:
                        goToDetailFragment();
                        break;

                }
            }
        });

    }

    private void goToNotFoundFragment() {
        try {
            SearchFragmentDirections.SearchToNotFound action = SearchFragmentDirections.searchToNotFound(mViewModel.getArticleDb());
            if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.searchFragment) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void goToDetailFragment() {
        try {
            SearchFragmentDirections.SearchToDetail action = SearchFragmentDirections.searchToDetail(mViewModel.getArticleAnalysis());
            if (Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.searchFragment) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hideKeyboard(TextView editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    @Override
    public void onItemClick(int position) {
        mViewModel.findArticle(mDataSet.get(position).getEan());
    }
}
