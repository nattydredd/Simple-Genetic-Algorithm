package ga;

import java.util.ArrayList;
import java.util.Random;

//@author n2-duran
public class AssignmentGA {

    //Utility variables
    public static String fileName = "data1.txt";
    public static ArrayList data;
    public static Random rng = new Random();

    //Algorithm variables
    public static BinaryFitnessFunction fitness;
    public static Population population;

    //Algorithm parameters
    public static int populationSize = 50;
    public static int geneLength = 60;
    public static double mutationRate = 0.01;
    public static int maxGenerations = 50;

    public static void main(String[] args) {

        //Load data from file
        DataLoader loader = new DataLoader("src/ga/data/", fileName);
        //Create solution set from data
        fitness = new BinaryFitnessFunction(loader.loadData());     
        
        //Display data
//        data = loader.loadData();
//        for (int i = 0; i < data.size(); i++) {
//            System.out.println(data.get(i));
//        }
//        System.out.println(data.size());

        //Initialise a new population
//        population = new Population(populationSize, geneLength);
//        population.initialise();
//        
//        for (int i = 0; i < populationSize; i++) {
//            System.out.println(population.getIndividuals()[i].toString());
//        }
    }
}
