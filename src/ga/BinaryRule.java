package ga;

// @author Nate

public class BinaryRule {

    //Variables
    char[] antecedent;
    char consequent;
    
    //Constructors
    public BinaryRule(char[] antecedent, char consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }
       
    //Methods
    public char[] getAntecedent() {
        return antecedent;
    }

    public char getConsequent() {
        return consequent;
    }

    @Override
    public String toString() {
        
        String result = "";
        for (int i = 0; i < antecedent.length; i++) {
            result += antecedent[i];
        }
        
        result += " " + consequent;
        
        return result; 
    }

}//End BinaryRule
