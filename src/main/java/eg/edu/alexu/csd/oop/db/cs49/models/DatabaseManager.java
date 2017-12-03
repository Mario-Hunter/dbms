package eg.edu.alexu.csd.oop.db.cs49.models;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.ResultIterator;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.QueryFilterBuilder;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.RowFilterBuilder;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.interpreter.MainInterpreter;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.queries.Query;
import eg.edu.alexu.csd.oop.db.cs49.models.queries.QueryFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import static eg.edu.alexu.csd.oop.db.cs49.models.Logger.*;

public class DatabaseManager {
    private Query query;
    private Table table;
    private List<Column> columns;
    private static DatabaseManager manager = null;
    private Database database;
    private HashMap<String, String> rows;
    private static Context context;
    private HashMap<Integer, Runnable> map;
    private Result result;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance(String input) {
        if (manager == null) {
            manager = new DatabaseManager();
        }
        setContext(input);
        return manager;
    }

    public void setQuery(final Query query) {
        this.query = query;
    }

    public void setTable(final Table table) {
        this.table = table;
    }

    public void setColumns(final List<Column> columns) {
        this.columns = columns;
    }

    public void execute() throws SQLException {
        MainInterpreter.interpret(context);
        loadData();
        database.accept(query);
    }

    private void loadData() {
        //HashMap<Integer, Runnable> methodMap = new MethodLogMap().getMap();
        Queue<Integer> iter = Logger.getLogsQueue();

        while (!iter.isEmpty()) {
            int event = iter.poll();
            switch (event) {
                case QUERY:
                    createQuery();
                    break;
                case SET_TABLE_NAME:
                    createTable();
                    break;
                case SET_DATABASE_NAME:
                    setDatabase();
                    break;
                case SET_COLUMNS_AND_TYPES:
                    setColumnsAndTypes();
                    break;
                case SET_COLUMNS_AND_VALUES:
                    setColumnsAndValues();
                    break;
                case CONDITIONS:
                    setConditions();
                    break;
                case SELECT_CONDITIONS:
                    setSelectCondition();
                    break;

            }
//            methodMap.get().run();
        }
    }

    private void createQuery() {
        query = QueryFactory.getQuery(context.getCommand());
    }

    private void createTable() {
        this.table = new Table(context.getTableName());
        if (database != null) {
            database.setFocusedTable(table);
            table.setDatabase(database);
        }
    }


    public void setDatabase() {
        this.database = new Database(context.getDatabaseName());
        if (table != null) table.setDatabase(database);
    }

    private void setColumnsAndTypes() {
        this.table.setColumnsAndTypes(context.getColumnsAndTypesMap());
        this.table.setColumnsOrder(context.getColumnsOrder());
    }

    private void setColumnsAndValues() {
        this.table.setColumnsAndValues(context.getColumnsAndValues());
    }

    public static void setContext(final String input) {
        context = Context.getInstance(input);
    }

    private void setConditions() {
        try {
            Filter filter = RowFilterBuilder.buildFilter(context.getPostfixConditions());
            query.setFilter(filter);
        } catch (InvalidCondition invalidCondition) {
            invalidCondition.printStackTrace();
        }

    }

    private void setSelectCondition() {
        Filter filter = QueryFilterBuilder.buildFilter(context.getSelectColumns());
        query.setSelectFilter(filter);

    }

    public Object[][] getResultRows() {
        result = query.getResult();
        List<Row> rows = query.getResultRows();
        if (rows.size() == 0) return new Object[0][];
        Object[][] result = new Object[rows.size()][rows.get(0).getFields().size()];
        for (int i = 0; i < rows.size(); i++) {
            int j = 0;
            for (Field entry : rows.get(i).getOrderedFields()) {
                result[i][j] = entry.getValue();
                j++;
            }
        }
        return result;
    }

    public boolean isSuccess() {
        return query.isSuccess();
    }

    public int getRowsCount() {
        return query.getRowsCount();
    }

    public String getDatabasePath() {
        return query.getDatabasePath();
    }

    public void setDatabaseName(String databaseName) {
        this.database = new Database(databaseName);
    }

    public ResultIterator getResultIterator() {
        return query.getResult().getIterator();
    }

    public Result getResult() {
        return query.getResult();
    }

    public void setDatabasePath(final String databasePath) {
        if (this.database == null) this.database = new Database("");
        this.database.setAbsolutePath(databasePath);
    }

    private class MethodLogMap {
        HashMap<Integer, Runnable> methodMap = new HashMap<>();

//        public HashMap getMap() {
//            methodMap.put(QUERY, DatabaseManager.this::createQuery);
//            methodMap.put(SET_TABLE_NAME, DatabaseManager.this::createTable);
//            methodMap.put(SET_DATABASE_NAME, DatabaseManager.this::setDatabase);
//            methodMap.put(SET_COLUMNS_AND_TYPES, DatabaseManager.this::setColumnsAndTypes);
//            methodMap.put(SET_COLUMNS_AND_VALUES, DatabaseManager.this::setColumnsAndValues);
//            methodMap.put(CONDITIONS, DatabaseManager.this::setConditions);
//            methodMap.put(SELECT_CONDITIONS, DatabaseManager.this::setSelectCondition);
//            return methodMap;
//        }
    }


}
