package cz.mtr.analyzaprodeju.fragments.scraper;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.mtr.analyzaprodeju.MainActivity;
import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.detail.DetailFragmentDirections;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.fragments.notfounddetail.NotFoundFragmentDirections;
import cz.mtr.analyzaprodeju.fragments.scraper.stores.WebItem;
import cz.mtr.analyzaprodeju.fragments.scraper.suppliers.WebItemSuppliers;
import cz.mtr.analyzaprodeju.models.Model;

public class ScrapInfoAsyncTask extends AsyncTask<String, Void, Void> {
    public static final String TAG = ScrapInfoAsyncTask.class.getSimpleName();

    private List<WebItem> mItems = new ArrayList<>();
    private List<WebItemSuppliers> mSuppliersItems = new ArrayList<>();
    private View mView;
    private DialogLoadingFragment mProgressDialog;
    private boolean isLoggedIn = true;
    private Context mContext;
    private String mImageLink = "";
    private String largeImageLink = "";
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
    private String loginUrl = "https://www.knihydobrovsky.cz/nette-admin/sign/in";
    private String loginActionUrl = "https://www.knihydobrovsky.cz/nette-admin/sign/in";

    public ScrapInfoAsyncTask(View view, Context context) {
        mContext = context;
        mView = view;
        mProgressDialog = new DialogLoadingFragment();
        mProgressDialog.setCancelable(false);
        mProgressDialog.show(((MainActivity) context).getSupportFragmentManager(), "FragmentChangeDialog");
    }


    @Override
    protected Void doInBackground(String... args) {
        String ean = args[0];
        try {


            Map<String, String> formData = new HashMap<String, String>();
            formData.put("username", Model.getInstance().getPrefs().getLogin());
            formData.put("password", Model.getInstance().getPrefs().getPassword());
            formData.put("send", "Přihlásit");
            formData.put("do", "signInForm-submit");


            // Po vyhledání podle eanu najde odkaz na detail knížky
            Document searchQuery = Jsoup.connect("https://www.knihydobrovsky.cz/vyhledavani?search=" + ean)
                    .userAgent(userAgent)
                    .get();

            mImageLink = searchQuery
                    .select("#snippet-bookSearchList-itemListSnippet > div > div > ul > li > div > h2 > a > span.img.shadow > img")
                    .attr("src");

            String linkToPage = searchQuery
                    .select("#snippet-bookSearchList-itemListSnippet > div > div > ul > li > div > h2 > a")
                    .attr("href");


            //login to get login cookies and check if logged in.
            Connection.Response homePage = Jsoup.connect(loginActionUrl)
                    .data(formData)
                    .method(Connection.Method.POST).userAgent(userAgent)
                    .execute();
            if (homePage.parse().html().contains("Neplatné přihlášení.")) {
                isLoggedIn = false;
                this.cancel(true);
            }


            //open detail page while logged in
            Document pageDetail = Jsoup.connect("https://www.knihydobrovsky.cz/" + linkToPage)
                    .cookies(homePage.cookies())
                    .userAgent(userAgent).get();


            largeImageLink = pageDetail.select("#main > div.section.section-gradient-bottom-big > div > div > div.img.shelf > div.book > img").attr("src");


            Elements table = pageDetail.select("table");
            createStoresList(table);
            createSupplierList(table);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void createStoresList(Elements table) {
        Elements rows = table.get(0).select("tr");
        for (Element tr : rows) {
            Elements tds = tr.getElementsByTag("td");
            Elements storeName = tr.getElementsByTag("th");
            if (tds.size() == 0) {
                continue;
            }
            WebItem item = new WebItem();
            item.setName(storeName.text());
            item.setStore(tds.get(0).text());
            item.setEshop(tds.get(1).text());
            item.setTotal(tds.get(2).text());
            item.setVk(tds.get(3).text());
            item.setRegal(tds.get(4).text());
            item.setPrice(tds.get(5).text());
            mItems.add(item);
        }
    }

    private void createSupplierList(Elements table) {
        Elements rows = table.get(2).select("tr");
        for (Element tr : rows) {
            Elements tds = tr.getElementsByTag("td");
            Elements supplierName = tr.getElementsByTag("th");
            if (tds.size() == 0) {
                continue;
            }
            WebItemSuppliers item = new WebItemSuppliers();
            item.setName(supplierName.text());
            item.setAvailability(tds.get(0).text());
            item.setPrice(tds.get(1).text());
            item.setDate(tds.get(2).text());
            mSuppliersItems.add(item);
        }
    }

    protected void onCancelled() {
        mProgressDialog.dismiss();
        Toast.makeText(mContext, "Neplatné přihlášení.", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(Void v) {

        if (isLoggedIn) {
            Model.getInstance().setItems(mItems);
            Model.getInstance().setSuppliersItems(mSuppliersItems);
            Model.getInstance().setImageLink(mImageLink);
            Model.getInstance().setLargeImageLink(largeImageLink);
            moveToScraperFragment();
            mProgressDialog.dismiss();
        }
    }

    private void moveToScraperFragment() {
        if (Navigation.findNavController(mView).getCurrentDestination().getId() == R.id.notFoundFragment) {
            Navigation.findNavController(mView).navigate(NotFoundFragmentDirections.notFoundToScraper());
        } else if (Navigation.findNavController(mView).getCurrentDestination().getId() == R.id.detailFragment) {
            Navigation.findNavController(mView).navigate(DetailFragmentDirections.detailToScraper());
        }
    }
}
