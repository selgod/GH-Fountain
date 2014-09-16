/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.model.timeline;

import choreography.io.FCWLib;
import choreography.model.fcw.FCW;
import choreography.view.music.MusicPaneController;
import choreography.view.sim.FountainSimController;
import choreography.view.sliders.SlidersController;
import choreography.view.timeline.TimelineController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;



public class Timeline {
    
    /**
    * 
    */
    public static final int OFF = -5;
    private static final long serialVersionUID = 7_242_109_851_591_362_314L;
    private static final Logger LOG = Logger.getLogger(Timeline.class.getName());
    private static final int BUFFERBOUNDARY = 200;
    /**
     *
     * @return
     */
   private int time;    
   private int numChannels;
   private ConcurrentSkipListMap<Integer, ArrayList<FCW>> timeline;
   private final ConcurrentSkipListMap<Integer, ArrayList<FCW>> waterTimeline;
   private ConcurrentSkipListMap<Integer, ArrayList<FCW>> lightTimeline;
   private ConcurrentSkipListMap<Integer, SortedMap<Integer, Integer>> channelColorMap;
   private int[] lightChannelAddresses;

    public Timeline() {
        timeline = new ConcurrentSkipListMap<>();
        waterTimeline = new ConcurrentSkipListMap<>();
        lightTimeline = new ConcurrentSkipListMap<>();
        channelColorMap = new ConcurrentSkipListMap<>();
        waterTimeline.putIfAbsent(0, new ArrayList<>());
    }
    
    public SortedMap<Integer, SortedMap<Integer, Integer>> getChannelColorMap() {
        return channelColorMap;
    }
    
    public void setChannelColorMap(ConcurrentSkipListMap<Integer, SortedMap<Integer, Integer>> channelColorMap) {
        this.channelColorMap = channelColorMap;
    }

	public int getTime() {
            return time;
        }

	public void setTime(int time) {
            this.time = time;
        }

	public int getNumChannels() {
            return numChannels;
        }

	public void setNumChannels(int numChannels) {
            this.numChannels = numChannels;
        }

	public void setLightTimeline(ConcurrentSkipListMap<Integer, ArrayList<FCW>> lightTimeline) {
            this.lightTimeline = lightTimeline;
            
        }

	/**
     * @param timeline the timeline to set
     */
    public void setTimeline(ConcurrentSkipListMap<Integer, ArrayList<FCW>> timeline) {
        this.timeline = timeline;
        timeline.keySet().stream().forEach((i) -> {
            timeline.get(i).stream().forEach((f) -> {
                if (f.getIsWater()) {
                    insertIntoTimeline(waterTimeline, i, f);
                }
                else {
                    if(f.getAddr() != 85)
                        insertIntoTimeline(lightTimeline, i, f);
                }
            });
        });
        time = (int)(MusicPaneController.SONG_TIME * 10); //tenths of a second
        numChannels = countUChannels(lightTimeline);
//        lightFCWColorMap = new LinkedHashMap<>(numChannels);
        channelColorMap = new ConcurrentSkipListMap<>(); //new//new int[time][numChannels];
        startAndEndPoints(channelColorMap);
        fillTheSpaces(channelColorMap);
//        populateLightFcwArray();
//        TimelineController.getInstance().rePaint();
    }

	/**
     * @return the timeline
     */
    public SortedMap<Integer, ArrayList<FCW>> getTimeline() {
        collapseTimelines();
        return timeline;
    }

    /**
     * @return the waterTimeline
     */
    public SortedMap<Integer, ArrayList<FCW>> getWaterTimeline() {
        return waterTimeline;
    }

    /**
     * @return the lightTimeline
     */
    public SortedMap<Integer, ArrayList<FCW>> getLightTimeline() {
//        collapseLightArray();
        return lightTimeline;
    }
    
    /**
     *
     * @param pointInTime
     * @param f
     */
    public void setWaterFcwAtPoint(int pointInTime, FCW f) {
        String[] fActions = FCWLib.getInstance().reverseLookupData(f);
        for(String s: fActions) {
            if(s.equals("RESETALL")) {
                waterTimeline.remove(pointInTime);
            }
        }
//        if()
        if(checkForCollision(waterTimeline, pointInTime, f)) {
            ArrayList<FCW> exists = waterTimeline.get(pointInTime);
            ListIterator<FCW> it = exists.listIterator();
            while(it.hasNext()) {
                FCW fExists = it.next();
                if(fExists.getAddr() == f.getAddr()) {
                    it.remove();
                }
            }
        }
        insertIntoTimeline(waterTimeline, pointInTime, f);
        TimelineController.getInstance().rePaintWaterTimeline();
        
//        waterTimeline.get(pointInTime).add(f);
    }
    
    /**
     *
     * @param channel
     * @param start
     * @param end
     * @param color
     */
    public void setLightFcwWithRange(SortedMap<Integer, Integer> channel, int start, int end, int color) {
        for(int i = start; i < end; i++) {
            channel.put(i, color);
        }
    }
    
    public void setLightFcwAtPoint(int point, FCW f) {
        
    }
    
    public void fillTheSpaces(SortedMap<Integer, SortedMap<Integer, Integer>> channelMap) {
        for(Integer channel: channelMap.keySet()) {
            int start, end, color;
            SortedMap<Integer, Integer> newMap = new ConcurrentSkipListMap<>();
            for(Integer tenth: channelMap.get(channel).keySet()) {
                if(channelMap.get(channel).get(tenth) != 0){
                    start = tenth;
                    color = channelMap.get(channel).get(tenth);
                    Iterator<Entry<Integer, Integer>> it = channelMap.get(channel).tailMap(start + 1).entrySet().iterator();
                    while(it.hasNext()) {
                        Entry<Integer, Integer> timeColor = it.next();
                        if(timeColor.getValue() == 0 && timeColor.getKey() != start) {
                            end = timeColor.getKey();
                            setLightFcwWithRange(newMap, start, end, color);
                            break;
                        }
                        else if(timeColor.getValue() != color) {
                            end = timeColor.getKey();// - 1;
                            setLightFcwWithRange(newMap, start, end, color);
                            break;
                        }
                    }
                }
                channelMap.get(channel).putAll(newMap);
            }
        }
    }
//    /**

//    private void collapseLightAndWaterMaps() {
    private void insertIntoTimeline(SortedMap<Integer, ArrayList<FCW>> srcTimeline, Integer i, FCW f){
        if(srcTimeline.containsKey(i)) {
            for(FCW currentF: srcTimeline.get(i)) {
                if(currentF.equals(f)) {
                    
                }
            }
            srcTimeline.get(i).add(f);
        }
        else {
            ArrayList<FCW> newFcw = new ArrayList(1);
            newFcw.add(f);
            srcTimeline.put(i, newFcw);
        }
    }
    private void startAndEndPoints(SortedMap<Integer, SortedMap<Integer, Integer>> channelMap) {
        
        for(Integer timeIndex: lightTimeline.keySet()) {
            SortedMap<Integer, Integer> newMap = new ConcurrentSkipListMap<>();
            int start = 0;
            for(FCW f: lightTimeline.get(timeIndex)) {
//                String name = FCWLib.getInstance().reverseLookupAddress(f);
//                String[] actions = FCWLib.getInstance().reverseLookupData(f);
                int color = f.getData();
                int tenthOfSec = timeIndex % 10;
                int secondsOnly = timeIndex /10; 
                double tenths = (double) tenthOfSec;
                double newTime = secondsOnly + (tenths / 10);
                int colAtTime = (int) (newTime * MusicPaneController.getInstance().getTimeFactor());
                if(colAtTime != 0){
                    colAtTime = colAtTime - 1;
                }
                if(color == 0) {
//                    setLightFcwWithRange(newMap, start, timeIndex, f);
                }
                if(channelMap.containsKey(f.getAddr())) {
                    channelMap.get(f.getAddr()).put(timeIndex, color);
                    start = timeIndex;
                } else {
                    newMap.put(timeIndex, color);
                    channelMap.putIfAbsent(f.getAddr(), newMap);  
                    start = timeIndex;
                }
            }
            //[f.getAddr()] = data;
        }
    }
    
    private int countUChannels(SortedMap<Integer, ArrayList<FCW>> srcTimeline){
        HashSet<Integer> address = new HashSet<>();
        
        for(ArrayList<FCW> a: srcTimeline.values()){
            for(FCW f: a){
                if(!address.contains(f.getAddr())){
                    address.add(f.getAddr());
                }
            }
        }
        
        return address.size();
        
    }
//    private void populateLightFcwArray() {

    public void sendTimelineInstanceToSliders(int time) {
//        if(waterTimeline.containsKey(time)) {
            Integer closestKey = waterTimeline.floorKey(time);
            SlidersController.getInstance().setSlidersWithFcw(waterTimeline.get(closestKey));
//        }
            
    }
    
    public void sendTimelineInstanceToSim(int time) {
//      if(waterTimeline.containsKey(time)) {
        Integer closestKey = waterTimeline.floorKey(time);
        if(time == 0) {
            closestKey = time;
        }
          FountainSimController.getInstance().drawFcw(waterTimeline.get(closestKey));
          
  }
    
//    public void updateColorsLists(int time){
//    	for (int channel: channelColorMap.keySet()){
//    		for()
//    	}
//    	FountainSimController.getInstance().getFrontCurtain().setFill(arg0);
//    }
       
    public boolean getActionsAtTime(int time){
    	return waterTimeline.containsKey(time);
    }
    
    /**
     * Adds start and end commands to light timeline. Fills channelColorMap
 with data and repaints timeline.
     * 
     * @param f the new FCW
     * @param start the point where the light turns on
     * @param end the point where the light should turn off
     */
    public void setLightFcw(FCW f, int start, int end) {
        insertIntoTimeline(lightTimeline, start, f);
        insertIntoTimeline(lightTimeline, end, new FCW(f.getAddr(), 0));
        SortedMap<Integer, Integer> channel = channelColorMap.get(f.getAddr());
        setLightFcwWithRange(channel, start, end, f.getData());
        TimelineController.getInstance().rePaintLightTimeline();
    }

    public void sendSubmapToSim(int tenthsTime) {
//        FountainSimController.getInstance().acceptSubmapOfFcws(timeline.tailMap(timeline.floorKey(tenthsTime)));
//        FountainSimController.getInstance().acceptSubmapOfFcws(timeline.subMap(tenthsTime, true, MusicPaneController.getInstance().SONG_TIME, true));
    }

    private boolean checkForCollision(SortedMap<Integer, ArrayList<FCW>> timeline, int pointInTime, FCW query) {
        boolean result = false;
        if(timeline.containsKey(pointInTime)) {
            for(FCW f: timeline.get(pointInTime)) {
                if(f.getAddr() == query.getAddr()) {
                    
                    String[] fActions = FCWLib.getInstance().reverseLookupData(f);
                    String[] queryActions = FCWLib.getInstance().reverseLookupData(query);
                    for(String fAction:fActions) {
                        for(String queryAction: queryActions) {
                            if(fAction.equals(queryAction) && !FCWLib.getInstance().isLevel(fAction)) {
//                                query.setData(query.getData() + f.getData());
                                return true;
                            }
//                            else if(!fAction.equals(queryAction) && !FCWLib.getInstance().isLevel(fAction)) {
//                                if(f.getData() > query.getData()) {
//                                    query.setData(f.getData() - query.getData());
//                                }
//                                else {
//                                    query.setData(f.getData() + query.getData());
//                                }
//                            }
                        }
                    }
                    if(f.getAddr() == 54) {
                        result = true;
                        break;
                    }
                }
            }
        }
            return result;
    }

    public void deleteActionAtTime(int i) {
        waterTimeline.remove(i);
    }
    
    public void collapseTimelines() {
        ConcurrentSkipListMap<Integer, ArrayList<FCW>> result = new ConcurrentSkipListMap<>();
//        timeline.clear();
        insertFcwsIntoTimeline(lightTimeline);
        insertFcwsIntoTimeline(waterTimeline);
        timeline = result;
    }

    public void insertFcwsIntoTimeline(SortedMap<Integer, ArrayList<FCW>> srcTimeline) {
        for(Integer timeIndex: srcTimeline.keySet()) {
            for(FCW f: srcTimeline.get(timeIndex)) {
                insertIntoTimeline(timeline, timeIndex, f);
            }
        }
    }
    
}
