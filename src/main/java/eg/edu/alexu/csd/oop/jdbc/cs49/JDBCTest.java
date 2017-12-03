package eg.edu.alexu.csd.oop.jdbc.cs49;

import eg.edu.alexu.csd.oop.jdbc.cs49.connections.ConnectionPool;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class JDBCTest {

    @BeforeClass
    public static void setupAll() {
        try {
            Class cls = Class.forName("eg.edu.alexu.csd.oop.jdbc.cs49.DriverImpl");
            DriverManager.registerDriver((Driver) cls.newInstance());
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {

    }

    @Test
    public void test() {
        // Contains Database name.
        String url = "jdbc:mysql:students";
        Connection con;
        Statement stmt;
        String createString = "CREATE DATABASE sample";
        String createTableString = "CREATE TABLE table_1 (column_1 INT,column_2 varChar)";
        String insertString = "INSERT into table_1 (column_1,column_2) values(5,'foo')";
        String insertString2 = "INSERT into table_1 (column_1,column_2) values(50,'bar')";
        String selectString = "SELECT * FROM table_1";
        String updateString = "UPDATE table_1 set column_1 = 10,column_2 = 'foobar' where column_1 < 5";
        try {
            Driver driver = new DriverImpl();
            Properties info = new Properties();
            File dbDir = new File("databases/student");
            info.put("path", dbDir.getAbsolutePath());
            con = driver.connect("jdbc:mysql:students", info);
            //con = DriverManager.getConnection(url, "myLogin", "myPassword");
            stmt = con.createStatement();
            stmt.addBatch(createString); // Execute SQL queries.
            stmt.addBatch(createTableString); // Execute SQL queries.
            stmt.addBatch(insertString); // Execute SQL queries.
            stmt.addBatch(insertString2); // Execute SQL queries.
            stmt.addBatch(selectString); // Execute SQL queries.
            stmt.addBatch(updateString); // Execute SQL queries.
            stmt.addBatch(selectString); // Execute SQL queries.
            stmt.executeBatch();
            con.close();
            ConnectionPool.getInstance().checkOut();
            ResultSet resultSet = stmt.getResultSet();
            resultSet.first();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

    }

}
