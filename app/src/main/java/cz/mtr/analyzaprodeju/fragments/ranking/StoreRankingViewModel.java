package cz.mtr.analyzaprodeju.fragments.ranking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cz.mtr.analyzaprodeju.fragments.ranking.other.RankingItem;
import cz.mtr.analyzaprodeju.repository.RankingRepository;

public class StoreRankingViewModel extends AndroidViewModel {


    private LiveData<List<RankingItem>> mAllRankingItems;
    private MutableLiveData<String> mInfo = new MutableLiveData<>();
    private RankingRepository mRepository;


    public StoreRankingViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RankingRepository();
        mAllRankingItems = mRepository.getAllRankingItems();
    }


    public LiveData<List<RankingItem>> getAllItems() {
        return mAllRankingItems;
    }

    public MutableLiveData<String> getInfo() {
        mInfo.setValue(mRepository.getInfo());
        return mInfo;
    }


}
