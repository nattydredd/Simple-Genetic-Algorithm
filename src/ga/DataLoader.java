package ga;

//@author n2-duran
import static ga.AssignmentGA.fileName;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {

    String fileName;
    String path;
    ArrayList data;

    public DataLoader(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public ArrayList getData() {
        return data;
    }

    public ArrayList loadBinaryData() {
        ArrayList instance;
        String line;
        try {
            //Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                instance = new ArrayList();
                
                data.add(line);
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return data;
    }
}
