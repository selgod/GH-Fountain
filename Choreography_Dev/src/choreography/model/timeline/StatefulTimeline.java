package choreography.model.timeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

import choreography.model.fcw.FCW;

/**
 * 
 * @author Arash Besadi
 * @see Timeline
 * @see FCW
 * @version 1.0
 * @since December 12, 2014
 */

public class StatefulTimeline {

	private ConcurrentSkipListMap<Integer, ArrayList<FCW>> statefulTimlineMap;

	public StatefulTimeline() {
		statefulTimlineMap = new ConcurrentSkipListMap<Integer, ArrayList<FCW>>();
	}

	public ConcurrentSkipListMap<Integer, ArrayList<FCW>> getStatefulTimelineMap() {
		return statefulTimlineMap;
	}

	/**
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
					System.out.println("removed");
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
									if ((newFcwList.get(g).getAddr() == oldFcw.getAddr())
											&& fcwAlreadyAdded(newFcwList.get(g), oldFcw)) {

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
							if ((newFcwList.get(g).getAddr() == oldFcw.getAddr())
									&& fcwAlreadyAdded(newFcwList.get(g), oldFcw)) {
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

	public void fillStatefulTimeline(ConcurrentSkipListMap<Integer, ArrayList<FCW>> timeline) {
		NavigableSet<Integer> keySet = timeline.keySet();
		Iterator<Integer> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			ArrayList<FCW> statelessFcwList = timeline.get(next);
			ArrayList<FCW> statefulFCWList = new ArrayList<FCW>(statelessFcwList);
			checkStatefulTimeline(next, statefulFCWList);

		}
	}

}
