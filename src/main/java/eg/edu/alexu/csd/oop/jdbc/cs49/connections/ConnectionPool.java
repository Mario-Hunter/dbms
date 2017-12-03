package eg.edu.alexu.csd.oop.jdbc.cs49.connections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

public class ConnectionPool extends ObjectPool<Connection> {

    private String url;
    private Properties info;
    private static ConnectionPool pool;

    public ConnectionPool(String driver, String url, Properties info) {
        super();
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.url = url;
        this.info = info;
    }

    public ConnectionPool() {
    }

    public static ConnectionPool getInstance(String url, Properties info) {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        pool.setUrl(url);
        pool.setProperties(info);
        return pool;
    }

    @Override
    protected Connection create() {
        try {
            return new ConnectionImpl(url, info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean validate(Connection o) {
        try {
            return !o.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void expire(Connection o) {
        try {
            o.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void adjust(final Connection connection) {
        ((ConnectionImpl) connection).setUrlAndInfo(url, info);
    }

    public static Connection checkOut(String path) {
        return null;
    }

    public void setProperties(final Properties properties) {
        this.info = properties;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public static ConnectionPool getInstance() {
        if (pool == null) pool = new ConnectionPool();
        return pool;
    }
}
