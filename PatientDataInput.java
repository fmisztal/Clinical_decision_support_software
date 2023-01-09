import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Class providing GUI to enter patient data.
 */
public class PatientDataInput implements ActionListener, FileActions {
    String[] patientData = new String[6];
    JTextField tf1, tf2, tf4, tf5, tf6;
    JLabel l1, l2, l3, l4, l5, l6;
    JLabel le1, le2, le3, le4, le5, le6;
    JButton b1, b2;
    JFrame frame;
    JPanel p1, p2, p3, p4, panel;
    Box verticalBox;
    JComboBox<String> cb;

    /**
     * Constructor responsible for setting up the GUI and collecting data from user.
     */
    public PatientDataInput() {
        frame = new JFrame();
        frame.setTitle("Panel wprowadzania danych pacjenta");
        ImageIcon img = new ImageIcon("..\\pjava-2022z-projekt\\medical-team.png");
        frame.setIconImage(img.getImage());
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        p1.setPreferredSize(new Dimension(120, 300));
        p2.setPreferredSize(new Dimension(260, 300));
        p4.setPreferredSize(new Dimension(100, 300));
        p3.setPreferredSize(new Dimension(240, 50));

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        l1 = new JLabel("Imię:", SwingConstants.RIGHT);
        l1.setPreferredSize(new Dimension(100, 30));
        l1.setFont(new Font("Calibri", Font.BOLD, 14));
        l1.setVerticalAlignment(SwingConstants.CENTER);
        l2 = new JLabel("Nazwisko:", SwingConstants.RIGHT);
        l2.setPreferredSize(new Dimension(100, 30));
        l2.setFont(new Font("Calibri", Font.BOLD, 14));
        l2.setVerticalAlignment(SwingConstants.CENTER);
        l3 = new JLabel("Płeć:", SwingConstants.RIGHT);
        l3.setPreferredSize(new Dimension(100, 30));
        l3.setFont(new Font("Calibri", Font.BOLD, 14));
        l3.setVerticalAlignment(SwingConstants.CENTER);
        l4 = new JLabel("Wiek:", SwingConstants.RIGHT);
        l4.setPreferredSize(new Dimension(100, 30));
        l4.setFont(new Font("Calibri", Font.BOLD, 14));
        l4.setVerticalAlignment(SwingConstants.CENTER);
        l5 = new JLabel("Pesel:", SwingConstants.RIGHT);
        l5.setPreferredSize(new Dimension(100, 30));
        l5.setFont(new Font("Calibri", Font.BOLD, 14));
        l5.setVerticalAlignment(SwingConstants.CENTER);
        l6 = new JLabel("Data urodzenia:", SwingConstants.RIGHT);
        l6.setPreferredSize(new Dimension(100, 30));
        l6.setFont(new Font("Calibri", Font.BOLD, 14));
        l6.setVerticalAlignment(SwingConstants.CENTER);


        Font font = new Font("Calibri", Font.ITALIC, 0);

        le1 = new JLabel("Tylko litery!", SwingConstants.LEFT);
        le1.setPreferredSize(new Dimension(100, 30));
        le1.setFont(font);
        le2 = new JLabel("Tylko litery!", SwingConstants.LEFT);
        le2.setPreferredSize(new Dimension(100, 30));
        le2.setFont(font);
        le3 = new JLabel();
        le3.setPreferredSize(new Dimension(100, 30));
        le4 = new JLabel("Tylko cyfry!", SwingConstants.LEFT);
        le4.setPreferredSize(new Dimension(100, 30));
        le4.setFont(font);
        le5 = new JLabel("Tylko cyfry!", SwingConstants.LEFT);
        le5.setPreferredSize(new Dimension(100, 30));
        le5.setFont(font);
        le6 = new JLabel("Zły format!", SwingConstants.LEFT);
        le6.setPreferredSize(new Dimension(100, 30));
        le6.setFont(font);

        tf1 = new JTextField(10);
        tf1.setPreferredSize(new Dimension(100, 30));
        tf2 = new JTextField(10);
        tf2.setPreferredSize(new Dimension(100, 30));

        String[] sexChoice = {"Mężczyzna", "Kobieta"};
        cb = new JComboBox<>(sexChoice);
        cb.setPreferredSize(new Dimension(110, 30));
        cb.setFont(new Font("Calibri", Font.PLAIN, 14));
        ((JLabel) cb.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) cb.getRenderer()).setVerticalAlignment(SwingConstants.BOTTOM);

        tf4 = new JTextField(10);
        tf4.setPreferredSize(new Dimension(100, 30));
        tf5 = new JTextField(10);
        tf5.setPreferredSize(new Dimension(100, 30));
        tf6 = new JTextField(10);
        tf6.setPreferredSize(new Dimension(100, 30));

        b1 = new JButton("Zatwierdź");
        b1.setPreferredSize(new Dimension(90, 30));
        b1.addActionListener(this);
        b2 = new JButton("Dalej");
        b2.setPreferredSize(new Dimension(90, 30));
        b2.addActionListener(this);

        b1.setEnabled(false);
        b2.setEnabled(false);

        p1.add(l1);
        p1.add(l2);
        p1.add(l3);
        p1.add(l4);
        p1.add(l5);
        p1.add(l6);

        p2.add(tf1);
        p2.add(le1);
        p2.add(tf2);
        p2.add(le2);
        p2.add(cb);
        p2.add(le3);
        p2.add(tf4);
        p2.add(le4);
        p2.add(tf5);
        p2.add(le5);
        p2.add(tf6);
        p2.add(le6);

        p3.add(b1);
        p3.add(b2);

        panel.add(p4);
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);

        verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        verticalBox.add(panel);
        verticalBox.add(Box.createVerticalGlue());

        frame.add(verticalBox);

        DocumentListener doclistener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            /**
             * Method that has the task to check (when changed) if JTextFields are filled with correct
             * type of data and appropriately enabling or disabling the navigation buttons
             */
            public void changed() {
                Font fontShow = new Font("SansSerif", Font.ITALIC, 9);
                Font fontHide = new Font("SansSerif", Font.ITALIC, 0);

                le1.setFont(!tf1.getText().chars().allMatch(Character::isLetter) ? fontShow : fontHide);
                le2.setFont(!tf2.getText().chars().allMatch(Character::isLetter) ? fontShow : fontHide);

                if (!tf4.getText().chars().allMatch(Character::isDigit)) {
                    le4.setText("Tylko cyfry!");
                    le4.setFont(fontShow);
                } else {
                    if (!tf4.getText().equals("")) {
                        int age = Integer.parseInt(tf4.getText());
                        if (tf4.getText().startsWith("0")) {
                            le4.setFont(fontShow);
                            le4.setText("Nie zaczynaj od 0!");
                        } else if (age < 1 | age > 120) {
                            le4.setFont(fontShow);
                            le4.setText("Wiek poza zakresem!");
                        } else
                            le4.setFont(fontHide);
                    } else
                        le4.setFont(fontHide);
                }

                if (!tf5.getText().chars().allMatch(Character::isDigit)) {
                    le5.setText("Tylko cyfry!");
                    le5.setFont(fontShow);
                } else {
                    if (!tf5.getText().equals("") & tf5.getText().length() != 11) {
                        le5.setFont(fontShow);
                        le5.setText("Wprowadź 11 cyfr!");
                    } else
                        le5.setFont(fontHide);
                }

                if (!tf6.getText().equals("")) {
                    try {
                        LocalDate date = LocalDate.parse(tf6.getText(),
                                DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT));
                        if (!date.isBefore(LocalDate.now()) |
                                !date.isAfter(LocalDate.now().minusYears(120))) {
                            le6.setFont(fontShow);
                            le6.setText("Data poza zakresem!");
                        } else
                            le6.setFont(fontHide);
                    } catch (Exception ex) {
                        le6.setFont(fontShow);
                        le6.setText("Format: dd-MM-uuuu!");
                    }
                } else
                    le6.setFont(fontHide);

                b1.setEnabled(!tf1.getText().equals("") & !tf2.getText().equals("") & !tf4.getText().equals("") &
                        !tf5.getText().equals("") & !tf6.getText().equals("") & le1.getFont().equals(fontHide) &
                        le2.getFont().equals(fontHide) & le4.getFont().equals(fontHide) &
                        le5.getFont().equals(fontHide) & le6.getFont().equals(fontHide));
            }
        };

        tf1.getDocument().addDocumentListener(doclistener);
        tf2.getDocument().addDocumentListener(doclistener);
        tf4.getDocument().addDocumentListener(doclistener);
        tf5.getDocument().addDocumentListener(doclistener);
        tf6.getDocument().addDocumentListener(doclistener);

        frame.setSize(550, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Method inserting data from JTextFields into the array patientData and operating the JButton actions.
     * Event called by JButton b1 activates the process of saving collected data into database.med file using
     * writeFile method.
     * Event called by JButton b2 removes existing frame content and transfers it to a new instance of
     * the TestSelection class.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        patientData[0] = tf1.getText();
        patientData[1] = tf2.getText();
        patientData[2] = (String) cb.getSelectedItem();
        patientData[3] = tf4.getText();
        patientData[4] = tf5.getText();
        patientData[5] = tf6.getText();

        if (e.getSource() == b1) {
            File directory = new File("..\\pjava-2022z-projekt\\Databases");
            if (!directory.exists())
                directory.mkdir();
            writeFile("..\\pjava-2022z-projekt\\Databases\\database.med");
            b2.setEnabled(true);
        }

        if (e.getSource() == b2) {
            frame.remove(verticalBox);
            new TestSelection(frame);
        }
    }

    /**
     * Overridden method from FileAction interface, unused in this class.
     */
    @Override
    public void readFile(String path) {
    }

    /**
     * Overridden method from FileAction interface that is supposed to write data collected from the user
     * into the database. When it fails to do so, the JOptionPane with warning message pops up.
     *
     * @param path the location of database file
     */
    @Override
    public void writeFile(String path) {
        try {
            File file = new File((path));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (String temp : patientData) {
                writer.write(temp);
                writer.newLine();
            }
            writer.close();

        } catch (IOException ex) {
            ex.getStackTrace();
            JOptionPane.showMessageDialog(frame, "Błąd zapisu badań!", "Ostrzeżenie",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
