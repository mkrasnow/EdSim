// TypeDistribution.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.io.Serializable;

public class TypeDistribution implements Serializable {
    public String type;
    public double proportion;
    public double sd, se, nviable, viable, solo, mate, offspring, groupOff, alphas, helped, asked;

    
    
    //Creates a new instance of TypeDistribution
    public TypeDistribution(String t, double p) {
        type=t;
        proportion=p;      
    }
    
    public TypeDistribution(String t, double p, double[] newSDSE){
        type=t;
        proportion=p; 
        sd = newSDSE[0];
        se = newSDSE[1];
    }
    
}
