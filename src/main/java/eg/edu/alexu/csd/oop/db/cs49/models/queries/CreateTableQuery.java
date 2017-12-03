package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.Utilities.FileParser;
import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;
import eg.edu.alexu.csd.oop.db.cs49.utilities.STAXUtilities;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader.FILE_SEPARATOR;

public class CreateTableQuery extends Query {



    public CreateTableQuery() {

    }

    @Override
    public void visit(Host host) throws SQLException {
        Database db = (Database) host;
        Table table = db.getTable();
        String dtd = STAXUtilities.getDTD(table.getOrderedColumns());

        try {
            result.setDatabasePath(TableLoader.createTable(table));
            result.setSuccess(true);

            FileParser.save(dtd, db.getAbsolutePath() + FILE_SEPARATOR + table.getName() + ".dtd");

        } catch (XMLStreamException e) {
            throw new SQLException("Table is corrupted");
        } catch (IOException e) {
            throw new SQLException("Error creating the database");
        }
    }
}
