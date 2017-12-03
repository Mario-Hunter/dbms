package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;


import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;

import java.sql.SQLException;
import java.util.List;

public class ResultIterator implements IteratorExtended {
    private final List<Row> rows;
    private int currentRow;
    private Result result;

    public ResultIterator(Result result) {
        this.result = result;
        this.rows = result.getResultRows();
        currentRow = -1;
    }

    @Override
    public boolean next() {
        currentRow = currentRow + 1 - (currentRow / result.getRowsCount());
        return currentRow != result.getRowsCount();
    }


    @Override
    public String getString(int columnIndex) {
        return (String) rows.get(currentRow).getOrderedFields().get(columnIndex).getValue();
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {

        try {
            return (int) rows.get(currentRow).getOrderedFields().get(columnIndex).getValue();
        } catch (ClassCastException e) {
            throw new SQLException(e.getMessage());
        }

    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        try {
            return (String) rows.get(currentRow).getFieldOfName(columnLabel).getValue();
        } catch (ClassCastException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        try {
            return (int) rows.get(currentRow).getFieldOfName(columnLabel).getValue();
        } catch (ClassCastException e) {
            throw new SQLException(e.getMessage());
        }
    }


    @Override
    public Object getObject(int columnIndex) {
        return rows.get(currentRow).getOrderedFields().get(columnIndex).getValue();
    }

    @Override
    public int findColumn(String columnLabel) {
        return rows.get(currentRow).findColumn(columnLabel);
    }

    @Override
    public boolean isBeforeFirst() {
        return currentRow == -1;
    }

    @Override
    public boolean isAfterLast() {
        return currentRow == rows.size();
    }

    @Override
    public boolean isFirst() {
        return currentRow == 0;
    }

    @Override
    public boolean isLast() {
        return currentRow == rows.size() - 1;
    }

    @Override
    public void beforeFirst() {
        currentRow = -1;
    }

    @Override
    public void afterLast() {
        currentRow = rows.size();
    }

    @Override
    public boolean first() {
        if (rows.isEmpty()) return false;
        currentRow = 0;
        return true;
    }

    @Override
    public boolean last() {
        if (rows.isEmpty()) return false;
        currentRow = rows.size() - 1;
        return true;

    }

    @Override
    public boolean absolute(int row) {
        if (row < 0) row += rows.size();
        if (row < 0) {
            currentRow = -1;
            return false;
        }
        if (row >= rows.size()) {
            currentRow = rows.size();
            return false;
        }
        currentRow = row;
        return true;
    }

    @Override
    public boolean previous() {
        currentRow--;
        if (currentRow < -1) currentRow = -1;
        return currentRow != -1;
    }


}
