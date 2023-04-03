// HDagent.java
// Author: Max Krasnow
// Completed: ?
package sim155;

import rsrc.agent;
import rsrc.kindGene;
import java.util.ArrayList;

public class HDagent extends agent {
    //String type;
    
    //Creates a new instance of HDagent
    public HDagent(double basefit, double newtype) {
        fitness = basefit;
        genes = new ArrayList();
        genes.add(new kindGene(newtype));
    }
    
    public void setType(double newtype){
        genes.set(0, new kindGene(newtype));
    }
    public double getType(){
        return genes.get(0).value;
    }
    
}
