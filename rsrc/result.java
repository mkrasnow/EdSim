// result.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.io.Serializable;
import java.util.ArrayList;
import sim155.bat;

public class result implements Serializable{
    public String resultType, message, ecology, cogType, distType;
    public ArrayList<agent> AgentList;
    public ArrayList<TypeDistribution> typesDist;
    public double b, b1, b2, c, p, k, pop, band, gen, basefit, group, w, d, osp, x1shot, x2shot, x3shot, x4shot, x5shot, ms, mr, kmr, optimum, meetRadius, sRate, coopRate, punRate, rho, rho12, rho13, rho23, rhof1, rhof2, rhof3, LastcoopRate, LastpunRate, avePun, aveGain, rPunGain, reps, worlds, PID, GID, PDS, GDS;
    public ArrayList<bat> deadBats;
    public ArrayList<Double> dailyProb, dailyBase, correlations;
    public ArrayList<ArrayList<TypeDistribution>> ageCueDist;
    public int iteration;
    
    //Creates a new instance of result
    public result(ArrayList<agent> A, ArrayList<TypeDistribution> t) {
        AgentList = A;
        typesDist = t;
        message = "";
    }
    public result(){
        message = "";
        typesDist = new ArrayList();
    }    
}
