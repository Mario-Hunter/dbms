package eg.edu.alexu.csd.oop.db.cs49.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Logger {
    public static final int QUERY = 0;
    public static final int SET_DATABASE_NAME = 1;
    public static final int SET_TABLE_NAME = 2;
    public static final int SET_COLUMNS_AND_TYPES = 3;
    public static final int SET_COLUMNS_AND_VALUES = 4;
    public static final int CONDITIONS = 5;
    public static final int SELECT_CONDITIONS = 6;

    private static final Queue<Integer> log = new PriorityQueue<>();

    public static  void log(Integer event){
        log.offer(event);
    }

    public static  Iterator<Integer> getLogs(){
        return log.iterator();
    }

    public static  Queue<Integer> getLogsQueue() {
        return log;
    }
}
