import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class providing GUI to select commissioned tests.
 */
public class TestSelection extends JFrame implements ActionListener, FileActions {
    JFrame frame;
    JLabel l1, l2, l3, l4;
    JCheckBox cb1, cb2, cb3, cb4, cb5, cb6;
    JPanel p1, p2, p3, p4, p5, panel;
    Box verticalBox;
    JButton b;

    /**
     * Constructor responsible for setting up the GUI and saving information about chosen tests.
     *
     * @param frame main frame
     */
    public TestSelection(JFrame frame) {
        this.frame = frame;
        frame.setTitle("Wybór badań");
        ImageIcon img = new ImageIcon("..\\pjava-2022z-projekt\\medical-team.png");
        frame.setIconImage(img.getImage());

        p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
        p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
        p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
        p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));
        p5 = new JPanel();
        p5.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        p1.setPreferredSize(new Dimension(200, 100));
        p2.setPreferredSize(new Dimension(200, 100));
        p3.setPreferredSize(new Dimension(200, 100));
        p4.setPreferredSize(new Dimension(200, 100));
        p5.setPreferredSize(new Dimension(200, 100));

        p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        l1 = new JLabel("Analityka ogólna:", SwingConstants.LEFT);
        l1.setPreferredSize(new Dimension(160, 15));
        l1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        l1.setFont(new Font("Calibri", Font.BOLD, 14));
        l2 = new JLabel("Hematologia:", SwingConstants.LEFT);
        l2.setPreferredSize(new Dimension(160, 15));
        l2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        l2.setFont(new Font("Calibri", Font.BOLD, 14));
        l3 = new JLabel("Chemia kliniczna:", SwingConstants.CENTER);
        l3.setPreferredSize(new Dimension(160, 15));
        l3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        l3.setFont(new Font("Calibri", Font.BOLD, 14));
        l4 = new JLabel("Diagnostyka tarczycy:", SwingConstants.RIGHT);
        l4.setPreferredSize(new Dimension(160, 15));
        l4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        l4.setFont(new Font("Calibri", Font.BOLD, 14));

        cb1 = new JCheckBox("Mocz - badania ogólne");
        cb1.setPreferredSize(new Dimension(160, 15));
        cb1.setFont(new Font("Calibri", Font.PLAIN, 14));
        cb1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cb2 = new JCheckBox("Kał - badania ogólne");
        cb2.setFont(new Font("Calibri", Font.PLAIN, 14));
        cb2.setPreferredSize(new Dimension(160, 15));
        cb2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cb3 = new JCheckBox("Mocznik");
        cb3.setPreferredSize(new Dimension(160, 15));
        cb3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cb3.setFont(new Font("Calibri", Font.PLAIN, 14));
        cb4 = new JCheckBox("Cholesterol");
        cb4.setPreferredSize(new Dimension(160, 15));
        cb4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cb4.setFont(new Font("Calibri", Font.PLAIN, 14));
        cb5 = new JCheckBox("Morfologia");
        cb5.setPreferredSize(new Dimension(160, 15));
        cb5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cb5.setFont(new Font("Calibri", Font.PLAIN, 14));
        cb6 = new JCheckBox("TSH III gen.");
        cb6.setPreferredSize(new Dimension(160, 15));
        cb6.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cb6.setFont(new Font("Calibri", Font.PLAIN, 14));

        b = new JButton("Zatwierdź");
        b.setPreferredSize(new Dimension(90, 40));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(this);
        b.setEnabled(false);

        p1.add(l1);
        p2.add(l2);
        p3.add(l3);
        p4.add(l4);
        p1.add(cb1);
        p1.add(cb2);
        p2.add(cb3);
        p2.add(cb4);
        p3.add(cb5);
        p4.add(cb6);
        p5.add(p1);
        p5.add(p2);
        p5.add(p3);
        p5.add(p4);
        panel.add(p5);
        panel.add(b);

        ItemListener itemListener = e -> b.setEnabled(cb1.isSelected() | cb2.isSelected() | cb3.isSelected() |
                cb4.isSelected() | cb5.isSelected() | cb6.isSelected());

        cb1.addItemListener(itemListener);
        cb2.addItemListener(itemListener);
        cb3.addItemListener(itemListener);
        cb4.addItemListener(itemListener);
        cb5.addItemListener(itemListener);
        cb6.addItemListener(itemListener);

        verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        verticalBox.add(panel);
        verticalBox.add(Box.createVerticalGlue());

        frame.add(verticalBox);
        frame.setMinimumSize(new Dimension(700, 550));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Method designed to save information about tests chosen by the user in JCheckBoxes to a database.med file
     * when the event occurs in JButton b. After that, it runs static method Generate() from OutcomeGenerator class
     * and then clears main frame and passes it to a new object of SummaryPanel clas. When error occurs,
     * the JOptionPane with warning message pops up.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            writeFile("..\\pjava-2022z-projekt\\Databases\\database.med");
            try {
                OutcomeGenerator.Generate();
                frame.remove(verticalBox);
                new SummaryPanel(frame);
            } catch (IOException ex) {
                ex.getStackTrace();
                JOptionPane.showMessageDialog(frame, "Błąd zapisu badań!", "Ostrzeżenie",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Overridden method from FileAction interface, unused in this class.
     */
    @Override
    public void readFile(String path) {
    }

    /**
     * Overridden method from FileAction interface that is supposed to write information from checkboxes and
     * write adequate information to the database.med file. When it fails to do so, the JOptionPane with warning message pops up.
     *
     * @param path location of the database
     */
    @Override
    public void writeFile(String path) {
        try {
            File file = new File((path));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            writer.newLine();
            if (cb1.isSelected()) {
                writer.write("me/");
            }
            if (cb2.isSelected()) {
                writer.write("ke/");
            }
            if (cb3.isSelected()) {
                writer.write("mk/");
            }
            if (cb4.isSelected()) {
                writer.write("cl/");
            }
            if (cb5.isSelected()) {
                writer.write("ma/");
            }
            if (cb6.isSelected()) {
                writer.write("th/");
            }
            writer.write("/");
            writer.close();

        } catch (IOException ex) {
            ex.getStackTrace();
            JOptionPane.showMessageDialog(frame, "Błąd zapisu badań!", "Ostrzeżenie",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
