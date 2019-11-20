package cz.mtr.analyzaprodeju;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

import cz.mtr.analyzaprodeju.asynctasks.UnziperTask;
import cz.mtr.analyzaprodeju.auth.Authentication;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogDownloadDatabase;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogUpdateFound;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.network.Client;
import cz.mtr.analyzaprodeju.repository.preferences.AnalysisPreferences;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;
import cz.mtr.analyzaprodeju.repository.preferences.StoreItemsPreferences;
import cz.mtr.analyzaprodeju.services.UpdateAnalysisJobService;
import cz.mtr.analyzaprodeju.services.UpdateStoreDataJobService;
import cz.mtr.analyzaprodeju.utils.KeyboardHider;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int UPDATE_DATA_JOB = 321;
    private static final int UPDATE_STORE_DATA_JOB = 320;
    private static final int MINUTES = 60;


    private DrawerLayout mDrawerLayout;
    private Button mHamburgerButton;
    private NavigationView mNavigationView;
    private NavController mNavController;
    private MainActivityViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        copyDatabaseFromAssetsToWorkingDirectory();
        setupDrawerLayout();
        handleHamburgerButtonPress();
        initializePreferences();
        Model.getInstance().saveMainActivityContextForPrinter(MainActivity.this);

        mNavigationView.setCheckedItem(R.id.nav_home);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);


        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getUpdateFound().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean updateFound) {
                if (updateFound) {
                    updateFoundDialog();
                }
            }
        });


        Authentication.getInstance().check();
        scheduleUpdateDataJob();
        shutDown();
    }

    private void shutDown(){
        if (Authentication.getInstance().isTurnedOff()) {
            Toast.makeText(MainActivity.this, "\t\t\t\t\t\tCorrupted Data\nApplication is shutting down", Toast.LENGTH_LONG).show();
            Handler quiter = new Handler();
            quiter.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Process.killProcess(Process.myPid());
                }
            }, 5000);
        }
    }

    private void initializePreferences() {
        Authentication.init(this);
        AnalysisPreferences.init(this);
        GeneralPreferences.init(this);
        StoreItemsPreferences.init(this);
    }


    private void scheduleUpdateDataJob() {
        if (!Authentication.getInstance().isDownloadBlocked()) {
            scheduleUpdateAnalysis();
            scheduleUpdateStoreData();
        }

    }

    private void scheduleUpdateAnalysis() {
        Log.d(TAG, "ANALYSIS JOB CALLED");
        ComponentName componentName = new ComponentName(this, UpdateAnalysisJobService.class);
        JobInfo info = new JobInfo.Builder(UPDATE_DATA_JOB, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(MINUTES * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Analysis Job scheduled");
        } else {
            Log.d(TAG, "Analysis Job scheduling failed");
        }
    }

    private void scheduleUpdateStoreData() {
        Log.d(TAG, "STORE DATA JOB CALLED");
        ComponentName componentName = new ComponentName(this, UpdateStoreDataJobService.class);
        JobInfo info = new JobInfo.Builder((UPDATE_STORE_DATA_JOB), componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(MINUTES * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Store dataJob scheduled");
        } else {
            Log.d(TAG, "Store Data Job scheduling failed");
        }

    }

    private void cancelUpdateDataJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(UPDATE_DATA_JOB);
    }

    @Override
    public void onStop() {
        super.onStop();
        Model.getInstance().saveOrdersAndReturns();
    }

    @Override
    public void onStart() {
        super.onStart();
        restoreData();
        askForPermission();
    }

    private void restoreData() {
        Model.getInstance().loadOrdersAndReturns();
        Model.getInstance().loadAnalysis();
        Model.getInstance().loadStoreItems();
    }


    private void updateFoundDialog() {
        DialogUpdateFound dialog = new DialogUpdateFound();
        dialog.show(getSupportFragmentManager(), "DialogUpdateFound");

    }

    public void createDownloadingDatabaseDialog() {
        DialogDownloadDatabase dialog = new DialogDownloadDatabase();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "DialogChangeFragment");
        mViewModel.updateDatabase(dialog);
    }


    public void askForPermission() {


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // you already have a permission

                handleUpdatingDatabase();

            } else {
                // asks for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        } else {// below api level 23
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    handleUpdatingDatabase();
                } else {
                    Toast.makeText(this, "Aplikace nebude správně fungovat bez povoleného přístupu k souborům.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void handleUpdatingDatabase() {
        if (mNavController.getCurrentDestination().getId() == R.id.homeFragment) {
//            ArticleRoomDatabase.getInstance(getApplication()).close();
//            mViewModel.checkForDatabaseUpdate();
        }
    }


    private void copyDatabaseFromAssetsToWorkingDirectory() {
        DialogLoadingFragment loadingDialog = new DialogLoadingFragment();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getSupportFragmentManager(), "FragmentChangeDialog");
        UnziperTask task = new UnziperTask(this, loadingDialog);
        task.execute();
//        new DatabaseCopier(this).copy();
    }

    private void setupDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        setupAnimation();
    }

    private void setupAnimation(){
        AnimationDrawable animation = (AnimationDrawable) mDrawerLayout.getBackground();
        animation.setEnterFadeDuration(2000);
        animation.setExitFadeDuration(4000);
        animation.start();
    }

    private void handleHamburgerButtonPress() {
        mHamburgerButton = (Button) findViewById(R.id.hamburger_button);
        mHamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
                KeyboardHider.closeKeyboard(MainActivity.this, mNavigationView);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        clearBackStack();
        if (!Authentication.getInstance().areButtonsBlocked()) {
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
                case R.id.nav_ftp:
                    if (mNavController.getCurrentDestination().getId() != R.id.ftpFragment) {
                        mNavController.navigate(R.id.toFtp);
                    }
                    break;
                case R.id.nav_import:
                    handleImport();
                    break;
                case R.id.nav_export:
                    handleExport();
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
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleImport() {
        Client client = new Client(GeneralPreferences.getInstance().loadIp(), this);
        client.execute("analyza");
    }

    private void handleExport() {
        Client client = new Client(GeneralPreferences.getInstance().loadIp(), this);
        client.execute("export");
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

}