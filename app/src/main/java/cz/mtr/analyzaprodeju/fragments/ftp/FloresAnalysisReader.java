package cz.mtr.analyzaprodeju.fragments.ftp;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class FloresAnalysisReader {

    private String mArticleAmount = "";

    public FloresAnalysisReader(){

    }

    public HashMap<String, SharedArticle> readAnalysisFromFtp(InputStream input) {
        HashMap<String, SharedArticle> map = new HashMap<>(500000, 1);
        SharedArticle shared;
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(input, "Windows-1250"), ';', '\"', 1);
            String[] record;
            int rankingCounter = 0;
            while ((record = reader.readNext()) != null) {
                try {
                    rankingCounter++;
                    if (record[3].contains("Dárková knižní") || record[3].contains("Klubový slevový poukaz")
                            || record[3].contains("VOUCHER")) {
                        rankingCounter--;
                        continue;
                    }
                    shared = new SharedArticle();
                    shared.setEshopCode(record[1]);
                    shared.setRanking(rankingCounter + "");
                    shared.setEan(record[2]);
                    shared.setName(record[3]);
                    shared.setSales1(record[4]);
                    shared.setSales2(record[5]);
                    shared.setRevenue(record[6]);
                    shared.setStored(record[7]);
                    shared.setDaysOfSupplies(record[8]);
                    shared.setLocation(record[9]);
                    shared.setPrice(record[10]);
                    shared.setSupplier(record[12]);
                    shared.setAuthor(record[13]);
                    shared.setDontOrder(record[14]);
                    shared.setDateOfLastSale(record[15]);
                    shared.setDateOfLastDelivery(record[16]);
                    shared.setReleaseDate(record[17]);
                    shared.setCommision(record[18]);
                    shared.setRankingEshop(record[19]);
                    shared.setSales1DateSince(record[20]);
                    shared.setSales1DateTo(record[21]);
                    shared.setSales1Days(record[22]);
                    shared.setSales2DateSince(record[23]);
                    shared.setSales2DateTo(record[24]);
                    shared.setSales2Days(record[25]);

                    map.put(record[2], shared);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            mArticleAmount = map.size() + "";
            if (map.size() != 0) {
                Model.getInstance().setAnalysis(map);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getArticleAmount(){
        return mArticleAmount;
    }
}
