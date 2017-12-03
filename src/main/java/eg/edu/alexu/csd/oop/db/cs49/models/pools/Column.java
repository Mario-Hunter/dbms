package eg.edu.alexu.csd.oop.db.cs49.models.pools;

import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validatable;

public class Column implements Validatable {
    private String name;
    private Table parentTable;
    private Type sqlType;
    private String table;

    public Column(final String name, final String type) {
        this.name = name;
        sqlType = Type.getInstance(type.trim().toLowerCase());
    }

    public Column() {
    }

    public String getName() {
        return name;
    }

    public void setTable(final String table) {
        this.table = table;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final String type) {
        sqlType = Type.getInstance(type);
    }

    public String getTypeAsString() {
        return sqlType.name();
    }

    public Type getType() {
        return sqlType;
    }

    @Override
    public Object getValidationField() {
        return sqlType;
    }

    @Override
    public boolean validate(final Validatable roleModel) {
        return roleModel.getValidationField() == sqlType;
    }
}
