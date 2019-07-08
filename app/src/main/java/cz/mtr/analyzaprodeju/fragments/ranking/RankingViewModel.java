package cz.mtr.analyzaprodeju.fragments.ranking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cz.mtr.analyzaprodeju.fragments.ranking.item.RankingItem;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.RankingRepository;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class RankingViewModel extends AndroidViewModel {
    private static final String TAG = RankingViewModel.class.getSimpleName();


    private LiveData<List<RankingItem>> mAllRankingItems;
    private MutableLiveData<String> mInfo = new MutableLiveData<>();
    private RankingRepository mRepository;
    private MutableLiveData<SharedArticle> mArticleAnalysis = new MutableLiveData<>();


    public RankingViewModel(@NonNull Application application) {
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

    public MutableLiveData<SharedArticle> getArticleAnalysis() {
        return mArticleAnalysis;
    }

    public void sendPosition(int position) {
        mArticleAnalysis.setValue(Model.getInstance().getAnalysis().get(mAllRankingItems.getValue().get(position).getEan()));
    }

}
