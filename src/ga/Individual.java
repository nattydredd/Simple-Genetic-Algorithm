package ga;

//@author n2-duran
import java.util.Arrays;

public class Individual {

    private int fitness = 0;
    private char[] chromosome;
    private int ruleLength;

    public Individual() {
        this.fitness = 0;
    }

    public Individual(char[] chromosome, int ruleLength) {
        this.fitness = 0;
        this.chromosome = chromosome;
        this.ruleLength = ruleLength;
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

    public int getRuleLength() {
        return ruleLength;
    }

    public void setRuleLength(int ruleLength) {
        this.ruleLength = ruleLength;
    }

    public BinaryRule[] getRules() {

        BinaryRule[] rules = new BinaryRule[chromosome.length / ruleLength];
        BinaryRule rule;

        int ruleStartIndex = 0;
        int ruleEndIndex = ruleLength - 1;

        for (int i = 0; i < rules.length; i++) {

            rule = new BinaryRule(Arrays.copyOfRange(chromosome, ruleStartIndex, ruleEndIndex), chromosome[ruleEndIndex]);
            rules[i] = rule;

            ruleStartIndex += ruleLength;
            ruleEndIndex += ruleLength;
        }

        return rules;
    }

    public String displayRules() {
        String result = "";
        BinaryRule[] rules = getRules();

        for (int i = 0; i < rules.length; i++) {
            result += rules[i].toString() + "\n";
        }
        return result;
    }

    @Override
    public String toString() {

        String result = "Genes = ";
        for (int i = 0; i < chromosome.length; i++) {
            result += chromosome[i];
        }
        result += " Fitness = " + fitness;

        return result;
    }

}// End Individual
