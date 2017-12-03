package eg.edu.alexu.csd.oop.db.cs49.models.pools;

public enum Type {
    varChar,
    INT;

    public static Type getInstance(final String type) {
        switch (type.toLowerCase()){
            case "int":
                return INT;
            case "varchar":
                return  varChar;
        }
        return null;
    }
}
