package ga;

//@author n2-duran
import java.util.ArrayList;
import java.util.Hashtable;

public class BinaryFitnessFunction {

    private static int rulesPerIndividual = 10;
    private int ruleLength;
    private ArrayList data;
    private Hashtable solutionSet;

    public BinaryFitnessFunction(ArrayList data) {
        this.data = data;
        this.solutionSet = new Hashtable(data.size());
        createSolutionSet();
    }

   public void fitnessFunction(Individual individual) {
       ruleLength = individual.getGenes().length / rulesPerIndividual;
       
       String[] individualsRules = new String[ruleLength];
       for (int i = 0; i < individual.getGenes().length; i+= ruleLength) {
           
       }

   }
    
    /** Creates a Hashtable of rules from the input data,
    * Key = antecedent
    * Value = consequent
    */
    private void createSolutionSet() {
        
        ArrayList tmp;           
        for (Object obj : data) {
            tmp = (ArrayList)obj;
            this.solutionSet.put(tmp.get(0), tmp.get(1));
        }
    }
}// End BinaryFitnessFunction
