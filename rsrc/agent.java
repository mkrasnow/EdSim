// agent.java
// Completed: ?
// author Max K

package rsrc;

import java.io.Serializable;
import java.util.ArrayList;

public class agent implements Serializable{
    public double fitness;
    public ArrayList<gene> genes;

    //constructors
    public agent(){
    }

    //Setter Functions
    public void changeFitness(double change){
        fitness+=change;
    }
    public void scaleFitness(double scaler){
        fitness/=scaler;
    }
    public void setFitness(double newfitness){
        fitness = newfitness;
    }

    //Getter Functions
    public double getFitness(){
        return fitness;
    }
    
    public String genesToString(){
        String toReturn = "";
        for(gene thisGene:genes){
            toReturn+=thisGene.value;
        }
        toReturn+=",";
        return toReturn;
    }
}