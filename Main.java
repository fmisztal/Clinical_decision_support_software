import javax.swing.*;

/**
 * Script that initiates and runs the program
 *
 * @author  Filip Misztal
 * @author  Agnieszka Gutowska
 */
public class Main {
    /**
     * Setting up UIManager look in main method and creating instance of PatientDataInput class
     *
     * @param args passed to main
     * @throws RuntimeException thrown when there is an error caused by incorrect action on UIManager
     */
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            new PatientDataInput();
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}