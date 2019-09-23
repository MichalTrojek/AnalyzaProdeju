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
        storeName = convertNameToShortcut(cleanString(storeName));
        setSelectedStore(storeName);
        setPassword(password);
        new DownloadAnalysisFtpTask(mContext, storeName, password, fragmentManager).execute();
//        new DownloadStoreDataFtpTask(GeneralPreferences.getInstance().loadFilename(), password).execute();
    }

    private String cleanString(String selectedName) {
        String name = Normalizer.normalize(selectedName, Normalizer.Form.NFD);
        name = name.replaceAll("[^\\p{ASCII}]", "");
        return name;
    }

    public String convertNameToShortcut(String name) {
        String temp = "", storeNameFilename = ""; // storeNameFilename je název souboru na ftp, který obsahuje stav prodejny
        switch (name.toLowerCase().trim()) {
            case "praha - vaclavske namesti":
                temp = "VAC";
                storeNameFilename = "PHAVAC.CSV";
                break;
            case "praha - andel city":
                temp = "AND";
                storeNameFilename = "AND.CSV";
                break;
            case "praha - arkady pankrac":
                temp = "PAN";
                storeNameFilename = "PANKRAC.CSV";
                break;
            case "praha - od kotva":
                temp = "KOT";
                storeNameFilename = "KOTVA.CSV";
                break;
            case "praha - nc metrolope zlicin":
                temp = "ZLI";
                storeNameFilename = "ZLI.CSV";
                break;
            case "praha - oc letnany":
                temp = "LET";
                storeNameFilename = "LET.CSV";
                break;
            case "praha - dejvice":
                temp = "DEJ";
                storeNameFilename = "DEJ.CSV";
                break;
            case "praha - vivo! hostivar":
                temp = "VIV";
                storeNameFilename = "VIV.CSV";
                break;
            case "praha - nc eden":
                temp = "EDE";
                storeNameFilename = "EDE.CSV";
                break;
            case "brno - jostova":
                temp = "JOS";
                storeNameFilename = "JOS.CSV";
                break;
            case "brno - galerie vankovka":
                temp = "VAN";
                storeNameFilename = "VAN.CSV";
                break;
            case "ostrava - nova karolina":
                temp = "OSK";
                storeNameFilename = "KAROLINA.CSV";
                break;
            case "ostrava - oc galerie":
                temp = "OSG";
                storeNameFilename = "OSTRAGAL.CSV";
                break;
            case "ceske budejovice - igy centrum":
                temp = "IGY";
                storeNameFilename = "CBIGY.CSV";
                break;
            case "ceske budejovice - od prior":
                temp = "LAP";
                storeNameFilename = "PRIOR.CSV";
                break;
            case "hradec kralove - oc fontana":
                temp = "HRA";
                storeNameFilename = "HRADEC.CSV";
                break;
            case "karvina - tesco":
                temp = "KAR";
                storeNameFilename = "KAR.CSV";
                break;
            case "kolin - oc futurum":
                temp = "KOL";
                storeNameFilename = "KOLIN.CSV";
                break;
            case "liberec - oc nisa":
                temp = "LIN";
                storeNameFilename = "NISA.CSV";
                break;
            case "liberec - ng plaza":
                temp = "LIB";
                storeNameFilename = "LIBEREC.CSV";
                break;
            case "most - central":
                temp = "MOS";
                storeNameFilename = "MOS.CSV";
                break;
            case "olomouc - galerie santovka":
                temp = "SAN";
                storeNameFilename = "OLOSAN.CSV";
                break;
            case "olomouc - city":
                temp = "OLC";
                storeNameFilename = "OCITY.CSV";
                break;
            case "opava - oc silesia":
                temp = "OPG";
                storeNameFilename = "OPG.CSV";
                break;
            case "pardubice- atrium palac":
                temp = "PAR";
                storeNameFilename = "PAR.CSV";
                break;
            case "plzen - namesti republiky":
                temp = "PLN";
                storeNameFilename = "PLN.CSV";
                break;
            case "plzen - sedlackova":
                temp = "PLS";
                storeNameFilename = "PLSEDL.CSV";
                break;
            case "plzen - oc plzen":
                temp = "PLG";
                storeNameFilename = "PLOC.CSV";
                break;
            case "teplice - galerie teplice":
                temp = "TEP";
                storeNameFilename = "TEPLICEG.CSV";
                break;
            case "tachov - namesti republiky":
                temp = "TAC";
                storeNameFilename = "TACHOV.CSV";
                break;
            case "usti nad labem - forum":
                temp = "UST";
                storeNameFilename = "UNLFORUM.CSV";
                break;
        }
        GeneralPreferences.getInstance().saveFilename(storeNameFilename);
        return temp;
    }


}
