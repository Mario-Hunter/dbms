package eg.edu.alexu.csd.oop.db.cs49.models.filter;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnFilter implements Filter {

    private Map<String, ColumnCriteria> criteriasMap;

    public ColumnFilter(final Map<String, ColumnCriteria> criteriaMap) {
        this.criteriasMap = criteriaMap;
    }


    @Override
    public List<Filterable> meetCriteria(List<Filterable> elementsToFiler) throws InvalidCondition {
        List<Filterable> rows = new ArrayList();
        for (Filterable row : elementsToFiler) {
            Row filteredRow = new Row();
            for (Map.Entry<Column, Field> entry : ((Row) row).getFields().entrySet()) {
                if (criteriasMap.containsKey(entry.getKey().getName())) {
                    if (criteriasMap.get(entry.getKey().getName()).test(entry.getValue())) {
                        filteredRow.getFields().put(entry.getKey(), entry.getValue());
                    }
                } else if (criteriasMap.get("*") != null) {
                    filteredRow.getFields().put(entry.getKey(), entry.getValue());
                }
            }
            filteredRow.setColumnsOrder(((Row) row).getColumnsOrder());
            filteredRow.setTableName(((Row) row).getTable());
            rows.add(filteredRow);
        }
        return rows;
    }
}
