package cz.mtr.analyzaprodeju.fragments.ranking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cz.mtr.analyzaprodeju.fragments.ranking.other.rankingItem;
import cz.mtr.analyzaprodeju.repository.RankingRepository;

public class StoreRankingViewModel extends AndroidViewModel {


    private List<rankingItem> mAllRankingItems;
    private RankingRepository mRepository;
    private String mInfo;

    public StoreRankingViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RankingRepository();
        mAllRankingItems = mRepository.getStoreListOfItems();
    }


    public List<rankingItem> getAllItems() {
        return mAllRankingItems;
    }

    public String getInfo() {
        mInfo = mRepository.getInfo();
        return mInfo;
    }


}
