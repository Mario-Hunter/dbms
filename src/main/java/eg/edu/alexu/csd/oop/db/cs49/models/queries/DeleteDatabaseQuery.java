package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result.UPDATE_COUNT;

public class DeleteDatabaseQuery extends Query {

    @Override
    public void visit(final Host host) throws SQLException {
        Database db = (Database) host;
        Table table = db.getTable();
        try {
            TableLoader.deserialize(table);
            List<Filter.Filterable> rows = filter.meetCriteria(table.getFilterableRows());
            table.deleteRows(rows);
            TableLoader.createTable(table);
            result.setRowsCount(rows.size());
            result.setSuccess(true);
            result.setResultType(UPDATE_COUNT);
        } catch (FileNotFoundException e) {
            throw new SQLException("Table not created");
        } catch (XMLStreamException e) {
            throw new SQLException("Table is corrupted created");
        } catch (InvalidCondition invalidCondition) {
            throw new SQLException("Condition is invalid");
        } catch (IOException e) {
            throw new SQLException("Table is not found");
        }
    }
}