package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;


import java.sql.SQLException;

interface IteratorExtended<Row> {

    boolean next();


    String getString(int columnIndex);


    int getInt(int columnIndex) throws SQLException;


    String getString(String columnLabel) throws SQLException;


    int getInt(String columnLabel) throws SQLException;


    Object getObject(int columnIndex);


    int findColumn(String columnLabel);


    boolean isBeforeFirst();


    boolean isAfterLast();


    boolean isFirst();


    boolean isLast();


    void beforeFirst();


    void afterLast();


    boolean first();


    boolean last();


    boolean absolute(int row);


    boolean previous();


}
