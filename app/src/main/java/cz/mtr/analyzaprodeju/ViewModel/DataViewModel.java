package cz.mtr.analyzaprodeju.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import cz.mtr.analyzaprodeju.repository.DataRepository;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<String> mName;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mName = mRepository.getName();
    }

    public LiveData<String> getName() {
        return mName;
    }


}
