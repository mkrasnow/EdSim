/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim155;

import java.util.ArrayList;
import java.util.Collections;
import rsrc.agent;

/**
 *
 * @author krasnow
 */
public class SocialWorld {
    
    ArrayList<agent> population;
    ArrayList<group> groupList;
    
    public SocialWorld(){
        population = new ArrayList();
        groupList = new ArrayList();
    }
    
    public SocialWorld(ArrayList<agent> a){
        population = a;
        groupList = new ArrayList();
    }
    
    public void shuffle(){
        int i = 5 + (int)(Math.random()*5);
        for(int x=0; x<i; x++){
            Collections.shuffle(population);
        }
    }
    
    public void formGroups(int n) {
        group temp = new group(0);
        groupList = new ArrayList();
        for(int i = 0; i < population.size(); i++){
            if(i>0&&i%n==0){
                groupList.add(temp);
                temp = new group(groupList.size());
            }
            temp.members.add(population.get(i));
        }
        groupList.add(temp);
        population.clear();
    }
    
}
