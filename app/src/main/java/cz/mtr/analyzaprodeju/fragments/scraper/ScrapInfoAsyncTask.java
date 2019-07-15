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
import cz.mtr.analyzaprodeju.fragments.detail.DetailFragmentDirections;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
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

    public ScrapInfoAsyncTask(View view, Context context) {
        mContext = context;
        mView = view;
        mProgressDialog = new DialogLoadingFragment();
        mProgressDialog.setCancelable(false);
        mProgressDialog.show(((MainActivity) context).getSupportFragmentManager(), "FragmentChangeDialog");
    }


    @Override
    protected Void doInBackground(String... args) {
        try {
            String ean = args[0];
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";

            String loginUrl = "https://www.knihydobrovsky.cz/nette-admin/sign/in";
            String loginActionUrl = "https://www.knihydobrovsky.cz/nette-admin/sign/in";

            Map<String, String> formData = new HashMap<String, String>();
            formData.put("username", Model.getInstance().getPrefs().getLogin());
            formData.put("password", Model.getInstance().getPrefs().getPassword());
            formData.put("send", "Přihlásit");
            formData.put("do", "signInForm-submit");

            Map<String, String> cookies = new HashMap<String, String>();

            Connection.Response loginForm = Jsoup.connect(loginUrl).method(Connection.Method.GET).userAgent(userAgent)
                    .execute();
            cookies.putAll(loginForm.cookies());

            Connection.Response loggedInPage = Jsoup.connect(loginActionUrl).cookies(cookies).data(formData)
                    .method(Connection.Method.POST).userAgent(userAgent).execute();


            if (loggedInPage.parse().html().contains("Neplatné přihlášení.")) {
                isLoggedIn = false;
                this.cancel(true);
            }

            cookies.clear();
            cookies.putAll(loggedInPage.cookies());


            String searchString = "https://www.knihydobrovsky.cz/admin/widgetloader-ajax.php?wplocha=4&wid=dobro&wakce=search&cislo=" + ean + "&fulltext=";
            Document doc = Jsoup.connect(searchString).referrer("https://www.knihydobrovsky.cz/admin/modul-eshop.php")
                    .cookies(cookies).get();


            String productString = "https://www.knihydobrovsky.cz/admin/modul-eshop-produkt.php?cil=" + getBookId(doc.html().substring(doc.html().indexOf("cil="), doc.html().indexOf("cil=") + 25));
            Document product = Jsoup.connect(productString).referrer("https://www.knihydobrovsky.cz/admin/modul-eshop.php")
                    .cookies(cookies).get();

            Elements table = product.select("table");


            createStoresList(table);
            createSupplierList(table);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getBookId(String bookId) {
        return bookId = bookId.replaceAll(" ", "").replaceAll("[^0-9]", "");
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
            Navigation.findNavController(mView).navigate(DetailFragmentDirections.detailToScraper());
            mProgressDialog.dismiss();
        }
    }
}
