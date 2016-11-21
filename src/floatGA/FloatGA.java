package floatGA;

// @author Nate
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
import utilitys.DataLoader;
import utilitys.FloatGAChartBuilder;

public class FloatGA {

    //Utility variables
    public static String trainingFileName = "data3Training.txt";
    public static String validationFileName = "data3Validation.txt";
    public static DataLoader trainingLoader;
    public static DataLoader validationLoader;

    public static ArrayList results = new ArrayList();
    public static FloatGAChartBuilder resultsChart;

    //Algorithm variables
    public static FloatFitnessFunction fitnessFunction;
    public static FloatFitnessFunction validationFunction;
    public static FloatPopulation population;

    public static int rulesPerIndividual = 6;
    public static int ruleLength = 7;

    public static int populationSize = 600;
    public static int chromosomeLength = rulesPerIndividual * ((ruleLength * 2) - 1);
    public static double mutationRate = 0.003;
    public static double mutationIncrement = 1e4;//1e4 - 1e8

    public static int targetFitness = 1215;//Training 1215, Validation 785
    public static int maxGenerations = 20000;
    public static int generation = 0;
    public static boolean elitism = true;//Keep best individual every generation
    public static boolean inbreeding = true;//Allow duplicate parents for oneChildCrossover

    public static void main(String[] args) {

        //Load validation set from file
        validationLoader = new DataLoader("src/data/", validationFileName);

        //Create validation set from data
        validationFunction = new FloatFitnessFunction(validationLoader.getData(), rulesPerIndividual, ruleLength);

        //Load training data from file
        trainingLoader = new DataLoader("src/data/", trainingFileName);

        //Create training solution set from data
        fitnessFunction = new FloatFitnessFunction(trainingLoader.getData(), rulesPerIndividual, ruleLength);

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
        //Display final evaluation
        evaluate(population.getFittestIndividual().clone(), true);

        //Display chart
        displayChart();
    }

    //Evaluate individuals performance against evaluation set
    public static double evaluate(FloatIndividual fittest, boolean verbose) {

        validationFunction.evaluateIndividual(fittest);

        if (verbose) {
            System.out.println("Validation Set Results:");
            System.out.println("Fitness " + fittest.getFitness());
            double percentCorrect = ((double) fittest.getFitness() / (double) validationFunction.getRuleSet().size()) * 100.0;
            System.out.println("Percent Correct " + percentCorrect + "%");
        }

        return fittest.getFitness();
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
        System.out.println("");
//        System.out.println(population.toString());

        //Add data to results   
        double[] generationResults = new double[3];
        generationResults[0] = averageFitness;
        generationResults[1] = population.getFittestIndividual().getFitness();
        generationResults[2] = evaluate(population.getFittestIndividual().clone(), true);

        results.add(generationResults);
    }

    public static void displayChart() {
        resultsChart = new FloatGAChartBuilder("Results", results);
        resultsChart.pack();
        RefineryUtilities.centerFrameOnScreen(resultsChart);
        resultsChart.setVisible(true);
    }

}//End FloatGA
