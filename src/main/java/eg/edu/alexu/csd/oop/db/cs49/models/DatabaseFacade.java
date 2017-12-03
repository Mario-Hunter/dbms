package eg.edu.alexu.csd.oop.db.cs49.models;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.ResultIterator;

import java.sql.SQLException;

public class DatabaseFacade implements Database {

    public DatabaseFacade(String databasePath) {
        DatabaseManager.getInstance("").setDatabasePath(databasePath);
    }

    public DatabaseFacade() {
    }

    @Override
    public String createDatabase(final String databaseName, final boolean dropIfExists) {
        DatabaseManager manager;
        if (dropIfExists) {
            manager = DatabaseManager.getInstance("drop database " + databaseName);
            try {
                manager.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        manager = DatabaseManager.getInstance("create database " + databaseName);
        try {
            manager.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return manager.getDatabasePath();
    }

    @Override
    public boolean executeStructureQuery(final String query) throws SQLException {
        DatabaseManager manager = DatabaseManager.getInstance(query);
        manager.execute();

        return manager.isSuccess();
    }

    @Override
    public Object[][] executeQuery(final String query) throws SQLException {
        DatabaseManager manager = DatabaseManager.getInstance(query);
        manager.execute();
        return manager.getResultRows();
    }

    @Override
    public int executeUpdateQuery(final String query) throws SQLException {
        DatabaseManager manager = DatabaseManager.getInstance(query);
        manager.execute();
        return manager.getRowsCount();
    }

    public ResultIterator getResultIterator(){
        return DatabaseManager.getInstance("").getResultIterator();
    }

    public Result getResult() {
        return DatabaseManager.getInstance("").getResult();
    }
}
