package cz.mtr.analyzaprodeju;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import cz.mtr.analyzaprodeju.fragments.dialogs.PrinterDialog;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.network.Client;
import cz.mtr.analyzaprodeju.network.DatabaseDownloader;
import cz.mtr.analyzaprodeju.network.DatabaseVersion;
import cz.mtr.analyzaprodeju.repository.room.DatabaseCopier;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PrinterDialog.OnPrintClicked {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private Button mHamburgerButton;
    private NavigationView mNavigationView;
    private NavController mNavController;
    private ProgressBar downloadingDatabaseProgressBar;
    private AlertDialog downloadingDialog;
    private TextView downloadDatabaseTextview;
    private int onlineDbVersionNumber;

    public interface Api {

        String BASE_URL = "http://skladovypomocnik.cz/";

        @GET("getdbversion.php")
        Call<DatabaseVersion> getDatabaseVersionInfo();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseFromAssetsToWorkingDirectory();
        setupDrawerLayout();
        handleHamburgerButtonPress();

        mNavigationView.setCheckedItem(R.id.nav_home);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Model.getInstance().createPrefs(this);
    }

    private void updateDatabase() {
        try {

            DatabaseDownloader databaseDownloader = new DatabaseDownloader(this, onlineDbVersionNumber, downloadingDialog, downloadingDatabaseProgressBar);
            databaseDownloader.download("http://www.skladovypomocnik.cz/BooksDatabase.db");

        } catch (Exception e) {
            Toast.makeText(this, "Aktulalizace se nezdařila.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void askForPermission() {
        Log.d(TAG, "ASK FOR PERM");
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d(TAG, "version > 23");
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // you already have a permission
                checkForDatabaseUpdate();
                Log.d(TAG, "Checking for update");
            } else {
                // asks for permission
                Log.d(TAG, "asks for persmission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.d(TAG, "belowe 23");
        }
    }

    private void checkForDatabaseUpdate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<DatabaseVersion> call = api.getDatabaseVersionInfo();
        Log.d(TAG, "Inside checkForDatabaseUpdate");
        call.enqueue(new Callback<DatabaseVersion>() {
            @Override
            public void onResponse(Call<DatabaseVersion> call, Response<DatabaseVersion> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Dotaz nebyl úspešný");
                    Toast.makeText(getApplicationContext(), "Dotaz nebyl úspešný" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d(TAG, "Dotaz byl úspešný");
                    DatabaseVersion version = response.body();
                    onlineDbVersionNumber = Integer.valueOf(version.getDatabaseVersion());
                    if (onlineDbVersionNumber > Model.getInstance().getPrefs().getCurrentDatabaseVersion()) {
                        if (isWifiEnabled()) {
                            Log.d(TAG, "Wifi jo");
                            Log.d(TAG, Model.getInstance().getPrefs().getCurrentDatabaseVersion() + " vs olnine " + onlineDbVersionNumber);
                            updateFoundDialog(MainActivity.this);
                        } else {
                            Log.d(TAG, "Wifi ne");
                            Toast.makeText(getApplicationContext(), "Je dostupná nová databáze, pro stažení se připojte k WIFI.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DatabaseVersion> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                Toast.makeText(getApplicationContext(), "Nepřipojeno k internetu", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isWifiEnabled() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    private void updateFoundDialog(Context context) {
        new AlertDialog.Builder(context).setTitle(String.format("Aktualizace databaze (v.%d)", onlineDbVersionNumber)).setMessage("Je dostupná aktualizace databáze.").setPositiveButton("Aktualizovat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createDownloadingDatabaseDialog();
                updateDatabase();
            }
        }).setNegativeButton("Neaktualizovat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setIcon(R.drawable.ic_file_download).show();
    }

    private void createDownloadingDatabaseDialog() {
        View view = getLayoutInflater().inflate(R.layout.start_downloading_dialog, null);
        downloadingDatabaseProgressBar = view.findViewById(R.id.progressBar);
        downloadDatabaseTextview = (TextView) view.findViewById(R.id.loadingInfoTextView);
        downloadingDialog = new AlertDialog.Builder(this).setView(view).setCancelable(false).create();
    }


    private void copyDatabaseFromAssetsToWorkingDirectory() {
        new DatabaseCopier(this).copy();
    }

    private void setupDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void handleHamburgerButtonPress() {
        mHamburgerButton = (Button) findViewById(R.id.hamburger_button);
        mHamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
                closeKeyboard();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        clearBackStack();
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                if (mNavController.getCurrentDestination().getId() != R.id.homeFragment) {
                    mNavController.navigate(R.id.toHome);
                }
                break;
            case R.id.nav_about:
                if (mNavController.getCurrentDestination().getId() != R.id.aboutFragment) {
                    mNavController.navigate(R.id.toAbout);
                }
                break;
            case R.id.nav_settings:
                if (mNavController.getCurrentDestination().getId() != R.id.settingsFragment) {
                    mNavController.navigate(R.id.toSettings);
                }
                break;
            case R.id.nav_import:

                handleImport();
                break;
            case R.id.nav_export:
                handleExport();
                break;
            case R.id.nav_printer:
                handlePrintJob();
                break;
            case R.id.nav_ranking:
                if (mNavController.getCurrentDestination().getId() != R.id.rankingFragment) {
                    if (Model.getInstance().getAnalysis().isEmpty()) {
                        Toast.makeText(this, "Není nahraná analýza.", Toast.LENGTH_SHORT).show();
                    } else {
                        mNavController.navigate(R.id.toRanking);
                    }
                }
                break;
            case R.id.nav_display:
                if (mNavController.getCurrentDestination().getId() != R.id.displayFragment) {
                    if (Model.getInstance().getReturns().isEmpty() && Model.getInstance().getOrders().isEmpty()) {
                        Toast.makeText(this, "Neobsahuje žádné položky.", Toast.LENGTH_SHORT).show();
                    } else {
                        mNavController.navigate(R.id.toDisplay);
                    }
                }
                break;
            case R.id.nav_search:
                if (mNavController.getCurrentDestination().getId() != R.id.searchFragment) {
                    mNavController.navigate(R.id.toSearch);
                }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleImport() {
        Client client = new Client(Model.getInstance().getPrefs().getIp(), this);
        client.execute("analyza");
    }

    private void handleExport() {
        Client client = new Client(Model.getInstance().getPrefs().getIp(), this);
        client.execute("export");
    }

    private void handlePrintJob() {
        PrinterDialog dialog = new PrinterDialog();
        dialog.getActivity();
        dialog.show(getSupportFragmentManager(), "PrinterDialog");


    }

    @Override
    public void returnsClicked() {
        print(Model.getInstance().getReturns(), "Vratka");
    }

    @Override
    public void ordersClicked() {
        print(Model.getInstance().getOrders(), "Objednávka");
    }

    private WebView mWebView;
    private List<PrintJob> mPrintJobs;

    public void print(List<ExportSharedArticle> list, String name) {
        WebView webview = new WebView(this);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("Page finished loading " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append(name + "<BR/>");
        for (ExportSharedArticle e : list) {
            sb.append("Počet: " + e.getExportAmount() + ", " + e.getName() + ", " + e.getEan() + ", lokace: " + e.getLocation() + "<BR/>");
        }
        String htmlDocument = "<html><body><p>" + sb.toString() + "</p></body></html>";
        webview.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
        mWebView = webview;
    }


    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        mPrintJobs = printManager.getPrintJobs();
        String jobName = getString(R.string.app_name) + " Document";
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
        mPrintJobs.add(printJob);
    }


    @Override
    public void onStop() {
        super.onStop();
        Model.getInstance().saveOrdersAndReturns();
    }

    @Override
    public void onStart() {
        super.onStart();
        Model.getInstance().loadOrdersAndReturns();
        Model.getInstance().loadAnalysis();
        askForPermission();
        Log.d(TAG, "On START");
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    private void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mNavigationView.getWindowToken(), 0);
    }


}