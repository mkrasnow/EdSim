// kindGene.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.io.Serializable;
import java.util.ArrayList;

public class kindGene extends gene implements Serializable{
        
    //Creates a new instance of kindGene
    public kindGene(double newValue) {
        value = newValue;
        type = 0;
        
    }
    
    public void mutation(ArrayList<Double> alternatives, double mutationrate){ //this is broken, needs to be rebuilt
        double newValue=value;
        System.out.print("Gene is "+ value + ", " + alternatives);
        alternatives.remove(newValue);
        System.out.println(", and can mutate into: "+ alternatives);
        double rand = Math.random();
        
        if(rand<mutationrate){
            System.out.println("mutation!");
            
            System.out.println(alternatives + ", " + alternatives.size());
            double rand2 = Math.random();
            boolean condition = true;
            int z = 0;
            while (condition){
                double comp = ((double)(z+1))/(double)alternatives.size();
                if(rand2<comp){
                    newValue = alternatives.get(z);
                    System.out.println(rand2 + " < " + comp + ", " + newValue);
                    condition = false;
                } else {
                    System.out.println(rand2 + " > " + comp);
                    z++;
                }
            }
        }
        value=newValue;
    }
    
}
