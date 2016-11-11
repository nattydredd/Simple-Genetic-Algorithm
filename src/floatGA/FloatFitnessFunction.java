package floatGA;

// @author Nate
import java.util.ArrayList;

public class FloatFitnessFunction {

    //Variables
    private int rulesPerIndividual;
    private int ruleLength;
    private ArrayList<FloatRule> ruleSet;

    //Constructors
    public FloatFitnessFunction(ArrayList data, int rulesPerIndividual, int ruleLength) {
        this.rulesPerIndividual = rulesPerIndividual;
        this.ruleLength = ruleLength;
        this.ruleSet = new ArrayList<FloatRule>();
        //Creates array of rules from raw data
        this.ruleSet = createRuleSet(data);

    }

    //Methods
    public void evaluatePopulation(FloatPopulation population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
            fitnessFunction(population.getIndividual(i));
        }
    }

    public void evaluateIndividual(FloatIndividual individual) {
        fitnessFunction(individual);
    }

    private void fitnessFunction(FloatIndividual individual) {

        int fitness = 0;
        FloatRule[] individualsRules = individual.getRules();

        for (int i = 0; i < ruleSet.size(); i++) {
            FloatRule rule = ruleSet.get(i);
            
            //Compare individuals rule to ruleSet
            for (int j = 0; j < individualsRules.length; j++) {
                
                if (compareAntecedents(individualsRules[j].antecedent, rule.antecedent)) {

                    if (individualsRules[j].consequent == rule.consequent) {
                        fitness++;
                    }
                    break;
                }
            }
        }
        individual.setFitness(fitness);
    }

    private boolean compareAntecedents(double[] individuals, double[] ruleSet) {
        
        boolean result = true;
        int index = 0;
        
        for (int i = 0; i < individuals.length - 1; i += 2) {

            //Check if rule values lie within max and min range from individuals pair
            if (individuals[i] < individuals[i + 1]) {
                result = ruleSet[index] > individuals[i] && ruleSet[index] < individuals[i + 1];
            }
            else if (individuals[i] > individuals[i + 1]) {
                result = ruleSet[index] < individuals[i] && ruleSet[index] > individuals[i + 1];
            }
            
            if (!result) {
                break;
            }
            index++;
        }
        return result;
    }
    
    //Creates an ArrayList of rules from the input data,
    private ArrayList createRuleSet(ArrayList data) {

        ArrayList tmp;

        for (Object obj : data) {
            tmp = (ArrayList) obj;
            double[] antecedent = new double[ruleLength - 1];
            double consequent;

            for (int i = 0; i < tmp.size() - 1; i++) {
                antecedent[i] = Double.parseDouble((String) tmp.get(i));
            }
            consequent = Double.parseDouble((String) tmp.get(ruleLength - 1));
            
            ruleSet.add(new FloatRule(antecedent, consequent));
        }

        return ruleSet;
    }
}//End FloatFitnessFunction
