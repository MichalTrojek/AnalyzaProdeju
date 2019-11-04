package cz.mtr.analyzaprodeju.fragments.about;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class AboutViewModel extends AndroidViewModel {

    private MutableLiveData<String> mDbVersion = new MutableLiveData<>();

    public AboutViewModel(@NonNull Application application) {
        super(application);
        Date storeDataTimestamp = new Date(GeneralPreferences.getInstance().loadStoreDataUpdateTime());
        Date analysisDataTimestamp = new Date(GeneralPreferences.getInstance().loadAnalysisUpdatedTime());

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm");


        mDbVersion.setValue(String.format("Aktualizace ze dne:\nAnalýza: %s\nStavy: %s", df.format(analysisDataTimestamp), df.format(storeDataTimestamp)));

    }

    public LiveData<String> getDataInfo() {
        return this.mDbVersion;
    }


}

