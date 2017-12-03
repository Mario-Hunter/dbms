package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

import java.util.HashMap;
import java.util.Map;

import static eg.edu.alexu.csd.oop.db.cs49.models.Context.CREATE_TABLE;

class ColumnsAndTypesInterpreter implements Interpreter {
    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        if (context.getColumnsAndTypes().isEmpty()) {
            if(context.getCommand().equals(CREATE_TABLE)){
                throw new InvalidQuerySyntaxException("missing columns");
            }
            return;
        }

        String[] columnsAndTypes = context.getColumnsAndTypes().split(",");

        Map<String, String> columnMap = new HashMap<>();
        Map<Integer,String> columnsOrder = new HashMap<>();
        int i =0;
        for (String col : columnsAndTypes) {
            String[] colAndType = col.trim().split("\\s+");
            if (colAndType.length != 2) {
                throw new InvalidQuerySyntaxException("Syntax error in " + col);
            }
            columnMap.put(colAndType[0].trim().toLowerCase(), colAndType[1].toLowerCase().trim());
            columnsOrder.put(i,colAndType[0].trim().toLowerCase());
            i++;
        }

        context.setColumnsAndTypesMap(columnMap);
        context.setColumnsOrder(columnsOrder);
    }
}
