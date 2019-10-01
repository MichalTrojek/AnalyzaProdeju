package cz.mtr.analyzaprodeju.fragments.ftp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.Normalizer;
import java.util.List;

import cz.mtr.analyzaprodeju.fragments.ftp.analysis.DownloadAnalysisFtpTask;
import cz.mtr.analyzaprodeju.fragments.ftp.storedata.DownloadStoreDataFtpTask;
import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;
import cz.mtr.analyzaprodeju.repository.room.Article;


public class FtpViewModel extends AndroidViewModel {

    private final static String TAG = FtpViewModel.class.getSimpleName();
    private DataRepository mRepository;
    private Context mContext;
    private MutableLiveData<String> mPassword = new MutableLiveData<>();
    private MutableLiveData<Integer> mLastSelectedItemIndex = new MutableLiveData<>();
    private MutableLiveData<String> filenameOfStore = new MutableLiveData<>();
    

    public FtpViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getBaseContext();
        mRepository = new DataRepository(application);
    }


    public void insertAll(List<Article> items) {
        mRepository.insertAll(items);
    }


    public void setPassword(String pass) {
        GeneralPreferences.getInstance().savePassword(pass);
    }


    public LiveData<String> getPassword() {
        if (GeneralPreferences.getInstance().loadPassword().isEmpty()) {
            mPassword.setValue("");
        } else {
            mPassword.setValue(GeneralPreferences.getInstance().loadPassword());
        }
        return mPassword;
    }

    public void setIndexOfLastSelectedItem(int i) {
        GeneralPreferences.getInstance().saveLastSelectedItem(i);
    }

    public LiveData<Integer> getIndexOfLastSelectedItem() {
        mLastSelectedItemIndex.setValue(GeneralPreferences.getInstance().loadIndexOfLastSelectedItem());
        return mLastSelectedItemIndex;
    }

    public void setSelectedStore(String name) {
        GeneralPreferences.getInstance().saveSelectedStore(name);
    }


    public MutableLiveData<String> getStoreFileName() {
        filenameOfStore.setValue(GeneralPreferences.getInstance().loadFilename());
        return filenameOfStore;
    }

    public void onDownloadDataClick(String storeName, String password, FragmentManager fragmentManager) {
        storeName = new NameConverter().convertNameToShortcut(cleanString(storeName));
        setSelectedStore(storeName);
        setPassword(password);
        new DownloadAnalysisFtpTask(mContext, storeName, password, fragmentManager).execute();
        new DownloadStoreDataFtpTask(GeneralPreferences.getInstance().loadFilename(), password).execute();
    }


    private String cleanString(String selectedName) {
        String name = Normalizer.normalize(selectedName, Normalizer.Form.NFD);
        name = name.replaceAll("[^\\p{ASCII}]", "");
        return name;
    }


}
