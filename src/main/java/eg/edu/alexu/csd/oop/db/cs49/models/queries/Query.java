package eg.edu.alexu.csd.oop.db.cs49.models.queries;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Result;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.models.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public abstract class Query implements Visitor {
    protected Table target;
    protected Filter filter;
    protected Filter selectFilter;
    protected Result result;

    public Query() {
        result = new Result();
    }

    public Table getTarget() {
        return target;
    }

    public void setTarget(final Table target) {
        this.target = target;
    }

    public void setFilter(final Filter filter) {
        this.filter = filter;
    }

    public void setSelectFilter(final Filter selectFilter) {
        this.selectFilter = selectFilter;
    }

    protected List<Row> getRowsFromFilterables(final List<Filter.Filterable> filterables) {
        List<Row> rows = new ArrayList<>();
        for (Filter.Filterable filterable : filterables) {
            rows.add((Row) filterable);
        }
        return rows;
    }

    public List<Row> getResultRows() {
        return result.getResultRows();
    }

    public boolean isSuccess() {
        return result.isSuccess();
    }

    public int getRowsCount() {
        return result.getRowsCount();
    }

    public String getDatabasePath() {
        return result.getDatabasePath();
    }

    public Result getResult() {
        return result;
    }
}
