package ga;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

//@author n2-duran
public class AssignmentGA {

    public static String fileName = "data1.txt";
    public static Random rng = new Random();

    public static int populationSize = 50;
    public static Individual[] currentPopulation = new Individual[populationSize];

    public static int numberOfGenes = 60;
    public static double mutationRate = 0.01;

    public static int maxGenerations = 50;
    public static int generation = 0;

//    public static Individual bestIndividual = new Individual(0);
//    public static boolean elitism = true;

    public static void main(String[] args) {

       
    }
}
