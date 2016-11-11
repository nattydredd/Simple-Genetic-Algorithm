package floatGA;

// @author Nate
import java.util.Arrays;

public class FloatIndividual {

    //Variables
    private int fitness = 0;
    private double[] chromosome;
    private int ruleLength;

    //Constructors
    public FloatIndividual() {
        this.fitness = 0;
    }

    public FloatIndividual(double[] chromosome, int ruleLength) {
        this.fitness = 0;
        this.chromosome = chromosome;
        this.ruleLength = ruleLength;
    }

    //Methods
    public double[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(double[] chromosome) {
        this.chromosome = chromosome;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getRuleLength() {
        return ruleLength;
    }

    public void setRuleLength(int ruleLength) {
        this.ruleLength = ruleLength;
    }

    public FloatRule[] getRules() {

        FloatRule[] rules = new FloatRule[chromosome.length / ruleLength];
        FloatRule rule;

        int ruleStartIndex = 0;
        int ruleEndIndex = ruleLength - 1;

        double[] antecedent;
        double consequent;

        for (int i = 0; i < rules.length; i++) {

            antecedent = orderRangePairs(Arrays.copyOfRange(chromosome, ruleStartIndex, ruleEndIndex));
            consequent = chromosome[ruleEndIndex];

            rule = new FloatRule(antecedent, consequent);
            rules[i] = rule;

            ruleStartIndex += ruleLength;
            ruleEndIndex += ruleLength;
        }

        return rules;
    }

    public String displayRules() {
        String result = "";
        FloatRule[] rules = getRules();

        for (int i = 0; i < rules.length; i++) {
            result += rules[i].toString() + "\n";
        }
        return result;
    }
    
    public double[] orderRangePairs(double[] antecedent) {

        for (int i = 0; i < antecedent.length; i += 2) {

            if (antecedent[i] > antecedent[i + 1]) {
                double tmp = antecedent[i];
                antecedent[i] = antecedent[i + 1];
                antecedent[i + 1] = tmp;
            }
        }

        return antecedent;
    }
    
    @Override
    public String toString() {

        int consequentIndex = ruleLength - 1;
        String result = "Genes = ";
        for (int i = 0; i < chromosome.length; i++) {
            if (i != consequentIndex) {
                result += String.format("%.6f ", chromosome[i]);
            } else {
                result += String.format("%.1f ", chromosome[i]);
            }
        }
        result += " Fitness = " + fitness;

        return result;
    }

}//End FloatIndividual
