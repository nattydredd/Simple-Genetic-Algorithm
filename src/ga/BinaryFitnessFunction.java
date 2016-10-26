package ga;

//@author n2-duran
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryFitnessFunction {

    private int rulesPerIndividual;
    private int ruleLength;
    private static ArrayList<BinaryRule> ruleSet;

    public BinaryFitnessFunction(ArrayList data, int rulesPerIndividual, int ruleLength) {
        this.rulesPerIndividual = rulesPerIndividual;
        this.ruleLength = ruleLength;       
        this.ruleSet = new ArrayList<BinaryRule>();
        this.ruleSet = createRuleSet(data);
    }

    public void evaluatePopulation(Population population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
            fitnessFunction(population.getIndividual(i));
        }
    }

    public void evaluateIndividual(Individual individual) {
        fitnessFunction(individual);
    }

    private void fitnessFunction(Individual individual) {

        int fitness = 0;
        char[] antecedent = new char[rulesPerIndividual];
        char consequent;
        int index = 0;
        for (int i = 0; i < rulesPerIndividual; i++) {

            //Extract rules from individual
            antecedent = Arrays.copyOfRange(individual.getChromosome(), index, (index + ruleLength - 1));
            consequent = individual.getChromosome()[index + ruleLength - 1];
            index += ruleLength;

            //Compare individuals rules to ruleSet
            for (int j = 0; j < ruleSet.size(); j++) {
                BinaryRule rule = ruleSet.get(j);

                if (compareAntecedents(antecedent, rule.antecedent)) {

//                    System.out.println("Rule matches!");
//                    System.out.print("Antecedent = ");
//                    for (int k = 0; k < antecedent.length; k++) {
//                        System.out.print(antecedent[k]);
//                    }
//                    System.out.print("\nRule Antecedent = ");
//                    for (int k = 0; k < antecedent.length; k++) {
//                        System.out.print(rule.antecedent[k]);
//                    }
//                    System.out.println("\nConsequent = " + consequent + "\nRule Consequent = " + rule.consequent);
                   
                    if (consequent == rule.consequent) {
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
