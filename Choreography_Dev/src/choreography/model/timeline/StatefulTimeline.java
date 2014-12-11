package choreography.model.timeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

import choreography.model.fcw.FCW;

/**
 * The <b>stateful Timeline</b> class is written in addition to the stateless
 * Timeline class.
 * <p>
 * There a two major problems with just a stateless timeline:
 * 
 * <p>
 * 1. By pausing the simulation the a stateless timeline just shows the last
 * triggered event instead of going back until time 1 and see what sliders have
 * been fired. Therefore, most slides are going to be at level 0.
 * 
 * <p>
 * 2. Rewinding the simulation doesn't work properly. By putting the scrollbar
 * at a specific time in the past just the last slider is loaded and all other
 * are ignored. Thus, sliders are not shown at the simulation because their
 * triggering was missed.
 * 
 * <p>
 * Solution: To solve these problems a sateful timeline has been written. Every
 * time contains now the state of all fired sliders. In average there are around
 * 6 different kind of sliders per time (if pause is clicked).
 * 
 * <p>
 * The code to solve the first problem has already been implemented. The second
 * hasn't because the clean functions for the simulation are not written yet. By
 * going back the timeline the simulation has to been cleaned and since there is
 * now a stateful timeline the simulaiton could be repainted just with the
 * current time.
 * 
 * @author Arash Besadi
 * @see Timeline
 * @see FCW
 * @version 1.0
 * @since December 12, 2014
 */

public class StatefulTimeline {

	private ConcurrentSkipListMap<Integer, ArrayList<FCW>> statefulTimlineMap;

	/**
	 * The concept of the timeline is based on a map(key,value). The key is the
	 * time when the sliders where added/fired. The value is an
	 * <tt>ArrayList</tt> which contains the sliders. The slider class is called
	 * <tt>FCW</tt> and hold the address and the value(how high) of the slider.
	 */
	public StatefulTimeline() {

		statefulTimlineMap = new ConcurrentSkipListMap<Integer, ArrayList<FCW>>();
	}

	public ConcurrentSkipListMap<Integer, ArrayList<FCW>> getStatefulTimelineMap() {
		return statefulTimlineMap;
	}

	/**
	 * This is the algorithm for adding a slidder at a specific time in the
	 * stateful timeline. When the user changes the value of a slider the whole
	 * timeline is read back until time 1 is reached. If a slidder with a new
	 * address and its corresponding value are found the slider is added in the
	 * stateful timeline. If the same slider its found its not added again. A
	 * much more efficient way would be just going one time back by using the
	 * floorKey(time) function which is provided in the ConcurrentSkipListMap
	 * class.
	 * <p>
	 * 
	 * @param time
	 *            the time when the slider was fired
	 * @param currentFcw
	 *            the FCW which contains the sliders address and value
	 */
	public void insertIntoTimelineStateful(Integer time, FCW currentFcw) {

		if (statefulTimlineMap.containsKey(time)) {

			ArrayList<FCW> newFcwList = statefulTimlineMap.get(time);

			for (int g = 0; g < newFcwList.size(); g++) {
				if ((newFcwList.get(g).getAddr() == currentFcw.getAddr())
						&& fcwAlreadyAdded(newFcwList.get(g), currentFcw)) {
					newFcwList.remove(g);
					newFcwList.add(currentFcw);
					break;
				} else {
					newFcwList.add(currentFcw);
				}
			}

		} else {
			ArrayList<FCW> newFcwList = new ArrayList<FCW>();
			newFcwList.add(currentFcw);

			// until time 1 is reached
			for (int j = time; j > 1; j--) {

				if (statefulTimlineMap.containsKey(j)) {
					ArrayList<FCW> oldFcwList = statefulTimlineMap.get(j);
					if (!oldFcwList.isEmpty()) {
						for (FCW oldFcw : oldFcwList) {

							if (!newFcwList.isEmpty()) {

								boolean lock = true;
								for (int g = 0; g < newFcwList.size(); g++) {
									if ((newFcwList.get(g).getAddr() == oldFcw
											.getAddr())
											&& fcwAlreadyAdded(
													newFcwList.get(g), oldFcw)) {

										lock = false;
										break;

									}
								}
								// if loke == flase then this item is already in
								// the timeline
								if (lock) {
									newFcwList.add(oldFcw);
								}
							} else {
								newFcwList.add(oldFcw);
							}
						}
					}
				}
			}

			statefulTimlineMap.put(time, newFcwList);
		}
	}

	/**
	 * Checks if two sliders their values are both between 16 and 21 to figure
	 * out if they are from the same type.
	 * 
	 * @param newFcw
	 *            the new Slider
	 * @param oldFcw
	 *            the old Slider
	 * @return True if their value have the same range.
	 */
	private boolean fcwAlreadyAdded(FCW newFcw, FCW oldFcw) {

		int oldType = 1;
		int newType = 1;

		if (oldFcw.getData() >= 16 && oldFcw.getData() <= 21) {
			oldType = 1;
		} else {
			oldType = 2;
		}

		if (newFcw.getData() >= 16 && newFcw.getData() <= 21) {
			newType = 1;
		} else {
			newType = 2;
		}

		return oldType == newType;

	}

	/**
	 * This function converts an existing stateless timeline into a stateful
	 * which is the case if you open a CTL file. The drawback is that it takes
	 * two times more time two load a CTL file.
	 * 
	 * @param timeline
	 *            The timeline which was load from a CTL file.
	 */
	public void loadExistingTimeline(
			ConcurrentSkipListMap<Integer, ArrayList<FCW>> timeline) {
		NavigableSet<Integer> keySet = timeline.keySet();
		Iterator<Integer> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			ArrayList<FCW> statelessFcwList = timeline.get(next);
			ArrayList<FCW> statefulFCWList = new ArrayList<FCW>(
					statelessFcwList);
			checkStatefulTimeline(next, statefulFCWList);

		}
	}

	/**
	 * This functions adds one Slider from stateless timeline into the stateful.
	 * 
	 * @param time
	 *            The time when the Slider was added
	 * @param newFcwList
	 *            The slider's class
	 */
	public void checkStatefulTimeline(Integer time, ArrayList<FCW> newFcwList) {

		if (statefulTimlineMap.isEmpty()) {
			statefulTimlineMap.put(time, newFcwList);
		} else {
			// until time 1 is reached
			for (int j = time; j > 1; j--) {

				if (statefulTimlineMap.containsKey(j)) {
					ArrayList<FCW> oldFcwList = statefulTimlineMap.get(j);

					for (FCW oldFcw : oldFcwList) {

						boolean lock = true;
						for (int g = 0; g < newFcwList.size(); g++) {
							if ((newFcwList.get(g).getAddr() == oldFcw
									.getAddr())
									&& fcwAlreadyAdded(newFcwList.get(g),
											oldFcw)) {
								lock = false;
								break;

							}
						}
						// if loke == flase then this item is already in
						// the timeline
						if (lock) {
							newFcwList.add(oldFcw);
						}
					}
				}
			}
			statefulTimlineMap.put(time, newFcwList);
		}
	}
}
