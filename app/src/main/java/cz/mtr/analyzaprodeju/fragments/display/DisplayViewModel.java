package cz.mtr.analyzaprodeju.fragments.display;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.fragments.display.email.SaveOfflineFilesTask;
import cz.mtr.analyzaprodeju.fragments.display.printer.Printer;
import cz.mtr.analyzaprodeju.models.Model;

public class DisplayViewModel extends AndroidViewModel {

    public static final String TAG = DisplayViewModel.class.getSimpleName();


    public DisplayViewModel(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Boolean[]> getOrderAndReturnsStatus() {
        return Model.getInstance().getOrdersAndReturnsStatus();
    }

    public void print(int position) {
        Printer printer = new Printer(Model.getInstance().getMainActivityContext());
        printer.print(position == 0 ?
                        Model.getInstance().getReturns() :
                        Model.getInstance().getOrders(),
                position == 0 ?
                        "Vratka" :
                        "Objednávka");
    }

    public void createFileAndOpenEmailClient(int position, DialogLoadingFragment progressDialog) {
        Log.d("testuju", "TET");
        SaveOfflineFilesTask excelCreator = new SaveOfflineFilesTask(getApplication(), progressDialog);
        excelCreator.execute(position);
    }


}
