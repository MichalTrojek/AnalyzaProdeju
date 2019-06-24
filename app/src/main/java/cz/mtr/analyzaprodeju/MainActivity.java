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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;

import cz.mtr.analyzaprodeju.ViewModel.DataViewModel;
import cz.mtr.analyzaprodeju.fragments.HomeFragment;
import cz.mtr.analyzaprodeju.room.DatabaseCopier;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private Button hamburgerButton;
    private NavigationView navigationView;
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseFromAssetsToWorkingDirectory();
        setupDrawerLayout();
        handleHamburgerButtonPress();
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
        }

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mDataViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                Toast.makeText(getApplicationContext(), "Nazev " + s, Toast.LENGTH_LONG).show();
            }
        });
    }




    private void showHomeFragment() {
        HomeFragment fragment = HomeFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_left, R.anim.enter_from_bottom, R.anim.exit_to_left); //the second pair of animations is there so back button shows animations.
        transaction.add(R.id.center_container, fragment, "HOME_FRAGMENT").commit();
    }

    private void copyDatabaseFromAssetsToWorkingDirectory() {
        new DatabaseCopier(this).copy();
    }

    private void setupDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.center_container, new HomeFragment()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
