package floatGA;

// @author Nate
import java.util.Random;

public class FloatPopulation {

    //Variables
    private Random rng = new Random();

    private int populationSize;
    private int chromosomeLength;
    private int ruleLength;
    private FloatIndividual fittestIndividual;
    private FloatIndividual[] individuals;

    //Constructors
    public FloatPopulation(int populationSize, int chromosomeLength, int ruleLength) {
        this.populationSize = populationSize;
        this.chromosomeLength = chromosomeLength;
        this.ruleLength = (ruleLength * 2) - 1;
        this.fittestIndividual = new FloatIndividual();
        this.individuals = new FloatIndividual[populationSize];
        //Initialise a new population
        initialise();
    }

    //Methods
    public int getPopulationSize() {
        return populationSize;
    }

    public int getChromosomeLength() {
        return chromosomeLength;
    }

    public FloatIndividual[] getIndividuals() {
        return individuals;
    }

    public FloatIndividual getIndividual(int index) {
        return individuals[index];
    }

    public FloatIndividual getFittestIndividual() {
        return fittestIndividual;
    }

    public void setFittestIndividual(FloatIndividual fittestIndividual) {
        this.fittestIndividual = fittestIndividual;
    }

    public void findFittest() {

        fittestIndividual = new FloatIndividual();

        for (int i = 0; i < populationSize; i++) {

            if (individuals[i].getFitness() > fittestIndividual.getFitness()) {
                fittestIndividual = individuals[i];
            }
        }

        setFittestIndividual(fittestIndividual);
    }

    public void putFittest() {
        int lowestFitness = individuals[0].getFitness();
        int lowestFitnessIndex = 0;

        for (int i = 0; i < populationSize; i++) {

            if (individuals[i].getFitness() < lowestFitness) {
                lowestFitness = individuals[i].getFitness();
                lowestFitnessIndex = i;
            }
        }

        individuals[lowestFitnessIndex] = getFittestIndividual();
    }

    public void initialise() {

        for (int i = 0; i < populationSize; i++) {

            double[] tmpChromosome = new double[chromosomeLength];

            int consequentIndex = ruleLength - 1;
            for (int j = 0; j < chromosomeLength; j++) {

                //Check index is not a rule consequent
                if (j != consequentIndex) {

                    tmpChromosome[j] = rng.nextInt(1000000) / 1e6;
                } else {
                    tmpChromosome[j] = rng.nextBoolean() ? 1.0 : 0.0;
                    consequentIndex += ruleLength;
                }
            }
            individuals[i] = new FloatIndividual(tmpChromosome, ruleLength);
        }
    }

    public void tournamentSelection() {

        FloatIndividual[] newPopulation = new FloatIndividual[populationSize];

        for (int i = 0; i < populationSize; i++) {

            FloatIndividual selectedIndividual;

            int parent1 = rng.nextInt(populationSize);
            int parent2 = rng.nextInt(populationSize);

            if (individuals[parent1].getFitness() >= individuals[parent2].getFitness()) {
                selectedIndividual = individuals[parent1];
            } else {
                selectedIndividual = individuals[parent2];
            }

            newPopulation[i] = selectedIndividual;
        }
        this.individuals = newPopulation;
    }

    public void rouletteSelection() {

        FloatIndividual[] newPopulation = new FloatIndividual[populationSize];

        int totalFitness = 0;
        for (int i = 0; i < populationSize; i++) {
            totalFitness += individuals[i].getFitness();
        }

        for (int i = 0; i < populationSize; i++) {

            FloatIndividual selectedIndividual = new FloatIndividual();
            int selectionPoint = rng.nextInt(totalFitness);
            int tmpFitness = 0;
            for (int j = 0; j < populationSize; j++) {

                tmpFitness += individuals[j].getFitness();
                if (tmpFitness >= selectionPoint) {
                    selectedIndividual = individuals[j];
                    break;
                }
            }
            newPopulation[i] = selectedIndividual;
        }
        this.individuals = newPopulation;
    }

    public void oneChildCrossover() {

        FloatIndividual[] newPopulation = new FloatIndividual[populationSize];

        for (int i = 0; i < populationSize; i++) {

            FloatIndividual parent1 = individuals[rng.nextInt(populationSize)];
            FloatIndividual parent2 = individuals[rng.nextInt(populationSize)];

            FloatIndividual offspring = new FloatIndividual(parent1.getChromosome().clone(), ruleLength);

            int crossoverPoint = rng.nextInt(chromosomeLength);

            for (int j = crossoverPoint; j < chromosomeLength; j++) {
                offspring.getChromosome()[j] = parent2.getChromosome()[j];

            }

            newPopulation[i] = offspring;
        }

        this.individuals = newPopulation;
    }

    public void twoChildCrossover() {

        FloatIndividual[] newPopulation = new FloatIndividual[populationSize];

        for (int i = 0; i < populationSize; i += 2) {

            FloatIndividual parent1 = individuals[i];
            FloatIndividual parent2 = individuals[i + 1];

            FloatIndividual offspring1 = new FloatIndividual(parent1.getChromosome().clone(), ruleLength);
            FloatIndividual offspring2 = new FloatIndividual(parent2.getChromosome().clone(), ruleLength);

            int crossoverPoint = rng.nextInt(chromosomeLength);

            for (int j = crossoverPoint; j < chromosomeLength; j++) {
                offspring1.getChromosome()[j] = parent2.getChromosome()[j];
                offspring2.getChromosome()[j] = parent1.getChromosome()[j];
            }

            newPopulation[i] = offspring1;
            newPopulation[i + 1] = offspring2;
        }

        this.individuals = newPopulation;
    }

    public void mutation(double mutationRate, double mutationIncrement) {

        for (int i = 0; i < populationSize; i++) {

            int consequentIndex = ruleLength - 1;
            for (int j = 0; j < chromosomeLength; j++) {

                if (Math.random() <= mutationRate) {

                    //Check index is not a rule consequent
                    if (j != consequentIndex) {
                       
                        double mutationAmount = (((rng.nextInt(1000) * 2) - 1000) / mutationIncrement);

                        if (mutationAmount + individuals[i].getChromosome()[j] >= 1 || 
                            mutationAmount + individuals[i].getChromosome()[j] <= 0) {
                            mutationAmount *= -1;
                        }
                        
                        individuals[i].getChromosome()[j] += mutationAmount;
                        
                        //Check gene is > 0 and < 1
                        if (individuals[i].getChromosome()[j] > 1 || individuals[i].getChromosome()[j] < 0) {
                            System.exit(0);
                        }
                        
                    } else if (j == consequentIndex) {

                        if (individuals[i].getChromosome()[j] == 1.0) {
                            individuals[i].getChromosome()[j] = 0.0;
                        } else {
                            individuals[i].getChromosome()[j] = 1.0;
                        }
                    }
                }
                if (j == consequentIndex) {
                    consequentIndex += ruleLength;
                }
            }
        }
    }

    @Override
    public String toString() {

        String result = "";
        for (int i = 0; i < populationSize; i++) {
            result += individuals[i].toString();
            result += "\n";
        }
        return result;
    }

}//End FloatPopulation
