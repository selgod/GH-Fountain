/**
 * 
 */
package choreography.model.lagtime;

import java.util.ArrayList;

import choreography.io.FCWLib;
import choreography.model.fcw.FCW;

/**
 * @author elementsking
 *
 */
public class LagTimeTable {
	private static LagTimeTable instance;
	private static ArrayList<LagTime> delays;

	/**
	 *
	 * @return
	 */
	public static LagTimeTable getInstance() {
		if (instance == null)
			instance = new LagTimeTable();
		return instance;
	}

	private LagTimeTable() {
		delays = new ArrayList<>();
	}

	/**
	 *
	 * @return
	 */
	public static ArrayList<LagTime> getDelays() {
		return delays;
	}

	/**
	 *
	 * @param delayTimes
	 */
	public static void setLagTimes(ArrayList<LagTime> delayTimes) {
		LagTimeTable.delays = delayTimes;
	}

	
	/**
	 *
	 * @param f
	 * @return
	 */
	public static synchronized double getLagTime(FCW f) {
		double lagTime = 1;
		String[] actions = FCWLib.getInstance().reverseLookupData(f);
		for (String a : actions) {
			for (LagTime lt : delays) {
				if (lt.getDelayName().equalsIgnoreCase(a)) {
					lagTime = lt.getDelayTime();
					break;
				}
			}

		}
		return lagTime;
	}
}
