// paramGene.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.io.Serializable;

public class paramGene extends gene implements Serializable{
    
    //Creates a new instance of paramGene
    public paramGene(double newValue) {
        value = newValue;
        type = 2;
    }
    
    public void mutation(double mrate, double msize){
        double rand = Math.random();
        if(rand<mrate){
            value += msize*Math.pow((-2*Math.log(Math.random())),.5) * Math.cos(2*Math.PI*Math.random());
        }
    }
}
