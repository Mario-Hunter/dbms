package eg.edu.alexu.csd.oop.jdbc.cs49;

import com.jcabi.aspects.Loggable;
import eg.edu.alexu.csd.oop.db.cs49.models.interpreter.InvalidQuerySyntaxException;
import eg.edu.alexu.csd.oop.db.cs49.models.interpreter.SyntaxValidator;
import eg.edu.alexu.csd.oop.jdbc.cs49.connections.ConnectionPool;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverImpl implements Driver {
    public static String URL_PATTERN = "jdbc:mysql:([a-zA-Z_0-9]+)";
    private Connection connection;

    @Override
    @Loggable
    public Connection connect(String url, Properties info) throws SQLException {
        if (!acceptsURL(url)) {
            return null;
        }
        if (connection != null) ConnectionPool.getInstance().checkIn(connection);
        return ConnectionPool.getInstance(url,info).checkOut();
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        try {
            SyntaxValidator.validateAndGroupInput(url, URL_PATTERN);
        } catch (InvalidQuerySyntaxException e) {
            return false;
        }
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
