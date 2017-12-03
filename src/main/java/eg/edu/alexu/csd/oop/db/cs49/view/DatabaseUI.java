package eg.edu.alexu.csd.oop.db.cs49.view;

import eg.edu.alexu.csd.oop.db.cs49.models.DatabaseFacade;

import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseUI {


    private static final String description = "Database command line Interface.";
    private static final String actions = "1- create database\n" +
            "2- structural query\n" +
            "3- update query\n" +
            "4- execute query\n" +
            "5- exit";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        DatabaseFacade facade = new DatabaseFacade();
        System.out.println(description);
        System.out.println(actions);

        int input = in.nextInt();
        while (input != 5) {
            in.nextLine();
            String query = in.nextLine();
            try {
                switch (input) {
                    case 1:
                        facade.createDatabase(query, false);
                        break;
                    case 2:
                        boolean success = facade.executeStructureQuery(query);
                        if (success) {
                            System.out.println("Query executed successfully");
                        } else {
                            System.err.println("Query didn't execute successfully");
                        }
                        break;
                    case 3:
                        int count = facade.executeUpdateQuery(query);
                        System.out.println("Number of updated rows" + count);
                        break;
                    case 4:
                        Object[][] rows = facade.executeQuery(query);
                        for (Object[] row : rows) {
                            System.out.print("[");
                            for (Object element : row) {
                                System.out.print(element + ",");
                            }
                            System.out.println("]");
                        }
                        break;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            input = in.nextInt();


        }
    }
}
