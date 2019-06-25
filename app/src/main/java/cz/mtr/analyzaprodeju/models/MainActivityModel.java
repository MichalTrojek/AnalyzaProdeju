package cz.mtr.analyzaprodeju.models;

public class MainActivityModel {

    private static volatile MainActivityModel INSTANCE;


    public MainActivityModel() {

    }


    public static MainActivityModel getInstance() {
        if (INSTANCE == null) {
            synchronized (MainActivityModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainActivityModel();
                }
            }
        }

        return INSTANCE;
    }


}
