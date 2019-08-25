package cz.mtr.analyzaprodeju.fragments.ftp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.Article;


public class FtpViewModel extends AndroidViewModel {

    private final static String TAG = FtpViewModel.class.getSimpleName();
    private DataRepository mRepository;
    private Context mContext;

    public FtpViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        mRepository = new DataRepository(application);
    }


    public void insertAll(List<Article> items) {
        mRepository.insertAll(items);
    }


    public String convertNameToShortcut(String name) {
        String temp = "";
        switch (name) {
            case "Praha - Vaclavske Namesti":
                temp = "VAN";
                break;
        }
        return temp;
    }


}
