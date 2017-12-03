package eg.edu.alexu.csd.oop.db.cs49.models.visitor;

import java.sql.SQLException;

public interface Host {
    void accept(Visitor visitor) throws SQLException;
}
