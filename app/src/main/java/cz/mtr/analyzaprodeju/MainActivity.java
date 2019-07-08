package cz.mtr.analyzaprodeju;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import cz.mtr.analyzaprodeju.Network.Client;
import cz.mtr.analyzaprodeju.fragments.dialogs.PrinterDialog;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.room.DatabaseCopier;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PrinterDialog.OnPrintClicked {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private Button mHamburgerButton;
    private NavigationView mNavigationView;
    private NavController mNavController;


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
            case R.id.nav_printer:
                handlePrintJob();
                break;
            case R.id.nav_ranking:
                if (Model.getInstance().getAnalysis().isEmpty()) {
                    Toast.makeText(this, "Není nahraná analýza.", Toast.LENGTH_SHORT).show();
                } else {
                    mNavController.navigate(R.id.rankingFragment);
                }
                break;
            case R.id.nav_display:
                if (Model.getInstance().getReturns().isEmpty() && Model.getInstance().getOrders().isEmpty()) {
                    Toast.makeText(this, "Není nic nachystaného k tisknutí.", Toast.LENGTH_SHORT).show();
                } else {
                    mNavController.navigate(R.id.displayFragment);
                }
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
            sb.append("Počet :" + e.getExportAmount() + ", " + e.getName() + ", " + e.getEan() + ", lokace: " + e.getLocation() + "<BR/>");
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
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}