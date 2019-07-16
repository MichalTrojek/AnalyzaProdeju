package cz.mtr.analyzaprodeju.network;

public class DatabaseVersion {


    private String databaseName;
    private String databaseVersion;


    public DatabaseVersion(String databaseName, String databaseVersion) {
        this.databaseName = databaseName;
        this.databaseVersion = databaseVersion;
    }


    public String getDatabaseName() {
        return databaseName;
    }


    public String getDatabaseVersion() {
        return databaseVersion;
    }


}
