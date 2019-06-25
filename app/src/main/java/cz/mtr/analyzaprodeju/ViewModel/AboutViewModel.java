package cz.mtr.analyzaprodeju.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.models.Model;

public class AboutViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mDbVersion = new MutableLiveData<>();

    public AboutViewModel(@NonNull Application application) {
        super(application);
        int version = Model.getInstance().getPrefs().getCurrentDatabaseVersion();
        mDbVersion.postValue(version);

    }

    public LiveData<Integer> getDbVersion() {
        return this.mDbVersion;
    }


}

