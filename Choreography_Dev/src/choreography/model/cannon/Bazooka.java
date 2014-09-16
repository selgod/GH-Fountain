/**
 * 
 */
package choreography.model.cannon;

import choreography.model.cannon.IndependentCannon;

/**
 * @author madridf
 *
 */
public class Bazooka extends IndependentCannon {

    /**
    * @param level
     */
    public Bazooka(int level) {
            super(level);
    }

    public Bazooka(int level, String name) {
        super(level, name);
    }

}
