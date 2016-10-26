package ga;

//@author n2-duran
import java.util.Random;

public class Population {

    private Random rng = new Random();

    private int populationSize;
    private int chromosomeLength;
    private Individual fittestIndividual = new Individual();
    private Individual[] individuals;

    public Population(int populationSize, int chromosomeLength) {
        this.populationSize = populationSize;
        this.chromosomeLength = chromosomeLength;
        this.individuals = new Individual[populationSize];
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

    public void initialise() {

        for (int i = 0; i < populationSize; i++) {

            char[] tmpGenes = new char[chromosomeLength];

            for (int j = 0; j < chromosomeLength; j++) {
                tmpGenes[j] = rng.nextBoolean() ? '1' : '0';
            }
            individuals[i] = new Individual(tmpGenes);
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

            Individual offspring1 = new Individual(parent1.getChromosome().clone());
            Individual offspring2 = new Individual(parent2.getChromosome().clone());

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
            for (int j = 0; j < chromosomeLength; j++) {

                if (Math.random() <= mutationRate) {
                    if (individuals[i].getChromosome()[j] == '1') {
                        individuals[i].getChromosome()[j] = '0';
                    } else {
                        individuals[i].getChromosome()[j] = '1';
                    }
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
