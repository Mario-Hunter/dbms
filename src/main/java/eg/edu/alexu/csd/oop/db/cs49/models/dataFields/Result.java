package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.jdbc.cs49.ResultSetMetadataImpl;

import java.util.List;

public class Result {
    private List<Row> resultRows;
    private int rowsCount;
    private String databasePath;
    private boolean success;
    private int resultType;
    public static int UPDATE_COUNT = 0;
    public static int SELECT_ROWS = 1;


    public List<Row> getResultRows() {
        return resultRows;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setResultRows(List<Row> resultRows) {
        this.resultRows = resultRows;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultIterator getIterator(){
        return new ResultIterator(this);
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public ResultMetadata getResultMetadata() {
        return new ResultMetadata(this);
    }
}
