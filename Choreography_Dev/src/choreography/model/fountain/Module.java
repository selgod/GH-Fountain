/**
 * 
 */
package choreography.model.fountain;

import choreography.model.cannon.Sweep;
import choreography.model.cannon.Multi;
import choreography.model.cannon.Ring;
import choreography.model.cannon.Cannon;
import choreography.model.cannon.Candelabra;


/**
 * 
 * 
 * @author madridf
 *
 */
public class Module {
	private int number;
	private Ring r1;
	private Ring r2;
	private Ring r3;
	private Ring r4;
	private Ring r5;
	private Sweep sw;
	private Multi mx;
	private Candelabra candle;
	

	/**
	 * @param number A number that identifies this Module
	 * @param r1
	 * @param r2
	 * @param r3
	 * @param r4
     * @param r5
	 * @param sw
	 * @param mx
	 * @param candle
	 */
	public Module(int number, Ring r1, Ring r2, Ring r3,
			Ring r4, Ring r5, Multi mx, Candelabra candle, Sweep sw) {
		super();
		this.number = number;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
		this.r4 = r4;
		this.r5 = r5;
		this.sw = sw;
		this.mx = mx;
		this.candle = candle;
	}

    /**
     *
     * @param number
     * @param rings
     * @param m1
     * @param c1
     * @param s1
     */
    public Module(int number, Ring[] rings, Multi m1, Candelabra c1, Sweep s1){
		this.mx = m1;
		this.candle = c1;
		this.sw = s1;
		r1 = rings[0];
		r2 = rings[1];
		r3 = rings[2];
		r4 = rings[3];
		r5 = rings[4];
	}

    /**
     *
     * @param number
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @param r5
     * @param mx
     * @param c
     * @param sw
     */
    public Module(int number, Cannon r1, Cannon r2, Cannon r3, Cannon r4, 
                Cannon r5, Cannon mx, Cannon c, Cannon sw){
            this.number = number;
		this.r1 = (Ring)r1;
		this.r2 = (Ring)r2;
		this.r3 = (Ring)r3;
		this.r4 = (Ring)r4;
		this.r5 = (Ring)r5;
		this.sw = (Sweep)sw;
		this.mx = (Multi)mx;
		this.candle = (Candelabra)c;
        }

    /**
     *
     */
    public Module() {
		
	}

	/**
	 * @return the number
	 */
	protected int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	protected void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the r1
	 */
	public Ring getR1() {
		return r1;
	}

	/**
	 * @param r1 the r1 to set
	 */
	protected void setR1(Ring r1) {
		this.r1 = r1;
	}

	/**
	 * @return the r2
	 */
	public Ring getR2() {
		return r2;
	}

	/**
	 * @param r2 the r2 to set
	 */
	protected void setR2(Ring r2) {
		this.r2 = r2;
	}

	/**
	 * @return the r3
	 */
	public Ring getR3() {
		return r3;
	}

	/**
	 * @param r3 the r3 to set
	 */
	protected void setR3(Ring r3) {
		this.r3 = r3;
	}

	/**
	 * @return the r4
	 */
	public Ring getR4() {
		return r4;
	}

	/**
	 * @param r4 the r4 to set
	 */
	protected void setR4(Ring r4) {
		this.r4 = r4;
	}
	
	/**
	 * @return the r4
	 */
	public Ring getR5() {
		return r5;
	}

	/**
     * @param r5
	 */
	protected void setR5(Ring r5) {
		this.r5 = r5;
	}

	/**
	 * @return the sw
	 */
	public Sweep getSw() {
		return sw;
	}

	/**
	 * @param sw the sw to set
	 */
	protected void setSw(Sweep sw) {
		this.sw = sw;
	}

	/**
	 * @return the mx
	 */
	public Multi getMx() {
		return mx;
	}

	/**
	 * @param mx the mx to set
	 */
	protected void setMx(Multi mx) {
		this.mx = mx;
	}

	/**
	 * @return the candle
	 */
	public Candelabra getCandle() {
		return candle;
	}

	/**
	 * @param candle the candle to set
	 */
	protected void setCandle(Candelabra candle) {
		this.candle = candle;
	}

    /**
     *
     * @return
     */
    public String toString() {
		return number + " " + r1 + " " + r2 + " " + r3;
		
	}

}
