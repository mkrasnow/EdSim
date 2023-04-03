/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim155;

import java.util.ArrayList;
import rsrc.agent;

/**
 *
 * @author krasnow
 */
public class World {
    ArrayList<agent> population;
    ArrayList<SocialWorld> SWList;
    int MaxPopSize, numSW;
    
    public World(int mps, int nsw){
        population = new ArrayList();
        SWList = new ArrayList();
        MaxPopSize = mps;
        numSW = nsw;
    }
    
    public World(ArrayList<agent> i, int mps, int nsw){
        population = i;
        SWList = new ArrayList();
        MaxPopSize = mps;
        numSW = nsw;
    }
    
    public void formSocialWorlds(){
        SocialWorld temp = new SocialWorld();
        SWList = new ArrayList();
        for(int i = 0; i < population.size(); i++){
            if(i>0&&i%(Math.floor(MaxPopSize/numSW))==0){
                SWList.add(temp);
                temp = new SocialWorld();
            }
            temp.population.add(population.get(i));
        }
        SWList.add(temp);
        population.clear();
    }
    
}
