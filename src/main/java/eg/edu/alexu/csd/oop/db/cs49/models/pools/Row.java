package eg.edu.alexu.csd.oop.db.cs49.models.pools;


import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.ValueAndTypeMismatchException;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.VarCharField;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Filter;
import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validatable;

import java.util.*;

public class Row implements Filter.Filterable {
    private int rowNumber;
    private int id;
    private Map<String, String> values;
    private Map<Column, Field> rowValues;
    private List<String> columnsOrder;
    private String table;

    public Row() {
        rowValues = new HashMap<>();
        values = new HashMap<>();
    }

    public Row(final Map<String, String> newValues) {
        this();
        this.values = newValues;
        updateRowField();
    }

    public void setRowNumber(final int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setValues(final Map<String, String> values) {
        this.values = values;
    }

    public int getRowNumber() {

        return rowNumber;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public Map<Column, Field> getFields() {
        return rowValues;
    }

    public Map<String, Validatable> getFieldsIndexedByColumnName() {
        Map<String, Validatable> map = new HashMap<>();
        for (Map.Entry<Column, Field> entry : rowValues.entrySet()) {
            map.put(entry.getKey().getName(), entry.getValue());
        }
        return map;
    }

    public void updateValues(final Row updatedRow) {
        for (Map.Entry<Column, Field> entry : updatedRow.getFields().entrySet()) {
            this.values.put(entry.getKey().getName(), (String) entry.getValue().getValue());
        }
        updateRowField();
    }

    private void updateRowField() {
        rowValues.clear();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            try {
                Field field = new VarCharField(entry.getValue().trim(), entry.getKey().trim().toLowerCase());
                Column column = new Column();
                column.setName(entry.getKey().trim());
                column.setType("varchar");
                rowValues.put(column, field);
            } catch (ValueAndTypeMismatchException valueAndTypeMismatchException) {
                valueAndTypeMismatchException.printStackTrace();
            }
        }
    }

    public Field getFieldOfName(String column){
        for(Map.Entry<Column,Field> entry: this.rowValues.entrySet()){
            if(entry.getKey().getName().equals(column)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void setColumnsOrder(final List<String> columnsOrder) {
        this.columnsOrder = columnsOrder;
    }

    public List<Field> getOrderedFields(){
        Field[] fields = new Field[this.rowValues.size()];
        int j=0;
        for(int i=0;i<fields.length;i++){
            Field element = null;
            while(element == null){
                element = getFieldOfName(columnsOrder.get(j));
                j++;
            }
            fields[i] = element;
        }
        return Arrays.asList(fields);
    }

    public List<String> getColumnsOrder() {
        return columnsOrder;
    }

    public int findColumn(String columnLabel) {
        List<Field> orderedFields = getOrderedFields();
        for(int i=0;i<orderedFields.size();i++){
            if(orderedFields.get(i).getColumnName().equals(columnLabel)){
                return i;
            }
        }
        return -1;
    }

    public String getTable() {
        return table;
    }

    public void setTableName(final String tableName) {
        this.table = tableName;
    }
}
