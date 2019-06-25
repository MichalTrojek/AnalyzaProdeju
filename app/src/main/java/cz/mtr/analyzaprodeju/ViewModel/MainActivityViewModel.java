package cz.mtr.analyzaprodeju.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import cz.mtr.analyzaprodeju.models.MainActivityModel;

public class MainActivityViewModel extends AndroidViewModel {

    private MainActivityModel mModel;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mModel = MainActivityModel.getInstance();

    }


}
