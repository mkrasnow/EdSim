/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sim155;

import java.util.ArrayList;
import rsrc.agent;
import rsrc.kindGene;

/**
 *
 * @author Max
 */
public class langur extends agent{
    //Ecology: infertile -1 to 0, gestating 0-gestL, nursing gestL-ageAtween, juvinile ageAtween-60, mature 60+;
    //kind gene: 0=no infanticide, 1=kills nursing, 2=kills nursing & gestating
    char sex;
    int age;
    int gestLac;
    double paternalGene;
    double strength;
    boolean dead;
    int ageAtween, gestL, lactL;

    public langur (char s, double dType, int gL, int lL){
        sex=s;
        genes = new ArrayList();
        genes.add(new kindGene(dType));
        dead = false;
        age=0;
        ageAtween=gL+lL;
        gestL=gL;
        lactL=lL;
        gestLac=-1;
    }

    public langur (double dType, int gL, int lL){
        if(Math.random()<.5){
            sex='m';
        } else {
            sex='f';
        }
        genes = new ArrayList();
        genes.add(new kindGene(dType));
        dead = false;
        age=0;
        ageAtween=gL+lL;
        gestL=gL;
        lactL=lL;
        gestLac=-1;
    }

    public void age(){
        age++;
        //System.out.println(age);
        if(age>120){
            if(Math.random()<0.15){
                dead=true;
            }
            if(age>180){
                dead=true;
            }
        }
    }
}
