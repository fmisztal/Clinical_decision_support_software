/**
 * Interface with methods connected to actions done on files
 */
public interface FileActions {
    /**
     * Method that reads file from the chosen path
     * @param path specifies the path to the file
     */
    void readFile(String path);
    /**
     * Method that writes to file from the chosen path
     * @param path specifies the path to the file
     */
    void writeFile(String path);
}
