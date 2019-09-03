package cz.mtr.analyzaprodeju.fragments.about;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AboutViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mDbVersion = new MutableLiveData<>();

    public AboutViewModel(@NonNull Application application) {
        super(application);
        mDbVersion.postValue(1);

    }

    public LiveData<Integer> getDbVersion() {
        return this.mDbVersion;
    }


}

