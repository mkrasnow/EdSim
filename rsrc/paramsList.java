/*
 * paramsList.java
 *
 * Created on September 2, 2007, 1:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rsrc;

import java.util.ArrayList;

/**
 *
 * @author Max
 */
public class paramsList {
    public double b, b1, b2, c, p, k, basefit, d, osp, x1shot, x2shot, x3shot, x4shot, x5shot, w, kmr, mr, ms, errorRate, sRate, optimum, wGroup, dist, meetRadius, PID, GID, PDS, GDS;
    public int popsize, n, numsw, gens, i, days, gest, nurse, tenureLength, leRate, iteration;
    public String game, distro, cog;
    public ArrayList<TypeDistribution> typesDist;
    public boolean walls, tf2t, htft;
    
    /** Creates a new instance of paramsList */
    public paramsList() {
        typesDist = new ArrayList();
    }
    
}
