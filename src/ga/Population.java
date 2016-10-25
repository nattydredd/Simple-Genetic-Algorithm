package ga;

//@author n2-duran
import java.util.Random;

public class Population {

    private int populationSize;
    private int geneLength;
    private Individual[] individuals;

    public Population(int populationSize, int geneLength) {
        this.populationSize = populationSize;
        this.geneLength = geneLength;
        this.individuals = new Individual[populationSize];
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getGeneLength() {
        return geneLength;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public void initialise() {
        Random rng = new Random();
        
        for (int i = 0; i < populationSize; i++) {

            char[] tmpGenes = new char[geneLength];

            for (int j = 0; j < geneLength; j++) {
                tmpGenes[j] = rng.nextBoolean() ? '1' : '0';
            }
            this.individuals[i] = new Individual(tmpGenes);
        }
    }
}
