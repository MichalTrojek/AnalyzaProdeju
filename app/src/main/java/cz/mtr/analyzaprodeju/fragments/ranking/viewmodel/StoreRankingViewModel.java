package cz.mtr.analyzaprodeju.fragments.ranking.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cz.mtr.analyzaprodeju.fragments.ranking.other.Item;
import cz.mtr.analyzaprodeju.repository.RankingRepository;

public class StoreRankingViewModel extends AndroidViewModel {


    private List<Item> mAllItems;
    private RankingRepository mRepository;
    private String mInfo;

    public StoreRankingViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RankingRepository();
        mAllItems = mRepository.getStoreListOfItems();
    }


    public List<Item> getAllItems() {
        return mAllItems;
    }

    public String getInfo() {
        mInfo = mRepository.getInfo();
        return mInfo;
    }


}
