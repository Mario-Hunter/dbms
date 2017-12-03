package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

import java.sql.SQLException;
import java.sql.Types;

public class ResultMetadata {

    private Result result;

    public ResultMetadata(Result result) {
        this.result = result;
    }

    public int getColumnCount() {
        return result.getResultRows().get(0).getFields().size();
    }


    public String getColumnLabel(int column) {
        return result.getResultRows().get(0).getOrderedFields().get(column).getColumnName();
    }


    public String getColumnName(int column) {
        return getColumnLabel(column);
    }


    public int getColumnType(int column) {
        switch (result.getResultRows().get(0).getOrderedFields().get(column).type) {
            case INT:
                return Types.INTEGER;
            case varChar:
                return Types.VARCHAR;
            default:
                return -1;
        }
    }


    public String getTableName(int column) {
        return result.getResultRows().get(0).getTable();
    }
}
