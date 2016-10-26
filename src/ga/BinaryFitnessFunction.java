package ga;

//@author n2-duran
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryFitnessFunction {

    private int rulesPerIndividual;
    private int ruleLength;
    private static ArrayList<BinaryRule> ruleSet;

    public BinaryFitnessFunction(ArrayList data, int rulesPerIndividual) {
        this.rulesPerIndividual = rulesPerIndividual;
        this.ruleSet = new ArrayList<BinaryRule>();
        this.ruleSet = createRuleSet(data);
    }

    public void evaluatePopulation(Population population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
//            System.out.println("Evaluating individual " + i);
            fitnessFunction(population.getIndividual(i));
        }
    }

    public void evaluateIndividual(Individual individual) {
        fitnessFunction(individual);
    }

    private void fitnessFunction(Individual individual) {

        int fitness = 0;
        ruleLength = individual.getChromosome().length / rulesPerIndividual;
        ArrayList<BinaryRule> tmpRuleSet = (ArrayList) ruleSet.clone();
        char[] antecedent = new char[rulesPerIndividual];
        char consequent;
        int index = 0;
        for (int i = 0; i < rulesPerIndividual; i++) {

            //Extract rules from individual
            antecedent = Arrays.copyOfRange(individual.getChromosome(), index, (index + ruleLength - 1));
            consequent = individual.getChromosome()[index + ruleLength - 1];
            index += ruleLength;

            //Compare individuals rules to ruleSet
//            for (BinaryRule rule : tmpRuleSet) {
            for (int j = 0; j < tmpRuleSet.size(); j++) {
                BinaryRule rule = tmpRuleSet.get(j);

                if (compareAntecedents(antecedent, rule.antecedent) && consequent == rule.consequent) {

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

                    tmpRuleSet.remove(j);
                    fitness++;
                }
            }
        }
        individual.setFitness(fitness);
    }

    private boolean compareAntecedents(char[] individuals, char[] ruleSet) {
        for (int i = 0; i < ruleLength - 1; i++) {
            if (individuals[i] != ruleSet[i]) {
                return false;
            }
        }
        return true;
    }

    // Creates an ArrayList of rules from the input data,
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

        //Display rule Set
//        System.out.println("Rule Set: ");
//        for (int i = 0; i < ruleSet.size(); i++) {
//            System.out.println(ruleSet.get(i));
//        }
//        System.out.println("Rule Set Size: " + ruleSet.size());
        return ruleSet;
    }

}// End BinaryFitnessFunction
