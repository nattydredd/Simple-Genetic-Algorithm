package utilitys;

// @author Nate
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StratifyData {

    //Create array for training and validation sets
    public static ArrayList<String> training = new ArrayList<String>();
    public static ArrayList<String> validation = new ArrayList<String>();

    //Create array for class'
    public static ArrayList<String> class0 = new ArrayList<String>();
    public static ArrayList<String> class1 = new ArrayList<String>();

    //Percentage split
    public static double percentage = 0.6;

    public static void main(String[] args) {

        String line;
        try {
            //Read file at specified path
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/data/data3.txt"));

            while ((line = bufferedReader.readLine()) != null) {

                String[] lineToken = line.split(" ");
                int consequentIndex = lineToken.length - 1;

                //Split classes into separate arrays
                if (lineToken[consequentIndex].equalsIgnoreCase("0")) {
                    class0.add(line);
                } else {
                    class1.add(line);
                }

            }

            //Close
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }

        System.out.println("Class 0 Set Size " + class0.size());
        System.out.println("Class 1 Set Size " + class1.size());

        //Randomly distribute classes
        Random rng = new Random();
        int index;
        while (class0.size() > 0) {

            index = rng.nextInt(class0.size());

            if (Math.random() <= percentage) {
                training.add(class0.get(index));
            } else {
                validation.add(class0.get(index));
            }
            class0.remove(index);
        }
        while (class1.size() > 0) {

            index = rng.nextInt(class1.size());

            if (Math.random() <= percentage) {
                training.add(class1.get(index));
            } else {
                validation.add(class1.get(index));
            }
            class1.remove(index);
        }

        //Shuffle
        long seed = System.nanoTime();
        Collections.shuffle(training, new Random(seed));
        Collections.shuffle(validation, new Random(seed));

//        System.out.println("Training Set:");
//        for (int i = 0; i < training.size(); i++) {
//            System.out.println(training.get(i));
//        }
//        
//        System.out.println("Validation Set:");
//        for (int i = 0; i < validation.size(); i++) {
//            System.out.println(validation.get(i));
//        }
        System.out.println("Training Set Size " + training.size());
        System.out.println("Validation Set Size " + validation.size());
        
        //Write data to files
        try {
            PrintWriter trainingOut = new PrintWriter(new FileWriter("src/data/data3Training.txt", true));
            PrintWriter validationOut = new PrintWriter(new FileWriter("src/data/data3Validation.txt", true));
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

}//End StratifyData
