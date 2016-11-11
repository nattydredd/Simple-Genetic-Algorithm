package binaryGA;

//@author n2-duran
import java.util.Arrays;
import java.util.Random;

public class BinaryPopulation {

    //Variables
    private Random rng = new Random();

    private int populationSize;
    private int chromosomeLength;
    private int ruleLength;
    private BinaryIndividual fittestIndividual;
    private BinaryIndividual[] individuals;

    //Constructors
    public BinaryPopulation(int populationSize, int chromosomeLength, int ruleLength) {
        this.populationSize = populationSize;
        this.chromosomeLength = chromosomeLength;
        this.ruleLength = ruleLength;
        this.fittestIndividual = new BinaryIndividual();
        this.individuals = new BinaryIndividual[populationSize];
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

    public BinaryIndividual[] getIndividuals() {
        return individuals;
    }

    public BinaryIndividual getIndividual(int index) {
        return individuals[index];
    }

    public BinaryIndividual getFittestIndividual() {
        return fittestIndividual;
    }

    public void setFittestIndividual(BinaryIndividual fittestIndividual) {
        this.fittestIndividual = fittestIndividual;
    }

    public void findFittest() {

        fittestIndividual = new BinaryIndividual();

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

            char[] tmpChromosome = new char[chromosomeLength];

            int consequentIndex = ruleLength - 1;
            for (int j = 0; j < chromosomeLength; j++) {

                //Check index is not a rule consequent
                if (j != consequentIndex) {

                    int tmpRng = rng.nextInt(3);
                    if (tmpRng == 2) {
                        tmpChromosome[j] = '#';
                    } else {
                        tmpChromosome[j] = (tmpRng == 1) ? '1' : '0';
                    }
                } else {
                    tmpChromosome[j] = rng.nextBoolean() ? '1' : '0';
                    consequentIndex += ruleLength;
                }
            }
            individuals[i] = new BinaryIndividual(tmpChromosome, ruleLength);
        }
    }

    public void tournamentSelection() {

        BinaryIndividual[] newPopulation = new BinaryIndividual[populationSize];

        for (int i = 0; i < populationSize; i++) {

            BinaryIndividual selectedIndividual;
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

        BinaryIndividual[] newPopulation = new BinaryIndividual[populationSize];

        int totalFitness = 0;
        for (int i = 0; i < populationSize; i++) {
            totalFitness += individuals[i].getFitness();
        }

        for (int i = 0; i < populationSize; i++) {

            BinaryIndividual selectedIndividual = new BinaryIndividual();
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

    public void oneChildCrossover(boolean inbreeding) {

        BinaryIndividual[] newPopulation = new BinaryIndividual[populationSize];

        for (int i = 0; i < populationSize; i++) {

            BinaryIndividual parent1 = individuals[rng.nextInt(populationSize)];
            BinaryIndividual parent2 = individuals[rng.nextInt(populationSize)];

            if (!inbreeding) {
                while (isDuplicate(parent1, parent2)) {
                    parent2 = individuals[rng.nextInt(populationSize)];
                }
            }

            BinaryIndividual offspring = new BinaryIndividual(parent1.getChromosome().clone(), ruleLength);

            int crossoverPoint = rng.nextInt(chromosomeLength);

            for (int j = crossoverPoint; j < chromosomeLength; j++) {
                offspring.getChromosome()[j] = parent2.getChromosome()[j];

            }

            newPopulation[i] = offspring;
        }

        this.individuals = newPopulation;
    }

    public void twoChildCrossover() {

        BinaryIndividual[] newPopulation = new BinaryIndividual[populationSize];

        for (int i = 0; i < populationSize; i += 2) {

            BinaryIndividual parent1 = individuals[i];
            BinaryIndividual parent2 = individuals[i + 1];

            BinaryIndividual offspring1 = new BinaryIndividual(parent1.getChromosome().clone(), ruleLength);
            BinaryIndividual offspring2 = new BinaryIndividual(parent2.getChromosome().clone(), ruleLength);

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

    public void mutation(double mutationRate) {

        for (int i = 0; i < populationSize; i++) {

            int consequentIndex = ruleLength - 1;
            for (int j = 0; j < chromosomeLength; j++) {

                if (Math.random() <= mutationRate) {

                    //Check index is not a rule consequent
                    if (j != consequentIndex) {

                        if (individuals[i].getChromosome()[j] == '1') {
                            individuals[i].getChromosome()[j] = (rng.nextBoolean()) ? '#' : '0';
                        } else if (individuals[i].getChromosome()[j] == '0') {
                            individuals[i].getChromosome()[j] = (rng.nextBoolean()) ? '#' : '1';
                        } else {
                            individuals[i].getChromosome()[j] = (rng.nextBoolean()) ? '1' : '0';
                        }
                    } else if (j == consequentIndex) {

                        if (individuals[i].getChromosome()[j] == '1') {
                            individuals[i].getChromosome()[j] = '0';
                        } else {
                            individuals[i].getChromosome()[j] = '1';
                        }
                    }
                }
                if (j == consequentIndex) {
                    consequentIndex += ruleLength;
                }

            }
        }
    }

    private boolean isDuplicate(BinaryIndividual parent1, BinaryIndividual parent2) {

        boolean result = true;
        BinaryRule[] parent1Rules = parent1.getRules();
        BinaryRule[] parent2Rules = parent2.getRules();

        for (int i = 0; i < parent1Rules.length; i++) {

            BinaryRule parent1Rule = parent1Rules[i];
            BinaryRule parent2Rule = parent2Rules[i];

            if (!Arrays.equals(parent1Rule.getAntecedent(), parent2Rule.getAntecedent()) && 
                    parent1Rule.getConsequent() != parent2Rule.getConsequent()) {
                result = false;
                break;
            }
        }
        return result;
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

}// End BinaryPopulation
