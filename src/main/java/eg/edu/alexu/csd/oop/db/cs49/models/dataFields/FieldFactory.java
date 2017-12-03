package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Type;

public class FieldFactory {

    public static Field getField(final Type type, final String name, final String value) throws
            ValueAndTypeMismatchException {
        switch (type){
            case INT:
                return new IntField(value,name);
            case varChar:
                return new VarCharField(value,name);
        }
        return null;
    }
}
