package cz.mtr.analyzaprodeju.fragments.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.ItemFts;

public class SearchViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private static final String TAG = SearchViewModel.class.getSimpleName();

    private DataRepository mRepository;
    private List<ItemFts> mList = new ArrayList<>();


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }


    public List<ItemFts> searchByName(String name) {
        mList = mRepository.searchByName(name);
        Log.d(TAG, mList.size() + " ");
        return mList;
    }
}
