package eg.edu.alexu.csd.oop.db.cs49;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Table;
import eg.edu.alexu.csd.oop.db.cs49.utilities.STAXUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class StaxParserDemoTest {
//    @JDBCTest
//    void parseTable() throws IOException, XMLStreamException {
//        Table table = new Table("");
//        for (int i = 0; i < 5; i++) {
//            Row rw = new Row();
//            Map<String, String> prop = new HashMap<>();
//            Random random = new Random();
//            prop.put("key1", String.valueOf(random.nextInt()));
//            prop.put("key2", String.valueOf(random.nextInt()));
//            prop.put("key3", String.valueOf(random.nextInt()));
//            prop.put("key4", String.valueOf(random.nextInt()));
//            rw.setValues(prop);
//            rw.setRowNumber(i);
//            table.addRow(rw);
//        }
//        stax.parse(table,"testWriting.xml");
//    }
//
//    private StaxParserDemo stax;
//
//    @BeforeEach
//    void setUp() {
//        stax = new StaxParserDemo();
//    }
//
//    @JDBCTest
//    void parse() throws IOException, XMLStreamException {
//        Row rw = new Row();
//        Map<String, String> prop = new HashMap<>();
//        prop.put("key1", "value1");
//        prop.put("key2", "value2");
//        prop.put("key3", "value3");
//        prop.put("key4", "value4");
//        rw.setValues(prop);
//        rw.setRowNumber(1);
//        //stax.parse(rw);
//    }
//
//    @JDBCTest
//    void deserialize() throws FileNotFoundException, XMLStreamException {
//        Table tbl = stax.deserialize("databases/students/grades.dtd");
//        tbl.addRow(null);
//    }
//
//    @JDBCTest
//    void deserializeDTD() throws FileNotFoundException, XMLStreamException {
//        STAXUtilities.getColumns("databases/students/grades.dtd");
//    }


}