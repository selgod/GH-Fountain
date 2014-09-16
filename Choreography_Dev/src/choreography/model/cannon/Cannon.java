package choreography.model.cannon;

/**
 *
 * @author elementsking
 */
public abstract class Cannon {

    /**
     *
     */
    protected int level;

    /**
     *
     */
    protected String name;

    /**
     *
     * @param level
     */
    public Cannon(int level) {
            this.level = level;
	}

    /**
     *
     * @param level
     * @param name
     */
    public Cannon(int level, String name) {
            this.level = level;
            this.name = name;
        }
        
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 * 
	 * @return Whether the operation succeeded or not.
	 */
	public int setLevel(int level) {
		return this.level = level;
	}

    /**
     *
     * @return
     */
    @Override
	public String toString(){
		return this.getClass().getSimpleName() + " " + level;
	}

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
