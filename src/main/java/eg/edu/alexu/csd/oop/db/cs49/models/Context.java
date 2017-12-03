package eg.edu.alexu.csd.oop.db.cs49.models;

import java.util.Map;
import java.util.Stack;

import static eg.edu.alexu.csd.oop.db.cs49.models.Logger.*;

public class Context {
    private String input;
    private static Context mContext;
    private String database;
    private String table;
    private String command;
    private String targetName;
    private String columnsAndTypes;
    private Map<String, String> columnsAndTypesMap;
    private String columns;
    private String values;
    private Map<String, String> columnsAndValuesMap;
    public final static String CREATE_TABLE = "create table";
    public final static String CREATE_DATABASE = "create database";
    public final static String DROP_TABLE = "drop table";
    public final static String DROP_DATABASE = "drop database";
    public final static String INSERT = "insert";
    public final static String UPDATE = "update";
    public final static String DELETE = "delete";
    public final static String SELECT = "select";
    private String conditions;
    private Stack<String> postfixConditions;
    private String[] selectColumns;
    private Map<Integer, String> columnsOrder;


    public void setDatabase(final String database) {
        this.database = database;
        Logger.log(SET_DATABASE_NAME);
    }

    public void setTable(final String table) {
        this.table = table;
        Logger.log(SET_TABLE_NAME);
    }

    public void setCommand(final String command) {
        this.command = command;
        Logger.log(QUERY);
    }

    private Context(String input) {
        this.input = input.trim().toLowerCase();
    }

    public static Context getInstance(String input) {
        if (mContext == null) {
            mContext = new Context(input);
        } else {
            mContext.setInput(input);
        }
        return mContext;
    }

    public void setInput(final String input) {
        this.input = input;

    }

    public String getInput() {
        return input;
    }

    public void setTargetName(final String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setColumnsAndTypes(final String columnsAndTypes) {
        this.columnsAndTypes = columnsAndTypes;
    }

    public String getColumnsAndTypes() {
        return columnsAndTypes;
    }

    public void setColumnsAndTypesMap(final Map<String, String> columnsAndTypesMap) {
        this.columnsAndTypesMap = columnsAndTypesMap;
        Logger.log(SET_COLUMNS_AND_TYPES);
    }

    public void setColumns(final String columns) {
        this.columns = columns;
    }

    public void setValues(final String values) {
        this.values = values;
    }

    public String getColumns() {
        return columns;
    }

    public String getValues() {
        return values;
    }

    public void setColumnsAndValuesMap(final Map<String, String> columnsAndValuesMap) {
        this.columnsAndValuesMap = columnsAndValuesMap;
        Logger.log(SET_COLUMNS_AND_VALUES);

    }

    public String getCommand() {
        return command;
    }

    public String getTableName() {
        return table;
    }

    public String getDatabaseName() {
        return database;
    }

    public Map<String, String> getColumnsAndTypesMap() {
        return columnsAndTypesMap;
    }

    public Map<String, String> getColumnsAndValues() {
        return columnsAndValuesMap;
    }

    public void setConditions(final String conditions) {
        this.conditions = conditions;
    }

    public String getConditions() {
        return conditions;
    }

    public void setPostfixConditions(final Stack<String> postfixConditions) {
        this.postfixConditions = postfixConditions;
        Logger.log(CONDITIONS);
    }

    public Stack<String> getPostfixConditions() {
        return postfixConditions;
    }

    public void setSelectColumns(final String[] selectColumns) {
        this.selectColumns = selectColumns;
        Logger.log(SELECT_CONDITIONS);
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    public void setColumnsOrder(final Map<Integer, String> columnsOrder) {
        this.columnsOrder = columnsOrder;
    }

    public Map<Integer, String> getColumnsOrder() {
        return columnsOrder;
    }
}
