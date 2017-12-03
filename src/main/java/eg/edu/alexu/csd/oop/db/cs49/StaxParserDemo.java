package eg.edu.alexu.csd.oop.db.cs49;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import javax.xml.stream.events.Namespace;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class StaxParserDemo {

//
//    public void parse(Row row, XMLEventWriter writer, XMLEventFactory eventFactory,Namespace rowNamespace) throws
//            IOException, XMLStreamException {
//
//        Map<String, String> prop = row.getValues();
//
//
//        XMLEvent event = eventFactory.createStartElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(),
//                "row");
//        writer.add(event);
//
//
//        event = eventFactory.createAttribute("id", String.valueOf(row.getRowNumber()));
//        writer.add(event);
//
//        for (Entry<String, String> entry : prop.entrySet()) {
//            event = eventFactory.createStartElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(), entry.getKey());
//            writer.add(event);
//
//            event = eventFactory.createCharacters(entry.getValue());
//            writer.add(event);
//
//            event = eventFactory.createEndElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(),
//                    entry.getKey());
//            writer.add(event);
//        }
//
//        event = eventFactory.createEndElement(rowNamespace.getPrefix(), rowNamespace.getNamespaceURI(),
//                "row");
//        writer.add(event);
//
//
//
//
//    }
//
//    private void transform(final Writer sb, String path) throws IOException {
//        try {
//            Transformer t = TransformerFactory.newInstance().newTransformer();
//            t.setOutputProperty(OutputKeys.INDENT, "yes");
//            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//            t.setOutputProperty(OutputKeys.ENCODING,"UTF-16");
//            Writer out = new FileWriter(path);
//            String xml  = sb.toString();
//            xml= xml.trim().replaceFirst("^([\\W]+)<","<");
//            t.transform(new StreamSource(new StringReader(xml)), new StreamResult(out));
//        } catch (TransformerConfigurationException e) {
//            e.printStackTrace();
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void parse(Table table, String path) throws IOException, XMLStreamException {
//        Writer sb = new StringWriter();
//        XMLOutputFactory factory = XMLOutputFactory.newInstance();
//        XMLEventFactory eventFactory = XMLEventFactory.newFactory().newInstance();
//        XMLEventWriter writer = factory.createXMLEventWriter(sb);
//        factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
//
//        XMLEvent event = eventFactory.createStartDocument();
//        writer.add(event);
//
//        Namespace tableNamespace = eventFactory.createNamespace("db", "http://db");
//
//        event = eventFactory.createStartElement(tableNamespace.getPrefix(),tableNamespace.getNamespaceURI(),"table");
//        writer.add(event);
//        writer.add(tableNamespace);
//
//        for(Row rw:table.getRows()){
//            parse(rw,writer,eventFactory,tableNamespace);
//        }
//
//        event = eventFactory.createEndElement(tableNamespace.getPrefix(),tableNamespace.getNamespaceURI(),"table");
//        writer.add(event);
//
//        event = eventFactory.createEndDocument();
//        writer.add(event);
//
//        writer.close();
//
//        transform(sb,path);
//
//    }
//
//    public Table deserialize(String path) throws FileNotFoundException, XMLStreamException {
//        FileReader file = new FileReader(path);
//        XMLInputFactory factory = XMLInputFactory.newInstance();
//        XMLEventReader reader = factory.createXMLEventReader(file);
//        Table table = new Table("");
//        String qName = "";
//        Map<String, String> map = new HashMap<>();
//        while (reader.hasNext()) {
//            XMLEvent event = (XMLEvent) reader.next();
//
//            switch (event.getEventType()) {
//                case XMLStreamConstants.START_ELEMENT:
//                    StartElement element = event.asStartElement();
//                    if (element.getName().getLocalPart().equals("table")) break;
//                    if (element.getName().getLocalPart().equals("row")) table.addRow(readRow(reader));
//                    qName = element.getName().getLocalPart();
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                    Characters characters = event.asCharacters();
//                    if (characters.toString().contains("\n")) break;
//                    map.put(qName, characters.toString());
//                    break;
//            }
//        }
//        return table;
//    }
//
//    private Row readRow(final XMLEventReader reader) {
//        Row rw = new Row();
//        Map<String,String> map = new HashMap<>();
//        String qName="";
//        boolean inRow = true;
//        while (inRow) {
//            XMLEvent event = (XMLEvent) reader.next();
//
//            switch (event.getEventType()) {
//                case XMLStreamConstants.START_ELEMENT:
//                    StartElement element = event.asStartElement();
//                    qName = element.getName().getLocalPart();
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                    Characters characters = event.asCharacters();
//                    if (characters.toString().contains("\n")) break;
//                    map.put(qName, characters.toString());
//                    break;
//                    case XMLStreamConstants.END_ELEMENT:
//                        EndElement endElement= event.asEndElement();
//                        if((endElement.getName().getLocalPart()).equals("row")) {
//                            inRow = false;
//                        }
//            }
//        }
//        rw.setValues(map);
//        return rw;
//    }
}
