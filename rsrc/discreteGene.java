/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rsrc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this type of gene is represented as a double value between 0-1.  it holds discrete states & mutations only shift to adjacent states
 * @author Max
 */
public class discreteGene extends gene implements Serializable{

    public discreteGene(double v){
        value = v;
        type = 1;
    }

    public void mutation(double mrate, ArrayList<Double> alts){
        if(Math.random()<mrate){
            int newindex;
            int index = alts.indexOf(value);
            if(index==0){newindex=1;
            } else if(index==alts.size()-1){newindex=alts.size()-2;
            } else if(Math.random()<.5){newindex=index++;
            } else {newindex=index--;}
            value=alts.get(newindex);
        }

    }

}
