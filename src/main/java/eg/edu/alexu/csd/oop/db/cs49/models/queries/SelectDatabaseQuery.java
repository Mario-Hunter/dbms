package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validator;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;
import eg.edu.alexu.csd.oop.db.cs49.utilities.STAXUtilities;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result.SELECT_ROWS;
import static eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader.FILE_SEPARATOR;

public class SelectDatabaseQuery extends Query {

    public SelectDatabaseQuery() {
    }

    @Override
    public void visit(Host host) throws SQLException {
        Database db = (Database) host;
        Table table = db.getTable();
        try {
            TableLoader.deserialize(table);
            Validator.validateAndCastRows(table);
            table.setColumnsOrder(STAXUtilities.getColumnsOrder(db.getAbsolutePath() + FILE_SEPARATOR +
                    table.getName() + ".dtd"));
            List<Filter.Filterable> selectedRows = filter.meetCriteria(table.getFilterableRows());
            List<Row> rows  = getRowsFromFilterables(selectFilter.meetCriteria(selectedRows));
            result.setResultRows(rows);
            result.setRowsCount(rows.size());
            result.setSuccess(true);
            result.setResultType(SELECT_ROWS);
        } catch (FileNotFoundException e) {
            throw new SQLException("table doesn't exist");
        } catch (XMLStreamException e) {
            throw new SQLException("There was an error loading the table");
        } catch (InvalidCondition invalidCondition) {
            throw new SQLException("Condition is Invalid");
        }
    }


}
