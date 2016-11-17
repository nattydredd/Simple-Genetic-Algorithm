package floatGA;

// @author Nate
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
import utilitys.ChartBuilder;
import utilitys.DataLoader;

public class FloatGA {

    //Utility variables
    public static String fileName = "data3.txt";
    public static ArrayList data;
    public static ArrayList results = new ArrayList();
    public static ChartBuilder resultsChart;

    //Algorithm variables
    public static FloatFitnessFunction fitnessFunction;
    public static FloatPopulation population;

    public static int rulesPerIndividual = 10;
    public static int ruleLength = 7;

    public static int populationSize = 100;
    public static int chromosomeLength = rulesPerIndividual * ((ruleLength * 2) - 1);
    public static double mutationRate = 0.01;
    public static double mutationIncrement = 1e4;//1e4 - 1e8

    public static int targetFitness = 2000;
    public static int maxGenerations = 1000;
    public static int generation = 0;
    public static boolean elitism = true;//Keep best individual every generation
    public static boolean inbreeding = true;//Allow duplicate parents for oneChildCrossover

    public static void main(String[] args) {

        //Load data from file
        DataLoader loader = new DataLoader("src/data/", fileName);

        //Create solution set from data
        fitnessFunction = new FloatFitnessFunction(loader.getData(), rulesPerIndividual, ruleLength);

        //Initialise and evaluate a new population
        population = new FloatPopulation(populationSize, chromosomeLength, ruleLength);
        fitnessFunction.evaluatePopulation(population);

        //Display initial population
        System.out.println("Initial Population:");
        printGeneration();

        //While maxiumum generations or target fitness not reached
        while ((generation < maxGenerations) && (population.getFittestIndividual().getFitness() < targetFitness)) {
            generation++;

            //Keep best individual from previous generation
            if (elitism) {
                population.findFittest();
            }

            //Selection
            population.tournamentSelection();
//            population.rouletteSelection();

            //Crossover
//            population.oneChildCrossover(inbreeding);
            population.twoChildCrossover();

            //Mutation
            population.mutation(mutationRate, mutationIncrement);

            //Replace worst individual with best individual
            if (elitism) {
                fitnessFunction.evaluatePopulation(population);
                population.putFittest();
            }

            //Evaluate
            fitnessFunction.evaluatePopulation(population);

            //Display generation results
            printGeneration();
        }

        //Display final result
        System.out.println("---------------------------------------------");
        System.out.println("Found solution or reached maximum generations");
        System.out.println("Generation: " + generation);
        System.out.println("Best Individual: " + population.getFittestIndividual().getFitness());
        System.out.println("Rules: \n" + population.getFittestIndividual().displayRules());

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
        population.findFittest();
        System.out.println("Best Individual = " + population.getFittestIndividual().getFitness());
//        System.out.println("");
//        System.out.println(population.toString());

        //Add data to results   
        double[] generationResults = new double[2];
        generationResults[0] = averageFitness;
        generationResults[1] = population.getFittestIndividual().getFitness();
        results.add(generationResults);
    }

    public static void displayChart() {
        resultsChart = new ChartBuilder("Fitness Results", results);
        resultsChart.pack();
        RefineryUtilities.centerFrameOnScreen(resultsChart);
        resultsChart.setVisible(true);
    }

}//End FloatGA
