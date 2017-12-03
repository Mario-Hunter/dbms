package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;

import java.sql.SQLException;

public class DropDatabaseQuery extends Query {
    @Override
    public void visit(final Host host) throws SQLException {
        Database db = (Database) host;
        result.setSuccess(TableLoader.dropDatabase(db.getName()));
        result.setSuccess(true);

    }
}
