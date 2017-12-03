package eg.edu.alexu.csd.oop.db.cs49.models.queries;

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
import java.util.Map;

import static eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result.UPDATE_COUNT;

public class InsertDatabaseQuery extends Query {


    public InsertDatabaseQuery() {
    }

    @Override
    public void visit(final Host host) throws SQLException {
        Database db = (Database) host;
        Table table = db.getTable();
        Row row = table.getSetRow();
        try {
            if (!Validator.validate(table, row.getFieldsIndexedByColumnName())) {
                throw new SQLException("Invalid input");
            }
            TableLoader.insert(row, table);
            result.setRowsCount(1);
            result.setSuccess(true);
            result.setResultType(UPDATE_COUNT);
        } catch (FileNotFoundException e) {
            throw new SQLException("Table not created");
        } catch (XMLStreamException e) {
            throw new SQLException("Table is corrupted");
        } catch (IOException e) {
            throw new SQLException("Table not created");
        }
    }
}
