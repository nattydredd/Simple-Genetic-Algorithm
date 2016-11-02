package ga;

import utilitys.DataLoader;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
import utilitys.ChartBuilder;

//@author n2-duran
public class AssignmentGA {

    //Utility variables
    public static String fileName = "data1.txt";
    public static ArrayList data;
    public static ArrayList results = new ArrayList();
    public static ChartBuilder resultsChart;

    //Algorithm variables
    public static BinaryFitnessFunction fitness;
    public static Population population;

    public static int rulesPerIndividual = 10;
    public static int ruleLength = 6;

    public static int populationSize = 10;
    public static int chromosomeLength = rulesPerIndividual * ruleLength;
    public static double mutationRate = 0.03;

    public static int targetFitness = 32;
    public static int maxGenerations = 100;
    public static int generation = 0;

    public static void main(String[] args) {

        //Load data from file
        DataLoader loader = new DataLoader("src/data/", fileName);

        //Create solution set from data
        fitness = new BinaryFitnessFunction(loader.getData(), rulesPerIndividual, ruleLength);

        //Initialise and evaluate a new population
        population = new Population(populationSize, chromosomeLength, ruleLength);
        fitness.evaluatePopulation(population);

        //Display initial population
        System.out.println("Initial Population:");
        printGeneration();

        //While maxiumum generations or target fitness not reached
        while ((generation < maxGenerations) ^ (population.getFittest().getFitness() == targetFitness)) {
            generation++;

            //Selection
            population.tournamentSelection();

            //Crossover
            population.crossover();

            //Mutation
            population.mutation(mutationRate);

            //Evaluate
            fitness.evaluatePopulation(population);
            printGeneration();
        }

        //Display final result
        System.out.println("---------------------------------------------");
        System.out.println("Found solution or reached maximum generations");
        System.out.println("Generation: " + generation);
        System.out.println("Best Individual: " + population.getFittest().toString());
        System.out.println("Rules: \n" + population.getFittest().displayRules());

        //Display chart
        displayChart();
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

        //Add data to results   
        double[] generationResults = new double[2];
        generationResults[0] = averageFitness;
        generationResults[1] = population.getFittest().getFitness();
        results.add(generationResults);

    }

    public static void displayChart() {
        resultsChart = new ChartBuilder("Fitness Results", results);
        resultsChart.pack();
        RefineryUtilities.centerFrameOnScreen(resultsChart);
        resultsChart.setVisible(true);
    }
}
