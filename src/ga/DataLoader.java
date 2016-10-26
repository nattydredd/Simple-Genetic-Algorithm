package ga;

//@author n2-duran
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {

    private String fileName;
    private String path;
    private ArrayList data;

    public DataLoader(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
        this.data = new ArrayList();
        this.data = loadData();
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

    //Load data
    public ArrayList loadData() {

        String line;      
        try {
            //Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path + fileName));

            while ((line = bufferedReader.readLine()) != null) {
                
                ArrayList<String> instance = new ArrayList<String>();
                int varCount = line.split(" ").length;
                
                for (int i = 0; i < varCount; i++) {
                    instance.add(line.split(" ")[i]);
                }
                data.add(instance);
            }

            //Close
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return data;
    }
    
}// End DataLoader
