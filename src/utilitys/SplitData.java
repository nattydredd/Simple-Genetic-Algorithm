package utilitys;

// @author Nate
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SplitData {

    //Create array for training and validation sets
    public static ArrayList<String> training = new ArrayList<String>();
    public static ArrayList<String> validation = new ArrayList<String>();

    public static void main(String[] args) {
        // TODO code application logic here
        String line;
        try {
            //Read file at specified path
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/data/data2.txt"));

            while ((line = bufferedReader.readLine()) != null) {

                if (Math.random() <= 0.6) {
                    training.add(line);
                } else {
                    validation.add(line);
                }
            }

            //Close
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }

        System.out.println("Training Set " + training);
        System.out.println("Training Set Size " + training.size());
        System.out.println("Validation Set " + validation);
        System.out.println("Validation Set Size " + validation.size());

        try {
            PrintWriter trainingOut = new PrintWriter(new FileWriter("src/data/data2Training.txt", true));
            PrintWriter validationOut = new PrintWriter(new FileWriter("src/data/data2Validation.txt", true));
            for (String inst : training) {
                trainingOut.println(inst);
            }
            for (String inst : validation) {
                validationOut.println(inst);
            }

            trainingOut.println();
            trainingOut.flush();

            validationOut.println();
            validationOut.flush();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }//End main

}//End SplitData
