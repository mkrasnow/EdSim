// kindGene.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.io.Serializable;
import java.util.ArrayList;

public class psuedoProbGene extends gene implements Serializable{
        
    //Creates a new instance of kindGene
    public psuedoProbGene(double newValue) {
        value = newValue;
        type = 0;
        
    }
    
    public void mutation(ArrayList<Double> alternatives, double mutationrate){
        double newType=value;
        double rand = Math.random();
        if(rand<mutationrate){
            //System.out.println("mutation!");
            double rand2 = Math.random();
            boolean condition = true;
            int z = 0;
            while (condition){
                double comp = ((double)(z+1))/(double)alternatives.size();
                if(rand2<comp){
                    newType = alternatives.get(z);
                    //System.out.println(rand2 + " < " + comp + ", " + newType);
                    condition = false;
                } else {
                    //System.out.println(rand2 + " > " + comp);
                    z++;
                }
            }
        }
        value=newType;
    }
    
}
