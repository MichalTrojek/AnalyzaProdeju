package cz.mtr.analyzaprodeju;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogDownloadDatabase;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.network.DatabaseDownloader;
import cz.mtr.analyzaprodeju.network.DatabaseVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private MutableLiveData<Boolean> updateFound = new MutableLiveData();
    private int onlineDbVersionNumber;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }


    public void checkForDatabaseUpdate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<DatabaseVersion> call = api.getDatabaseVersionInfo();

        call.enqueue(new Callback<DatabaseVersion>() {
            @Override
            public void onResponse(Call<DatabaseVersion> call, Response<DatabaseVersion> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Dotaz nebyl úspešný" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    DatabaseVersion version = response.body();
                    onlineDbVersionNumber = Integer.valueOf(version.getDatabaseVersion());
                    if (onlineDbVersionNumber > Model.getInstance().getPrefs().getCurrentDatabaseVersion()) {
                        if (isWifiEnabled()) {
                            updateFound.setValue(true);
                        } else {
                            Toast.makeText(getApplication(), "Je dostupná nová databáze, pro stažení se připojte k WIFI.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DatabaseVersion> call, Throwable t) {
                Toast.makeText(getApplication(), "Nepřipojeno k internetu", Toast.LENGTH_LONG).show();
            }
        });

    }


    public boolean isWifiEnabled() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        return false;
    }


    public void updateDatabase(DialogDownloadDatabase downloadingDialog) {
        try {
            DatabaseDownloader databaseDownloader = new DatabaseDownloader(getApplication(), onlineDbVersionNumber, downloadingDialog);
            databaseDownloader.download("http://www.skladovypomocnik.cz/BooksDatabase.db");
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Aktulalizace se nezdařila.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    public LiveData<Boolean> getUpdateFound() {
        return this.updateFound;
    }


    public interface Api {
        String BASE_URL = "http://skladovypomocnik.cz/";

        @GET("getdbversion.php")
        Call<DatabaseVersion> getDatabaseVersionInfo();
    }
}
