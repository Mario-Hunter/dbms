package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validator;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UpdateDatabaseQuery extends Query {
    private Row updatedRow;

    public UpdateDatabaseQuery(final Filter filter, Row updatedRow) {
        this.filter = filter;
        this.updatedRow = updatedRow;
    }

    public UpdateDatabaseQuery() {


    }

    @Override
    public void visit(final Host host) throws SQLException {
        Database db = (Database) host;
        Table table = db.getTable();
        Row updatedRow = table.getSetRow();

        try {
            TableLoader.deserialize(table);
            if (!Validator.validate(table, updatedRow.getFieldsIndexedByColumnName())) {
                throw new SQLException("Invalid input");
            }

            List<Filter.Filterable> rows = filter.meetCriteria(table.getFilterableRows());
            for (Filter.Filterable row : rows) {
                ((Row) row).updateValues(updatedRow);
            }
            TableLoader.createTable(table);
            result.setRowsCount(rows.size());
        } catch (FileNotFoundException e) {
            throw new SQLException("Error loading table");
        } catch (XMLStreamException e) {
            throw new SQLException("Table is corrupted created");
        } catch (IOException e) {
            throw new SQLException("Table is not found");
        } catch (InvalidCondition invalidCondition) {
            throw new SQLException("The condition is invalid");
        }
    }
}
