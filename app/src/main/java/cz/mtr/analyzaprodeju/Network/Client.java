package cz.mtr.analyzaprodeju.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.Message;

public class Client extends AsyncTask<String, Integer, Void> {

    private static final String TAG = Client.class.getSimpleName();

    private boolean connectedToServer = true;
    private int port;
    private Context context;
    private String ip;
    private String message;


    public Client(String ip, Context c) {
        Log.e(TAG, "CLIENT INSUDE");
        this.ip = ip;
        this.context = c;
    }

    @Override
    protected Void doInBackground(String... voids) {
        this.message = voids[0];
        handleAnalysisImport();

        return null;
    }

    private void handleAnalysisImport() {
        try (Socket socket = new Socket()) {
            port = 9998;
            socket.connect(new InetSocketAddress(ip, port), 500);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) objectInputStream.readObject();
            Model.getInstance().setAnalysis(message.getAnalysis());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            connectedToServer = false; //  this means that server is offline
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //
    @Override
    protected void onPostExecute(Void v) {
        Log.e(TAG, "TAD3");
        if (connectedToServer && port == 9998) {
            if (Model.getInstance().getAnalysis() == null || Model.getInstance().getAnalysis().isEmpty()) {
                Toast.makeText(context, "Nebyl vložen CSV soubor.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, String.format("Data byla nahraná\nAnalyza: %d", Model.getInstance().getAnalysis().size()), Toast.LENGTH_LONG).show();
            }

        } else {
//            Toast.makeText(context, "Připojení selhalo.", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(context, String.format("Analyza: %d", Model.getInstance().getAnalysis().size()) + " adresa  " + ip, Toast.LENGTH_LONG).show();
    }


}
