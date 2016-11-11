package floatGA;

// @author Nate

public class FloatRule {

    //Variables
    double[] antecedent;
    double consequent;
    
    //Constructors
    public FloatRule(double[] antecedent, double consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }
       
    //Methods
    public double[] getAntecedent() {
        return antecedent;
    }

    public double getConsequent() {
        return consequent;
    }

    @Override
    public String toString() {
        
        String result = "";
        for (int i = 0; i < antecedent.length; i++) {
            result += String.format("%.6f ", antecedent[i]);
        }
        
        result += " " + consequent;
        
        return result; 
    }

}//End FloatRule
