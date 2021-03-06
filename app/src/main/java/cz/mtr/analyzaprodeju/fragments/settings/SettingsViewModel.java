package cz.mtr.analyzaprodeju.fragments.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.regex.Pattern;

import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mIpAddress = new MutableLiveData<>();



    private final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    public SettingsViewModel() {

    }


    public void setIpAddress(String ip) {
        GeneralPreferences.getInstance().saveIp(ip);
    }



    public LiveData<String> getIpAddress() {
        mIpAddress.setValue(GeneralPreferences.getInstance().loadIp());
        return mIpAddress;
    }







    public boolean validateIpAddress(String ip) {
        return PATTERN.matcher(ip).matches();
    }


}
