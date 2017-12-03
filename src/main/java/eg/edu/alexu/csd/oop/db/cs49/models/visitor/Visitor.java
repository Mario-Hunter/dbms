package eg.edu.alexu.csd.oop.db.cs49.models.visitor;


import java.sql.SQLException;

public interface Visitor {
    void visit(Host host) throws SQLException;
}
