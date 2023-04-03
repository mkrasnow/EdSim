// environment.java
// Author: Max Krasnow
// Completed: ?
package sim155;

import rsrc.result;
import rsrc.resultListener;
import rsrc.resultEvent;
import java.util.ArrayList;

public class environment {
    public population thePopulation;
    private ArrayList<resultListener> listeners = new ArrayList();
    
    //Creates a new instance of environment
    public environment(population a) {
        thePopulation = a;
    }
    
    public synchronized void addResultListener(resultListener thisListener) {
        listeners.add(thisListener);
    }
    
    public synchronized void removeResultListener(resultListener thisListener) {
        listeners.remove(thisListener);
    }
     
    public synchronized void fireResultEvent(result thisResult){
        resultEvent thisResultEvent = new resultEvent(this, thisResult);
        for(resultListener thisListener:listeners){
           thisListener.resultReceived(thisResultEvent); 
        }
    }
    
}
