package eg.edu.alexu.csd.oop.jdbc.cs49;

import com.jcabi.aspects.Loggable;
import eg.edu.alexu.csd.oop.db.cs49.models.DatabaseFacade;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result.SELECT_ROWS;

public class StatementImpl implements Statement {
    private List<String> queries;
    private Connection connection;
    private DatabaseFacade facade;
    private int queryTimeOut = 3000;
    private ResultSetImpl resultSet;
    private boolean isClosed = false;

    public StatementImpl(Connection connection, DatabaseFacade db) {
        this.connection = connection;
        facade = db;
        queries = new ArrayList<>();
    }

    @Override
    @Loggable
    public ResultSet executeQuery(String sql) throws SQLException {
        facade.executeQuery(sql);
        return null;
    }

    @Override
    @Loggable
    public int executeUpdate(String sql) throws SQLException {
        return facade.executeUpdateQuery(sql);
    }

    @Override
    @Loggable
    public void close() throws SQLException {
        connection.close();
        facade = null;
        this.queries = null;
        this.resultSet = null;
        this.isClosed = true;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return this.queryTimeOut;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        this.queryTimeOut = seconds;
    }

    @Override
    @Loggable
    public boolean execute(String sql) throws SQLException {
        facade.executeUpdateQuery(sql);
        resultSet = new ResultSetImpl(facade.getResult(), this);
        return facade.getResult().getResultType() == SELECT_ROWS;
    }

    @Override
    @Loggable
    public void addBatch(String sql) throws SQLException {
        queries.add(sql);
    }

    @Override
    @Loggable
    public void clearBatch() throws SQLException {
        queries.clear();
    }

    @Override
    @Loggable
    public int[] executeBatch() throws SQLException {
        int[] responses = new int[queries.size()];
        int i = 0;
        for (String query : queries) {
            execute(query);
            responses[i] = resultSet.getUpdateCount();
        }
        return responses;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }


    @Override
    public long getLargeUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public void setLargeMaxRows(long max) throws SQLException {

    }

    @Override
    public long getLargeMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        return new long[0];
    }

    @Override
    public long executeLargeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }


    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }
}