/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.model.cannon;

/**
 *
 * @author elementsking
 */
public enum CannonEnum {

    /**
     *
     */
    RING1 ("RING1"), 

    /**
     *
     */
    RING2 ("RING2"), 

    /**
     *
     */
    RING3 ("RING3"), 

    /**
     *
     */
    RING4 ("RING4"), 

    /**
     *
     */
    RING5 ("RING5"), 

    /**
     *
     */
    CANDELABRA ("CANDELABRA"), 

    /**
     *
     */
    MULTI ("MULTI"), 

    /**
     *
     */
    SWEEP ("SWEEP"), 

    /**
     *
     */
    FTCURT ("FTCURT"),

    /**
     *
     */
    BKCURT ("BKCURT"),

    /**
     *
     */
    BAZOOKA ("BAZOOKA"),

    /**
     *
     */
    PEACOCK ("PEACOCK"),

    /**
     *
     */
    SPOUT ("SPOUT"),

    /**
     *
     */
    BKFTCURT ("BKFTCURT");
    
    private final String name;
    
    private CannonEnum(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
