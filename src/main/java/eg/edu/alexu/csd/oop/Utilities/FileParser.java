package eg.edu.alexu.csd.oop.Utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Utility Class to save data in files.
 */
public  final class FileParser {
    /**
     * Private Constructor for the utility class.
     */
    private FileParser() {
    }

    /**
     * Saves a stream of data into a file path.
     *
     * @param data     the string to be saved.
     * @param filePath the path of the file.
     */
    public static void save(final String data, final String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            System.out.println("Saving File .. ");
            writer.flush();
            writer.write(data);
            writer.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a stream of data from a file.
     *
     * @param filePath the path of the file to load data from.
     * @return data represented as string.
     */
    public static String load(final String filePath) {
        String buildString = "";

        try {
            FileReader reader = new FileReader(filePath);
            System.out.println("Loading File ..");
            Scanner in = new Scanner(reader);
            buildString = getString(in);
            in.close();
            reader.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            return buildString;
        }
    }

    /**
     * Fetches the data in the scanner and format it.
     *
     * @param in a scanner that holds the data.
     * @return a string representation of the data in the scanner.
     */
    private static String getString(final Scanner in) {
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            sb.append(in.nextLine());
            sb.append('\n');
        }
        return sb.toString();
    }

}
