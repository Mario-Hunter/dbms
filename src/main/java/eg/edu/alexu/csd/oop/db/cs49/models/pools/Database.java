package eg.edu.alexu.csd.oop.db.cs49.models.pools;

import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Visitor;

import java.sql.SQLException;
import java.util.ArrayList;

public class Database implements Host {
    private String name;
    private ArrayList<Table> tables;
    private Table focusedTable;
    private String absolutePath;
    private String input;

    public Database(final String databaseName) {
        name = databaseName;
    }

    @Override
    public void accept(final Visitor visitor) throws SQLException {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public Table getTable() {
        return focusedTable;
    }

    public void setFocusedTable(final Table focusedTable) {
        this.focusedTable = focusedTable;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setAbsolutePath(final String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getInput() {
        return input;
    }

    public void setInput(final String input) {
        this.input = input;
    }
}
