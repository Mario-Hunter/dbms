package eg.edu.alexu.csd.oop.jdbc.cs49.UI;

import eg.edu.alexu.csd.oop.jdbc.cs49.DriverImpl;
import eg.edu.alexu.csd.oop.jdbc.cs49.connections.ConnectionPool;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import static eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader.FILE_SEPARATOR;
import static eg.edu.alexu.csd.oop.jdbc.cs49.connections.ConnectionImpl.PATH;
import static java.lang.System.err;
import static java.lang.System.out;

public class JdbcCLI {

    private static Connection connection;
    private static Statement stmt;
    private static HashMap<String, Runnable> methodMap;
    private static Scanner in;
    private static HashMap<String, Runnable> resultMap;
    private static ResultSet resultSet;
    private static DriverImpl driver;

    static {
        methodMap = new HashMap<>();
        methodMap.put("add batch", JdbcCLI::addBatch);
        methodMap.put("clear batch", JdbcCLI::clearBatch);
        methodMap.put("execute", JdbcCLI::execute);
        methodMap.put("execute query", JdbcCLI::executeQuery);
        methodMap.put("execute update", JdbcCLI::executeUpdate);
        methodMap.put("execute batch", JdbcCLI::executeBatch);
        methodMap.put("set query timeout", JdbcCLI::queryTimeOut);
        methodMap.put("result", JdbcCLI::resultInterface);
        methodMap.put("set database", JdbcCLI::setDatabase);
        resultMap = new HashMap<>();
        resultMap.put("set result absolute position", JdbcCLI::absolute);
        resultMap.put("after last", JdbcCLI::afterLast);
        resultMap.put("before first", JdbcCLI::beforeFirst);
        resultMap.put("find column", JdbcCLI::findColumn);
        resultMap.put("first", JdbcCLI::first);
        resultMap.put("get field", JdbcCLI::getField);
        resultMap.put("last", JdbcCLI::last);
        resultMap.put("next", JdbcCLI::next);
        resultMap.put("previous", JdbcCLI::previous);
        resultMap.put("metadata", JdbcCLI::resultMetadata);
    }

    private static void resultMetadata() {
        out.println("showing result metadata");
        try {
            out.println(resultSet.getMetaData());
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void setDatabase() {
        out.println("You are attempting to connect to a different database.");
        out.println("Type the name of the database you wish to connect");
        String databaseName = in.nextLine();
        Properties info = new Properties();
        File dbDir = new File("databases" + FILE_SEPARATOR + databaseName.toLowerCase().trim());
        info.put("path", dbDir.getAbsoluteFile());
        try {
            Connection connection = driver.connect("jdbc:mysql:students", info);
            ConnectionPool.getInstance().checkIn(JdbcCLI.connection);
            JdbcCLI.connection = connection;
            stmt = JdbcCLI.connection.createStatement();
            out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    private static void resultInterface() {
        out.println("You have entered the result interface.");
        try {
            resultSet = stmt.getResultSet();
            if (resultSet == null) {
                err.println("No queries has submitted any result, exiting result interface");
                return;
            }
            String line = in.nextLine();
            while (!line.toLowerCase().trim().equals("done")) {
                resultMap.getOrDefault(line.toLowerCase().trim(), JdbcCLI::noActionFound).run();
                line = in.nextLine();
            }
        } catch (SQLException e) {
            err.println(e.getMessage());
            err.println("Exiting result interface");
        }
    }

    private static void previous() {
        out.println("selecting previous row");
        try {
            boolean result = resultSet.previous();
            if (result) {
                out.println("cursor moved to the previous row successfully.");
            } else {
                out.println("cursor now stands on an invalid row.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void next() {
        out.println("selecting next row");
        try {
            boolean result = resultSet.next();
            if (result) {
                out.println("cursor moved to the next row successfully.");
            } else {
                out.println("cursor now stands on an invalid row.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void last() {
        out.println("selecting last row");
        try {
            boolean result = resultSet.last();
            if (result) {
                out.println("cursor moved to the last row successfully.");
            } else {
                out.println("cursor now stands on an invalid row.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getField() {
        out.println("Get field value of a column");
        out.println("Select the field by its index or label");
        String line = in.nextLine();
        if (line.toLowerCase().trim().equals("index")) {
            getFieldByIndex();
        } else if (line.trim().toLowerCase().equals("label")) {
            getFieldByLabel();
        } else {
            err.println("operation unrecognized, exiting the get field interface.");
        }

    }

    private static void getFieldByIndex() {
        out.println("Type the type of the column you wish to retrieve");
        out.println("int string object");
        String line = in.nextLine();
        try {
            int index;
            switch (line.trim().toLowerCase()) {
                case "int":
                    index = in.nextInt();
                    int result = resultSet.getInt(index);
                    out.println("value is :" + result);
                    break;
                case "string":
                    index = in.nextInt();
                    String string = resultSet.getString(index);
                    out.println("value is :" + string);
                    break;
                case "object":
                    index = in.nextInt();
                    Object object = resultSet.getObject(index);
                    out.println("value is :" + object);
                    break;
                default:
                    err.println("The type is unrecognized, exiting the get field interface");
                    break;
            }
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void first() {
        out.println("selecting first row");
        try {
            boolean result = resultSet.first();
            if (result) {
                out.println("cursor moved to the first row successfully.");
            } else {
                out.println("cursor now stands on an invalid row.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void findColumn() {
        out.println("Type the label of the column you wish to find the index for.");
        String line = in.nextLine();
        try {
            int index = resultSet.findColumn(line);
            out.println("The column index is:" + index);
        } catch (SQLException e) {
            err.println(e.getMessage());
            err.println("Exiting the find column interface.");
        }
    }

    private static void beforeFirst() {
        out.println("selecting before first row");
        try {
            resultSet.beforeFirst();
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void afterLast() {
        out.println("selecting after last row");
        try {
            resultSet.beforeFirst();
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void absolute() {
        out.println("selecting a row by its absolute position");
        try {
            int index = in.nextInt();
            boolean result = resultSet.absolute(index);
            if (result) {
                out.println("cursor moved to the first row successfully.");
            } else {
                out.println("cursor now stands on an invalid row.");
            }
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void queryTimeOut() {
        out.println("Set the query time out");
        int num = in.nextInt();
        try {
            stmt.setQueryTimeout(num);
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void executeBatch() {
        out.println("Executing the batch registered earlier");
        try {
            stmt.executeBatch();
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void executeUpdate() {
        out.println("You entered the execute interface.");
        out.println("Type your query in the next line.");
        String line = in.nextLine();
        try {
            stmt.executeUpdate(line);
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void executeQuery() {
        out.println("You entered the execute interface.");
        out.println("Type your query in the next line.");
        String line = in.nextLine();
        try {
            stmt.executeQuery(line);
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void execute() {
        out.println("You entered the execute interface.");
        out.println("Type your query in the next line.");
        String line = in.nextLine();
        try {
            stmt.execute(line);
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void clearBatch() {
        try {
            stmt.clearBatch();
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void addBatch() {
        out.println("You entered the addBatch interface.");
        out.println("Type your queries each in a new line.");
        out.println("When you finish type done.");
        out.println("Note that your queries will be executed in the order you entered");
        String line = in.nextLine();
        while (!line.trim().toLowerCase().equals("done")) {
            try {
                stmt.addBatch(line.trim().toLowerCase());
            } catch (SQLException e) {
                err.println(e.getMessage());
            }
            line = in.nextLine();
        }
    }


    private static void noActionFound() {
        System.err.println("The action you typed doesn't map to any known procedure, please refer to the manual, then" +
                " retry.");
    }

    public static void getFieldByLabel() {
        out.println("Type the type of the column you wish to retrieve");
        out.println("int string object");
        String line = in.nextLine();
        try {
            String label;
            switch (line.trim().toLowerCase()) {
                case "int":
                    label = in.nextLine();
                    int result = resultSet.getInt(label);
                    out.println("value is :" + result);
                    break;
                case "string":
                    label = in.nextLine();
                    String string = resultSet.getString(label);
                    out.println("value is :" + string);
                    break;
                default:
                    err.println("The type is unrecognized, exiting the get field interface");
                    break;
            }
        } catch (SQLException e) {
            err.println(e.getMessage());
        }
    }

    private static void establishConnection() throws SQLException {
        out.println("create or select a database");
        String line = in.nextLine();
        File dbDir;
        Properties info;
        switch (line.trim().toLowerCase()) {
            case "select":
                out.println("enter database name");
                line = in.nextLine();
                info = new Properties();
                dbDir = new File("databases" + FILE_SEPARATOR + line);
                info.put(PATH, dbDir.getAbsolutePath());
                connection = driver.connect("jdbc:mysql:students", info);
                stmt = connection.createStatement();
                break;
            case "create":
                info = new Properties();
                dbDir = new File("databases" + FILE_SEPARATOR);
                info.put(PATH, dbDir.getAbsolutePath());
                connection = driver.connect("jdbc:mysql:students", info);
                stmt = connection.createStatement();
                out.println("enter database name");
                line = in.nextLine();
                stmt.execute("create database " + line);
                break;
            default:
                err.println("unknown command");
                establishConnection();
                break;
        }
    }

    public static void main(String[] args) {
        driver = new DriverImpl();

        in = new Scanner(System.in);
        try {

            establishConnection();
            while (true) {
                methodMap.getOrDefault(in.nextLine().trim().toLowerCase(), JdbcCLI::noActionFound).run();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}
