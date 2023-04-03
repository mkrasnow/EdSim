/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author krasnow
 */
public class switchGene extends gene implements Serializable{
        
    //Creates a new instance of kindGene
    public switchGene(double newValue) {
        value = newValue;
        type = 3;
        
    }
    
    public void mutation(double mutationrate){
        double rand = Math.random();
        if(rand<mutationrate){
            if (value==1){
                value=0;
            } else {
                value=1;
            }
        }
    }
    
}