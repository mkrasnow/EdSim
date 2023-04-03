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
public class IAagent extends agent{
    //genes: 0 is unrestrained, 1 refuses mf match, 2 refuses mfg match, 3 refuses any match
    int name, mother, father, mgmom, mgdad, mmggmom, mmggdad, pgmom, pgdad, ppggmom, ppggdad, mpggdad, mpggmom, pmggmom, pmggdad;
    double fitMult;
    ArrayList<Double> alternatives = new ArrayList();

    public IAagent(double type){
        name = (int)Math.round(Math.random()*10000);
        genes = new ArrayList();
        genes.add(new kindGene(type));
        genes.add(new kindGene(type));
        fitMult = 1;
        mother=(int)Math.round(Math.random()*10000);
        father=(int)Math.round(Math.random()*10000);
        mgmom=(int)Math.round(Math.random()*10000);
        mgdad=(int)Math.round(Math.random()*10000);
        mmggmom=(int)Math.round(Math.random()*10000);
        mmggdad=(int)Math.round(Math.random()*10000);
        pgmom=(int)Math.round(Math.random()*10000);
        pgdad=(int)Math.round(Math.random()*10000);
        ppggmom=(int)Math.round(Math.random()*10000);
        ppggdad=(int)Math.round(Math.random()*10000);
        mpggdad=(int)Math.round(Math.random()*10000);
        mpggmom=(int)Math.round(Math.random()*10000);
        pmggmom=(int)Math.round(Math.random()*10000);
        pmggdad=(int)Math.round(Math.random()*10000);
    }

    public IAagent(IAagent m, IAagent d, double leRate, double mRate){
        alternatives.add(0.0);
        alternatives.add(1.0);
        alternatives.add(2.0);
        alternatives.add(3.0);
        fitMult = 1;
        name = (int)Math.round(Math.random()*10000);
        mother=m.name;
        father=d.name;
        mgmom=m.mother;
        mgdad=m.father;
        pgmom=d.mother;
        pgdad=d.father;
        mmggmom=m.mgmom;
        mmggdad=m.mgdad;
        ppggmom=d.pgmom;
        ppggdad=d.pgdad;
        mpggmom=m.pgmom;
        mpggdad=m.pgdad;
        pmggmom=d.mgmom;
        pmggdad=d.mgdad;
        double rand = Math.random();
        genes=new ArrayList();
        kindGene geneM1 = (kindGene)m.genes.get(0);
        kindGene geneM2 = (kindGene)m.genes.get(1);
        kindGene geneD1 = (kindGene)d.genes.get(0);
        kindGene geneD2 = (kindGene)d.genes.get(1);
        //geneM1.mutation(alternatives, mRate);
        //geneM2.mutation(alternatives, mRate);
        //geneD1.mutation(alternatives, mRate);
        //geneD2.mutation(alternatives, mRate);
        if(rand<.5){
            genes.add(geneM1);
        } else {
            genes.add(geneM2);
        }
        rand = Math.random();
        if(rand<.5){
            genes.add(geneD1);
        } else {
            genes.add(geneD2);
        }
    }

    
    @Override
    public String toString(){
        String toReturn="";
        toReturn+= genes.get(0).value + ":" + genes.get(1).value;
        return toReturn;
    }

}
