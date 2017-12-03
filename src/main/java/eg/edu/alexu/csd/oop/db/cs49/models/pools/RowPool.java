package eg.edu.alexu.csd.oop.db.cs49.models.pools;

public class RowPool {
    private static RowPool pool;

    private RowPool() {
    }

    public static RowPool getInstance(){
        if(pool == null){
            pool = new RowPool();
        }
        return pool;
    }
}
