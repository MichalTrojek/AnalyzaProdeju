package cz.mtr.analyzaprodeju.fragments.ftp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cz.mtr.analyzaprodeju.models.Model;
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
        switch (name.toLowerCase().trim()) {
            case "praha - vaclavske namesti":
                temp = "VAC";
                break;
            case "praha - andel city":
                temp = "AND";
                break;
            case "praha - arkady pankrac":
                temp = "PAN";
                break;
            case "praha - od kotva":
                temp = "KOT";
                break;
            case "praha - nc metrolope zlicin":
                temp = "ZLI";
                break;
            case "praha - oc letnany":
                temp = "LET";
                break;
            case "praha - dejvice":
                temp = "DEJ";
                break;
            case "praha - vivo! hostivar":
                temp = "VIV";
                break;
            case "praha - nc eden":
                temp = "EDE";
                break;
            case "brno - jostova":
                temp = "JOS";
                break;
            case "brno - galerie vankovka":
                temp = "VAN";
                break;
            case "ostrava - nova karolina":
                temp = "OSK";
                break;
            case "ostrava - oc galerie":
                temp = "OSG";
                break;
            case "ceske budejovice - igy centrum":
                temp = "IGY";
                break;
            case "ceske budejovice - od prior":
                temp = "LAP";
                break;
            case "hradec kralove - oc fontana":
                temp = "HRA";
                break;
            case "karvina - tesco":
                temp = "KAR";
                break;
            case "kolin - oc futurum":
                temp = "KOL";
                break;
            case "liberec - oc nisa":
                temp = "LIN";
                break;
            case "liberec - ng plaza":
                temp = "LIB";
                break;
            case "most - central":
                temp = "MOS";
                break;
            case "olomouc - galerie santovka":
                temp = "SAN";
                break;
            case "olomouc - city":
                temp = "OLC";
                break;
            case "opava - oc silesia":
                temp = "OPG";
                break;
            case "pardubice- atrium palac":
                temp = "PAR";
                break;
            case "plzen - namesti republiky":
                temp = "PLN";
                break;
            case "plzen - sedlackova":
                temp = "PLS";
                break;
            case "plzen - oc plzen":
                temp = "PLG";
                break;
            case "teplice - galerie teplice":
                temp = "TEP";
                break;
            case "tachov - namesti republiky":
                temp = "TAC";
                break;
            case "usti nad labem - forum":
                temp = "UST";
                break;
        }
        Log.d(TAG, name.toLowerCase().trim() + " temp " + temp);
        return temp;
    }

    public String getPassword() {
        return Model.getInstance().getPrefs().getPassword();
    }

    public void savePassword(String pass) {
        Model.getInstance().getPrefs().setPassword(pass);
    }

    public void setLastSelectedItem(int i) {
        Model.getInstance().getPrefs().setLastSelectedItem(i);
    }

    public int getLastSelectedItem() {
        return Model.getInstance().getPrefs().getLastSelectedItem();
    }

    public void setSelectedStore(String name) {
        Model.getInstance().getPrefs().setSelectedStore(name);
    }



}
