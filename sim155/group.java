// group.java
// Author: Max Krasnow
// Completed: ?

// update PG2 to reflect wGroup changes
package sim155;
import rsrc.agent;
import java.util.ArrayList;

public class group {
    ArrayList<agent> members;
    ArrayList<Character> decisions;
    ArrayList<Integer> names;
    double[][] history;
    String type;
    double benefit;
    int coop, pun, rep, nopun, gname, inxns, successes;
    
    //Creates a new instance of group
    public group() {
        members = new ArrayList();
    }
    public group(int gn) {
        gname = gn;
        members = new ArrayList();
    }
    
    public group(ArrayList<agent> agents) {
        members = new ArrayList();
        members = agents;
    }
}
