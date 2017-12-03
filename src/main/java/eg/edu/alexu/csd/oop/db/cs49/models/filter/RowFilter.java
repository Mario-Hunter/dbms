package eg.edu.alexu.csd.oop.db.cs49.models.filter;


import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.RowCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;

import java.util.ArrayList;
import java.util.List;

public class RowFilter implements Filter {
    private RowCriteria criteria;

    public RowFilter(final RowCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public List<Filterable> meetCriteria(final List<Filterable> elementsToFiler) throws InvalidCondition {
        List<Filterable> rows = new ArrayList();
        for (Filterable row : elementsToFiler) {
            try {
                if (criteria.test((Row) row)) rows.add(row);
            }catch (NullPointerException e ){
                if(criteria == null)
                throw new InvalidCondition("criteria is null");
                if(criteria.getColumn() == null)
                    throw new InvalidCondition("column is null");
                if(((Row) row).getFieldOfName(criteria.getColumn()) == null){
                    throw new InvalidCondition("field of "+criteria.getColumn()+" is null");
                }

            }
        }
        return rows;
    }
}
