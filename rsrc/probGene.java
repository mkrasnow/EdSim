// probGene.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.io.Serializable;
import java.util.Random;

public class probGene extends gene implements Serializable{
      
    //Creates a new instance of probGene
    public probGene(double newValue) {
        if(newValue>=0 && newValue<=1){
            value = newValue;
            type = 1;
        } else {
            System.out.println("Gene state out of defined range!");
        }
    }
    
    public void mutation(double mrate, double msize){
        double rand = Math.random();
        Random rando = new Random();
        if(rand<mrate){
            value += msize*rando.nextGaussian();
        }
        value = Math.max(Math.min(value,1),0);
    }
}
