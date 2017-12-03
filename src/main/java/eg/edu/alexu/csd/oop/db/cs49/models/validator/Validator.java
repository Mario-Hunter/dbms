package eg.edu.alexu.csd.oop.db.cs49.models.validator;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.FieldFactory;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.ValueAndTypeMismatchException;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.utilities.STAXUtilities;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Validator {

    public static boolean validate(final Table table, Map<String, Validatable> toValidateElements)
            throws FileNotFoundException, XMLStreamException {
        return validate(table, toValidateElements, false);
    }

    public static boolean validate(final Table table, Map<String, Validatable> toValidateElements, boolean
            isContainingAllCheck)
            throws FileNotFoundException, XMLStreamException {
        Map<String, Validatable> roleModels = loadRoleModelsFor(table);

        for (Map.Entry<String, Validatable> entry : toValidateElements.entrySet()) {
            if (!roleModels.containsKey(entry.getKey()) || !entry.getValue().validate(roleModels.get(entry.getKey()))) {
                return false;
            }
        }
        if (isContainingAllCheck && !toValidateElements.keySet().containsAll(roleModels.keySet())) return false;
        return true;
    }

    private static Map<String, Validatable> loadRoleModelsFor(final Table table)
            throws FileNotFoundException, XMLStreamException {
        final Map<String, Column> DTDvalidators = STAXUtilities.getColumns(table.getDatabase().getAbsolutePath() + "/" +
                table.getName() + ".dtd");
        final Map<String, Validatable> validators = new HashMap<>();
        for (Map.Entry<String, Column> entry : DTDvalidators.entrySet()) {
            validators.put(entry.getKey(), entry.getValue());
        }
        return validators;
    }

    public static void validateAndCastRows(final Table table) throws FileNotFoundException, XMLStreamException {
        Map<String, Validatable> roleModels = loadRoleModelsFor(table);
        for (Row row : table.getRows()) {
            if (!validate(table, row.getFieldsIndexedByColumnName())) {
                return;
            }
            for (Map.Entry<Column, Field> entry : row.getFields().entrySet()) {
                Field field = entry.getValue();
                Column correctType = (Column) roleModels.get(entry.getKey().getName());
                try {
                    Field castedField = FieldFactory.getField(correctType.getType(), field.getColumnName(), field.toString());
                    row.getFields().put(entry.getKey(), castedField);
                } catch (ValueAndTypeMismatchException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
