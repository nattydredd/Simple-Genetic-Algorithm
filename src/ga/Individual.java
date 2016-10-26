package ga;

//@author n2-duran

public class Individual {
    private char[] chromosome;
    private int fitness = 0;

    public Individual() {
        this.fitness = 0;
    }

    public Individual(char[] chromosome) {
        this.fitness = 0;
        this.chromosome = chromosome;
    }

    public char[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(char[] chromosome) {
        this.chromosome = chromosome;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    
    @Override
    public String toString() {
        
        String result = "Genes = ";     
        for (int i = 0; i < chromosome.length; i++) {           
            result += chromosome[i];
        }
        result += " Chromosome length = " + chromosome.length;
        result += " Fitness = " + fitness;
        
        return result;               
    }
    
}// End Individual
