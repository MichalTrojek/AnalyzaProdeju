package cz.mtr.analyzaprodeju.fragments.ftp.storedata;

import android.util.Log;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.models.datastructures.StoreItem;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class StoreDataReader {

    public final String TAG = StoreDataReader.class.getSimpleName();


    public StoreDataReader() {

    }

    public HashMap<String, StoreItem> getStoreStatus(InputStream input, long timestamp) {
        return readStoreStatus(input, timestamp);
    }


    private HashMap<String, StoreItem> readStoreStatus(InputStream input, long timestamp) {
        HashMap<String, StoreItem> items = new HashMap<>();
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(input, "Windows-1250")).withSkipLines(1).withCSVParser(parser).build();
            String[] record;
            while ((record = reader.readNext()) != null) {
                try {
                    if (items.containsKey(record[0].trim().toLowerCase())) {
                        StoreItem itemInMap = items.get(record[0]);
                        int totalAmount = Integer.parseInt(itemInMap.getAmount()) + Integer.parseInt(record[8]);
                        String regal = itemInMap.getLocation() + " " + record[3];
                        items.get(record[0]).setAmount(totalAmount + "");
                        items.get(record[0]).setLocation(regal);
                    } else {
                        items.put(record[0].trim().toLowerCase(), new StoreItem(record[0], record[3], record[8], record[2], record[4]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            if (items.size() != 0) {
                if(timestamp != 0) {
                    GeneralPreferences.getInstance().saveStoreDataTime(timestamp);
                    Log.d("TestService", "Saved store data timestamp");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
