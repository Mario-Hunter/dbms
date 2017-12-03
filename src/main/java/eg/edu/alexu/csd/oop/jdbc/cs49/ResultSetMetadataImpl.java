package eg.edu.alexu.csd.oop.jdbc.cs49;


import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.ResultMetadata;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class ResultSetMetadataImpl implements ResultSetMetaData {
    private ResultMetadata resultSet;

    public ResultSetMetadataImpl(ResultMetadata resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public int getColumnCount() {
        return resultSet.getColumnCount();
    }

    @Override
    public String getColumnLabel(int column) {
        return resultSet.getColumnLabel(column);
    }

    @Override
    public String getColumnName(int column) {
        return resultSet.getColumnName(column);
    }

    @Override
    public int getColumnType(int column) {
        return resultSet.getColumnType(column);
    }

    @Override
    public String getTableName(int column) {
        return resultSet.getTableName(column);
    }

    @Override
    public String toString() {
        return "ResultSetMetadataImpl{" +
                "column count=" + getColumnCount() +
                ",\n column labels and types=[\n" + getLabelsAndTypes()
                + "\n],\n"
                + '}';
    }

    private String getLabelsAndTypes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getColumnCount(); i++) {
            try {
                sb.append(getColumnLabel(i) + " " + getColumnTypeName(i)+"\n");
            } catch (SQLException e) {
                continue;
            }
        }
        return sb.toString();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        return false;
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return 0;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return null;
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        return 0;
    }

    @Override
    public int getScale(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        return null;
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        switch (getColumnType(column)) {
            case Types.INTEGER:
                return "int";
            case Types.VARCHAR:
                return "varchar";
            default:
                throw new SQLException("Found an unknown type" + getColumnType(column)+" of column"+column);
        }

    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
