package eg.edu.alexu.csd.oop.db.cs49.utilities;

import eg.edu.alexu.csd.oop.Utilities.FileParser;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class STAXUtilities {
    private static final char NEW_LINE = '\n';

    public static String getDTD(final List<Column> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE TABLE[");
        sb.append(NEW_LINE);
        sb.append("<!ELEMENT ROW (");
        for (Column column : columns) {
            sb.append(column.getName() + "," + " ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")>");
        sb.append(NEW_LINE);
        for (Column column : columns) {
            sb.append("<!ELEMENT " + column.getName() + " (#CDATA)>");
            sb.append(NEW_LINE);
        }
        for (Column column : columns) {
            sb.append("<!ATTLIST " + column.getName() + " type CDATA #FIXED " + column.getTypeAsString() + ">");
            sb.append(NEW_LINE);
        }
        sb.append("]>");
        return sb.toString();
    }

    public static Map<String, Column> getColumns(final String pathToDTD) throws FileNotFoundException,
            XMLStreamException {
        String content = FileParser.load(pathToDTD);
        String[] lines = content.split("\n");
        Map<String, Column> columnMap = new HashMap<>();
        for (String line : lines) {
            if (line.startsWith("<!ELEMENT ROW")) {
                String columns = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String[] columnsArray = columns.split(",");
                for (String column : columnsArray) {
                    Column co = new Column();
                    co.setName(column);
                    columnMap.put(column.toLowerCase().trim(), co);
                }
            } else if (line.startsWith("<!ATTLIST")) {
                int startIndex = line.indexOf("ATTLIST") + 8;
                int endIndex = line.indexOf(" ", startIndex);
                String columnName = line.substring(startIndex, endIndex);
                int typeStartIndex = line.indexOf(" ", line.indexOf("#FIXED")) + 1;
                columnMap.get(columnName.toLowerCase()).setType(line.substring(typeStartIndex, line.indexOf(">")));
            }
        }
        return columnMap;
    }

    public static List<String> getColumnsOrder(final String pathToDTD) {
        String content = FileParser.load(pathToDTD);
        String[] lines = content.split("\n");
        List<String> columnMap = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("<!ELEMENT ROW")) {
                String columns = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String[] columnsArray = columns.split(",");
                for (int i = 0; i < columnsArray.length; i++) {
                    columnMap.add(columnsArray[i].toLowerCase().trim());
                }
            }
        }
        return columnMap;
    }

    private static Column getColumnByName(final List<Column> columnList, final String columnName) {
        for (Column column : columnList) {
            if (column.getName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }
}
