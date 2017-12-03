package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxValidator {
    public static final String TABLES_NAMES = "[a-zA-Z,\\.\\'\\\"\\`]+";
    public static final String DATABASE_NAME = "[a-zA-Z\\.\\'\\\"\\`]+";
    public static final String CONDITION = "\\s*( where| WHERE)\\s+([a-zA-Z\\.\\'\"\\`]+)\\s+\"" +
            "([<>=]{1,2})\\s+([a-zA-Z0-9\\.\\'\\\"\\`]+)\\s*";

    public static final String SELECT_PATTERN = "([a-zA-Z]+)\\s(\\*|" + TABLES_NAMES + ")\\s(from|FROM)\\s" +
            "(" + DATABASE_NAME + ")(" + CONDITION + ")*";

    public static final String CREATE_PATTERN = "\\s*(?i)create\\s+(table|database)(?-i)\\s*([a-zA-Z_\\`]+)\\.{0,1}" +
            "([a-zA-Z_\\`]*)" +
            "\\s*\\((\\s*([a-zA-Z_\\`]+\\s+[a-zA-Z]+\\s*,*\\s*)+\\s*)\\){0,1}\\s*;{0,1}\\s*";

    public static final String INSERT_PATTERN = "\\s*([a-zA-Z]+)\\s+(?i)into(?-i)\\s+([a-zA-Z_\\\\`]+)\\." +
            "([a-zA-Z_\\`]+)\\s+" +
            "\\(([a-zA-Z_\\`,\\s]+)\\)\\s+(?i)values(?-i)\\s+\\(([a-zA-Z\\',\\s0-9]+)\\s*\\)\\s*;{0,1}\\s*";

    public static final String UPDATE_PATTERN = "\\s*([a-zA-Z]+)\\s+([a-zA-Z_\\\\`]+)\\.([a-zA-Z_\\`]+)\\s+(?i)set" +
            "(?-i)" +
            "\\s+(" +
            "([a-zA-Z\\`\\s0-9_]+\\s" +
            "*=[a-zA-Z\\'\\s0-9]+\\s*,{0,1})+)\\s*(?i)where(?-i)\\s*([a-zA-Z\\`\\s0-9]+([=<>]{1,2})[a-zA-Z_0-9\\']+)" +
            "\\s*;{0,1}\\s*";

    public static String[] validateAndGroupInput(String input, String patternExpression) throws InvalidQuerySyntaxException {
        Pattern pattern = Pattern.compile(patternExpression);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new InvalidQuerySyntaxException(input);
        }

        String[] groups = new String[matcher.groupCount()];
        for (int i = 0; i < groups.length; i++) {
            if (matcher.group(i + 1) == null) {
                groups[i] = null;
                continue;
            }
            groups[i] = matcher.group(i + 1).replace("`", "").replace("'", "");
        }
        return groups;
    }


}
