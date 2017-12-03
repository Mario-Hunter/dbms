package eg.edu.alexu.csd.oop.db.cs49.models.io;

import eg.edu.alexu.csd.oop.Utilities.FileUtilities;
import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Column;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.RowPool;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableLoader {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private RowPool rowPool;
    private static Object lock = new Object();

    public static List<Row> deserialize(String path) throws FileNotFoundException, XMLStreamException {
        synchronized (lock) {
            path = path + ".xml";
            FileReader file = new FileReader(path);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(file);

            List<Row> rows = new ArrayList<>();
            String qName = "";
            Map<String, String> map = new HashMap<>();
            while (reader.hasNext()) {
                XMLEvent event = (XMLEvent) reader.next();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement element = event.asStartElement();
                        if (element.getName().getLocalPart().equals("table")) break;
                        if (element.getName().getLocalPart().equals("row")) rows.add(readRow(reader));
                        qName = element.getName().getLocalPart();
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (characters.toString().contains("\n")) break;
                        map.put(qName, characters.toString());
                        break;
                }
            }
            return rows;
        }
    }

    private static Row readRow(final XMLEventReader reader) {
        synchronized (lock) {
            Map<String, String> map = new HashMap<>();
            String qName = "";
            boolean inRow = true;
            while (inRow) {
                XMLEvent event = (XMLEvent) reader.next();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement element = event.asStartElement();
                        qName = element.getName().getLocalPart();
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (characters.toString().contains("\n")) break;
                        map.put(qName, characters.toString());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if ((endElement.getName().getLocalPart()).equals("row")) {
                            inRow = false;
                        }
                        break;

                }
            }
            return new Row(map);
        }
    }

    public static String createDatabase(String name, boolean dropIfExists) {
        synchronized (lock) {
            File dir = new File("databases" + FILE_SEPARATOR + name);
            if (!dir.exists()) {
                while (!dir.mkdirs() || !dir.exists()) ;
            } else if (dropIfExists) {
                dir.delete();
                while (!dir.mkdirs() || !dir.exists()) ;
            }
            return dir.getAbsolutePath();
        }
    }

    public static String createTable(final Table table) throws XMLStreamException, IOException {
        synchronized (lock) {
            Writer sb = new StringWriter();
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLEventFactory eventFactory = XMLEventFactory.newFactory().newInstance();
            XMLEventWriter writer = factory.createXMLEventWriter(sb);
            factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);

            XMLEvent event = eventFactory.createStartDocument();
            writer.add(event);

            Namespace tableNamespace = eventFactory.createNamespace("db", "http://db");

            event = eventFactory.createStartElement(tableNamespace.getPrefix(), tableNamespace.getNamespaceURI(), "table");
            writer.add(event);
            writer.add(tableNamespace);

            for (Row rw : table.getRows()) {
                write(rw, writer, eventFactory, tableNamespace);
            }

            event = eventFactory.createEndElement(tableNamespace.getPrefix(), tableNamespace.getNamespaceURI(), "table");
            writer.add(event);

            event = eventFactory.createEndDocument();
            writer.add(event);

            writer.close();

            String path = table.getDatabase().getAbsolutePath() + FILE_SEPARATOR + table.getName() + ".xml";
            transform(sb, path);
            return path;
        }
    }

    private static void transform(final Writer sb, String path) throws IOException {
        synchronized (lock) {
            try {
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                Writer out = new FileWriter(path);
                String xml = sb.toString();
                xml = xml.trim().replaceFirst("^([\\W]+)<", "<");
                StreamSource stream = new StreamSource(new StringReader(xml));
                StreamResult result = new StreamResult(out);
                t.transform(stream, result);
                out.close();
                result.getWriter().close();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insert(final Row row, final Table table) throws IOException, XMLStreamException {
        synchronized (lock) {
            String path = table.getDatabase().getAbsolutePath() + FILE_SEPARATOR + table.getName() + ".xml";
            StreamSource source = new StreamSource(path);
            Writer result = new StringWriter();
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(source);
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLEventWriter writer = outputFactory.createXMLEventWriter(result);
            XMLEventFactory eventFactory = XMLEventFactory.newFactory().newInstance();
            Namespace nameSpace = null;

            while (reader.hasNext()) {
                XMLEvent event = (XMLEvent) reader.next();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (event.asStartElement().getName().getLocalPart().equals("table")) {
                            nameSpace = (Namespace) event.asStartElement().getNamespaces().next();
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if (event.asEndElement().getName().getLocalPart().equals("table")) {
                            write(row, writer, eventFactory, nameSpace);
                        }
                        break;
                }
                writer.add(event);
            }
            transform(result, path);
        }
    }

    private static void write(final Row row, final XMLEventWriter writer, final XMLEventFactory eventFactory, final Namespace
            rowNamespace) throws XMLStreamException {

        synchronized (lock) {
            Map<Column, Field> prop = row.getFields();


            XMLEvent event = eventFactory.createStartElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(),
                    "row");
            writer.add(event);


            for (Map.Entry<Column, Field> entry : prop.entrySet()) {
                event = eventFactory.createStartElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(), entry
                        .getKey().getName());
                writer.add(event);


                event = eventFactory.createCharacters(entry.getValue().toString());
                writer.add(event);

                event = eventFactory.createEndElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(),
                        entry.getKey().getName());
                writer.add(event);
            }

            event = eventFactory.createEndElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(),
                    "row");
            writer.add(event);

        }

    }

    public static void deserialize(final Table table) throws FileNotFoundException, XMLStreamException {
        synchronized (lock) {
            List<Row> rows = deserialize(table.getDatabase().getAbsolutePath() + FILE_SEPARATOR + table.getName());
            for (Row row : rows) {
                row.setTableName(table.getName());
            }
            table.setRows(rows);
        }
    }

    public static boolean dropDatabase(final String name) {
        synchronized (lock) {
            File dir = new File("databases" + FILE_SEPARATOR + name);
            if (dir.exists()) {
                return FileUtilities.deleteDirectory(dir);
            }
            return false;
        }
    }

    public static boolean dropTable(Table table) {
        synchronized (lock) {
            File dir = new File(table.getDatabase().getAbsolutePath() + FILE_SEPARATOR + table.getName() + ".xml");
            if (dir.exists()) {
                FileUtilities.deleteDirectory(dir);
            }
            dir = new File(table.getDatabase().getAbsolutePath() + FILE_SEPARATOR + table.getName() + ".dtd");
            if (dir.exists()) {
                FileUtilities.deleteDirectory(dir);
            }
            return true;
        }
    }
}
