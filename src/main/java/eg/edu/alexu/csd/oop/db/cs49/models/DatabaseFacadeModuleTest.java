package eg.edu.alexu.csd.oop.db.cs49.models;

import eg.edu.alexu.csd.oop.Utilities.FileUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseFacadeModuleTest {
    private DatabaseFacade facade;
    private final String DATABASE_PATH = "databases";
    private File file;

    @BeforeEach
    void setUp() {
        file = new File(DATABASE_PATH);
        file.mkdirs();
        while (file.listFiles().length != 0) {
            FileUtilities.deleteDirectory(file);
            file.mkdirs();
        }

        facade = new DatabaseFacade();
    }


    @Test
    void createDatabase() {
        assertTrue(file.listFiles().length == 0);
        facade.createDatabase("students", false);
        assertTrue(file.listFiles().length == 1);
    }

    @Test
    void createDatabaseAndDrop() {
        assertTrue(file.listFiles().length == 0);
        facade.createDatabase("gr", true);
        assertTrue(file.listFiles().length == 1);
    }

    @Test
    void tester() throws SQLException {
        facade.createDatabase("sample", true);
        facade.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar,  column_name3 " +
                "varchar,column_name2 int)");
        facade.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3'," +
                " 4)");
        facade.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
        facade.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)"
        );
        //int result = facade.executeUpdateQuery("DELETE From table_name1 ");
        Object[][] arr = facade.executeQuery("Select * from table_name1");

        int result = 0;
        result += 5;
    }

    @Test
    void createDatabaseWithStructureQuery() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        boolean result = facade.executeStructureQuery("create database student");
        assertTrue(file.listFiles().length == 1);
        assertTrue(result);
    }

    @Test
    void createDatabaseWIthStructureQueryWithSpaces() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        boolean result = facade.executeStructureQuery("      create    database     student   ");
        assertTrue(file.listFiles().length == 1);
        assertTrue(result);

    }

    @Test
    void createDatabaseWithStructureQueryWithMixOfCases() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        boolean result = facade.executeStructureQuery("CreaTe daTaBase student");
        assertTrue(file.listFiles().length == 1);
        assertTrue(result);

    }

    @Test
    void createDatabaseWithWrongSyntax() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        try {
            boolean result = facade.executeStructureQuery("      create    databse     student   ");
            fail("should throw sql exception with wrong syntax");
        } catch (SQLException e) {
            assertTrue(file.listFiles().length == 0);

        }

    }

    @Test
    void createTable() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        boolean result = facade.executeStructureQuery("      create    database     student   ");
        assertTrue(file.listFiles().length == 1);
        assertTrue(result);

        result = facade.executeStructureQuery("create table grades(oop int, maths varChar, grade INT)");
        assertTrue(result);
        assertTrue(file.listFiles()[0].listFiles().length == 2);
    }

    @Test
    void createTableWithMixCase() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        boolean result = facade.executeStructureQuery("      create    database     student   ");
        assertTrue(file.listFiles().length == 1);
        assertTrue(result);

        result = facade.executeStructureQuery("creAte tAble grAdes(oop int, maths varCHAR, grade InT)");
        assertTrue(result);
        assertTrue(file.listFiles()[0].listFiles().length == 2);
    }

    @Test
    void createTableWithMixSpaces() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        boolean result = facade.executeStructureQuery("      create    database     student   ");
        assertTrue(file.listFiles().length == 1);
        assertTrue(result);

        result = facade.executeStructureQuery("    creAte   tAble   grAdes(  oop  int, maths   varCHAR,   grade   " +
                "InT) ");
        assertTrue(result);
        assertTrue(file.listFiles()[0].listFiles().length == 2);
    }

    @Test
    void createTableWithWrongSyntax() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        try {
            boolean result = facade.executeStructureQuery("      create    table     student   ");
            fail("should throw sql exception with wrong syntax");
        } catch (SQLException e) {
            assertTrue(file.listFiles().length == 0);
        }
    }

    @Test
    void createTableWithWrongSyntax_2() throws SQLException {
        assertTrue(file.listFiles().length == 0);
        try {
            boolean result = facade.executeStructureQuery("      create    table     student   (oop,math int)");
            fail("should throw sql exception with wrong syntax");
        } catch (SQLException e) {
            assertTrue(file.listFiles().length == 0);
        }
    }

    @Test
    void dropDatabase() throws SQLException {
        createDatabase();
        boolean result = facade.executeStructureQuery("drop database students");
        assertTrue(result);
        assertTrue(file.listFiles().length == 0);
    }

    @Test
    void dropTable() throws SQLException {
        createTable();
        boolean result = facade.executeStructureQuery("drop table grades");
        assertTrue(result);
        assertTrue(file.listFiles().length != 0);
        assertTrue(file.listFiles()[0].listFiles().length == 0);
    }

    @Test
    void createDatabaseAndForceDrop() throws SQLException {
        createTable();
        String result = facade.createDatabase("student", true);
        assertNotNull(result);
        assertTrue(file.listFiles().length != 0);
        assertTrue(new File(result).listFiles().length == 0);
    }

    @Test
    void insertRows() throws SQLException {
        createTable();
        int result = facade.executeUpdateQuery("insert into grades(oop,maths,grade) values(50,sd,20)");
        assertEquals(1, result);
    }

    @Test
    void insertRowsWithSpaces() throws SQLException {
        createTable();
        int result = facade.executeUpdateQuery("  insert  into   grades (oop  ,maths  ,grade) values ( 50,sd,20)   ");
        assertEquals(1, result);
    }

    @Test
    void insertRowsWithMixedCase() throws SQLException {
        createTable();
        int result = facade.executeUpdateQuery("  inSert  Into   grades (oop  ,maths  ,grade) vAlues ( 50,sd,20)   ");
        assertEquals(1, result);
    }

    @Test
    void insertRowWithInvalidType() throws SQLException {
        createTable();
        int result;
        try {
            result = facade.executeUpdateQuery("  inSert  Into   graADes (oop  ,maths  ,grade) vAlues ( 50,sd,sd)   ");
            fail("problem validating types");
        } catch (SQLException e) {

        }
    }


    @Test
    void insertRowWithMissingColumns() throws SQLException {
        //TODO put null in missing columns
        createTable();
        int result;
        result = facade.executeUpdateQuery("  inSert  Into   grades (oop  ,maths  ) vAlues ( 50,sd)   ");
        assertEquals(1, result);
    }


    @Test
    void updateRow() throws SQLException {
        insertRows();
        int result;
        result = facade.executeUpdateQuery("update grades set oop=2 where maths=sd");
        assertEquals(1, result);
    }

    @Test
    void updateRoWithSpaces() throws SQLException {
        insertRows();
        int result;
        result = facade.executeUpdateQuery("   update   grades   set   oop = 2   where   maths  =  sd    ");
        assertEquals(1, result);
    }

    @Test
    void updateRoWithMixedCases() throws SQLException {
        insertRows();
        int result;
        result = facade.executeUpdateQuery("UpdAtE grades sET oop=5 whERE maths=sd");
        assertEquals(1, result);
    }

    @Test
    void updateWithUnsatisfyingCondition() throws SQLException {
        insertRows();
        int result;
        result = facade.executeUpdateQuery("update grades set oop=2 where maths=d");
        assertEquals(0, result);

        result = facade.executeUpdateQuery("update grades set oop=2 where oop<40");
        assertEquals(0, result);

        result = facade.executeUpdateQuery("update grades set oop=2 where oop>50");
        assertEquals(0, result);
    }

    @Test
    void updateWithGreaterThanCondition() throws SQLException {
        insertRows();
        int result;
        result = facade.executeUpdateQuery("update grades set oop=2 where oop>5");
        assertEquals(01, result);
    }

    @Test
    void updateWithLessThanCondition() throws SQLException {
        insertRows();
        int result;
        result = facade.executeUpdateQuery("update grades set oop=2 where oop<100");
        assertEquals(1, result);
    }


    @Test
    void updateWithInvalidCondition() throws SQLException {
        insertRows();
        try {
            facade.executeUpdateQuery("Update grades set oop =2 where maths>5");
            fail("error comparing strings to numbers");
        } catch (SQLException e) {

        }
        try {
            facade.executeUpdateQuery("Update grades set oop =2 where maths<5");
            fail("error comparing strings to numbers");
        } catch (SQLException e) {
        }
    }

    @Test
    void deleteRow() throws SQLException {
        insertRows();
        int result = facade.executeUpdateQuery("delete from grades where maths=sd");
        assertEquals(1, result);
    }

    @Test
    void deleteRowWithSpaces() throws SQLException {
        insertRows();
        int result = facade.executeUpdateQuery("   delete    from    grades    where    maths  =  sd   ");
        assertEquals(1, result);
    }

    @Test
    void deleteRowWithMixedCases() throws SQLException {
        insertRows();
        int result = facade.executeUpdateQuery("DEleTe fRom grades wHEre maths=sd");
        assertEquals(1, result);
    }

    @Test
    void selectRow() throws SQLException {
        insertRows();
        Object[][] result = facade.executeQuery("select * from grades where maths = sd");
        int x = 5;
        x = x - 5;
    }
}