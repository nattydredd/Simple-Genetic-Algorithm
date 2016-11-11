package binaryGA;

//@author n2-duran
import java.util.ArrayList;

public class BinaryFitnessFunction {

    //Variables
    private int rulesPerIndividual;
    private int ruleLength;
    private ArrayList<BinaryRule> ruleSet;

    //Constructors
    public BinaryFitnessFunction(ArrayList data, int rulesPerIndividual, int ruleLength) {
        this.rulesPerIndividual = rulesPerIndividual;
        this.ruleLength = ruleLength;
        this.ruleSet = new ArrayList<BinaryRule>();
        //Creates array of rules from raw data
        this.ruleSet = createRuleSet(data);
    }

    //Methods
    public void evaluatePopulation(BinaryPopulation population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
            fitnessFunction(population.getIndividual(i));
        }
    }

    public void evaluateIndividual(BinaryIndividual individual) {
        fitnessFunction(individual);
    }

    private void fitnessFunction(BinaryIndividual individual) {

        int fitness = 0;
        BinaryRule[] individualsRules = individual.getRules();

        for (int i = 0; i < ruleSet.size(); i++) {
            BinaryRule rule = ruleSet.get(i);

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

    private boolean compareAntecedents(char[] individuals, char[] ruleSet) {
        for (int i = 0; i < ruleLength - 1; i++) {
            if (individuals[i] != ruleSet[i] && individuals[i] != '#') {
                return false;
            }
        }
        return true;
    }

    //Creates an ArrayList of rules from the input data,
    private ArrayList createRuleSet(ArrayList data) {

        ArrayList tmp;
        String antecedent;
        String consequent;

        for (Object obj : data) {
            tmp = (ArrayList) obj;
            antecedent = tmp.get(0).toString();
            consequent = tmp.get(1).toString();
            ruleSet.add(new BinaryRule(antecedent.toCharArray(), consequent.charAt(0)));
        }

        return ruleSet;
    }

}// End BinaryFitnessFunction
