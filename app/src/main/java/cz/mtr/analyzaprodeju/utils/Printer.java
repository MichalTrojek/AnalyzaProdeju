package cz.mtr.analyzaprodeju.utils;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class Printer {

    private WebView mWebView;
    private List<PrintJob> mPrintJobs;
    private Context mContext;

    public Printer(Context context) {
        mContext = context;
    }

    public void print(List<ExportSharedArticle> list, String name) {
        WebView webview = new WebView(mContext);
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
            sb.append("Počet: " + e.getExportAmount() + ", " + e.getName() + ", " + e.getEan() + ", lokace: " + e.getLocation() + "<BR/>");
        }
        String htmlDocument = "<html><body><p>" + sb.toString() + "</p></body></html>";
        webview.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
        mWebView = webview;
    }


    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager) mContext.getSystemService(Context.PRINT_SERVICE);
        mPrintJobs = printManager.getPrintJobs();
        String jobName = mContext.getString(R.string.app_name) + " Document";
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
        mPrintJobs.add(printJob);
    }
}
