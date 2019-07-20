package cz.mtr.analyzaprodeju;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogDownloadDatabase;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogUpdateFound;
import cz.mtr.analyzaprodeju.fragments.dialogs.PrinterDialog;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.network.Client;
import cz.mtr.analyzaprodeju.repository.room.DatabaseCopier;
import cz.mtr.analyzaprodeju.utils.KeyboardHider;
import cz.mtr.analyzaprodeju.utils.Printer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PrinterDialog.OnPrintClicked {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private Button mHamburgerButton;
    private NavigationView mNavigationView;
    private NavController mNavController;
    private MainActivityViewModel mViewModel;


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

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getUpdateFound().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean updateFound) {
                if (updateFound) {
                    updateFoundDialog();
                }
            }
        });

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
                mViewModel.checkForDatabaseUpdate();
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
                    mViewModel.checkForDatabaseUpdate();
                } else {
                    Toast.makeText(this, "Aplikace nebude správně fungovat bez povoleného přístupu k souborům.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
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
                KeyboardHider.closeKeyboard(MainActivity.this, mNavigationView);
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
        Printer printer = new Printer(this);
        printer.print(Model.getInstance().getReturns(), "Vratka");
    }

    @Override
    public void ordersClicked() {
        Printer printer = new Printer(this);
        printer.print(Model.getInstance().getOrders(), "Objednávka");
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