package cz.mtr.analyzaprodeju.fragments.ftp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.Article;


public class FtpViewModel extends AndroidViewModel {


    private DataRepository mRepository;

    public FtpViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }


    public void insertAll(List<Article> items) {
        mRepository.insertAll(items);
    }

}
