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
    public static int rulesPerIndividual = 10;

    //Algorithm parameters
    public static int populationSize = 10;
    public static int chromosomeLength = 60;
    public static double mutationRate = 0.01;

    public static int targetFitness = 10;
    public static int maxGenerations = 50;
    public static int generation = 0;

    public static void main(String[] args) {

        //Load data from file
        DataLoader loader = new DataLoader("src/ga/data/", fileName);
        //Create solution set from data
        fitness = new BinaryFitnessFunction(loader.getData(), rulesPerIndividual);

        //Initialise and evaluate a new population
        population = new Population(populationSize, chromosomeLength);
        population.initialise();
        fitness.evaluatePopulation(population);

        System.out.println("Initial Population:");
        printGeneration();

        while (generation <= maxGenerations ^ (population.getFittest().getFitness() == targetFitness)) {
            generation++;

            //Selection
            population.tournamentSelection();
            fitness.evaluatePopulation(population);
            System.out.println("After Selection:");
            printGeneration();

            //Crossover
            population.crossover();
            System.out.println("After Crossover:");
            fitness.evaluatePopulation(population);
            printGeneration();

            //Mutation
            population.mutation(mutationRate);
            System.out.println("After Mutation:");
            fitness.evaluatePopulation(population);
            printGeneration();

            //Evaluate
            fitness.evaluatePopulation(population);
            printGeneration();
        }
        System.out.println("--------------------");
        System.out.println("Found Solution!");
        System.out.println("Generation: " + generation);
    }

    public static void printGeneration() {

        int totalFitness = 0;
        double averageFitness = 0.0;

        for (int i = 0; i < populationSize; i++) {
            totalFitness += population.getIndividual(i).getFitness();
        }

        averageFitness = (double) totalFitness / populationSize;
        System.out.println("********************");
        System.out.println("Generation: " + generation);
        System.out.println("Average Fitness = " + averageFitness);
        System.out.println("Best Individual = " + population.getFittest().getFitness());
        System.out.println("");
        System.out.println(population.toString());
    }
}
