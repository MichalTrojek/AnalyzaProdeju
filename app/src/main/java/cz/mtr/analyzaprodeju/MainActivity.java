package cz.mtr.analyzaprodeju;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

import cz.mtr.analyzaprodeju.Network.Client;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.room.DatabaseCopier;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private Button mHamburgerButton;
    private NavigationView mNavigationView;
    private NavController mNavController;
    private AlertDialog loadingAnalysisDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseFromAssetsToWorkingDirectory();
        setupDrawerLayout();
        handleHamburgerButtonPress();
        mNavigationView.setCheckedItem(R.id.nav_home);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Model.getInstance().createPrefs(this);


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
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                mNavController.navigate(R.id.homeFragment);
                break;
            case R.id.nav_about:
                mNavController.navigate(R.id.aboutFragment);
                break;
            case R.id.nav_settings:
                mNavController.navigate(R.id.settingsFragment);
                break;
            case R.id.nav_import:
                handleImport();
                break;
            case R.id.nav_ranking:
                mNavController.navigate(R.id.rankingFragment);
                break;
            case R.id.nav_printer:
                mNavController.navigate(R.id.printerFragment);
                break;
            case R.id.nav_search:
                mNavController.navigate(R.id.searchFragment);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleImport() {
        Model.getInstance().clearAnalysis();
        Client client = new Client(Model.getInstance().getPrefs().getIp(), this);
        client.execute("analyza");
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
    }


}