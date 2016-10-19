package ga;

//@author n2-duran

public class Individual {
    boolean[] genes;
    int fitness = 0;

    public Individual() {     
    }
    
    public Individual(boolean[] genes) {
        this.genes = genes;
    }

    public Individual(int fitness) {
        this.fitness = fitness;
    }
    
    public boolean[] getGenes() {
        return genes;
    }

    public void setGenes(boolean[] genes) {
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
            
            result += genes[i] ? "1 " : "0 ";
        }
        
        result += " Fitness = " + fitness;
        return result;
                
    }
        
}
