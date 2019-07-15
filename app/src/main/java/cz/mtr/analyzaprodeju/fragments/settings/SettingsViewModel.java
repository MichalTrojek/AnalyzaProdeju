package cz.mtr.analyzaprodeju.fragments.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.regex.Pattern;

import cz.mtr.analyzaprodeju.models.Model;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mIpAddress = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();
    private MutableLiveData<String> mLogin = new MutableLiveData<>();


    private final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    public SettingsViewModel() {

    }


    public void setIpAddress(String ip) {
        Model.getInstance().getPrefs().setIp(ip);
    }

    public void setPassword(String pass) {
        Model.getInstance().getPrefs().setPassword(pass);
    }

    public void setLogin(String login) {
        Model.getInstance().getPrefs().setLogin(login);
    }


    public LiveData<String> getIpAddress() {
        mIpAddress.setValue(Model.getInstance().getPrefs().getIp());
        return mIpAddress;
    }

    public LiveData<String> getPassword() {
        if (Model.getInstance().getPrefs().getPassword().isEmpty()) {
            mPassword.setValue("Vložte eshop heslo");
        } else {
            mPassword.setValue(hidePassword(Model.getInstance().getPrefs().getPassword().length()));
        }
        return mPassword;
    }

    public LiveData<String> getLogin() {
        if (Model.getInstance().getPrefs().getLogin().isEmpty()) {
            mLogin.setValue("Vložte eshop login");
        } else {

            mLogin.setValue(Model.getInstance().getPrefs().getLogin());
        }
        return mLogin;
    }

    private String hidePassword(int i) {
        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            sb.append("*");
            i--;
        }
        return sb.toString();
    }


    public boolean validateIpAddress(String ip) {
        return PATTERN.matcher(ip).matches();
    }


}
