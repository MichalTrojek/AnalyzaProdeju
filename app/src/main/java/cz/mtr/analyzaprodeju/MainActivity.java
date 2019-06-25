package cz.mtr.analyzaprodeju;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

import cz.mtr.analyzaprodeju.ViewModel.DataViewModel;
import cz.mtr.analyzaprodeju.room.DatabaseCopier;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;

    private Button hamburgerButton;

    private NavigationView navigationView;

    public NavController navController;


    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseFromAssetsToWorkingDirectory();
        setupDrawerLayout();
        handleHamburgerButtonPress();

        navigationView.setCheckedItem(R.id.nav_home);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mDataViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                Toast.makeText(getApplicationContext(), "Nazev " + s, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void copyDatabaseFromAssetsToWorkingDirectory() {
        new DatabaseCopier(this).copy();
    }

    private void setupDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void handleHamburgerButtonPress() {
        hamburgerButton = (Button) findViewById(R.id.hamburger_button);
        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                navController.navigate(R.id.homeFragment);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}