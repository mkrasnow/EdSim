// resultEvent.java
// Author: Max Krasnow
// Completed: ?
package rsrc;

import java.util.EventObject;

public class resultEvent extends EventObject{
    private result theResult;
    
    //Creates a new instance of resultEvent
    public resultEvent(Object source, result newResult) {
        super(source);
        theResult = newResult;
    }
    
    public result getResult(){
        return theResult;
    }
    
}
