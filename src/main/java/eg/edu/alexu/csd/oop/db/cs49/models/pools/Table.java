package eg.edu.alexu.csd.oop.db.cs49.models.pools;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Visitor;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table implements Host {
    private List<Row> rows;
    private String path;
    private List<Filter.Filterable> filterbaleRows;
    private String name;
    private Database database;
    private List<Column> columns;
    private Row setRow;
    private Map<Integer, String> columnsOrderMap;

    public List<Row> getRows() {
        return rows;
    }

    public Table(final String name) {
        this.name = name;
        rows = new ArrayList<>();
        filterbaleRows = new ArrayList<>();
    }

    public void addRow(final Row rw) {
        rows.add(rw);

    }

    public void load() throws FileNotFoundException, XMLStreamException {
        rows = TableLoader.deserialize(path);
    }

    public List<Filter.Filterable> getFilterableRows() {
        List<Filter.Filterable> filterables = new ArrayList<>(rows.size());
        filterables.addAll(rows);
        return filterbaleRows;
    }

    private List<Filter.Filterable> orderedFilterables() {

        return null;
    }

    public String getName() {
        return name;
    }

    public Database getDatabase() {
        return database;
    }

    public String getDatabaseName() {
        return database.getName();
    }

    public void setDatabase(final Database database) {
        this.database = database;
    }

    public void setRows(final List<Row> rows) {
        this.rows = rows;
        for (Row row : rows) {
            filterbaleRows.add(row);
        }
    }

    public void accept(final Visitor query) throws SQLException {
        query.visit(this);
    }

    public void setColumnsAndTypes(final Map<String, String> columnsAndTypes) {
        columns = new ArrayList();
//        columnsAndTypes.forEach((k, v) -> columns.add(new Column(k, v)));
        for (Map.Entry<String, String> entry : columnsAndTypes.entrySet()) {
            columns.add(new Column(entry.getKey(), entry.getValue()));
        }
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumnsAndValues(final Map<String, String> columnsAndValues) {
        Row row = new Row(columnsAndValues);
        setRow = row;

    }

    public Row getSetRow() {
        return setRow;
    }

    public void deleteRows(final List<Filter.Filterable> rows) {
        for (Filter.Filterable row : rows) {
            this.rows.remove(row);
        }
    }

    public void setColumnsOrder(final List<String> columnsOrder) {
        for (Row row : rows) {
            row.setColumnsOrder(columnsOrder);
        }
    }

    public void setColumnsOrder(final Map<Integer, String> columnsOrder) {
        columnsOrderMap = columnsOrder;
    }

    public List<Column> getOrderedColumns() {
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < this.columns.size(); i++) {
            columns.add(getColumnWithName(columnsOrderMap.get(i)));
        }
        return columns;
    }

    private Column getColumnWithName(final String s) {
        for(Column col : columns){
            if(col.getName().equals(s)){
                return col;
            }
        }
        return null;
    }
}
