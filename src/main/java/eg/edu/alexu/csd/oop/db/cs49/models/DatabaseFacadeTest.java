package eg.edu.alexu.csd.oop.db.cs49.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseFacadeTest {
    private DatabaseFacade facade;
    private final String DATABASE_PATH = "databases/";

    @BeforeEach
    void setUp() {
        facade = new DatabaseFacade();
    }

    @Test
    void createDatabase() {
        facade.createDatabase("test", false);
        File file = new File(DATABASE_PATH + "test");
        assertTrue(file.exists());
        assertTrue(file.isDirectory());
    }

    @Test
    void createDatabaseAndDropExistingOne() {
        try {
            facade.executeStructureQuery("create database students;");
        } catch (SQLException e) {
            fail("Couldn't create database");
        }
        File file = new File(DATABASE_PATH + "students");
        assertTrue(file.exists());
        assertTrue(file.isDirectory());
        assertTrue(file.listFiles().length > 0);

        facade.createDatabase("students", true);

        file = new File(DATABASE_PATH + "students");
        assertTrue(file.exists());
        assertTrue(file.isDirectory());
        assertTrue(file.listFiles().length == 0);
    }

    @Test
    void createTable() {
        try {
            facade.executeStructureQuery("create database students;");
            facade.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar , column_name2 int, column_name3 varchar)  ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void insertRow() {
        try {
            facade.executeStructureQuery("create table `students`.`grades` (oop INT,maths INT);");
            facade.executeUpdateQuery("INSERT INTO `students`.`grades` (`oop`, `maths`) VALUES ('0', '52')" +
                    ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateRow() {
        try {
            //facade.executeStructureQuery("create table `students`.`grades` (oop INT,maths INT);");
            facade.executeUpdateQuery("UPDATE `students`.`grades` SET `oop`='sd', `maths`='3' WHERE " +
                    "`maths`>='2';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}