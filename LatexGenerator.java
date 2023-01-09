import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

/**
 * Public class generating .tex file with patient's data and test's outcomes.
 */
public class LatexGenerator implements FileActions {
    public static ArrayList<String> patientData = new ArrayList<>();
    public static Vector<String> diagnosisList = new Vector<>();
    public static Vector<Vector<String>> analysis = new Vector<>();
    public static HashSet<String> headers = new HashSet<>();

    /**
     * Constructor to LatexGenerator class clearing any leftover data in static fields.
     */
    public LatexGenerator() {
        patientData.clear();
        diagnosisList.clear();
        analysis.clear();
        headers.clear();
    }

    /**
     * Overridden method of FileActions interface reading specified file for patient's data and test's outcomes and sets
     * all the static fields of LatexGenerator class.
     * @param path specifies the path to the file
     */
    @Override
    public void readFile(String path) {
        try {
            File results = new File(path);
            BufferedReader readDatabase = new BufferedReader(new FileReader(results));

            for (int i = 0; i < 6; i++)
                patientData.add(readDatabase.readLine());

            int diagnosisCounter = 0;
            while (true) {
                Vector<String> row = new Vector<>();
                String line = readDatabase.readLine();
                if (line == null)
                    break;
                if (line.contains(":")) {
                    String[] temp = (line.split("(/)|(:)"));

                    File dictionary = new File("..\\pjava-2022z-projekt\\dictionary.txt");
                    BufferedReader readDict = new BufferedReader(new FileReader(dictionary));
                    while (true) {
                        String dictLine = readDict.readLine();
                        if (dictLine == null)
                            break;
                        String[] tempDict = dictLine.split(":");
                        if (tempDict[0].contains(temp[0]))
                            temp[0] = tempDict[1];
                        if (tempDict[0].contains(temp[1])) {
                            temp[1] = tempDict[1];
                            if (tempDict.length > 2)
                                temp[3] += " " + tempDict[2];
                        }
                    }
                    readDict.close();

                    switch (temp[2]) {
                        case "0" -> temp[2] = "norma";
                        case "1" -> temp[2] = "zbyt wysoki";
                        case "-1" -> temp[2] = "zbyt niski";
                    }
                    row.addAll(Arrays.asList(temp));
                    analysis.add(row);
                }
                if (line.contains("*")) {
                    diagnosisCounter++;
                    diagnosisList.addElement(line.replace("*", ""));
                }
            }
            if (diagnosisCounter == 0)
                diagnosisList.addElement("Brak wskazań");
            readDatabase.close();

            for (Vector<String> vec : analysis) {
                headers.add(vec.get(0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Overridden method of FileActions interface writing patient's data and test's outcomes in a new file named as
     * patientName_patientPESEL.tex in the specified path. Uses data from static fields in LatexGenerator class.
     * @param path specifies the path where the file will be created.
     */
    @Override
    public void writeFile(String path) {
        try {
            File file = new File(path + patientData.get(1) + "_" + patientData.get(4) + ".tex");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write("""
                    \\documentclass[12pt]{article}
                    \\usepackage[polish]{babel}
                    \\usepackage[T1]{fontenc}
                    \\usepackage{multirow}
                    \\usepackage{tabularray}
                    \\usepackage[table]{xcolor}
                    \\usepackage[a4paper,top=2cm,bottom=2cm,left=2cm,right=2cm,marginparwidth=1.75cm]{geometry}
                    \\title{Karta Badania}
                    \\author{wygenerowano}
                    \\DefTblrTemplate{contfoot-text}{normal}{}
                    \\SetTblrTemplate{contfoot-text}{normal}
                    \\DefTblrTemplate{conthead-text}{normal}{}
                    \\SetTblrTemplate{conthead-text}{normal}
                    \\DefTblrTemplate{caption-tag}{normal}{}
                    \\SetTblrTemplate{caption-tag}{normal}{}
                    \\DefTblrTemplate{caption-sep}{normal}{}
                    \\SetTblrTemplate{caption-sep}{normal}{}\\begin{document}
                    \\maketitle
                    \\section{Dane Pacjenta}""");
            writer.write("\nImię: " + patientData.get(0) + "\\\\" +
                    "\nNazwisko: " + patientData.get(1) + "\\\\" +
                    "\nPłeć: " + patientData.get(2) + "\\\\" +
                    "\nWiek: " + patientData.get(3) + "\\\\" +
                    "\nPesel: " + patientData.get(4) + "\\\\" +
                    "\nData urodzenia: " + patientData.get(5));

            writer.write("""

                    \\section{Wyniki}
                    \\begin{longtblr}{|p{5cm}|p{5cm}|p{5cm}|}
                    """);
            writer.write("\\hline\n" +
                    "\\hfil \\textbf{Czynnik}&\\hfil \\textbf{Wartość}&\\hfil \\textbf{Stan}\\\\");
            for (String nazwa : headers) {
                writer.write("\\hline\n" +
                        "\\SetCell[c=3]{c}\\textbf{" + nazwa + "}\\\\\n");
                for (Vector<String> vector : analysis) {
                    if (vector.contains(nazwa)) {
                        String vectorRep = vector.get(3).replace("%", "\\%");
                        writer.write("\n\\hline\n" +
                                "\\hfil " + vector.get(1) +
                                " &\\hfil " + vectorRep +
                                " &\\hfil " + vector.get(2) + "\\\\");
                    }
                }
            }
            writer.write("""

                    \\hline
                    \\end{longtblr}""");

            writer.write("""

                    \\section{Potencjalne schorzenia}
                    \\begin{itemize}""");
            for (String sick : diagnosisList) {
                writer.write("\n\\item " + sick);
            }
            writer.write("""

                    \\end{itemize}
                    \\end{document}""");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
