package ga;

//@author n2-duran

public class Individual {
    private char[] genes;
    private int fitness = 0;

    public Individual() {     
    }

    public Individual(char[] genes) {
        this.genes = genes;
    }

    public char[] getGenes() {
        return genes;
    }

    public void setGenes(char[] genes) {
        this.genes = genes;
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
        
        for (int i = 0; i < this.genes.length; i++) {           
            result += genes[i];
        }
        result += " Chromosome length = " + genes.length;
        result += " Fitness = " + fitness;
        return result;
                
    }
        
}
