package ga;

//@author n2-duran
import java.util.Random;

public class Population {

    private Random rng = new Random();

    private int populationSize;
    private int chromosomeLength;
    private int ruleLength;
    private Individual fittestIndividual;
    private Individual[] individuals;

    public Population(int populationSize, int chromosomeLength, int ruleLength) {
        this.populationSize = populationSize;
        this.chromosomeLength = chromosomeLength;
        this.ruleLength = ruleLength;
        this.fittestIndividual = new Individual();
        this.individuals = new Individual[populationSize];
        //Initialise a new population
        initialise();
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getChromosomeLength() {
        return chromosomeLength;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {

        fittestIndividual = new Individual();

        for (int i = 0; i < populationSize; i++) {

            if (individuals[i].getFitness() > fittestIndividual.getFitness()) {
                fittestIndividual = individuals[i];
            }
        }

        return fittestIndividual;
    }

    public void setFittest() {
        int lowestFitness = individuals[0].getFitness();
        int lowestFitnessIndex = 0;

        for (int i = 0; i < populationSize; i++) {

            if (individuals[i].getFitness() < lowestFitness) {
                lowestFitness = individuals[i].getFitness();
                lowestFitnessIndex = i;
            }
        }

        individuals[lowestFitnessIndex] = fittestIndividual;
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
            individuals[i] = new Individual(tmpChromosome, ruleLength);
        }
    }

    public void tournamentSelection() {

        Individual[] newPopulation = new Individual[populationSize];

        for (int i = 0; i < populationSize; i++) {

            Individual offspring;
            int parent1 = rng.nextInt(populationSize);
            int parent2 = rng.nextInt(populationSize);

            if (individuals[parent1].getFitness() >= individuals[parent2].getFitness()) {
                offspring = individuals[parent1];
            } else {
                offspring = individuals[parent2];
            }
            newPopulation[i] = offspring;
        }
        this.individuals = newPopulation;
    }

    public void crossover() {

        Individual[] newPopulation = new Individual[populationSize];

        for (int i = 0; i < populationSize; i += 2) {

            Individual parent1 = individuals[i];
            Individual parent2 = individuals[i + 1];

            Individual offspring1 = new Individual(parent1.getChromosome().clone(), ruleLength);
            Individual offspring2 = new Individual(parent2.getChromosome().clone(), ruleLength);

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

                //Check index is not a rule consequent
                if (j != consequentIndex) {
                    if (Math.random() <= mutationRate) {

                        if (individuals[i].getChromosome()[j] == '1') {
                            individuals[i].getChromosome()[j] = (rng.nextBoolean()) ? '#' : '0';
                        } else {
                            individuals[i].getChromosome()[j] = (rng.nextBoolean()) ? '#' : '1';
                        }
                    }
                } else {
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

}// End Population
