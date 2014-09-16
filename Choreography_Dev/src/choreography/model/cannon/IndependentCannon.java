/**
 * 
 */
package choreography.model.cannon;


/**
 * @author madridf
 *
 */
public class IndependentCannon extends Cannon {
    
    private String module;

    /**
     *
     * @param level
     */
    public IndependentCannon(int level) {
        super(level);
    }

    /**
     *
     * @param level
     * @param name
     */
    public IndependentCannon(int level, String name) {
        super(level, name);
    }
    
    /**
     *
     * @param level
     * @param name
     * @param module
     */
    public IndependentCannon(int level, String name, String module) {
        super(level, name);
        this.module = module;
    }
    
    /**
     *
     * @return
     */
    public String getModule() {
        return module;
    }

}
