/**
 * 
 */
package choreography.model.cannon;


/**
 * @author madridf
 *
 */
public class Ring extends Cannon {
	final int size;
	
	/**
	 * @param level
	 * @param size
	 */
	public Ring(int size, int level) {
		super(level);
		this.size = size;
	}

    /**
     *
     * @param size
     * @param name
     */
    public Ring(int size, String name) {
            super(0 ,name);
            this.size = size;
        }

    /**
     *
     * @param size
     */
    public Ring(int size) {
		super(0);
		this.size = size;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

    /**
     *
     * @return
     */
    public String toString() {
		return this.getClass() + " size: " + size+ " level: " + level;
	}
}
