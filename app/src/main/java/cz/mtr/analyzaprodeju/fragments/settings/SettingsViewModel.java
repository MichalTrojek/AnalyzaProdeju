package cz.mtr.analyzaprodeju.fragments.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cz.mtr.analyzaprodeju.models.Model;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mIpAddress = new MutableLiveData<>();


    public SettingsViewModel() {

    }


    public void setIpAddress(String ip) {
        Model.getInstance().getPrefs().setIp(ip);
    }


    public LiveData<String> getIpAdress() {
        mIpAddress.setValue(Model.getInstance().getPrefs().getIp());
        return mIpAddress;
    }
}
