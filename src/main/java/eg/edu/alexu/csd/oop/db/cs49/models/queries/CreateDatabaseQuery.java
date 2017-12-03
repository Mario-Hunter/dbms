package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.db.cs49.models.io.TableLoader;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Database;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Host;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Visitor;

import java.io.File;
import java.sql.SQLException;

public class CreateDatabaseQuery extends Query {
    private String name;
    private boolean dropIfExists;

    public CreateDatabaseQuery(String name, boolean dropIfExists) {
        this.name = name;
        this.dropIfExists = dropIfExists;
    }

    public CreateDatabaseQuery() {

    }

    @Override
    public void visit(Host host) throws SQLException {
        Database db = (Database) host;

        result.setDatabasePath(TableLoader.createDatabase(db.getName(), dropIfExists));
        result.setSuccess(true);

        db.setAbsolutePath(result.getDatabasePath());
    }
}
