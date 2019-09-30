package godev.budgetgo.models;

public class Config {
    private static final String DB_PROPERTIES_FILE_NAME = "db.local.properties";
    private static final String TABLES_INIT_FILE_NAME = "tables.sql";

    private Config() {
    }

    public static String getDbPropertiesFileName() {
        return DB_PROPERTIES_FILE_NAME;
    }

    public static String getTablesInitFileName() {
        return TABLES_INIT_FILE_NAME;
    }
}
