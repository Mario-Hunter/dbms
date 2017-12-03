package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.DatabaseManager;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.QueryFilterBuilder;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.queries.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.QueryCriteriaFactory.*;
import static eg.edu.alexu.csd.oop.db.cs49.models.interpreter.SyntaxValidator.*;

public class OldInterpreter {


//    public static DatabaseManager interpretSelect(String input) throws InvalidQuerySyntaxException {
//        String[] groups = SyntaxValidator.validateAndGroupInput(SELECT_PATTERN, input);
//
//        String fields = groups[2];
//        String tables = groups[4];
//
//        List<Column> columns = createColumns(fields, tables);
//
//        Filter filter = createSelectFilter(new String[]{groups[7], groups[8], groups[9]});
//        Query query = new SelectDatabaseQuery(filter);
//
//        Table table = new Table(tables.replace(".", "/"));
//
//        DatabaseManager manager = DatabaseManager.getInstance();
//        manager.setQuery(query);
//        manager.setTable(table);
//        manager.setColumns(columns);
//        return manager;
//    }
//
//    private static List<Column> createColumns(final String fields, final String defaultTable) {
//        String[] columnsString = fields.split(",");
//        List<Column> columns = new ArrayList<>();
//        for (String column : columnsString) {
//            Column clmn = new Column();
//            String[] data = column.split(".");
//            if (data.length == 2) {
//                clmn.setTable(data[0]);
//                clmn.setName(data[1]);
//            } else {
//                clmn.setTable(defaultTable);
//                clmn.setName(data[0]);
//            }
//            columns.add(clmn);
//        }
//        return columns;
//    }
//
//    private static Filter createSelectFilter(String[] groups) {
//        Filter filter = null;
//        try {
//            String left = groups[0];
//            String op = groups[1];
//            String right = groups[2];
//            filter = QueryFilterBuilder.buildFilter()
//                    .addCriteria(getCriteriaType(op), left, right)
//                    .build();
//
//        } catch (IndexOutOfBoundsException e) {
//            filter = QueryFilterBuilder.buildFilter()
//                    .addCriteria(ANY_COLUMN, "", "")
//                    .build();
//        }
//        return filter;
//    }
//
//    private static int getCriteriaType(final String op) {
//        switch (op) {
//            case "=":
//                return COLUMN_EQUALS;
//            case "<":
//                return COLUMN_SMALLER_THAN;
//            case ">":
//                return COLUMN_GREATER_THAN;
//            case ">=":
//                return COLUMN_GREATER_THAN_OR_EQUAL;
//            case "<=":
//                return COLUMN_SMALLER_THAN_OR_EQUAL;
//        }
//        return 0;
//    }
//
//    public static DatabaseManager interpretCreateDatabase(final String databaseName, final boolean dropIfExists) {
//        Query query = new CreateDatabaseQuery(databaseName, dropIfExists);
//        DatabaseManager manager = DatabaseManager.getInstance();
//        manager.setQuery(query);
//        return manager;
//    }
//
//    public static DatabaseManager interpretStructure(String input) throws InvalidQuerySyntaxException {
//        String[] groups = SyntaxValidator.validateAndGroupInput(input, CREATE_PATTERN);
//
//        String queryType = groups[0];
//        String databaseName = groups[1].replace(".","");
//        String name = groups[2];
//
//        if (queryType.toLowerCase().equals("database")) {
//            return interpretCreateDatabase(name, false);
//        } else if (!queryType.toLowerCase().equals("table")) {
//            throw new InvalidQuerySyntaxException(input);
//
//        }
//
//        List<Column> columns = new ArrayList<>();
//        String columnsString = groups[3];
//        String[] data = columnsString.split(",");
//
//        for (int i = 0; i < data.length; i++) {
//            String[] values = data[i].trim().split(" ");
//            Column column = new Column();
//            column.setName(values[0].replace("`", ""));
//            column.setType(values[1].replace("`", ""));
//            columns.add(column);
//        }
//
//        Query query;
//        query = new CreateTableQuery(columns);
//
//        DatabaseManager manager = DatabaseManager.getInstance();
//        manager.setQuery(query);
//        manager.setTable(name);
//        manager.setDatabase(databaseName);
//
//        return manager;
//    }
//
//    public static DatabaseManager interpretUpdateInsert(final String query) throws InvalidQuerySyntaxException {
//        if (query.toLowerCase().trim().contains("update")) {
//            return interpretUpdate(query);
//        } else if (query.toLowerCase().trim().contains("insert")) {
//            return interpretInsert(query);
//        } else {
//            throw new InvalidQuerySyntaxException(query);
//        }
//    }
//
//    private static DatabaseManager interpretInsert(final String input) throws InvalidQuerySyntaxException {
//        String[] groups = SyntaxValidator.validateAndGroupInput(input, INSERT_PATTERN);
//        String instruction = groups[0];
//        String databaseName = groups[1];
//        String tableName = groups[2];
//        String columnsString = groups[3];
//        String valuesString = groups[4];
//
//        String[] columns = columnsString.split(",");
//        String[] values = valuesString.split(",");
//        if (columns.length != values.length) throw new InvalidQuerySyntaxException(input);
//
//        HashMap<String, String> map = new HashMap<>();
//        for (int i = 0; i < columns.length; i++) {
//            map.put(columns[i], values[i]);
//        }
//        Query query = new InsertDatabaseQuery(map);
//
//        DatabaseManager manager = DatabaseManager.getInstance();
//        manager.setDatabase(databaseName);
//        manager.setTable(tableName);
//        manager.setQuery(query);
//        return manager;
//    }
//
//    private static DatabaseManager interpretUpdate(final String input) throws InvalidQuerySyntaxException {
//        String[] groups = SyntaxValidator.validateAndGroupInput(input, UPDATE_PATTERN);
//        String instruction = groups[0];
//        String databaseName = groups[1];
//        String tableName = groups[2];
//        String updatedValues = groups[3];
//        String condition = groups[5];
//        String op = groups[6];
//
//        String[] columns = updatedValues.split(",");
//
//        HashMap<String, String> map = new HashMap<>();
//        for (int i = 0; i < columns.length; i++) {
//            String[] pair = columns[i].split("\\s*=\\s*");
//            map.put(pair[0].replace("`", ""), pair[1].replace("`", ""));
//        }
//        String[] splitCondition = condition.split("\\s*[=><]{1,2}\\s*");
//        Filter filter = createSelectFilter(new String[]{splitCondition[0].replace("`", ""),
//                op,
//                splitCondition[1].replace("`", "")});
//
//        Query query = new UpdateDatabaseQuery(filter, new Row(map));
//        DatabaseManager manager = DatabaseManager.getInstance();
//        manager.setDatabase(databaseName);
//        manager.setTable(tableName);
//        manager.setQuery(query);
//        return manager;
//    }

}
