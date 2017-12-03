package eg.edu.alexu.csd.oop.draw.cs49.models.JSONParser;


import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    private Class cls;
    private Field[] fields;
    private Map<String, String> map;

    public JsonParser(Class cls) {
        this.cls = cls;
        this.fields = cls.getDeclaredFields();
        map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), "");
        }
    }

    public String parseJson(Object obj) {
        if (!cls.isInstance(obj)) return null;

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (Collection.class.isAssignableFrom(field.get(obj).getClass())) {
                    StringBuilder data = new StringBuilder();
                    data.append("[");
                    for (Object subObj : (Collection) field.get(obj)) {
                        data.append(loadObject(subObj));
                        data.append(",");
                    }
                    data.deleteCharAt(data.length() - 1);
                    data.append("]");
                    map.put(field.getName(), data.toString());
                } else {
                    map.put(field.getName(), field.get(obj).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map.toString();
    }

    private String loadObject(Object subObj) {
        JsonParser parser = new JsonParser(subObj.getClass());
        return parser.parseJson(subObj);
    }
}
