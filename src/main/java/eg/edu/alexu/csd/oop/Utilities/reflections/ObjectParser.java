package eg.edu.alexu.csd.oop.Utilities;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ObjectParser<T> {
    public static final int JSON_PARSE = 0;
    public static final int XML_PARSE = 1;
    public static final int JSON_DECODE = 2;
    public static final int XML_DECODE = 3;
    private final Class<T> type;

    public ObjectParser(Class<T> type) {
        this.type = type;
    }

    public void serialize(int parseType, List<T> objects, File file,
                          List<Class> clsses, TypeToken... token) {
        switch (parseType) {
            case XML_PARSE:
                parseXML(objects, file, clsses);
                break;
            case JSON_PARSE:
                parseJSON(objects, file, clsses, token[0]);
                break;
        }
    }

    public List<T> deserialize(int decodeType, File file, List<Class> clsses, TypeToken... token) {
        if (!file.exists()) throw new RuntimeException("Can not find file");
        switch (decodeType) {
            case JSON_DECODE:
                 return  loadJson(file, clsses,token[0]);//, token[0]);
            case XML_DECODE:
                return loadXML(file, clsses);
            default:
                return null;
        }
    }

    private Gson createCustomGson(List<Class> supportedShapes) {

        final RuntimeTypeAdapterFactory<T> typeFactory =
                RuntimeTypeAdapterFactory
                        .of(type, "type");
        for (Class cls : supportedShapes) {
            typeFactory.registerSubtype(cls, cls.getSimpleName());
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(typeFactory)
                .setPrettyPrinting().create();
        return gson;
    }

    private void parseJSON(final List<T> objects, final File file, final
    List<Class> clsses, TypeToken token) {
        Gson gson = createCustomGson(clsses);
        String jsonString = gson.toJson(objects, token.getType());
        FileParser.save(jsonString, file.getPath());
    }

    private List<T> loadJson(File file, List<Class> clsses, TypeToken token) {
        Gson gson = createCustomGson(clsses);
        List<T> objects = gson.fromJson(FileParser.load(file.getPath()),
                token.getType());
        return objects;
    }

    private void parseXML(final List<T> objects, final File file, final List<Class> clsses) {
        SaveableList<T> list = new SaveableList<>(objects);
        clsses.add(SaveableList.class);
        Class[] classArray = new Class[clsses.size()];
        try {
            clsses.toArray(classArray);
            JAXBContext context = JAXBContext.newInstance(classArray);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(list, file);
            throw new RuntimeException("line start" + FileParser.load(file.getPath()) + "line end");

        } catch (JAXBException e) {

            throw new RuntimeException(e.getCause());

        }
    }

    public List<T> loadXML(File file, List<Class> clsses) {

        clsses.add(SaveableList.class);
        clsses.add(SaveableList.class);
        Class[] classArray = new Class[clsses.size()];
        clsses.toArray(classArray);
        try {
            JAXBContext context = JAXBContext.newInstance(classArray);
            Unmarshaller unmarshaller = context.createUnmarshaller();


            SaveableList<T> list = (SaveableList<T>) unmarshaller.unmarshal(file);

            return list.elements;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
