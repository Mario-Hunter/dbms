package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import static eg.edu.alexu.csd.oop.db.cs49.models.Context.*;

public class QueryFactory {

    public static Query getQuery(String query, String... Args) {
        switch (query) {
            case CREATE_DATABASE:
                return new CreateDatabaseQuery();
            case CREATE_TABLE:
                return new CreateTableQuery();
            case DROP_DATABASE:
                return new DropDatabaseQuery();
            case DROP_TABLE:
                return new DropTableQuery();
            case INSERT:
                return new InsertDatabaseQuery();
            case UPDATE:
                return new UpdateDatabaseQuery();
            case DELETE:
                return new DeleteDatabaseQuery();
            case SELECT:
                return new SelectDatabaseQuery();
            default:
                return null;
        }
    }
}
