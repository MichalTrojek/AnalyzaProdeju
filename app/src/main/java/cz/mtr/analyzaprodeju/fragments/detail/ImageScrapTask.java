package cz.mtr.analyzaprodeju.fragments.detail;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.room.linkDatabase.LinkRepository;

public class ImageScrapTask extends AsyncTask<String, Void, Void> {
    public static final String TAG = ImageScrapTask.class.getSimpleName();


    private String mImageLink = "";
    private String mLargeImageLink = "";
    private String mUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
    private ImageView mImageView;
    private ProgressBar mProgress;
    private LinkRepository mRepository;


    public ImageScrapTask(ImageView imageView, ProgressBar progressBar, LinkRepository repository) {
        mImageView = imageView;
        mProgress = progressBar;
        mRepository = repository;
    }


    @Override
    protected Void doInBackground(String... args) {
        String ean = args[0];
        try {

            Document searchQuery = Jsoup.connect("https://www.knihydobrovsky.cz/vyhledavani?search=" + ean)
                    .userAgent(mUserAgent)
                    .get();
            Elements images = searchQuery.select("img");
            for(Element image : images){
                if(image.attr("src").contains("mod_eshop/produkty")){
                    mImageLink = image.attr("src");
                    mLargeImageLink = mImageLink;
                }
            }
            mRepository.insertLink(ean, mImageLink);
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
        Model.getInstance().setLargeImageLink(mLargeImageLink);
        ImageDisplay display = new ImageDisplay(Model.getInstance().getImageLink(), mImageView, mProgress);
        display.show();

    }


}
