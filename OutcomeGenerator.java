import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class generating final and personalized database containing full information about patient
 * and commissioned tests.
 */
public class OutcomeGenerator {
    public static List<String> diagnose = new ArrayList<>();
    public static String outcomeFileName = "";

    /**
     * Static method that runs static Generate() method from ResultGenerator class and
     * based on generated results checks if there is a possibility of disease. If so, it writes
     * the information about it to the database.med file. After that the database is renamed
     * to a name consisting of the patient's surname and pesel number.
     *
     * @throws IOException appearing due to file opening error
     */
    public static void Generate() throws IOException {
        File results = new File("..\\pjava-2022z-projekt\\Databases\\database.med");
        File conditions = new File("..\\pjava-2022z-projekt\\conditions.txt");
        BufferedReader readConditions = new BufferedReader(new FileReader(conditions));

        ResultGenerator.Generate();

        while (true) {
            String condition = readConditions.readLine();
            if (condition == null)
                break;

            String[] cond = condition.split(";");
            String[] compounds = cond[1].split(" ");
            int occurrences = 0;
            for (String c : compounds) {
                String[] parts = c.split(":");
                BufferedReader readDatabase = new BufferedReader(new FileReader(results));
                while (true) {
                    String result = readDatabase.readLine();
                    if (result == null)
                        break;
                    if (result.contains(parts[0]))
                        occurrences++;
                }
                readDatabase.close();
            }
            if (occurrences > 1)
                diagnose.add(cond[0]);
        }
        readConditions.close();

        System.out.println();
        BufferedWriter writeDatabase = new BufferedWriter(new FileWriter(results, true));
        for (String el : diagnose)
            writeDatabase.write("*" + el + "\n");
        writeDatabase.close();

        try {
            extractName();
        } catch (Exception ex) {
            throw new IOException("Nie udało się wydobyć nowej nazwy bazy!");
        }

        File rename = new File("..\\pjava-2022z-projekt\\Databases\\database.med".replace("database", outcomeFileName));
        boolean flag = results.renameTo(rename);
        if (!flag)
            throw new IOException("Nie udało się zmienić nazwy bazy danych!");
    }

    /**
     * Method created to set new normalized database file name based on data from it
     *
     * @throws IOException caused by incorrect file opening
     */
    public static void extractName() throws IOException {
        BufferedReader readDatabase = new BufferedReader(new FileReader("..\\pjava-2022z-projekt\\Databases\\database.med"));
        int i = 0;
        while (i < 5) {
            i++;
            String data = readDatabase.readLine();
            if (data == null)
                break;
            if (i == 2)
                outcomeFileName += data + "_";
            if (i == 5)
                outcomeFileName += data;
        }
        readDatabase.close();
    }
}
