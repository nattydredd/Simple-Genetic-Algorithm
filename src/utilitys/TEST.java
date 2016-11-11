package utilitys;

// @author Nate
import java.util.Random;

public class TEST {

    public static void main(String[] args) {
        // TODO code application logic here
        Random rng = new Random();

        for (int i = 0; i < 100; i++) {
            double num = (((rng.nextInt(1000) * 2) - 1000) / 1e4);
            System.out.printf("%.6f\n", num);
            if (true) {
                
            }
        }
        
    }//End main

}//End TEST
