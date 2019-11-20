package cz.mtr.analyzaprodeju.fragments.display.email;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class SaveOfflineFilesTask extends AsyncTask<Integer, Integer, Void> {


    private Context mContext;
    private String mFilename;
    private DialogLoadingFragment mProgressDialog;


    public SaveOfflineFilesTask(Context c, DialogLoadingFragment progresDialog) {
        this.mContext = c;
        mProgressDialog = progresDialog;
    }

    @Override
    protected Void doInBackground(Integer... voids) {
        int position = voids[0];
        createOfflineFiles(position);
        return null;
    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onPostExecute(Void v) {
        mProgressDialog.dismissAllowingStateLoss();
        File attachment = new File(Environment.getExternalStorageDirectory(), mFilename);
        Toast.makeText(mContext, String.format("%s uložen.", attachment.getName()), Toast.LENGTH_LONG).show();
        EmailSender sender = new EmailSender();
        sender.sendEmail(mContext, attachment);
    }

    private void createOfflineFiles(int position) {
        if (position == 0) {
            createExcelReturnsFile(Model.getInstance().getReturns());
        } else if (position == 1) {
            createExcelOrdersFile(Model.getInstance().getOrders());
        }
    }


    public void createExcelReturnsFile(ArrayList<ExportSharedArticle> returns) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet();
        createFirstRow(sheet1, "Vratka");
        int i = 1;
        for (ExportSharedArticle e : returns) {
            Row row = sheet1.createRow(i);
            row.createCell(0).setCellValue(e.getRanking());
            row.createCell(1).setCellValue(e.getEshopCode());
            row.createCell(2).setCellValue(e.getEan());
            row.createCell(3).setCellValue(e.getName());
            row.createCell(4).setCellValue(e.getSales1());
            row.createCell(5).setCellValue(e.getRevenue());
            row.createCell(6).setCellValue(e.getStored());
            row.createCell(7).setCellValue(e.getLocation());
            row.createCell(8).setCellValue(e.getPrice());
            row.createCell(9).setCellValue(e.getSupplier());
            row.createCell(10).setCellValue(e.getAuthor());
            row.createCell(11).setCellValue(e.getDateOfLastSale());
            row.createCell(12).setCellValue(e.getDateOfLastDelivery());
            row.createCell(13).setCellValue(e.getReleaseDate());
            row.createCell(14).setCellValue(e.getCommision());
            row.createCell(15).setCellValue(e.getRankingEshop());
            row.createCell(16).setCellValue(e.getExportAmount());
            i++;
        }

        try {
            if (isExternalStorageWritable()) {
                mFilename = "VR_" + returnDate() + ".xlsx";
                File file = new File(Environment.getExternalStorageDirectory(), mFilename);
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                MediaScannerConnection.scanFile(
                        mContext,
                        new String[]{file.getAbsolutePath()}, // "file" was created with "new File(...)"
                        null,
                        null);
            } else {
                System.out.println("external storage is not writable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void createExcelOrdersFile(ArrayList<ExportSharedArticle> orders) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet();
        createFirstRow(sheet1, "Objednávka");
        int i = 1;
        for (ExportSharedArticle e : orders) {
            Row row = sheet1.createRow(i);
            row.createCell(0).setCellValue(e.getRanking());
            row.createCell(1).setCellValue(e.getEshopCode());
            row.createCell(2).setCellValue(e.getEan());
            row.createCell(3).setCellValue(e.getName());
            row.createCell(4).setCellValue(e.getSales1());
            row.createCell(5).setCellValue(e.getRevenue());
            row.createCell(6).setCellValue(e.getStored());
            row.createCell(7).setCellValue(e.getLocation());
            row.createCell(8).setCellValue(e.getPrice());
            row.createCell(9).setCellValue(e.getSupplier());
            row.createCell(10).setCellValue(e.getAuthor());
            row.createCell(11).setCellValue(e.getDateOfLastSale());
            row.createCell(12).setCellValue(e.getDateOfLastDelivery());
            row.createCell(13).setCellValue(e.getReleaseDate());
            row.createCell(14).setCellValue(e.getCommision());
            row.createCell(15).setCellValue(e.getRankingEshop());
            row.createCell(16).setCellValue(e.getExportAmount());
            i++;
        }

        try {
            if (isExternalStorageWritable()) {
                mFilename = "OBJ_" + returnDate() + ".xlsx";
                File file = new File(Environment.getExternalStorageDirectory(), mFilename);
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                MediaScannerConnection.scanFile(
                        mContext,
                        new String[]{file.getAbsolutePath()}, // "file" was created with "new File(...)"
                        null,
                        null);
            } else {
                System.out.println("external storage is not writable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    private void createFirstRow(Sheet sheet1, String text) {
        Row row = sheet1.createRow(0);
        row.createCell(0).setCellValue("Pořadí");
        row.createCell(1).setCellValue("Kód");
        row.createCell(2).setCellValue("Ean");
        row.createCell(3).setCellValue("Název");
        row.createCell(4).setCellValue("Prodej");
        row.createCell(5).setCellValue("Obrat");
        row.createCell(6).setCellValue("Stav skladu");
        row.createCell(7).setCellValue("Lokace");
        row.createCell(8).setCellValue("DPC");
        row.createCell(9).setCellValue("Dodavatel");
        row.createCell(10).setCellValue("Autor");
        row.createCell(11).setCellValue("Datum posledního prodeje");
        row.createCell(12).setCellValue("Datumm posledního příjmu");
        row.createCell(13).setCellValue("Datum vydání");
        row.createCell(14).setCellValue("Řada posledního příjmu");
        row.createCell(15).setCellValue("Pořadí eshop");
        row.createCell(16).setCellValue(text);
    }

    private String returnDate() {
        DateFormat sdf = new SimpleDateFormat("dd.MM.YYY_HHMMSS");
        Date date = new Date();
        System.out.println(sdf.format(date));
        return sdf.format(date).toString();
    }

}
