package cz.mtr.analyzaprodeju.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import cz.mtr.analyzaprodeju.MainActivity;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.Message;

public class Client extends AsyncTask<String, Integer, Void> {

    private static final String TAG = Client.class.getSimpleName();

    private boolean connectedToServer = true;
    private int port;
    private Context context;
    private String ip;
    private String message;
    private DialogLoadingFragment progressDialog;


    public Client(String ip, Context c) {
        this.ip = ip;
        this.context = c;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new DialogLoadingFragment();
        progressDialog.setCancelable(false);
        progressDialog.show(((MainActivity) context).getSupportFragmentManager(), "FragmentChangeDialog");

    }

    @Override
    protected Void doInBackground(String... voids) {
        this.message = voids[0];
        if (voids[0].equalsIgnoreCase("analyza")) {
            handleAnalysisImport();
        } else if (voids[0].equalsIgnoreCase("export")) {
            handleExport();
        }
        return null;
    }

    private void handleAnalysisImport() {
        try (Socket socket = new Socket()) {
            port = 9998;
            socket.connect(new InetSocketAddress(ip, port), 500);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) objectInputStream.readObject();
            Model.getInstance().clearAnalysis();
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

    private void handleExport() {
        try (Socket socket = new Socket()) {
            port = 5678;
            socket.connect(new InetSocketAddress(ip, port), 500);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message outputMessage = new Message();
            outputMessage.createExport(Model.getInstance().getOrders(), Model.getInstance().getReturns());
            objectOutputStream.writeObject(outputMessage);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            connectedToServer = false; //  this means that server is offline
            e.printStackTrace();
        }
    }


    @Override
    protected void onPostExecute(Void v) {
        if (connectedToServer && port == 9998) {
            if (Model.getInstance().getAnalysis() == null || Model.getInstance().getAnalysis().isEmpty()) {
                Toast.makeText(context, "Nebyl vložen CSV soubor.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, String.format("Data byla nahraná\nAnalyza: %d", Model.getInstance().getAnalysis().size()), Toast.LENGTH_LONG).show();
            }
        } else if (connectedToServer && port == 5678) {
            Toast.makeText(context, "Data byla vyexportováno.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Připojení selhalo.", Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }


}
