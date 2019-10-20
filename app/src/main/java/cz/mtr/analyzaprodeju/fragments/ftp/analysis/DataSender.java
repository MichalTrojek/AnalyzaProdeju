package cz.mtr.analyzaprodeju.fragments.ftp.analysis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataSender {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String URL = "http://skladovypomocnik.cz/reader.php";

    public DataSender() {

    }


    public void getData(String data0, String data1, String data2) {
        try {
            sendData(data0, data1, data2);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendData(String data0, String data1, String data2) throws JSONException, IOException {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        json.put("content", data0);
        json.put("contentA", data1);
        json.put("contentB", data2);


        RequestBody requestBody = RequestBody.create(json.toString(), JSON);


        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

    }


}
