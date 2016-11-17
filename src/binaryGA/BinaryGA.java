package binaryGA;

import utilitys.DataLoader;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
import utilitys.ChartBuilder;
import utilitys.XYPlotTestChartBuilder;

//@author n2-duran
public class BinaryGA {

    //Utility variables
    public static String fileName = "data2.txt";
    public static ArrayList data;
    public static ArrayList results = new ArrayList();
    public static ChartBuilder resultsChart;

    public static ArrayList testResults = new ArrayList();
    public static XYPlotTestChartBuilder testChart;

    //Algorithm variables
    public static BinaryFitnessFunction fitnessFunction;
    public static BinaryPopulation population;

    public static int rulesPerIndividual = 5;
    public static int ruleLength = 7;

    public static int populationSize = 300;
    public static int chromosomeLength = rulesPerIndividual * ruleLength;
    public static double mutationRate = 0.008;

    public static int targetFitness = 64;
    public static int maxGenerations = 1000;
    public static int generation = 0;
    public static boolean elitism = true;//Keep best individual every generation
    public static boolean inbreeding = true;//Allow duplicate parents for oneChildCrossover

    public static void main(String[] args) {

        //Load data from file
        DataLoader loader = new DataLoader("src/data/", fileName);

        //Create solution set from data
        fitnessFunction = new BinaryFitnessFunction(loader.getData(), rulesPerIndividual, ruleLength);

        //Initialise and evaluate a new population
        population = new BinaryPopulation(populationSize, chromosomeLength, ruleLength);
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
            population.mutation(mutationRate);

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

        //Add data to chart results   
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

}//End BinaryGA
