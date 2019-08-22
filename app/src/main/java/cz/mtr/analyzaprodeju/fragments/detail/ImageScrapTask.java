package cz.mtr.analyzaprodeju.fragments.detail;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import cz.mtr.analyzaprodeju.ImageDisplay;
import cz.mtr.analyzaprodeju.models.Model;

public class ImageScrapTask extends AsyncTask<String, Void, Void> {
    public static final String TAG = ImageScrapTask.class.getSimpleName();


    private View mView;
    private Context mContext;
    private String mImageLink = "";
    private String largeImageLink = "";
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
    private ImageView mImageView;
    private ProgressBar mProgress;


    public ImageScrapTask(View view, Context context, ImageView imageView, ProgressBar progressBar) {
        mContext = context;
        mView = view;
        mImageView = imageView;
        mProgress = progressBar;
    }


    @Override
    protected Void doInBackground(String... args) {
        String ean = args[0];

        try {


            // Po vyhledání podle eanu najde odkaz na detail knížky
            Document searchQuery = Jsoup.connect("https://www.knihydobrovsky.cz/vyhledavani?search=" + ean)
                    .userAgent(userAgent)
                    .get();


            mImageLink = searchQuery.select(
                    "#snippet-bookSearchList-itemListSnippet > section > div > ul > li > article > h2 > a > span.img.shadow > img")
                    .attr("src");

            String linkToPage = searchQuery
                    .select("#snippet-bookSearchList-itemListSnippet > section > div > ul > li > article > h2 > a")
                    .attr("href");


            //opens detail page
            Document pageDetail = Jsoup.connect("https://www.knihydobrovsky.cz/" + linkToPage)
                    .userAgent(userAgent).get();


            largeImageLink = pageDetail.select("#main > div.section.section-gradient-bottom-big > div > div > div.img.shelf > div.book > img").attr("src");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onCancelled() {

    }


    @Override
    protected void onPostExecute(Void v) {
        Model.getInstance().setImageLink(mImageLink);
        Model.getInstance().setLargeImageLink(largeImageLink);
        ImageDisplay display = new ImageDisplay(Model.getInstance().getImageLink(), mImageView, mProgress);
        display.show();

    }


}
