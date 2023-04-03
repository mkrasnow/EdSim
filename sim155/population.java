// population.java
// Author: Max Krasnow
// Completed: ?

package sim155;

import rsrc.result;
import rsrc.TypeDistribution;
import rsrc.agent;
import java.util.ArrayList;
import java.util.Collections;
import rsrc.gene;
import rsrc.kindGene;
import util.gen.*;
import java.util.Random;

public class population {
    private ArrayList<agent> AgentList, lastGenList;
    private ArrayList<group> GroupList = new ArrayList();
    private ArrayList<agent> nextGenList = new ArrayList();
    //private ArrayList<ArrayList<agent>> WorldList = new ArrayList();
    private int MaxPopSize;
    private double aNI, aKN, aKGN, oNI, oKN, oKGN, totNI, totKN, totKGN;
    private double helpedALLD, helpedALLC, helpedTFT, askedALLD, askedALLC, askedTFT;
    private ArrayList<tree> treeList;
    private ArrayList<langur> bachelors;
    private ArrayList<bat> deadBats;
    //Constructor for pedagogical sims
    public population(double basefit, int popsize, ArrayList<TypeDistribution> typesDist, String game) {
       MaxPopSize = popsize;
       AgentList = new ArrayList();
       double dType=0;
       //System.out.println(typesDist.get(0).proportion + ", " + typesDist.get(1).proportion + ", " + typesDist.get(2).proportion);

       if(game.equalsIgnoreCase("Hawk/Dove")){
           for(TypeDistribution dist:typesDist){
                if(dist.type.matches("Hawk")){
                    dType=0;
                } else if(dist.type.matches("Dove")){
                    dType=1;
                } else {
                    dType=-1;
                }
               for(int i=0;i < ((popsize)*dist.proportion);i++){
                   HDagent a = new HDagent(basefit,dType);
                   AgentList.add(a);
               }
           }
        } else if(game.equalsIgnoreCase("Vampire Bats")){
            for(TypeDistribution dist:typesDist){
                if(dist.type.matches("ALLD")){
                    dType=0;
                    //System.out.println("ALLD");
                } else if(dist.type.matches("ALLC")){
                    dType=1;
                } else if(dist.type.matches("TFT")){
                    dType=2;
                } else {
                    dType=-1;
                }
                for(int i=0;i < ((popsize)*dist.proportion);i++){
                   //System.out.println(i);
                   bat a = new bat(basefit, dType);
                   AgentList.add(a);
               }
            }
            //System.out.println(countC(0,0) + ", " + countC(0,1) + ", " + countC(0,2));
        } else if(game.equalsIgnoreCase("Incest Avoidance")){
            for(TypeDistribution dist:typesDist){
                if(dist.type.matches("U")){
                    dType=0;
                } else if(dist.type.matches("S")){
                    dType=1;
                } else if(dist.type.matches("SC")){
                    dType=2;
                } else if(dist.type.matches("SCSC")){
                    dType=3;
                }
                for(int i=0; i<((popsize)*dist.proportion); i++){
                    IAagent a=new IAagent(dType);
                    AgentList.add(a);
                }
            }
            //System.out.println("U:"+countC(0,0)+"  S:"+countC(0,1)+"  SC:"+countC(0,2)+"  SCSC:"+countC(0,3));
        }

       while(AgentList.size()>popsize){
           Collections.shuffle(AgentList);
           AgentList.remove(0);
       }
    }
    //constructor for infanticide sim
    public population(ArrayList<TypeDistribution> typesDist, int gestLength, int lactLength){
        treeList = new ArrayList();
        bachelors = new ArrayList();
        AgentList = new ArrayList();
        aNI=0;
        aKN=0;
        aKGN=0;
        oNI=0;
        oKN=0;
        oKGN=0;
        totNI=0;
        totKN=0;
        totKGN=0;
        double dType=0;
        for(TypeDistribution dist:typesDist){
            if(dist.type.matches("NI")){
                dType = 0;
            } else if(dist.type.matches("KN")){
                dType = 1;
            } else if(dist.type.matches("KGN")){
                dType = 2;
            }
            for(int j=0; j<(12.0*dist.proportion); j++){
                if(dType==0){
                    aNI++;
                } else if(dType==1){
                    aKN++;
                } else {
                    aKGN++;
                }
                //System.out.println(aNI + ", " + aKN + ", " + aKGN);
                tree thisTree = new tree();
                thisTree.alpha=new langur('m', dType, gestLength, lactLength);
                thisTree.females.add(new langur('f', dType, gestLength, lactLength));
                thisTree.females.add(new langur('f', dType, gestLength, lactLength));
                thisTree.females.add(new langur('f', dType, gestLength, lactLength));
                thisTree.females.add(new langur('f', dType, gestLength, lactLength));
                thisTree.tenure=10;
                treeList.add(thisTree);
            }
        }
        while(treeList.size()>12){
            treeList.remove(treeList.size()-1);
        }
    }

    public population(ArrayList<agent> a){
        AgentList = new ArrayList();
        AgentList=a;
        MaxPopSize = a.size();
        //System.out.println(MaxPopSize);
    }
    
    public void shuffle(){
        int i = 5 + (int)(Math.random()*5);
        for(int x=0; x<i; x++){
            Collections.shuffle(AgentList);
        }
    }
    
    public ArrayList<agent> shuffle(ArrayList<agent> a){
        int i = 5 + (int)(Math.random()*5);
        for(int x=0; x<i; x++){
            Collections.shuffle(a);
        }
        return a;
    }
      
    public int numGroups(){
        return GroupList.size();
    }
    public int numAgents(){
        return AgentList.size();
    }
    
    public agent getAgent(int i){
        return AgentList.get(i);
    }
    
    public group getGroup(int i){
        return GroupList.get(i);
    }
    public void setAgentList(ArrayList<agent> i){
        AgentList=i;
    }
    
    private double SSP(ArrayList<Double> x, ArrayList<Double> y){ //this calculates sum of squared deviations of the mean of XY (either SS or SP)
        double SSP=0;
        double sumx=0;
        double sumy=0;
        double sumprod=0;
        //System.out.print("X:");
        for(int i=0; i<x.size(); i++){
            //System.out.print(x.get(i)+",");
            sumx+=x.get(i);
            sumy+=y.get(i);
            sumprod+=x.get(i)*y.get(i);
        }
        //System.out.println();
        //System.out.println(sumx + ", " + sumy + ", " + sumprod);
        SSP=sumprod-(sumx*sumy)/x.size();
        return SSP;
    }
    
    public double pearsonr(ArrayList<Double> x, ArrayList<Double> y){
        double r=-2;
        r=SSP(x,y)/Math.sqrt(SSP(x,x)*SSP(y,y));
        //System.out.println(r + "= " + SSP(x,y)+ "/SQRT(" + SSP(x,x)+ " * " + SSP(y,y)+") & n=" + x.size());
        return r;
    } 
    
    public void formGroups(int n) {
        group temp = new group(0);
        GroupList = new ArrayList();
        for(int i = 0; i < MaxPopSize; i++){
            if(i>0&&i%n==0){
                GroupList.add(temp);
                temp = new group(GroupList.size());
            }
            temp.members.add(AgentList.get(i));
        }
        GroupList.add(temp);
    }
    
      
    public result hawkDove(double b, double c, double base) {
        AgentList.clear();
        for (int i=0; i < GroupList.size(); i++){
            group thisGroup = GroupList.get(i);
            HDagent a1 = (HDagent) thisGroup.members.get(0);
            HDagent a2 = (HDagent) thisGroup.members.get(1);
            double rand = Math.random();
            //0=Hawk, 1=Dove
            if(a1.getType()==0 && a2.getType()==0){
                if(rand < .5){
                    a1.setFitness(base+b);
                    a2.setFitness(base-c);
                } else {
                    a1.setFitness(base-c);
                    a2.setFitness(base+b);
                }
            }
            if(a1.getType()==0 && a2.getType()==1){
                a1.setFitness(base+b);
                a2.setFitness(base);
            }
            if(a1.getType()==1 && a2.getType()==0){
                a1.setFitness(base);
                a2.setFitness(base+b);
            }
            if(a1.getType()==1 && a2.getType()==1){
                if(rand < .5){
                    a1.setFitness(base+b);
                    a2.setFitness(base);
                } else {
                    a1.setFitness(base);
                    a2.setFitness(base+b);
                }
            }
            AgentList.add(a1);
            AgentList.add(a2);
            GroupList.get(i).members.clear();
        }

        reproduce("Hawk/Dove", .001, 0, 0);
        ArrayList<TypeDistribution> nextGenDist = new ArrayList();
        TypeDistribution hawks = new TypeDistribution("Hawk", 1-mean(0));
        TypeDistribution doves = new TypeDistribution("Dove", mean(0));
        nextGenDist.add(hawks);
        nextGenDist.add(doves); 
        result thisResult = new result(nextGenList, nextGenDist);
        thisResult.b = b;
        thisResult.c = c;
        thisResult.pop = nextGenList.size();
        thisResult.basefit = base;
        return thisResult;
    }

    public result vampireBat(double b, double sRate, boolean ALLDin, boolean ALLCin, boolean TFTin){
        //System.out.println("HUNTING");
        deadBats = new ArrayList();
        for(int i=0; i<AgentList.size(); i++){
            bat thisAgent = (bat)AgentList.get(i);
            thisAgent.age++;
            double success = Math.random();
            if(success<=sRate){
                //System.out.println(i + ", " + thisAgent.name + ": success");
                thisAgent.setFitness(1);
                thisAgent.availForAid=true;
                thisAgent.successful=true;
            } else {
                //System.out.println(i + ", " + thisAgent.name + ": failure");
                thisAgent.availForAid=false;
                thisAgent.successful=false;
            }
            thisAgent.fitness=Math.max(0, thisAgent.fitness-.3);
            //System.out.println(thisAgent.name + ", " + thisAgent.genes.get(0).value + ", " + thisAgent.fitness);
            AgentList.set(i, thisAgent);
        }
        //System.out.println("AIDING");
        Collections.shuffle(AgentList);
        for(int i=0; i<AgentList.size(); i++){
            bat thisAgent = (bat)AgentList.get(i);
            /*if(!thisAgent.successful){ //start only ask random other
                int j = (int)(Math.random()*AgentList.size());
                bat thatAgent = (bat) AgentList.get(j);
                if(thatAgent.name!=thisAgent.name){
                    if(thatAgent.availForAid){
                        if(thatAgent.getType()==1){
                            thisAgent.changeFitness(b);
                            thatAgent.changeFitness(-c);
                            thatAgent.availForAid=false;
                            thisAgent.helpedMe.add(thatAgent.name);
                            //System.out.println(thatAgent.name + " helped " + thisAgent.name);
                        } else if(thatAgent.getType()==2){
                            if(!thatAgent.cheatCheck(thisAgent.name)){
                               thisAgent.changeFitness(b);
                               thatAgent.changeFitness(-c);
                               thatAgent.availForAid=false;
                               thisAgent.helpedMe.add(thatAgent.name);
                               //System.out.println(thatAgent.name + " helped " + thisAgent.name);
                            } else {
                                thisAgent.cheatedMe.add(thatAgent.name);
                                //System.out.println(thatAgent.name + " refused " + thisAgent.name);
                            }
                        } else {
                            thisAgent.cheatedMe.add(thatAgent.name);
                            //System.out.println(thatAgent.name + " refused " + thisAgent.name);
                        }
                    }
                    AgentList.set(j, thatAgent);
                }
                
            }*/ //End only ask random other
            
            if(!thisAgent.successful){ //Start ask until helped
                boolean helped = false;
                int j=0;
                while (!helped && j<AgentList.size()){
                    bat thatAgent = (bat)AgentList.get(j);
                    if(thatAgent.name!=thisAgent.name){
                        if(thatAgent.availForAid){
                            if(thisAgent.getType()==0){
                                askedALLD++;
                            } else if(thisAgent.getType()==1){
                                askedALLC++;
                            } else {
                                askedTFT++;
                            }
                            if(thatAgent.getType()==1){
                                thisAgent.changeFitness(b);
                                thatAgent.changeFitness(-b);
                                thatAgent.availForAid=false;
                                helped=true;
                                if(thisAgent.getType()==0){
                                    helpedALLD++;
                                } else if(thisAgent.getType()==1){
                                    helpedALLC++;
                                } else {
                                    helpedTFT++;
                                }
                            } else if(thatAgent.getType()==2){
                                if(!thatAgent.cheatCheck(thisAgent.name)){
                                   thisAgent.changeFitness(b);
                                   thatAgent.changeFitness(-b);
                                   thatAgent.availForAid=false;
                                   helped=true;
                                   if(thisAgent.getType()==0){
                                        helpedALLD++;
                                    } else if(thisAgent.getType()==1){
                                        helpedALLC++;
                                    } else {
                                        helpedTFT++;
                                    }
                                } else {
                                    thisAgent.cheatedMe.add(thatAgent.name);
                                }
                            } else {
                                thisAgent.cheatedMe.add(thatAgent.name);
                            }
                        }
                    }
                    AgentList.set(i, thisAgent);
                    AgentList.set(j, thatAgent);
                    j++;
                }
            } //end ask until helped
            thisAgent.fitHist.add(thisAgent.fitness);
            AgentList.set(i, thisAgent);
            
        }

        //System.out.println("DIEING");
        int replace=0;
        ArrayList<bat> tempList = new ArrayList();
        //System.out.println(AgentList.size());
        for(int i=0; i<AgentList.size(); i++){
            bat thisAgent = (bat) AgentList.get(i);
            //System.out.println(i + ", " + thisAgent.name + ", " + thisAgent.fitHist.toString());
            if(thisAgent.fitness<0.01){
                //System.out.println("Dead " + thisAgent.genes.get(0).value + ", " + thisAgent.age + "," + thisAgent.fitness + ", " + thisAgent.helpedMe.toString());
                tempList.add(thisAgent);
                deadBats.add(thisAgent);
                replace++;
            } else {
                //System.out.println(thisAgent.fitness);
            }
        }
        for(bat thisBat:tempList){
            AgentList.remove(thisBat);
        }
        for(int i=0; i<replace; i++){
            double ALLDlev=countC(0,0);
            double ALLClev=countC(0,1);
            double TFTlev=countC(0,2);
            double add = 1;
            if(ALLDin){
                ALLDlev+=.05;
                add+=.05;
            }
            if(ALLCin){
                ALLClev+=.05;
                add+=.05;
            }
            if(TFTin){
                TFTlev+=.05;
                add+=.05;
            }
            double rand = Math.random()*add;
            if(rand<(ALLDlev)){
                AgentList.add(new bat(1,0));
            } else if(rand<(ALLDlev+ALLClev)){
                AgentList.add(new bat(1,1));
            } else {
                AgentList.add(new bat(1,2));
            }
        }
        ArrayList<TypeDistribution> nextGenDist = new ArrayList();
        if(ALLDin){
            TypeDistribution ALLD = new TypeDistribution("ALLD", countC(0,0));
            ALLD.helped=helpedALLD;
            ALLD.asked=askedALLD;
            nextGenDist.add(ALLD);
        }
        if(ALLCin){
            TypeDistribution ALLC = new TypeDistribution("ALLC", countC(0,1));
            ALLC.helped=helpedALLC;
            ALLC.asked=askedALLC;
            nextGenDist.add(ALLC);
        }
        if(TFTin){
            TypeDistribution TFT = new TypeDistribution("TFT", countC(0,2));
            TFT.helped=helpedTFT;
            TFT.asked=askedTFT;
            nextGenDist.add(TFT);
        }
        result thisResult=new result(AgentList, nextGenDist);
        thisResult.sRate=sRate;
        thisResult.deadBats=deadBats;
        helpedALLD=0;
        helpedALLC=0;
        helpedTFT=0;
        askedALLD=0;
        askedALLC=0;
        askedTFT=0;
        //System.out.println(AgentList.size() + ", " + countC(0,0) + ", " + countC(0,1) + ", " + countC(0,2));
        return thisResult;
    }

    public result IA(double mRate, int leRate, boolean uin, boolean sin, boolean scin, boolean scscin){
        //System.out.print("A");
        //System.out.println(AgentList.size());
        double soloU=0, soloS=0, soloSC=0, soloSCSC=0;
        double mateU=0, mateS=0, mateSC=0, mateSCSC=0;

        ArrayList<Double> alt = new ArrayList();
        if(uin){alt.add(0.0);}
        if(sin){alt.add(1.0);}
        if(scin){alt.add(2.0);}
        if(scscin){alt.add(3.0);}
        Collections.shuffle(alt);

        if(AgentList.size()>MaxPopSize){
            for(int i=0; i<(AgentList.size()-MaxPopSize); i++){
                AgentList.remove(i);
            }
        }
        //System.out.print("B");
        /*ArrayList<agent> sortList = new ArrayList();
        for(int i=0; i<AgentList.size(); i++){
            IAagent thisAgent = (IAagent)AgentList.get(i);
            if(thisAgent.genes.get(0).value==3||thisAgent.genes.get(1).value==3){
                sortList.add(thisAgent);
                AgentList.remove(thisAgent);
                i--;
            }
        }
        for(int i=0; i<AgentList.size(); i++){
            IAagent thisAgent = (IAagent)AgentList.get(i);
            if(thisAgent.genes.get(0).value==2||thisAgent.genes.get(1).value==2){
                sortList.add(thisAgent);
                AgentList.remove(thisAgent);
                i--;
            }
        }
        for(int i=0; i<AgentList.size(); i++){
            IAagent thisAgent = (IAagent)AgentList.get(i);
            if(thisAgent.genes.get(0).value==1||thisAgent.genes.get(1).value==1){
                sortList.add(thisAgent);
                AgentList.remove(thisAgent);
                i--;
            }
        }
        for(int i=0; i<AgentList.size(); i++){
            IAagent thisAgent = (IAagent)AgentList.get(i);
            sortList.add(thisAgent);
            AgentList.remove(thisAgent);
            i--;
        }
        

        AgentList = sortList;
        //System.out.println(AgentList.size());*/

        Collections.shuffle(AgentList);
        boolean PopUnmated = true;
        while(PopUnmated){
            //System.out.print(AgentList.size() + "-");
            IAagent thisAgent = (IAagent) AgentList.get(0);
            boolean unmated = true;
            int i = 1;
            while (unmated){
                //System.out.println(AgentList.size());
                if(i<AgentList.size()){
                    IAagent thatAgent = (IAagent) AgentList.get(i);
                    if(mateable(thisAgent, thatAgent)){
                        for(gene g:thisAgent.genes){
                            if(g.value==0){mateU++;}
                            if(g.value==1){mateS++;}
                            if(g.value==2){mateSC++;}
                            if(g.value==3){mateSCSC++;}
                        }
                        group tempGroup = new group();
                        tempGroup.members.add(thisAgent);
                        tempGroup.members.add(thatAgent);
                        GroupList.add(tempGroup);
                        AgentList.remove(i);
                        AgentList.remove(0);
                        unmated=false;
                        if(AgentList.isEmpty()){
                            PopUnmated=false;
                        }
                    } else {
                        i++;
                        if(i>=AgentList.size()){
                            //System.out.println("Couldn't mate " + thisAgent.genesToString());
                            unmated=false;
                            for(gene g:thisAgent.genes){
                                if(g.value==0){soloU++;}
                                if(g.value==1){soloS++;}
                                if(g.value==2){soloSC++;}
                                if(g.value==3){soloSCSC++;}
                            }
                            AgentList.remove(0);
                        }
                    }
                } else {
                    AgentList.clear();
                    PopUnmated=false;
                    unmated=false;
                }
            }
        }
        //System.out.print("C");
        //ArrayList<Double> fitList = new ArrayList();
        ArrayList<Double> propList = new ArrayList();


        /*for(group thisGroup:GroupList){
            IAagent m = (IAagent)thisGroup.members.get(0);
            IAagent f = (IAagent)thisGroup.members.get(1);
            double thisFit = 1 - 4*(Math.max(Math.max(m.genes.get(0).value,m.genes.get(1).value),Math.max(f.genes.get(0).value,f.genes.get(0).value))/MaxPopSize);
            thisFit-=leRate*relatedness(m,f);
            System.out.println(relatedness(m,f) + ", " + thisFit);
            if(thisFit>0){fitList.add(thisFit);}
        }
        //System.out.print("D");
        double totFit=0;
        for(Double thisFit:fitList){
            totFit+=thisFit;
        }
        //System.out.println("E");
        double runningProp=0;
        for(Double thisFit:fitList){
            runningProp+=thisFit/totFit;
            //System.out.print(runningProp+ ",");
            propList.add(runningProp);
        }
        if(propList.size()>0){
            propList.set(propList.size()-1,1.0);
        } else {
            //System.out.println("No Positive fitness!");
            result thisResult = new result();
            thisResult.message="No matings possible; population crashed!";
            return thisResult;
        }
        //System.out.println("  " + propList.size() + " F");*/

        for(int i=1; i<=GroupList.size(); i++){
           propList.add((double)((double)i/(double)GroupList.size()));
           //System.out.println((double)((double)i/(double)GroupList.size()));
        }

        double nvU=0;
        double nvS=0;
        double nvSC=0;
        double nvSCSC=0;
        double vU=0;
        double vS=0;
        double vSC=0;
        double vSCSC=0;

        if(propList.isEmpty()){
            result thisResult = new result();
            thisResult.resultType="Incest Avoidance";
            thisResult.message="No matings possible; population crashed!";
            return thisResult;
        }
        
        for(int i=0; i<MaxPopSize; i++){
            double rnd=Math.random();
            boolean condition = true;
            int z=0;
            while(condition){
                if(rnd<propList.get(z)){
                    condition=false;
                    IAagent m = (IAagent)GroupList.get(z).members.get(0);
                    IAagent f = (IAagent)GroupList.get(z).members.get(1);
                    //System.out.print(m.genesToString() + " " + f.genesToString() + " -> ");
                    double thisRel = relatedness(m,f);
                    int count=0;
                    for(int le = 0; le<2*leRate; le++){
                        if(((thisRel*.25)+.001)>Math.random()){count++;}
                    }
                    //System.out.println(leRate + " - " + count);
                    if(count==0){
                        IAagent kid = new IAagent(m, f, leRate, mRate);
                        //System.out.print(kid.genesToString() + " - ");
                        for(gene g:m.genes){
                            if(g.value==0){vU++;}
                            if(g.value==1){vS++;}
                            if(g.value==2){vSC++;}
                            if(g.value==3){vSCSC++;}
                        }
                        for(gene g:f.genes){
                            if(g.value==0){vU++;}
                            if(g.value==1){vS++;}
                            if(g.value==2){vSC++;}
                            if(g.value==3){vSCSC++;}
                        }
                        for(int j=0; j<2; j++){
                            if(Math.random()<mRate){
                                boolean cond=true;
                                double newVal=0;
                                while(cond){
                                    newVal = Math.floor(Math.random()*3.9999);
                                    cond=false;
                                    if(newVal==0 && !uin){cond=true;}
                                    if(newVal==1 && !sin){cond=true;}
                                    if(newVal==2 && !scin){cond=true;}
                                    if(newVal==3 && !scscin){cond=true;}
                                }
                                kid.genes.set(j, new kindGene(newVal));
                            }
                        }
                        
                        //System.out.println(kid.genesToString());
                        nextGenList.add(kid);
                    } else {
                        i--;
                        for(gene g:m.genes){
                            if(g.value==0){nvU++;}
                            if(g.value==1){nvS++;}
                            if(g.value==2){nvSC++;}
                            if(g.value==3){nvSCSC++;}
                        }
                        for(gene g:f.genes){
                            if(g.value==0){nvU++;}
                            if(g.value==1){nvS++;}
                            if(g.value==2){nvSC++;}
                            if(g.value==3){nvSCSC++;}
                        }
                    }
                } else {
                    z++;
                    if(z>=propList.size()){
                        System.out.println("Trouble!");
                        break;
                    }
                }
            }
        }

        ArrayList<TypeDistribution> nextGenDist = new ArrayList();
        TypeDistribution U = new TypeDistribution("U", countDip(0.0));
        TypeDistribution S = new TypeDistribution("S", countDip(1.0));
        TypeDistribution SC = new TypeDistribution("SC", countDip(2.0));
        TypeDistribution SCSC = new TypeDistribution("SCSC", countDip(3.0));
        U.nviable=nvU;
        U.viable=vU;
        U.solo=soloU;
        U.mate=mateU;
        S.nviable=nvS;
        S.viable=vS;
        S.solo=soloS;
        S.mate=mateS;
        SC.nviable=nvSC;
        SC.viable=vSC;
        SC.solo=soloSC;
        SC.mate=mateSC;
        SCSC.nviable=nvSCSC;
        SCSC.viable=vSCSC;
        SCSC.solo=soloSCSC;
        SCSC.mate=mateSCSC;
        //System.out.println(nvSCSC + ", " + vSCSC + ", " + soloSCSC + ", " + mateSCSC);
        if(uin){nextGenDist.add(U);}
        if(sin){nextGenDist.add(S);}
        if(scin){nextGenDist.add(SC);}
        if(scscin){nextGenDist.add(SCSC);}
        //System.out.println(U.soloRate + ", " + S.soloRate + ", " + SC.soloRate + ", " + SCSC.soloRate);
        result thisResult = new result(nextGenList, nextGenDist);
        //System.out.println("H");
        thisResult.resultType="Incest Avoidance";
        return thisResult;
    }

    public result infanticide(double mr, int tenure, boolean niIn, boolean knIn, boolean kgnIn){
        AgentList.clear();
        Collections.shuffle(treeList);
        for(int i=0; i<12; i++){
            tree thisTree = treeList.get(i);
            thisTree.tenure-=Math.random()*2;
            //System.out.println(thisTree.alpha.age);
            ArrayList<langur> tempFem = new ArrayList();
            ArrayList<langur> tempJuv = new ArrayList();
            for(langur thisFem:thisTree.females){
                thisFem.age();
                if(!thisFem.dead){
                    if(thisFem.gestLac==-1){
                        thisFem.gestLac=0;
                        thisFem.paternalGene=thisTree.alpha.genes.get(0).value;
                    } else if(thisFem.gestLac<thisFem.ageAtween){
                        thisFem.gestLac++;
                    } else {
                        thisFem.gestLac=-1;
                        double g = thisFem.paternalGene;
                        if(Math.random()<mr){
                            boolean cond=true;
                            double newVal=0;
                            while(cond){
                                newVal = Math.floor(Math.random()*2.9999);
                                cond=false;
                                if(newVal==0 && !niIn){cond=true;}
                                if(newVal==1 && !knIn){cond=true;}
                                if(newVal==2 && !kgnIn){cond=true;}
                            }
                            g=newVal;
                        }
                        thisTree.juviniles.add(new langur(g, thisFem.gestL, thisFem.lactL));
                        if(g==0){
                            oNI++;
                        } else if(g==1){
                            oKN++;
                        } else {
                            oKGN++;
                        }
                        if(thisTree.alpha.genes.get(0).value==0){
                            totNI++;
                        } else if(thisTree.alpha.genes.get(0).value==1){
                            totKN++;
                        } else {
                            totKGN++;
                        }
                        //System.out.println("New Juvee");
                    }
                    tempFem.add(thisFem);
                }
            }
            thisTree.females=tempFem;
            for(langur thisLang:thisTree.juviniles){
                thisLang.age();
                if(thisLang.age>=60){
                    if(thisLang.sex=='m'){
                        bachelors.add(thisLang);
                        //System.out.println("New Bachelor");
                    } else if(thisTree.females.size()<=10){
                        thisTree.females.add(thisLang);
                        //System.out.println("New Female");
                    }
                } else {
                    tempJuv.add(thisLang);
                }
            }
            thisTree.juviniles=tempJuv;
            if(thisTree.females.isEmpty()){
                langur a = thisTree.alpha;
                thisTree.females.add(new langur('f', a.genes.get(0).value, a.gestL, a.lactL));
            }
            treeList.set(i, thisTree);
        }
        //System.out.println(bachelors.size());
        Collections.shuffle(bachelors);
        for(int i=0; i<treeList.size(); i++){
            tree thisTree = treeList.get(i);
            if(thisTree.tenure<=0){
                if(bachelors.size()>0){
                    thisTree.alpha=bachelors.get(0);
                    if(thisTree.alpha.genes.get(0).value==0){
                        aNI++;
                    } else if(thisTree.alpha.genes.get(0).value==1){
                        aKN++;
                    } else {
                        aKGN++;
                    }
                    bachelors.remove(0);
                    thisTree.tenure=tenure;
                    ArrayList<langur> tempFem=new ArrayList();
                    for(langur thisFem:thisTree.females){
                        if(thisTree.alpha.genes.get(0).value==2){
                            thisFem.gestLac=-1;
                        } else if(thisTree.alpha.genes.get(0).value==1 && thisFem.gestLac<thisFem.gestL){
                            thisFem.gestLac=-1;
                        } else if(thisTree.alpha.genes.get(0).value==0 && thisFem.gestLac==0){
                            thisFem.gestLac=-1;
                        }
                        tempFem.add(thisFem);
                    }
                    thisTree.females=tempFem;
                    treeList.set(i, thisTree);
                }
            }
        }
        for(int i=0; i<bachelors.size(); i++){
            langur thisBach = bachelors.get(i);
            thisBach.age();
            if(thisBach.dead){
                bachelors.remove(i);
                i--;
            }
        }

        int alpha=0;
        int fem = 0;
        int bach =0;
        int juv=0;

        for(tree thisTree:treeList){
            AgentList.add(thisTree.alpha);
            alpha++;
            for(langur thisFem:thisTree.females){
                AgentList.add(thisFem);
                fem++;
            }
            for(langur thisJuv:thisTree.juviniles){
                AgentList.add(thisJuv);
                juv++;
            }
        }
        for(langur bachelor:bachelors){
            AgentList.add(bachelor);
            bach++;
        }
        //System.out.println("A:"+alpha+"  F:"+fem+"  J:"+juv+"  B:"+bach);
        TypeDistribution NI = new TypeDistribution("NI", countC(0,0));
        TypeDistribution KN = new TypeDistribution("KN", countC(0,1));
        TypeDistribution KGN = new TypeDistribution("KGN", countC(0,2));
        NI.offspring=oNI;
        NI.alphas=aNI;
        NI.groupOff=totNI;
        KN.offspring=oKN;
        KN.alphas=aKN;
        KN.groupOff=totKN;
        KGN.offspring=oKGN;
        KGN.alphas=aKGN;
        KGN.groupOff=totKGN;
        //System.out.println(aNI + ", " + aKN + ", " + aKGN);
        //System.out.println(None.proportion + ", " + KG.proportion + ", " + KL.proportion);
        ArrayList<TypeDistribution> nextGenDist = new ArrayList();
        if(niIn){nextGenDist.add(NI);}
        if(knIn){nextGenDist.add(KN);}
        if(kgnIn){nextGenDist.add(KGN);}
        result thisResult = new result(AgentList, nextGenDist);
        aNI=0;
        aKN=0;
        aKGN=0;
        oNI=0;
        oKN=0;
        oKGN=0;
        totNI=0;
        totKN=0;
        totKGN=0;
        return thisResult;
    }

    
    private double relatedness(IAagent a, IAagent b){
        double toReturn=0;
        boolean related=false;
        ArrayList<Integer> relA = new ArrayList();
        ArrayList<Integer> relB = new ArrayList();
        relA.add(a.mother);
        relA.add(a.father);
        relB.add(b.mother);
        relB.add(b.father);
        for(int pat:relA){
            for(int mat:relB){
                if(pat==mat){
                    toReturn=0.5;
                    related = true;
                }
            }
        }
        if(!related){
            relA.add(a.mgmom);
            relA.add(a.mgdad);
            relA.add(a.pgmom);
            relA.add(a.pgdad);
            relB.add(b.mgmom);
            relB.add(b.mgdad);
            relB.add(b.pgmom);
            relB.add(b.pgdad);
            for(int pat:relA){
                for(int mat:relB){
                    if(pat==mat){
                        toReturn=0.125;
                        related = true;
                    }
                }
            }
        }
        if(!related){
            relA.add(a.mmggmom);
            relA.add(a.mmggdad);
            relA.add(a.ppggmom);
            relA.add(a.ppggdad);
            relA.add(a.mpggdad);
            relA.add(a.mpggmom);
            relA.add(a.pmggmom);
            relA.add(a.pmggdad);
            relB.add(b.mmggmom);
            relB.add(b.mmggdad);
            relB.add(b.ppggmom);
            relB.add(b.ppggdad);
            relB.add(b.mpggdad);
            relB.add(b.mpggmom);
            relB.add(b.pmggmom);
            relB.add(b.pmggdad);
            for(int pat:relA){
                for(int mat:relB){
                    if(pat==mat){
                        toReturn=0.03125;
                    }
                }
            }
        }        
        return toReturn;
    }

    private boolean mateable(IAagent a, IAagent b){
        boolean toReturn = true;
        ArrayList<Integer> relA = new ArrayList();
        ArrayList<Integer> relB = new ArrayList();
        if(a.genes.get(0).value==3||a.genes.get(1).value==3||b.genes.get(0).value==3||b.genes.get(1).value==3){
            relA.add(a.mother);
            relA.add(a.father);
            relA.add(a.mgmom);
            relA.add(a.mgdad);
            relA.add(a.mmggmom);
            relA.add(a.mmggdad);
            relA.add(a.pgmom);
            relA.add(a.pgdad);
            relA.add(a.ppggmom);
            relA.add(a.ppggdad);
            relA.add(a.mpggdad);
            relA.add(a.mpggmom);
            relA.add(a.pmggmom);
            relA.add(a.pmggdad);
            relB.add(b.mother);
            relB.add(b.father);
            relB.add(b.mgmom);
            relB.add(b.mgdad);
            relB.add(b.mmggmom);
            relB.add(b.mmggdad);
            relB.add(b.pgmom);
            relB.add(b.pgdad);
            relB.add(b.ppggmom);
            relB.add(b.ppggdad);
            relB.add(b.mpggdad);
            relB.add(b.mpggmom);
            relB.add(b.pmggmom);
            relB.add(b.pmggdad);
        } else if(a.genes.get(0).value==2||a.genes.get(1).value==2||b.genes.get(0).value==2||b.genes.get(1).value==2){
            relA.add(a.mother);
            relA.add(a.father);
            relA.add(a.mgmom);
            relA.add(a.mgdad);
            relA.add(a.pgmom);
            relA.add(a.pgdad);
            relB.add(b.mother);
            relB.add(b.father);
            relB.add(b.mgmom);
            relB.add(b.mgdad);
            relB.add(b.pgmom);
            relB.add(b.pgdad);
        } else if(a.genes.get(0).value==1||a.genes.get(1).value==1||b.genes.get(0).value==1||b.genes.get(1).value==1){
            relA.add(a.mother);
            relA.add(a.father);
            relB.add(b.mother);
            relB.add(b.father);
        }
        for(int pat:relA){
            for(int mat:relB){
                if(pat==mat){
                    //System.out.println(pat + ":" + mat);
                    toReturn=false;
                }
            }
        }
        
        return toReturn;
    }

    private double countDip(double i){
        double geneTot = 0;
        double runTot = 0;
        for(agent thisAgent:nextGenList){
            for(gene thisGene:thisAgent.genes){
                runTot++;
                if(thisGene.value==i){
                    geneTot++;
                }
            }
        }
        geneTot/=runTot;
        return geneTot;
    }

    private double countC(int i, int j){
         double runTot = 0;
         for(agent thisAgent:AgentList){
             if(thisAgent.genes.get(i).value==j){
                 runTot+=1;
             }
         }
         runTot/=AgentList.size();
         return runTot;
     }
 
    private double mean(int i){
         double runTot = 0;
         for(agent thisAgent:nextGenList){
             runTot += thisAgent.genes.get(i).value;
         }
         runTot/=nextGenList.size();

         return runTot;
     }
    
 
    
    private void reproduce(String game, double kmutrate, double pmutrate, double mutsize){
        //System.out.println(tf2t + "," + htft);
        double b, c, gs, p;
        b=0;
        c=0;
        gs=0;
        p=0;
        double totalFitness=0;
        double runningProp=0;
        ArrayList<Double> propList = new ArrayList();
        double lowestFitness=0;
        for(agent thisAgent:AgentList){
            if(thisAgent.getFitness()<lowestFitness){
                lowestFitness = thisAgent.getFitness();
            }
        }
        if(lowestFitness<0){
            lowestFitness=Math.abs(lowestFitness)+1;
            for(int x=0; x<MaxPopSize; x++){
                AgentList.get(x).changeFitness(lowestFitness);
            }
        }
        shuffle();
        //System.out.print("Fitness: ");
        for(agent thisAgent:AgentList){
            totalFitness+=thisAgent.getFitness();
            //System.out.print(thisAgent.getFitness()+", ");
        }
        
        for(int i=0; i<MaxPopSize; i++){
            //System.out.print("Fitness: "+ AgentList.get(i).fitness);
            AgentList.get(i).scaleFitness(totalFitness);
            //System.out.print("Scaled Fitness: "+ AgentList.get(i).fitness);
            runningProp+=AgentList.get(i).getFitness();
            //System.out.print("RunningProp: "+ runningProp);
            propList.add(runningProp);
        }
        propList.set(propList.size()-1,1.0);
        /*for(Double x:propList){
            System.out.println(x);
        }*/
        for(int i=0; i<MaxPopSize; i++){
            double rnd = Math.random();
            boolean condition = true;
            int z=0;
            while(condition){
                if(rnd<=propList.get(z)){
                    //System.out.println(z);
                    condition=false;
                    if(game.matches("Hawk/Dove")){
                        double g=AgentList.get(z).genes.get(0).value;
                        if(kmutrate>Math.random()){
                            if(.5>Math.random()){
                                g=1;
                            } else {
                                g=0;
                            }
                        }
                        nextGenList.add(new HDagent(0, g));
                    } else {
                        System.out.println("No Such Game");
                    }
                } else {
                    z++;
                    if(z>=MaxPopSize){
                        int lastz = z-1;
                        for(double prop:propList){
                            System.out.print(prop + ", ");
                        }
                        System.out.println("Trouble!");
                        break;
                    }
                }
            } 
        }
        //System.out.println("TF2T? " + tf2t + ", HTFT? " + htft + " BC mutations: " + bcMutCount + "/" + MaxPopSize + ", C mutations: " + cMutCount + "/" + MaxPopSize + ", D mutations: " + dMutCount + "/" + MaxPopSize);
        AgentList.clear();
     }   
}