/**
 * 
 */
package choreography.model.lagtime;

import choreography.io.FCWLib;
import choreography.model.cannon.CannonEnum;
import choreography.model.fcw.FCW;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author elementsking
 *
 */
public class LagTimeTable {
	private static LagTimeTable instance;
	private static final int closeValve = 0;
	private static final double level1 = .2;
	private static final double level2 = .4;
	private static final double level3 = .6;
	private static final double level4 = .8;
	private static final double level5 = 1.0;
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
//            System.out.println(delays);
	}

	/**
	 * @return the closeValve
	 */
	public static synchronized int getCloseValve() {
		return closeValve;
	}

	/**
	 * @return the level1
	 */
	public static synchronized double getLevel1() {
		return level1;
	}

	/**
	 * @return the level2
	 */
	public static synchronized double getLevel2() {
		return level2;
	}

	/**
	 * @return the level3
	 */
	public static synchronized double getLevel3() {
		return level3;
	}

	/**
	 * @return the level4
	 */
	public static synchronized double getLevel4() {
		return level4;
	}

	/**
	 * @return the level5
	 */
	public static synchronized double getLevel5() {
		return level5;
	}

    /**
     *
     * @param f
     * @return
     */
    public static synchronized double getLagTime(FCW f) {
        String[] actions = FCWLib.getInstance().reverseLookupData(f);
        String cannon = FCWLib.getInstance().reverseLookupAddress(f.getAddr());
        if(cannon.contains("SWEEP")) {
            return 0;
        }
        double lagTime = 1.0; 
        if(cannon.contains("VOICE")) {
            for(LagTime lt: delays) {
                if(lt.getDelayName().equalsIgnoreCase(cannon)) {
                    return lt.getDelayTime();
                }
            }
        }
        for(LagTime lt: delays) {
            if(lt.getDelayName().equalsIgnoreCase(cannon)) {
                lagTime = lt.getDelayTime();
                break;
            }
        }
        for(String action: actions) {
            switch(action) {
                case "0": return 0.4;
                case "1": return lagTime *= level1;
                case "2": return lagTime *= level2;
                case "3": return lagTime *= level3;
                case "4": return lagTime *= level4;
                case "5": return lagTime *= level5;
                case "6": return lagTime *= level5;
            }
        }
        throw new IllegalArgumentException("Invalid lag time! " + f);
    }
}
