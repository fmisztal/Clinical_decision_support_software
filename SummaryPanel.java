import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

/**
 * Class that provides a GUI presenting all collected data in an accessible form. It allows to
 * perform actions such as: loading different databases multiple times, getting information from
 * the internet about diagnosed diseases, generating full documentation in LaTeX and navigating
 * through all data.
 */
public class SummaryPanel implements ActionListener {
    String selectedFilePath;
    Vector<Vector<String>> analysis;
    DefaultListModel<String> diagnosisList;
    JList<String> diagnosis;
    ArrayList<String> diagnosisURL;
    JFrame frame;
    JTable table;
    JScrollPane sp1, sp2;
    JLabel[] l1, l2, headers;
    JPanel[] p, separator;
    JPanel panel;
    Box verticalBox;
    JMenuBar menubar;
    JMenu menu;
    JMenuItem i1, i2;

    /**
     * Constructor setting up main frame with the JMenuBar allowing the user to take further actions.
     *
     * @param frame main frame
     */
    public SummaryPanel(JFrame frame) {
        this.frame = frame;
        frame.setTitle("Panel wyników");
        ImageIcon img = new ImageIcon("..\\pjava-2022z-projekt\\medical-team.png");
        frame.setIconImage(img.getImage());
        frame.setMinimumSize(new Dimension(1000, 750));

        i1 = new JMenuItem("Otórz plik");
        i2 = new JMenuItem("Zapisz dokumentację");
        i1.addActionListener(this);
        i2.addActionListener(this);

        menubar = new JMenuBar();
        menu = new JMenu("Menu");
        menu.add(i1);
        menu.add(i2);
        menubar.add(menu);
        frame.setJMenuBar(menubar);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Method that loads all contents from database into appropriate containers in the frame. If any exception
     * based on file reading error occurs, the processing of this method is canceled and its previous
     * state is loaded into the frame.
     */
    public void loadPanel() {
        try {
            readFileCustom("..\\pjava-2022z-projekt\\dictionary.txt");
        } catch (Exception e) {
            if (verticalBox != null)
                frame.add(verticalBox);
            JOptionPane.showMessageDialog(frame, "Nieprawidłowy format pliku!");
            return;
        }

        Vector<String> column = new Vector<>();
        column.add("Badanie");
        column.add("Czynnik");
        column.add("Stan");
        column.add("Wartość");

        table = new JTable(analysis, column);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(25);
        table.setRowSelectionAllowed(false);

        sp1 = new JScrollPane(table);
        int tableHeight = table.getRowHeight() * table.getRowCount() + 23;
        if (table.getRowCount() > 10)
            tableHeight = table.getRowHeight() * 10 + 23;
        sp1.setPreferredSize(new Dimension(600, tableHeight));

        diagnosis = new JList<>(diagnosisList);
        diagnosis.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        diagnosis.addMouseListener(new MouseAdapter() {
            /**
             * If some item of JList is clicked double-clicked, this method opens specific link
             * from the diagnosisURL ArrayList in the default browser.
             *
             * @param e the event to be processed
             */
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        if(!diagnosisURL.isEmpty())
                            desktop.browse(URI.create(diagnosisURL.get(index)));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        sp2 = new JScrollPane(diagnosis);
        sp2.setPreferredSize(new Dimension(265, 90));


        headers = new JLabel[2];

        headers[0] = new JLabel("Dane pacjenta:");
        headers[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        headers[0].setPreferredSize(new Dimension(160, 20));

        headers[1] = new JLabel("Diagnoza:");
        headers[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        headers[1].setPreferredSize(new Dimension(160, 20));

        p = new JPanel[9];
        separator = new JPanel[3];

        p[0] = new JPanel();
        p[0].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        p[0].setPreferredSize(new Dimension(150, 90));
        p[0].add(l2[0]);
        p[0].add(l1[0]);
        p[0].add(l2[1]);
        p[0].add(l1[1]);
        p[0].add(l2[2]);
        p[0].add(l1[2]);
        p[0].setBackground(Color.white);

        p[2] = new JPanel();
        p[2].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        p[2].setPreferredSize(new Dimension(150, 90));
        p[2].add(l2[3]);
        p[2].add(l1[3]);
        p[2].add(l2[4]);
        p[2].add(l1[4]);
        p[2].add(l2[5]);
        p[2].add(l1[5]);
        p[2].setBackground(Color.white);

        separator[0] = new JPanel();
        separator[0].setBackground(Color.black);
        separator[0].setMaximumSize(new Dimension(1, 90));
        separator[0].setMinimumSize(new Dimension(1, 90));
        separator[0].setPreferredSize(new Dimension(1, 90));
        p[4] = new JPanel();
        p[4].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        p[4].add(p[0]);
        p[4].add(separator[0]);
        p[4].add(p[2]);
        p[4].setBackground(Color.white);

        separator[1] = new JPanel();
        separator[1].setBackground(Color.black);
        separator[1].setMaximumSize(new Dimension(341, 1));
        separator[1].setMinimumSize(new Dimension(341, 1));
        separator[1].setPreferredSize(new Dimension(341, 1));
        p[5] = new JPanel();
        p[5].setLayout(new BoxLayout(p[5], BoxLayout.PAGE_AXIS));
        p[5].add(headers[0]);
        p[5].add(separator[1]);
        p[5].add(p[4]);
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        p[5].setBorder(blackLine);

        separator[2] = new JPanel();
        separator[2].setBackground(Color.black);
        separator[2].setMaximumSize(new Dimension(265, 1));
        separator[2].setMinimumSize(new Dimension(265, 1));
        separator[2].setPreferredSize(new Dimension(265, 1));
        p[6] = new JPanel();
        p[6].setLayout(new BoxLayout(p[6], BoxLayout.PAGE_AXIS));
        p[6].add(headers[1]);
        p[6].add(separator[2]);
        p[6].add(sp2);
        p[6].setBorder(blackLine);

        p[7] = new JPanel();
        p[7].setLayout(new FlowLayout(FlowLayout.CENTER, 80, 20));
        p[7].add(p[5]);
        p[7].add(p[6]);
        p[7].setPreferredSize(new Dimension(700, 140));
        p[7].setMinimumSize(new Dimension(700, 140));
        p[7].setMaximumSize(new Dimension(1000, 140));

        p[8] = new JPanel();
        p[8].setLayout(new FlowLayout(FlowLayout.CENTER, 80, 20));
        p[8].add(sp1);
        p[8].setPreferredSize(new Dimension(1000, tableHeight + 60));
        p[8].setMinimumSize(new Dimension(1000, tableHeight + 60));
        p[8].setMaximumSize(new Dimension(1000, tableHeight + 60));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(p[7]);
        panel.add(p[8]);

        verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        verticalBox.add(panel);
        verticalBox.add(Box.createVerticalGlue());
        frame.add(verticalBox);

        frame.repaint();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Method handling events coming from the menu.
     * Event from MenuItem i1 triggers the procedure of uploading new database into frame.
     * Event from MenuItem i2 creates new object of LatexGenerator class and runs its methods
     * responsible for creating documentation tex file. If the selectedFilePath is incorrect,
     * the JOptionPane pops up.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == i1) {
            if (verticalBox != null)
                frame.remove(verticalBox);
            loadPanel();
        }
        if (e.getSource() == i2) {
            LatexGenerator gen = new LatexGenerator();
            try {
                gen.readFile(selectedFilePath);
                File directory = new File("..\\pjava-2022z-projekt\\Documentations");
                if (!directory.exists())
                    directory.mkdir();
                gen.writeFile("..\\pjava-2022z-projekt\\Documentations\\");
            } catch (NullPointerException ex) {
                ex.getStackTrace();
                JOptionPane.showMessageDialog(frame, "Brak wybranego pliku!",  "Ostrzeżenie",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Method that allows the user to choose path to preferred database.
     * It's enabling only files with matching .med extension.
     *
     * @throws Exception occurring due to selecting file with wrong extension
     */
    public void fileChooser() throws Exception {
        final JFileChooser fileChooser = new JFileChooser(new File("..\\pjava-2022z-projekt\\Databases"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                final String name = f.getName();
                return name.endsWith(".med");
            }

            @Override
            public String getDescription() {
                return "*.med";
            }
        });
        fileChooser.showOpenDialog(fileChooser);
        File selectedFile = fileChooser.getSelectedFile();

        if (!selectedFile.getName().endsWith(".med"))
            throw new Exception();

        selectedFilePath = selectedFile.getPath();
    }

    /**
     * Picks database through fileChooser, and then it inserts processed data from this file to given containers.
     *
     * @param path location of the dictionary.txt
     * @throws Exception appearing due to file opening error or selecting file with wrong extension in fileChooser()
     */
    public void readFileCustom(String path) throws Exception {
        fileChooser();

        File results = new File(selectedFilePath);
        BufferedReader readDatabase = new BufferedReader(new FileReader(results));

        l1 = new JLabel[6];
        l2 = new JLabel[6];

        String[] patientData = {"Imię: ", "Nazwisko: ", "Płeć: ", "Wiek: ", "Pesel: ", "Data ur.: "};
        for (int i = 0; i < 6; i++) {
            l2[i] = new JLabel(patientData[i], SwingConstants.LEFT);
            l2[i].setPreferredSize(new Dimension(65, 20));
        }

        diagnosisURL = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            l1[i] = new JLabel(readDatabase.readLine(), SwingConstants.RIGHT);
            l1[i].setPreferredSize(new Dimension(85, 20));
        }

        diagnosisList = new DefaultListModel<>();
        analysis = new Vector<>();
        int diagnosisCounter = 0;
        while (true) {
            Vector<String> row = new Vector<>();
            String line = readDatabase.readLine();
            if (line == null)
                break;
            if (line.contains(":")) {
                String[] temp = (line.split("(/)|(:)"));

                File dictionary = new File(path);
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

                File urls = new File("..\\pjava-2022z-projekt\\conditions.txt");
                BufferedReader readUrls = new BufferedReader(new FileReader(urls));
                while (true) {
                    String url = readUrls.readLine();
                    if (url == null)
                        break;
                    if (url.contains(diagnosisList.lastElement()))
                        diagnosisURL.add(url.split(";")[2]);
                }
                readUrls.close();
            }
        }
        if (diagnosisCounter == 0)
            diagnosisList.addElement("Brak wskazań");
        readDatabase.close();
    }
}
