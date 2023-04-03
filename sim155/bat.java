/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sim155;

import rsrc.agent;
import rsrc.kindGene;
import java.util.ArrayList;

/**
 *
 * @author Danielle
 */
public class bat extends agent {
    //kindGene: 0=ALLD, 1=ALLC, 2=TFT

    int name, age;
    boolean availForAid, successful;
    ArrayList<Integer> cheatedMe, helpedMe;
    ArrayList<Double> fitHist;

    public bat(double startLife, double newType){
        fitness = startLife;
        name = (int)Math.round(Math.random()*1000);
        genes = new ArrayList();
        genes.add(new kindGene(newType));
        cheatedMe = new ArrayList();
        helpedMe = new ArrayList();
        fitHist = new ArrayList();
        age=0;
        
    }

    public void setType(double newtype){
        genes.set(0, new kindGene(newtype));
    }
    public double getType(){
        return genes.get(0).value;
    }

    public boolean cheatCheck(int alter){
        boolean toReturn = false;
        for(Integer thisInt:cheatedMe){
            if(thisInt.equals(alter)){
                toReturn=true;
            }
        }
        return toReturn;
    }

}
