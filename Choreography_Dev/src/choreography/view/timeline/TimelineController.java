
package choreography.view.timeline;

import choreography.io.FCWLib;
import choreography.model.fcw.FCW;
import choreography.view.ChoreographyController;
import choreography.view.colorPalette.ColorPaletteController;
import choreography.model.color.ColorPaletteEnum;
import choreography.model.color.ColorPaletteModel;
import choreography.view.music.MusicPaneController;
import choreography.view.sim.FountainSimController;
import choreography.view.sliders.SlidersController;
import choreography.model.timeline.Timeline;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author elementsking
 */
public class TimelineController implements Initializable {
    
    //TODO Add new Light FCWs to Timeline via setLightTimeWithRange()
	
    private static TimelineController instance;

    /**
     *
     * @return
     */
    public static TimelineController getInstance() {
        if (instance == null)
            instance = new TimelineController();
        return instance;
    }
    private final ColorPaletteEnum[] colorEnumArray;
    private Integer[] channelAddresses;
    boolean oldRecHasValue = false;
    Rectangle oldRec = new Rectangle();
    Rectangle copyWaterRec = new Rectangle();
    int copyWaterLocation = 0;
    private String[] labelNames;
    Rectangle selectedRec = new Rectangle();
    int selectedCol = 0;
    int selectedRow = 0;
    private int start;
    Tooltip t;
    private Timeline timeline;

    @FXML
    private GridPane timelineLabelPane;
    @FXML
    private ScrollPane timelineScrollPane, labelScrollPane;
    // NonFXML
    private int time;
    private NumberAxis numLine;

    int startRow = 0;
    int rowNumber;
    GridPane gridpaneLight;
    GridPane gridpaneWater;
    Rectangle[] waterRecArray;
    Rectangle[][] lightRecArray;
    final ArrayList<Rectangle> copyAL;
    final ArrayList<Integer> colAL;
    final ArrayList<Integer> rowAL;
    	
    MenuItem lightCopy = new MenuItem("copy");
    MenuItem lightPaste = new MenuItem("paste");
    MenuItem waterCopy = new MenuItem("copy");
    MenuItem waterPaste = new MenuItem("paste");
    
    final ContextMenu lightCM = new ContextMenu();
    final ContextMenu waterCM = new ContextMenu();
    
    public TimelineController() {
        timeline = new Timeline();
        this.colorEnumArray = ColorPaletteEnum.values();
        this.rowAL = new ArrayList<>();
        this.colAL = new ArrayList<>();
        this.copyAL = new ArrayList<>();
        this.channelAddresses = new Integer[1];
    }
    
    public ArrayList<Integer> getColAL(){
    	return this.colAL;
    }
    
    public ArrayList<Integer> getRowAL(){
    	return this.rowAL;
    }
    
    public void setLightRecArrayFade(int row, int col, double opacity){
    	lightRecArray[col][row].setOpacity(opacity);
    }
    
    public void setLightRecArrayStrobe(int row, int col, Paint c){
    	lightRecArray[col][row].setFill(c);
    }
    
    public void delete( int col, int row){
    	lightRecArray[col][row].setFill(Color.LIGHTGRAY);
    	
    }
    
    public void delete(int col, int row, int length, boolean first){
    	if(first){
    		lightRecArray[col][row].setFill(Color.LIGHTGRAY);
    		if(timeline.getChannelColorMap().containsKey(row)){
//    			   tgetChannelColorMaptGtfoMap().get(row).remove(row, value);//TODO ask frank about this
    		}
    		FCW off = new FCW(channelAddresses[row], 0);
    		lightRecArray[col][row].setFill(Color.LIGHTGRAY);
            timeline.setLightFcw(off, col, col+length);//not sure if length is needed?
    	}
    	
    	
    }
    
    public void delete(int waterCol){
    	if (waterCol != 0){
    		timeline.deleteActionAtTime(waterCol - 1);
    		waterRecArray[waterCol-1].setFill(Color.LIGHTGRAY);
    		t.uninstall(waterRecArray[waterCol-1], t);
    	}
    	else{
    		timeline.deleteActionAtTime(waterCol);
    		waterRecArray[waterCol].setFill(Color.LIGHTGRAY);
    		t.uninstall(waterRecArray[waterCol], t);
    	}
    	    SlidersController.getInstance().resetAllSliders();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("Convert2Lambda")
    public void initialize(URL url, ResourceBundle rb) {
        // setWaterGridPane();
        instance = this;
        initializeTimelines();
        lightCM.getItems().add(lightCopy);
        lightCM.getItems().add(lightPaste);
        
//        waterCM.getItems().add(waterCopy);
//        waterCM.getItems().add(waterPaste);
        
        lightCopy.setDisable(true);
        lightPaste.setDisable(true);
        
//        waterCopy.setDisable(true);
//        waterPaste.setDisable(true);
//        
//        waterCopy.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//            	waterPaste.setDisable(false);
//                System.out.println(copyAL.toString());
//                waterCopy.setDisable(true);
//            }
//    	});
//        
//        waterPaste.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//            	copyWaterRec = oldRec;
//            	waterRecArray[copyWaterLocation].setFill(copyWaterRec.getFill());
//            }
//    	});
        
        lightCopy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lightPaste.setDisable(false);
//                System.out.println(copyAL.toString());
                lightCopy.setDisable(true);
            }
    	});
    	
    	lightPaste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            	ArrayList<Integer> transColAL = new ArrayList<>();
                ArrayList<Integer> transRowAL = new ArrayList<>();
            	
            	transColAL.add(selectedCol);
            	transRowAL.add(selectedRow);
            	
            	for(int i = 1; i < colAL.size(); i++){
            		int currentCol = colAL.get(i);
            		int currentRow = rowAL.get(i);
            		int prevCol = colAL.get(i-1);
            		int prevRow = rowAL.get(i-1);
            		
            		if(currentCol > prevCol){
            			selectedCol = selectedCol+1;
            			transColAL.add(selectedCol);
            		}
            		if(currentCol < prevCol){
            			selectedCol = selectedCol-1;
            			transColAL.add(selectedCol);
            		}
            		if(currentCol == prevCol){
            			transColAL.add(selectedCol);
            		}
            		
            		if(currentRow > prevRow){
            			selectedRow = selectedRow+1;
            			transRowAL.add(selectedRow);
            		}
            		if(currentRow < prevRow){
            			selectedRow = selectedRow-1;
            			transRowAL.add(selectedRow);
            		}
            		if(currentRow == prevRow){
            			transRowAL.add(selectedRow);
            		}
            		
            	}
            	boolean outOfBounds = false;
            	for(int i = 0; i < colAL.size(); i++){
            		if(transRowAL.get(i) >= labelNames.length){
            			JOptionPane.showMessageDialog(null, "You have tried to paste out of bounds", "Pasting out of bounds", 2);
            			outOfBounds = true;
            			break;
            		}
            		
            		if(transColAL.get(i) >= MusicPaneController.SONG_TIME){
            			JOptionPane.showMessageDialog(null, "You have tried to paste out of bounds", "Pasting out of bounds", 2);
            			outOfBounds = true;
            			break;
            		}
            	}
            	
            	if(!outOfBounds){
            		for(int i = 0; i < colAL.size(); i++){
            		
            			int newCol = transColAL.get(i) - colAL.get(i);
            			int newRow = transRowAL.get(i) - rowAL.get(i);
            			lightRecArray[colAL.get(i) + newCol][rowAL.get(i) + newRow].setFill(copyAL.get(i).getFill());
            		}
            	}
            	
            	transRowAL.add(0);
            	ArrayList<Integer> start = new ArrayList<>();
            	for(int i = 1; i < transRowAL.size(); i++){
            		if(transRowAL.get(i) == 0){
            			//TODO copy and paste and then save and open to see if correct, will likely need to add offs
            			start.add(transRowAL.get(i-1));
            			int startpt = start.get(0);
            			int startOther = transColAL.get(i-1) - start.size()+1;
            			int endpt = transColAL.get(i-1)+1;
//            			FCW f = new FCW(channelAddresses[startpt], (Color)lightRecArray[transColAL.get(i-1)][transRowAL.get(i-1)].getFill());
            			FCW f = new FCW(channelAddresses[startpt], ColorPaletteModel.getInstance().getSelectedIndex() + 1);
            			FCW off = new FCW(channelAddresses[startpt], 0);
                        timeline.setLightFcw(f, startOther, endpt);
                        timeline.setLightFcw(off, startOther, endpt); //TODO causes stray boxes, might not be needed
                        System.out.println(f + " " + startOther + " " + endpt);
                        System.out.println(off + " " + endpt + " " + (endpt+1));
                        start.clear();
            		}
            		else if(transRowAL.get(i) != transRowAL.get(i-1)){
            			start.add(transRowAL.get(i-1));
            			int startpt = start.get(0);
            			int startOther = transColAL.get(i-1) - start.size()+1;
            			int endpt = transColAL.get(i-1)+1;
            			FCW f = new FCW(channelAddresses[startpt], ColorPaletteModel.getInstance().getSelectedIndex() + 1);
            			FCW off = new FCW(channelAddresses[startpt], 0);
                        timeline.setLightFcw(f, startOther, endpt);
                        timeline.setLightFcw(off, startOther, endpt);
                        System.out.println(f + " " + startOther + " " + endpt);
                        System.out.println(off + " " + endpt + " " + (endpt+1));
                        start.clear();
            		}
            		else{
            			start.add(transRowAL.get(i -1));
            		}
            	}
			}    		

            
		});
    }

    public void initializeTimelines() {
        setLabelNames(new String[]{"Module1", "Module2", "Module3", 
                "Module4", "Module5", "Module6", "Module7", "PeacockAB", 
                "FrontCurtain", "BackCurtain"});
        setLabelGridPane(getLabelNames());
        setTimelineGridPane();
//        ChoreographyController.getInstance().setBeatMarkGridPane();
    }
    
    public void disableCopyPaste(){
    	lightCopy.setDisable(true);
    	lightPaste.setDisable(true);
    }
    
    public Integer[] getSpecialChannels(){
    	//all of the strobe, fades, and the initial 10
    	Integer[] specialChannels = new Integer[] {
//          17, 18, 19, 20, 21, 22, 23, 24, 27, 200, 
            16, 101, 102, 103, 106, 107, 108, 111, 112, 113, 116, 117, 118, 
            121, 122, 123, 126, 127, 128, 131, 132, 133, 136, 137, 138, 141, 
            142, 143, 191, 192, 193, 201, 202, 203, 206, 207, 208, 
            231, 232, 233, 251, 252, 253};
    	
    	return specialChannels;
    	
    }
//        final String[] labelNames = new String[] {"Custom Channel", "Module 1", "Module 2",
//                        "Module 3", "Module 4", "Module 5", "Module 6", "Module 7",
//                        "A Modules", "B Modules", "Front Curtain", "Back Curtain",
//                        "Peacock", "Voice", "ALL LEDs", "L1-A Mod 1 Front Left",
//                        "L2-A Mod 1 Front Right", "L6-A Mod 2 Front Left",
//                        "L7-A Mod 2 Front Right", "L11-A Mod 3 Front Left",
//                        "L12-A Mod 3 Front Right", "L19-A Mod 4 Front Left",
//                        "L16-A Mod 4 Front Left Center", "L18-A Mod Front Center",
//                        "L17-A Mod 4 Front Right Center", "L20-A Mod 4 Front Right",
//                        "L21-A Mod 5 Front Left", "L22-A Mod 5 Front Right",
//                        "L26-A Mod 6 Front Left", "L27-A Mod 6 Front Right",
//                        "L31-A Mod 7 Front Left", "L32-A Mod 7 Front Right",
//                        "L4-A Mod 1 Back Left", "L3-A Mod 1 Back Center",
//                        "L5-A Mod 1 Back Right", "L9-A Mod 2 Back Left",
//                        "L8-A Mod 2 Back Center", "L10-A Mod 2 Back Right",
//                        "L14-A Mod 3 Back Left", "L13-A Mod 3 Back Center",
//                        "L15-A Mod 3 Back Right", "L37-Peacock 1, Left",
//                        "L38-Peacock 2, Left Center", "L39-Peacock 3, Right Center",
//                        "L40-Peacock 4- Right", "L24-A Mod 5 Back Left",
//                        "L23-A Mod 5 Back Center", "L25-A Mod 5 Back Right",
//                        "L29-A Mod 6 Back Left", "L28-A Mod 6 Back Center",
//                        "L30-A Mod 6 Back Right", "L34-A Mod 7 Back Left",
//                        "L33-A Mod 7 Back Center", "L35-A Mod 7 Back Right",
//                        "L36-Voice 1", "Space for Voice 2", "Custom Channel" };
    public void setLabelGridPane(String[] input) {
    	setLabelNames(input);
    	Set<Integer> addresses = new HashSet<>();
    	for(String in: input) {
            Integer addr = FCWLib.getInstance().lookupAddress(in);
            addresses.add(addr);
    	}
    	setChannelAddresses(addresses);
        injectIntoGtfo(channelAddresses);
    	setLabelGridPane(channelAddresses);
        rePaintLightTimeline();
    }
    
    public void setLabelGridPaneWithCtl(){
    	Set<Integer> channelAddressesSet = timeline.getChannelColorMap().keySet();
        setChannelAddresses(channelAddressesSet);
        labelNames = new String[channelAddresses.length];
        for(int i = 0; i < channelAddresses.length; i++) {
            labelNames[i] = FCWLib.getInstance().reverseLookupAddress(channelAddresses[i]);
        }
        setLabelGridPane(channelAddresses);
        setTimelineGridPane();
        rePaintLightTimeline();
    }

    public void setChannelAddresses(Set<Integer> channelAddressesSet) {
        ArrayList<Integer> okChannels = new ArrayList<>();
        for(Integer query: channelAddressesSet) {
            if(!checkForCollisions(query)) {
                okChannels.add(query);
            }
        }
        channelAddresses = channelAddressesSet.toArray(new Integer[1]);
    }
    	
    public void setLabelGridPane(Integer[] input) {
        timelineLabelPane = new GridPane();
    	timelineLabelPane.setGridLinesVisible(true);
    	Label[] labelArray = new Label[input.length];
    	for(int i=0; i<input.length;i++){
            timelineLabelPane.getRowConstraints().add(new RowConstraints(26));
    	}
    	timelineLabelPane.getColumnConstraints().add(new ColumnConstraints(98));
    	
    	for(int i=0; i < input.length;i++){
            labelArray[i] = new Label(FCWLib.getInstance().reverseLookupAddress((input[i])));
            timelineLabelPane.add(labelArray[i], 0, i);
    	}
        
        timelineScrollPane.vvalueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> o,Object oldVal, 
                     Object newVal){
                 labelScrollPane.setVvalue(timelineScrollPane.getVvalue());
            }
          });
    	 labelScrollPane.vvalueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> o,Object oldVal, 
                     Object newVal){
                    timelineScrollPane.setVvalue(labelScrollPane.getVvalue());
            }
          });
    	labelScrollPane.setContent(timelineLabelPane);
    }
    
    /**
     * 
     */
    public void setTimelineGridPane() {
        gridpaneLight = new GridPane();
        time = MusicPaneController.SONG_TIME;
        gridpaneLight.setGridLinesVisible(true);
        rowNumber =  labelNames.length;
        lightRecArray = new Rectangle[time][labelNames.length];
        
        for (int i = 0; i < time; i++) {
            gridpaneLight.getColumnConstraints().add(new ColumnConstraints(26));
            if (i < labelNames.length) { // because the array is not square this needs to be
                                            // here
                    gridpaneLight.getRowConstraints().add(new RowConstraints(26));
            }

            for (int j = 0; j < labelNames.length; j++) {
                // if (i == 0){
                // recArray[i][j] = new Rectangle(50,25, Color.RED);
                // continue;
                // }
                lightRecArray[i][j] = new Rectangle(25, 25, Color.LIGHTGRAY);
                gridpaneLight.add(lightRecArray[i][j], i, j);
                // these are needed to talk to the mouse pressed events
                final int testI = i;
                final int testJ = j;

                lightRecArray[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
                    

                    @Override
                    public void handle(MouseEvent me) {
                    	selectedRec = lightRecArray[testI][testJ];
                    	selectedCol = testI;
                    	selectedRow = testJ;
                    	
                        if(ChoreographyController.getInstance().getIsSelected()){
                        	if (me.getButton() == MouseButton.SECONDARY) {
                                lightCM.show(lightRecArray[testI][testJ], me.getScreenX(), me.getScreenY());
                            }
                        	
                        }
                    else{
                            startRow = testJ;
                            lightRecArray[testI][testJ]
                                    .setFill(ColorPaletteController
                                            .getInstance()
                                            .getSelectedColor());
                            int address = channelAddresses[testJ];
//                            timeline.setLightFcw(new FCW(address, 
//                                ColorPaletteModel.getInstance().getSelectedIndex() + 1), testI, testJ);
                            start = testI;
                    }
                        
                    }
                });

                lightRecArray[i][j].setOnDragDetected((MouseEvent me) -> {
                	
                	for(Rectangle rec: copyAL){
                        rec.setOpacity(1);
                    }
                    copyAL.clear();
                    colAL.clear();
                    rowAL.clear();
                    copyAL.add(lightRecArray[testI][testJ]);
                    if(ChoreographyController.getInstance().getIsSelected()){
                        lightCopy.setDisable(false);
                        lightRecArray[testI][testJ].startFullDrag();
                        lightRecArray[testI][testJ].setOpacity(50);

                        colAL.add(testI);
                        rowAL.add(testJ);
                    }
                	
                    lightRecArray[testI][testJ].startFullDrag();
                }); 
                // continues and ends the drag event
                lightRecArray[i][j].setOnMouseDragOver((MouseEvent me) -> {
                    
//                    if (!copyAL.contains(lightRecArray[testI][testJ])){
//                            copyAL.add(lightRecArray[testI][testJ]);
//                            colAL.add(testI);
//                            rowAL.add(testJ);
//                    }
                    if (ChoreographyController.getInstance().getIsSelected()) {
                    	lightRecArray[testI][testJ].setOpacity(.50);
                        if (!copyAL.contains(lightRecArray[testI][testJ])){
                            copyAL.add(lightRecArray[testI][testJ]);
                        colAL.add(testI);
                        rowAL.add(testJ);
                        }
                    } else {
                    	if (startRow == testJ) {
                        	lightRecArray[testI][testJ].setOpacity(1);
                            lightRecArray[testI][testJ]
                                    .setFill(ColorPaletteController
                                            .getInstance()
                                            .getSelectedColor());
                        }
//                    	lightRecArray[testI][testJ].setOpacity(1);
//                    	lightRecArray[testI][testJ].setFill(ColorPaletteController
//                                .getInstance()
//                                .getSelectedColor());
//                        timeline.setLightFcwAtPoint(testI, new FCW(testI, 
//                                ColorPaletteModel.getInstance().getSelectedIndex()));
                    }
                });
                lightRecArray[i][j].setOnMouseDragReleased((MouseEvent me) -> {
                	if (!ChoreographyController.getInstance().getIsSelected()){
                		if (startRow != testJ){
                		FCW f = new FCW(channelAddresses[startRow], ColorPaletteModel.getInstance().getSelectedIndex() + 1);
                        timeline.setLightFcw(f, start, testI + 1);
                        System.out.println(f + " " + start + " " + testI + 1);
                	}
                	else{
                		FCW f = new FCW(channelAddresses[testJ], ColorPaletteModel.getInstance().getSelectedIndex() + 1);
                    timeline.setLightFcw(f, start, testI + 1);
//                    System.out.println(f + " " + start + " " + testI + 1);
                	}
                	}
                	
                    
                });
            }
        }

        timelineScrollPane.setContent(gridpaneLight);
    }

    public void clearAllAL(){
        for (Iterator<Rectangle> it = copyAL.iterator(); it.hasNext();) {
            Rectangle rec = it.next();
            rec.setOpacity(1);
        }
    	copyAL.clear();
    	colAL.clear();
        rowAL.clear();
    }
    
    public void setWaterGridPane() {
        gridpaneWater = new GridPane();
        // NumberAxis valueAxis = new NumberAxis();

        time = MusicPaneController.SONG_TIME;
//        numLine = new NumberAxis((double) 0, (double) time, 1);

        gridpaneWater.setGridLinesVisible(true);

        waterRecArray = new Rectangle[time];
        for (int i = 0; i < time; i++) {
        	 final int testI = i;
                gridpaneWater.getColumnConstraints().add(new ColumnConstraints(26));
                if (i < 1) { // because the array is not square this needs to be
                                              // here
                        gridpaneWater.getRowConstraints().add(new RowConstraints(26));
                }
                
                waterRecArray[i] = new Rectangle(25, 25, Color.LIGHTGREY);
                gridpaneWater.add(waterRecArray[i], i, 0);
                                
                waterRecArray[i]
                        .setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {

                    if (me.getButton() == MouseButton.SECONDARY) {
//                            		currentWaterRec = waterRecArray[testI];
                        copyWaterLocation = testI;
                        waterCopy.setDisable(false);
                        waterCM.show(waterRecArray[testI], me.getScreenX(), me.getScreenY());
                    }

//                    System.out.println("Col " + (testI));
                    Duration duration = MusicPaneController.getInstance().getMediaPlayer().getTotalDuration();
                    MusicPaneController.getInstance().getMediaPlayer().seek(Duration.seconds((((double)testI+1)/10)));
                    
//                    waterRecArray[testI].setFill(Color.LIGHTBLUE);
//                    System.out.println(timeline.getActionsAtTime(testI));
                    
//                    if (!oldRecHasValue){ //aka oldRed does not have a value
//                    	oldRec = waterRecArray[testI];
//                    	oldRecHasValue = true;
//                    }
//                    else{
                    	if(timeline.getActionsAtTime(testI)){
//                    		rePaintWaterTimeline();
//                            oldRec.setFill(Color.DARKBLUE);
//                            oldRec = waterRecArray[testI];
//                            waterRecArray[testI] = oldRec;
                    	}
                    	else{
//                            oldRec.setFill(Color.LIGHTGRAY);
//                            oldRec = waterRecArray[testI];
//                            waterRecArray[testI] = oldRec;
                    	}
                    	
                    	
//                    }
                }
                });
                
//                waterRecArray[i].setOnDragDetected((MouseEvent me) -> {
//                	for(Rectangle rec: copyAL){
//                        rec.setOpacity(1);
//                    }
//                    copyAL.clear();
//                    colAL.clear();
//                
//                	if(ChoreographyController.getInstance().getIsSelected()){
//                		lightCopy.setDisable(false);
//                		waterRecArray[testI].startFullDrag();
//                        copyAL.add(waterRecArray[testI]);
//                        
//                        colAL.add(testI);
//                	}
//                	
//                	waterRecArray[testI].startFullDrag();
//                });
//                
//                waterRecArray[i].setOnMouseDragOver((MouseEvent me) -> {
//                  
//                  if (ChoreographyController.getInstance().getIsSelected()) {
//                	  waterRecArray[testI].setOpacity(.50);
//                      if (!copyAL.contains(waterRecArray[testI])){
//                          copyAL.add(waterRecArray[testI]);
//                          
//                          colAL.add(testI);
//                      }
//                  } else {
//                	  waterRecArray[testI].setFill(ColorPaletteController
//                              .getInstance()
//                              .getSelectedColor());
//                  }
//              });
                
        MusicPaneController.getInstance().getWaterPane().setContent(gridpaneWater);
    }
    }

    /**
     * 
     * @return
     */
    public ScrollPane getScrollPane() {
            return timelineScrollPane;
    }
    
    public void rePaint() {
        rePaintWaterTimeline();
        rePaintLightTimeline();
    }
    
    /**
     *
     */
    public void rePaintWaterTimeline() {
        SortedMap<Integer, ArrayList<FCW>> waterTimeline = timeline.getWaterTimeline();
        for(Integer i: waterTimeline.keySet()){
            ArrayList<String> actionsList = new ArrayList<>();
//            StringBuilder actionList = new StringBuilder();
            int tenthOfSec = i % 10;
            int secondsOnly = i /10; 
            double tenths = (double) tenthOfSec;
            double newTime = secondsOnly + (tenths / 10);
            int colAtTime = (int) (newTime * MusicPaneController.getInstance().getTimeFactor());
            if(colAtTime != 0){
                    colAtTime = colAtTime - 1;
            }
            for(FCW f: waterTimeline.get(i)) {
                actionsList.add(f.getPrettyString());
                //TODO make mouse over info better
                //TODO update sliders
            }
             waterRecArray[colAtTime].setFill(Color.AZURE);
                t = new Tooltip(actionsList.toString());
                t.setAutoHide(true);
                Tooltip.install(waterRecArray[colAtTime], t);
        }
//        rePaintLightTimeline();
    }

    public void rePaintLightTimeline() {
        SortedMap<Integer, SortedMap<Integer, Integer>> channelColorMap = timeline.getChannelColorMap();
        for (int channel: channelColorMap.keySet()){
            for (int timeIndex: channelColorMap.get(channel).keySet()){
                 Integer color = timeline.getChannelColorMap().get(channel).get(timeIndex);
                Paint paintColor = ColorPaletteModel.getInstance().getColor(color);
                if(paintColor == null || paintColor.equals(Color.BLACK)){
                	paintColor = Color.LIGHTGRAY;
                }
                int row = lightRowLookupNumber(channel);
                lightRecArray[timeIndex][row].setFill(paintColor);
            }
        }
    }
    
    public void updateColors(int time){
    	SortedMap<Integer, SortedMap<Integer, Integer>> channelColorMap = timeline.getChannelColorMap();
    	for (int channel: channelColorMap.keySet()){
            if(time == MusicPaneController.SONG_TIME)
                time = time - 1;
            Paint color = lightRecArray[time][lightRowLookupNumber(channel)].getFill();
            if(color.equals(Color.BLACK)) {
                color = Color.LIGHTGRAY;
            }
            if(channel == 17 || channel == 110){
                    FountainSimController.getInstance().getMod1ring1().setFill(color);
                    FountainSimController.getInstance().getMod1ring2().setFill(color);
                    FountainSimController.getInstance().getMod1ring3().setFill(color);
                    FountainSimController.getInstance().getMod1ring4().setFill(color);
                    FountainSimController.getInstance().getMod1ring5().setFill(color);
                    FountainSimController.getInstance().getMod1candle1().setStroke(color);
                    FountainSimController.getInstance().getMod1candle2().setStroke(color);
                    FountainSimController.getInstance().getMod1candle3().setStroke(color);
                    FountainSimController.getInstance().getMod1candle4().setStroke(color);
                    FountainSimController.getInstance().getMod1candle5().setStroke(color);
                    FountainSimController.getInstance().getMod1candle6().setStroke(color);
                    FountainSimController.getInstance().getMod1sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod1sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka2().setStroke(color);

                    }
            if(channel == 18 || channel == 115){
                    FountainSimController.getInstance().getMod2ring1().setFill(color);
                    FountainSimController.getInstance().getMod2ring2().setFill(color);
                    FountainSimController.getInstance().getMod2ring3().setFill(color);
                    FountainSimController.getInstance().getMod2ring4().setFill(color);
                    FountainSimController.getInstance().getMod2ring5().setFill(color);
                    FountainSimController.getInstance().getMod2candle1().setStroke(color);
                    FountainSimController.getInstance().getMod2candle2().setStroke(color);
                    FountainSimController.getInstance().getMod2candle3().setStroke(color);
                    FountainSimController.getInstance().getMod2candle4().setStroke(color);
                    FountainSimController.getInstance().getMod2candle5().setStroke(color);
                    FountainSimController.getInstance().getMod2candle6().setStroke(color);
                    FountainSimController.getInstance().getMod2sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod2sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka4().setStroke(color);
                    }
            if(channel == 19|| channel == 120){
                FountainSimController.getInstance().getMod3ring1().setFill(color);
                FountainSimController.getInstance().getMod3ring2().setFill(color);
                FountainSimController.getInstance().getMod3ring3().setFill(color);
                FountainSimController.getInstance().getMod3ring4().setFill(color);
                FountainSimController.getInstance().getMod3ring5().setFill(color);
                FountainSimController.getInstance().getMod3candle1().setStroke(color);
                FountainSimController.getInstance().getMod3candle2().setStroke(color);
                FountainSimController.getInstance().getMod3candle3().setStroke(color);
                FountainSimController.getInstance().getMod3candle4().setStroke(color);
                FountainSimController.getInstance().getMod3candle5().setStroke(color);
                FountainSimController.getInstance().getMod3candle6().setStroke(color);
                FountainSimController.getInstance().getMod3sweep1().setStroke(color);
                FountainSimController.getInstance().getMod3sweep2().setStroke(color);
            }
            if(channel == 20|| channel == 125){
                FountainSimController.getInstance().getMod4ring1().setFill(color);
                FountainSimController.getInstance().getMod4ring2().setFill(color);
                FountainSimController.getInstance().getMod4ring3().setFill(color);
                FountainSimController.getInstance().getMod4ring4().setFill(color);
                FountainSimController.getInstance().getMod4ring5().setFill(color);
                FountainSimController.getInstance().getMod4candle1().setStroke(color);
                FountainSimController.getInstance().getMod4candle2().setStroke(color);
                FountainSimController.getInstance().getMod4candle3().setStroke(color);
                FountainSimController.getInstance().getMod4candle4().setStroke(color);
                FountainSimController.getInstance().getMod4candle5().setStroke(color);
                FountainSimController.getInstance().getMod4candle6().setStroke(color);
                FountainSimController.getInstance().getMod4sweep1().setStroke(color);
                FountainSimController.getInstance().getMod4sweep2().setStroke(color);
                FountainSimController.getInstance().getSpoutRec().setFill(color);

            }
            if(channel == 21|| channel == 130){
                FountainSimController.getInstance().getMod5ring1().setFill(color);
                FountainSimController.getInstance().getMod5ring2().setFill(color);
                FountainSimController.getInstance().getMod5ring3().setFill(color);
                FountainSimController.getInstance().getMod5ring4().setFill(color);
                FountainSimController.getInstance().getMod5ring5().setFill(color);
                FountainSimController.getInstance().getMod5candle1().setStroke(color);
                FountainSimController.getInstance().getMod5candle2().setStroke(color);
                FountainSimController.getInstance().getMod5candle3().setStroke(color);
                FountainSimController.getInstance().getMod5candle4().setStroke(color);
                FountainSimController.getInstance().getMod5candle5().setStroke(color);
                FountainSimController.getInstance().getMod5candle6().setStroke(color);
                FountainSimController.getInstance().getMod5sweep1().setStroke(color);
                FountainSimController.getInstance().getMod5sweep2().setStroke(color);
            }
            if(channel == 22|| channel == 135){
                FountainSimController.getInstance().getMod6ring1().setFill(color);
                FountainSimController.getInstance().getMod6ring2().setFill(color);
                FountainSimController.getInstance().getMod6ring3().setFill(color);
                FountainSimController.getInstance().getMod6ring4().setFill(color);
                FountainSimController.getInstance().getMod6ring5().setFill(color);
                FountainSimController.getInstance().getMod6candle1().setStroke(color);
                FountainSimController.getInstance().getMod6candle2().setStroke(color);
                FountainSimController.getInstance().getMod6candle3().setStroke(color);
                FountainSimController.getInstance().getMod6candle4().setStroke(color);
                FountainSimController.getInstance().getMod6candle5().setStroke(color);
                FountainSimController.getInstance().getMod6candle6().setStroke(color);
                FountainSimController.getInstance().getMod6sweep1().setStroke(color);
                FountainSimController.getInstance().getMod6sweep2().setStroke(color);
                FountainSimController.getInstance().getBazooka3().setStroke(color);
            }
            if(channel == 23|| channel == 140){
                FountainSimController.getInstance().getMod7ring1().setFill(color);
                FountainSimController.getInstance().getMod7ring2().setFill(color);
                FountainSimController.getInstance().getMod7ring3().setFill(color);
                FountainSimController.getInstance().getMod7ring4().setFill(color);
                FountainSimController.getInstance().getMod7ring5().setFill(color);
                FountainSimController.getInstance().getMod7candle1().setStroke(color);
                FountainSimController.getInstance().getMod7candle2().setStroke(color);
                FountainSimController.getInstance().getMod7candle3().setStroke(color);
                FountainSimController.getInstance().getMod7candle4().setStroke(color);
                FountainSimController.getInstance().getMod7candle5().setStroke(color);
                FountainSimController.getInstance().getMod7candle6().setStroke(color);
                FountainSimController.getInstance().getMod7sweep1().setStroke(color);
                FountainSimController.getInstance().getMod7sweep2().setStroke(color);
                FountainSimController.getInstance().getBazooka1().setStroke(color);
            }
            if(channel == 24|| channel == 205){
                //FountainSimController.getInstance().getBackCurtain().setFill(color);
            	FountainSimController.getInstance().getBackCurtain1().setFill(color);
        		FountainSimController.getInstance().getBackCurtain2().setFill(color);
                FountainSimController.getInstance().getBackCurtain3().setFill(color);
                FountainSimController.getInstance().getBackCurtain4().setFill(color);
                FountainSimController.getInstance().getBackCurtain5().setFill(color);
                FountainSimController.getInstance().getBackCurtain6().setFill(color);
                FountainSimController.getInstance().getBackCurtain9().setFill(color);
                FountainSimController.getInstance().getBackCurtain10().setFill(color);
                FountainSimController.getInstance().getBackCurtain11().setFill(color);
                FountainSimController.getInstance().getBackCurtain12().setFill(color);
                FountainSimController.getInstance().getBackCurtain13().setFill(color);
                FountainSimController.getInstance().getBackCurtain14().setFill(color);
                
                FountainSimController.getInstance().getBackCurtain1().setStroke(color);
        		FountainSimController.getInstance().getBackCurtain2().setStroke(color);
                FountainSimController.getInstance().getBackCurtain3().setStroke(color);
                FountainSimController.getInstance().getBackCurtain4().setStroke(color);
                FountainSimController.getInstance().getBackCurtain5().setStroke(color);
                FountainSimController.getInstance().getBackCurtain6().setStroke(color);
                FountainSimController.getInstance().getBackCurtain9().setStroke(color);
                FountainSimController.getInstance().getBackCurtain10().setStroke(color);
                FountainSimController.getInstance().getBackCurtain11().setStroke(color);
                FountainSimController.getInstance().getBackCurtain12().setStroke(color);
                FountainSimController.getInstance().getBackCurtain13().setStroke(color);
                FountainSimController.getInstance().getBackCurtain14().setStroke(color);
                
                FountainSimController.getInstance().getBackCurtain7().setFill(color);
                FountainSimController.getInstance().getBackCurtain8().setFill(color);
                FountainSimController.getInstance().getBackCurtain7().setStroke(color);
                FountainSimController.getInstance().getBackCurtain8().setStroke(color);
            }
            if(channel == 25){
                FountainSimController.getInstance().getPeacock1().setStroke(color);
                FountainSimController.getInstance().getPeacock2().setStroke(color);
                FountainSimController.getInstance().getPeacock3().setStroke(color);
                FountainSimController.getInstance().getPeacock4().setStroke(color);
            }
            if(channel == 26 || channel == 41){
                FountainSimController.getInstance().getPeacock5().setStroke(color);
                FountainSimController.getInstance().getPeacock6().setStroke(color);
                FountainSimController.getInstance().getPeacock7().setStroke(color);
                FountainSimController.getInstance().getPeacock8().setStroke(color);
                FountainSimController.getInstance().getPeacock9().setStroke(color);
            }
            if(channel == 27 || channel == 230){
            	FountainSimController.getInstance().getPeacock1().setStroke(color);
                FountainSimController.getInstance().getPeacock2().setStroke(color);
                FountainSimController.getInstance().getPeacock3().setStroke(color);
                FountainSimController.getInstance().getPeacock4().setStroke(color);
                FountainSimController.getInstance().getPeacock5().setStroke(color);
                FountainSimController.getInstance().getPeacock6().setStroke(color);
                FountainSimController.getInstance().getPeacock7().setStroke(color);
                FountainSimController.getInstance().getPeacock8().setStroke(color);
                FountainSimController.getInstance().getPeacock9().setStroke(color);
            }
            if(channel == 200){
            	FountainSimController.getInstance().getFrontCurtain1().setFill(color);
        		FountainSimController.getInstance().getFrontCurtain2().setFill(color);
                FountainSimController.getInstance().getFrontCurtain3().setFill(color);
                FountainSimController.getInstance().getFrontCurtain4().setFill(color);
                FountainSimController.getInstance().getFrontCurtain5().setFill(color);
                FountainSimController.getInstance().getFrontCurtain6().setFill(color);
                FountainSimController.getInstance().getFrontCurtain7().setFill(color);
                FountainSimController.getInstance().getFrontCurtain8().setFill(color);
                FountainSimController.getInstance().getFrontCurtain9().setFill(color);
                FountainSimController.getInstance().getFrontCurtain10().setFill(color);
                FountainSimController.getInstance().getFrontCurtain11().setFill(color);
                FountainSimController.getInstance().getFrontCurtain12().setFill(color);
                FountainSimController.getInstance().getFrontCurtain13().setFill(color);
                FountainSimController.getInstance().getFrontCurtain14().setFill(color);        
                }
            if(channel == 190|| channel == 194 || channel == 195){
                FountainSimController.getInstance().getSpoutRec().setFill(color);
            }
            if(channel == 49){
            	// Module A
            	if (FountainSimController.getInstance().getMod1ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod1ring1().setFill(color);
                    FountainSimController.getInstance().getMod1ring2().setFill(color);
                    FountainSimController.getInstance().getMod1ring3().setFill(color);
                    FountainSimController.getInstance().getMod1ring4().setFill(color);
                    FountainSimController.getInstance().getMod1ring5().setFill(color);
                    FountainSimController.getInstance().getMod1candle1().setStroke(color);
                    FountainSimController.getInstance().getMod1candle2().setStroke(color);
                    FountainSimController.getInstance().getMod1candle3().setStroke(color);
                    FountainSimController.getInstance().getMod1candle4().setStroke(color);
                    FountainSimController.getInstance().getMod1candle5().setStroke(color);
                    FountainSimController.getInstance().getMod1candle6().setStroke(color);
                    FountainSimController.getInstance().getMod1sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod1sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka2().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod3ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod3ring1().setFill(color);
                    FountainSimController.getInstance().getMod3ring2().setFill(color);
                    FountainSimController.getInstance().getMod3ring3().setFill(color);
                    FountainSimController.getInstance().getMod3ring4().setFill(color);
                    FountainSimController.getInstance().getMod3ring5().setFill(color);
                    FountainSimController.getInstance().getMod3candle1().setStroke(color);
                    FountainSimController.getInstance().getMod3candle2().setStroke(color);
                    FountainSimController.getInstance().getMod3candle3().setStroke(color);
                    FountainSimController.getInstance().getMod3candle4().setStroke(color);
                    FountainSimController.getInstance().getMod3candle5().setStroke(color);
                    FountainSimController.getInstance().getMod3candle6().setStroke(color);
                    FountainSimController.getInstance().getMod3sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod3sweep2().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod5ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod5ring1().setFill(color);
                    FountainSimController.getInstance().getMod5ring2().setFill(color);
                    FountainSimController.getInstance().getMod5ring3().setFill(color);
                    FountainSimController.getInstance().getMod5ring4().setFill(color);
                    FountainSimController.getInstance().getMod5ring5().setFill(color);
                    FountainSimController.getInstance().getMod5candle1().setStroke(color);
                    FountainSimController.getInstance().getMod5candle2().setStroke(color);
                    FountainSimController.getInstance().getMod5candle3().setStroke(color);
                    FountainSimController.getInstance().getMod5candle4().setStroke(color);
                    FountainSimController.getInstance().getMod5candle5().setStroke(color);
                    FountainSimController.getInstance().getMod5candle6().setStroke(color);
                    FountainSimController.getInstance().getMod5sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod5sweep2().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod7ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod7ring1().setFill(color);
                    FountainSimController.getInstance().getMod7ring2().setFill(color);
                    FountainSimController.getInstance().getMod7ring3().setFill(color);
                    FountainSimController.getInstance().getMod7ring4().setFill(color);
                    FountainSimController.getInstance().getMod7ring5().setFill(color);
                    FountainSimController.getInstance().getMod7candle1().setStroke(color);
                    FountainSimController.getInstance().getMod7candle2().setStroke(color);
                    FountainSimController.getInstance().getMod7candle3().setStroke(color);
                    FountainSimController.getInstance().getMod7candle4().setStroke(color);
                    FountainSimController.getInstance().getMod7candle5().setStroke(color);
                    FountainSimController.getInstance().getMod7candle6().setStroke(color);
                    FountainSimController.getInstance().getMod7sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod7sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka1().setStroke(color);
            	}
            }
            if(channel == 50){
            	// Module B
            	if (FountainSimController.getInstance().getMod2ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod2ring1().setFill(color);
                    FountainSimController.getInstance().getMod2ring2().setFill(color);
                    FountainSimController.getInstance().getMod2ring3().setFill(color);
                    FountainSimController.getInstance().getMod2ring4().setFill(color);
                    FountainSimController.getInstance().getMod2ring5().setFill(color);
                    FountainSimController.getInstance().getMod2candle1().setStroke(color);
                    FountainSimController.getInstance().getMod2candle2().setStroke(color);
                    FountainSimController.getInstance().getMod2candle3().setStroke(color);
                    FountainSimController.getInstance().getMod2candle4().setStroke(color);
                    FountainSimController.getInstance().getMod2candle5().setStroke(color);
                    FountainSimController.getInstance().getMod2candle6().setStroke(color);
                    FountainSimController.getInstance().getMod2sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod2sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka4().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod4ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod4ring1().setFill(color);
                    FountainSimController.getInstance().getMod4ring2().setFill(color);
                    FountainSimController.getInstance().getMod4ring3().setFill(color);
                    FountainSimController.getInstance().getMod4ring4().setFill(color);
                    FountainSimController.getInstance().getMod4ring5().setFill(color);
                    FountainSimController.getInstance().getMod4candle1().setStroke(color);
                    FountainSimController.getInstance().getMod4candle2().setStroke(color);
                    FountainSimController.getInstance().getMod4candle3().setStroke(color);
                    FountainSimController.getInstance().getMod4candle4().setStroke(color);
                    FountainSimController.getInstance().getMod4candle5().setStroke(color);
                    FountainSimController.getInstance().getMod4candle6().setStroke(color);
                    FountainSimController.getInstance().getMod4sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod4sweep2().setStroke(color);
                    FountainSimController.getInstance().getSpoutRec().setFill(color);
            	}
            	if (FountainSimController.getInstance().getMod6ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod6ring1().setFill(color);
                    FountainSimController.getInstance().getMod6ring2().setFill(color);
                    FountainSimController.getInstance().getMod6ring3().setFill(color);
                    FountainSimController.getInstance().getMod6ring4().setFill(color);
                    FountainSimController.getInstance().getMod6ring5().setFill(color);
                    FountainSimController.getInstance().getMod6candle1().setStroke(color);
                    FountainSimController.getInstance().getMod6candle2().setStroke(color);
                    FountainSimController.getInstance().getMod6candle3().setStroke(color);
                    FountainSimController.getInstance().getMod6candle4().setStroke(color);
                    FountainSimController.getInstance().getMod6candle5().setStroke(color);
                    FountainSimController.getInstance().getMod6candle6().setStroke(color);
                    FountainSimController.getInstance().getMod6sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod6sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka3().setStroke(color);
            	}
            }
            if(channel == 51 || channel == 250){
            	// Module A and B
            	if (FountainSimController.getInstance().getMod1ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod1ring1().setFill(color);
                    FountainSimController.getInstance().getMod1ring2().setFill(color);
                    FountainSimController.getInstance().getMod1ring3().setFill(color);
                    FountainSimController.getInstance().getMod1ring4().setFill(color);
                    FountainSimController.getInstance().getMod1ring5().setFill(color);
                    FountainSimController.getInstance().getMod1candle1().setStroke(color);
                    FountainSimController.getInstance().getMod1candle2().setStroke(color);
                    FountainSimController.getInstance().getMod1candle3().setStroke(color);
                    FountainSimController.getInstance().getMod1candle4().setStroke(color);
                    FountainSimController.getInstance().getMod1candle5().setStroke(color);
                    FountainSimController.getInstance().getMod1candle6().setStroke(color);
                    FountainSimController.getInstance().getMod1sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod1sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka2().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod3ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod3ring1().setFill(color);
                    FountainSimController.getInstance().getMod3ring2().setFill(color);
                    FountainSimController.getInstance().getMod3ring3().setFill(color);
                    FountainSimController.getInstance().getMod3ring4().setFill(color);
                    FountainSimController.getInstance().getMod3ring5().setFill(color);
                    FountainSimController.getInstance().getMod3candle1().setStroke(color);
                    FountainSimController.getInstance().getMod3candle2().setStroke(color);
                    FountainSimController.getInstance().getMod3candle3().setStroke(color);
                    FountainSimController.getInstance().getMod3candle4().setStroke(color);
                    FountainSimController.getInstance().getMod3candle5().setStroke(color);
                    FountainSimController.getInstance().getMod3candle6().setStroke(color);
                    FountainSimController.getInstance().getMod3sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod3sweep2().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod5ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod5ring1().setFill(color);
                    FountainSimController.getInstance().getMod5ring2().setFill(color);
                    FountainSimController.getInstance().getMod5ring3().setFill(color);
                    FountainSimController.getInstance().getMod5ring4().setFill(color);
                    FountainSimController.getInstance().getMod5ring5().setFill(color);
                    FountainSimController.getInstance().getMod5candle1().setStroke(color);
                    FountainSimController.getInstance().getMod5candle2().setStroke(color);
                    FountainSimController.getInstance().getMod5candle3().setStroke(color);
                    FountainSimController.getInstance().getMod5candle4().setStroke(color);
                    FountainSimController.getInstance().getMod5candle5().setStroke(color);
                    FountainSimController.getInstance().getMod5candle6().setStroke(color);
                    FountainSimController.getInstance().getMod5sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod5sweep2().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod7ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod7ring1().setFill(color);
                    FountainSimController.getInstance().getMod7ring2().setFill(color);
                    FountainSimController.getInstance().getMod7ring3().setFill(color);
                    FountainSimController.getInstance().getMod7ring4().setFill(color);
                    FountainSimController.getInstance().getMod7ring5().setFill(color);
                    FountainSimController.getInstance().getMod7candle1().setStroke(color);
                    FountainSimController.getInstance().getMod7candle2().setStroke(color);
                    FountainSimController.getInstance().getMod7candle3().setStroke(color);
                    FountainSimController.getInstance().getMod7candle4().setStroke(color);
                    FountainSimController.getInstance().getMod7candle5().setStroke(color);
                    FountainSimController.getInstance().getMod7candle6().setStroke(color);
                    FountainSimController.getInstance().getMod7sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod7sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka1().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod2ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod2ring1().setFill(color);
                    FountainSimController.getInstance().getMod2ring2().setFill(color);
                    FountainSimController.getInstance().getMod2ring3().setFill(color);
                    FountainSimController.getInstance().getMod2ring4().setFill(color);
                    FountainSimController.getInstance().getMod2ring5().setFill(color);
                    FountainSimController.getInstance().getMod2candle1().setStroke(color);
                    FountainSimController.getInstance().getMod2candle2().setStroke(color);
                    FountainSimController.getInstance().getMod2candle3().setStroke(color);
                    FountainSimController.getInstance().getMod2candle4().setStroke(color);
                    FountainSimController.getInstance().getMod2candle5().setStroke(color);
                    FountainSimController.getInstance().getMod2candle6().setStroke(color);
                    FountainSimController.getInstance().getMod2sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod2sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka4().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getMod4ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod4ring1().setFill(color);
                    FountainSimController.getInstance().getMod4ring2().setFill(color);
                    FountainSimController.getInstance().getMod4ring3().setFill(color);
                    FountainSimController.getInstance().getMod4ring4().setFill(color);
                    FountainSimController.getInstance().getMod4ring5().setFill(color);
                    FountainSimController.getInstance().getMod4candle1().setStroke(color);
                    FountainSimController.getInstance().getMod4candle2().setStroke(color);
                    FountainSimController.getInstance().getMod4candle3().setStroke(color);
                    FountainSimController.getInstance().getMod4candle4().setStroke(color);
                    FountainSimController.getInstance().getMod4candle5().setStroke(color);
                    FountainSimController.getInstance().getMod4candle6().setStroke(color);
                    FountainSimController.getInstance().getMod4sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod4sweep2().setStroke(color);
                    FountainSimController.getInstance().getSpoutRec().setFill(color);
            	}
            	if (FountainSimController.getInstance().getMod6ring1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getMod6ring1().setFill(color);
                    FountainSimController.getInstance().getMod6ring2().setFill(color);
                    FountainSimController.getInstance().getMod6ring3().setFill(color);
                    FountainSimController.getInstance().getMod6ring4().setFill(color);
                    FountainSimController.getInstance().getMod6ring5().setFill(color);
                    FountainSimController.getInstance().getMod6candle1().setStroke(color);
                    FountainSimController.getInstance().getMod6candle2().setStroke(color);
                    FountainSimController.getInstance().getMod6candle3().setStroke(color);
                    FountainSimController.getInstance().getMod6candle4().setStroke(color);
                    FountainSimController.getInstance().getMod6candle5().setStroke(color);
                    FountainSimController.getInstance().getMod6candle6().setStroke(color);
                    FountainSimController.getInstance().getMod6sweep1().setStroke(color);
                    FountainSimController.getInstance().getMod6sweep2().setStroke(color);
                    FountainSimController.getInstance().getBazooka3().setStroke(color);
            	}
            	if (FountainSimController.getInstance().getPeacock1().getStroke().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getPeacock1().setStroke(color);
                    FountainSimController.getInstance().getPeacock2().setStroke(color);
                    FountainSimController.getInstance().getPeacock3().setStroke(color);
                    FountainSimController.getInstance().getPeacock4().setStroke(color);
                    FountainSimController.getInstance().getPeacock5().setStroke(color);
                    FountainSimController.getInstance().getPeacock6().setStroke(color);
                    FountainSimController.getInstance().getPeacock7().setStroke(color);
                    FountainSimController.getInstance().getPeacock8().setStroke(color);
                    FountainSimController.getInstance().getPeacock9().setStroke(color);
            	}
            	//if (FountainSimController.getInstance().getFrontCurtain1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
                   
            	//}
            	if (FountainSimController.getInstance().getBackCurtain1().getFill().equals(Color.LIGHTGRAY)&& !color.equals(Color.LIGHTGRAY)){
            		FountainSimController.getInstance().getBackCurtain1().setFill(color);
            		FountainSimController.getInstance().getBackCurtain2().setFill(color);
                    FountainSimController.getInstance().getBackCurtain3().setFill(color);
                    FountainSimController.getInstance().getBackCurtain4().setFill(color);
                    FountainSimController.getInstance().getBackCurtain5().setFill(color);
                    FountainSimController.getInstance().getBackCurtain6().setFill(color);
                    FountainSimController.getInstance().getBackCurtain9().setFill(color);
                    FountainSimController.getInstance().getBackCurtain10().setFill(color);
                    FountainSimController.getInstance().getBackCurtain11().setFill(color);
                    FountainSimController.getInstance().getBackCurtain12().setFill(color);
                    FountainSimController.getInstance().getBackCurtain13().setFill(color);
                    FountainSimController.getInstance().getBackCurtain14().setFill(color);
                    
                    FountainSimController.getInstance().getBackCurtain1().setStroke(color);
            		FountainSimController.getInstance().getBackCurtain2().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain3().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain4().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain5().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain6().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain9().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain10().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain11().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain12().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain13().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain14().setStroke(color);
                    
                    FountainSimController.getInstance().getBackCurtain7().setFill(color);
                    FountainSimController.getInstance().getBackCurtain8().setFill(color);
                    FountainSimController.getInstance().getBackCurtain7().setStroke(color);
                    FountainSimController.getInstance().getBackCurtain8().setStroke(color);
            	}
            	 FountainSimController.getInstance().getFrontCurtain1().setFill(color);
         		 FountainSimController.getInstance().getFrontCurtain2().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain3().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain4().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain5().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain6().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain7().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain8().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain9().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain10().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain11().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain12().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain13().setFill(color);
                 FountainSimController.getInstance().getFrontCurtain14().setFill(color);
                 
                 FountainSimController.getInstance().getFrontCurtain1().setStroke(color);
         		 FountainSimController.getInstance().getFrontCurtain2().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain3().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain4().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain5().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain6().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain7().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain8().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain9().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain10().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain11().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain12().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain13().setStroke(color);
                 FountainSimController.getInstance().getFrontCurtain14().setStroke(color);
            }
            if (channel == 160){
            	FountainSimController.getInstance().getFrontCurtain2().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain4().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain6().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain8().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain10().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain12().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain14().setFill(color);
            	
            	FountainSimController.getInstance().getFrontCurtain2().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain4().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain6().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain8().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain10().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain12().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain14().setStroke(color);
            }
            if (channel == 170){
            	FountainSimController.getInstance().getFrontCurtain1().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain3().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain5().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain7().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain9().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain11().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain13().setFill(color);

            	FountainSimController.getInstance().getFrontCurtain1().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain3().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain5().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain7().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain9().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain11().setStroke(color);
            	FountainSimController.getInstance().getFrontCurtain13().setStroke(color);
            }
            if (channel == 161){
            	FountainSimController.getInstance().getFrontCurtain14().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain14().setStroke(color);
            }
            if (channel == 162){
            	FountainSimController.getInstance().getFrontCurtain12().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain12().setStroke(color);
            }
            if (channel == 163){
            	FountainSimController.getInstance().getFrontCurtain10().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain10().setStroke(color);
            }
            if (channel == 164 || channel == 214){
            	FountainSimController.getInstance().getFrontCurtain8().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain8().setStroke(color);
            }
            if (channel == 165){
            	FountainSimController.getInstance().getFrontCurtain6().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain6().setStroke(color);
            }
            if (channel == 166){
            	FountainSimController.getInstance().getFrontCurtain4().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain4().setStroke(color);
            }
            if (channel == 167){
            	FountainSimController.getInstance().getFrontCurtain2().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain2().setStroke(color);
            }
            if (channel == 171){
            	FountainSimController.getInstance().getFrontCurtain13().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain13().setStroke(color);
            }
            if (channel == 172){
            	FountainSimController.getInstance().getFrontCurtain11().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain11().setStroke(color);
            }
            if (channel == 173){
            	FountainSimController.getInstance().getFrontCurtain9().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain9().setStroke(color);
            }
            if (channel == 174 || channel == 224){
            	FountainSimController.getInstance().getFrontCurtain7().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain7().setStroke(color);
            }
            if (channel == 175){
            	FountainSimController.getInstance().getFrontCurtain5().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain5().setStroke(color);
            }
            if (channel == 176){
            	FountainSimController.getInstance().getFrontCurtain3().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain3().setStroke(color);
            }
            if (channel == 177){
            	FountainSimController.getInstance().getFrontCurtain1().setFill(color);
            	FountainSimController.getInstance().getFrontCurtain1().setStroke(color);
            }
            if (channel == 210){
        		FountainSimController.getInstance().getBackCurtain2().setFill(color);
                FountainSimController.getInstance().getBackCurtain4().setFill(color);
                FountainSimController.getInstance().getBackCurtain6().setFill(color);
                FountainSimController.getInstance().getBackCurtain10().setFill(color);
                FountainSimController.getInstance().getBackCurtain12().setFill(color);
                FountainSimController.getInstance().getBackCurtain14().setFill(color);
                
        		FountainSimController.getInstance().getBackCurtain2().setStroke(color);
                FountainSimController.getInstance().getBackCurtain4().setStroke(color);
                FountainSimController.getInstance().getBackCurtain6().setStroke(color);
                FountainSimController.getInstance().getBackCurtain10().setStroke(color);
                FountainSimController.getInstance().getBackCurtain12().setStroke(color);
                FountainSimController.getInstance().getBackCurtain14().setStroke(color);
            }
            if (channel == 220){
            	FountainSimController.getInstance().getBackCurtain1().setFill(color);
                FountainSimController.getInstance().getBackCurtain3().setFill(color);
                FountainSimController.getInstance().getBackCurtain5().setFill(color);
                FountainSimController.getInstance().getBackCurtain9().setFill(color);
                FountainSimController.getInstance().getBackCurtain11().setFill(color);
                FountainSimController.getInstance().getBackCurtain13().setFill(color);
                
                FountainSimController.getInstance().getBackCurtain1().setStroke(color);
                FountainSimController.getInstance().getBackCurtain3().setStroke(color);
                FountainSimController.getInstance().getBackCurtain5().setStroke(color);
                FountainSimController.getInstance().getBackCurtain9().setStroke(color);
                FountainSimController.getInstance().getBackCurtain11().setStroke(color);
                FountainSimController.getInstance().getBackCurtain13().setStroke(color);
            }
            if (channel == 211){
                FountainSimController.getInstance().getBackCurtain14().setFill(color);
                FountainSimController.getInstance().getBackCurtain14().setStroke(color);
            }
            if (channel == 212){
                FountainSimController.getInstance().getBackCurtain12().setFill(color);
                FountainSimController.getInstance().getBackCurtain12().setStroke(color);
            }
            if (channel == 213){
                FountainSimController.getInstance().getBackCurtain10().setFill(color);
                FountainSimController.getInstance().getBackCurtain10().setStroke(color);
            }
            if (channel == 215){
                FountainSimController.getInstance().getBackCurtain6().setFill(color);
                FountainSimController.getInstance().getBackCurtain6().setStroke(color);
            }
            if (channel == 216){
                FountainSimController.getInstance().getBackCurtain4().setFill(color);
                FountainSimController.getInstance().getBackCurtain4().setStroke(color);
            }
            if (channel == 217){
                FountainSimController.getInstance().getBackCurtain2().setFill(color);
                FountainSimController.getInstance().getBackCurtain2().setStroke(color);
            }
            if (channel == 221){
                FountainSimController.getInstance().getBackCurtain13().setFill(color);
                FountainSimController.getInstance().getBackCurtain13().setStroke(color);
            }
            if (channel == 222){
                FountainSimController.getInstance().getBackCurtain11().setFill(color);
                FountainSimController.getInstance().getBackCurtain11().setStroke(color);
            }
            if (channel == 223){
                FountainSimController.getInstance().getBackCurtain9().setFill(color);
                FountainSimController.getInstance().getBackCurtain9().setStroke(color);
            }
            if (channel == 225){
                FountainSimController.getInstance().getBackCurtain5().setFill(color);
                FountainSimController.getInstance().getBackCurtain5().setStroke(color);
            }
            if (channel == 226){
                FountainSimController.getInstance().getBackCurtain3().setFill(color);
                FountainSimController.getInstance().getBackCurtain3().setStroke(color);
            }
            if (channel == 227){
                FountainSimController.getInstance().getBackCurtain1().setFill(color);
                FountainSimController.getInstance().getBackCurtain1().setStroke(color);
            }
    	}
    }
    
    /**
     * @param timeline the timeline to set
     */
    public void setTimeline(ConcurrentSkipListMap<Integer, ArrayList<FCW>> timeline) {
       this.timeline.setTimeline(timeline);
    }

    private int lightRowLookupNumber(int channel){
    	for(int i = 0; i < channelAddresses.length; i++) {
    		if(channelAddresses[i] == channel) {
    			return i;
    		}
    	}
    	throw new IllegalArgumentException("Channel doesn't exist " + channel);
    //return 0;
    }
    
    private String waterTLWords(String badName){
    	String newName = "";
    	
    	switch(badName){
    	//From water address table
    	case "RING5":  newName = "Ring 5";
    	break;
    	case "RING4":  newName = "Ring 4";
		break;
    	case "RING3":  newName = "Ring 3";
		break;
    	case "RING2":  newName = "Ring 2";
		break;
    	case "RING1":  newName = "Ring 1";
		break;
    	case "SWEEP":  newName = "Sweeps";
    	break;
    	case "SPOUT":  newName = "Spout"; // TODO there are duplicate SPOUT in the FCW_DEF file one in the table A and one in WaterAddresses
    	break;
    	case "BAZOOKA": newName = "Bazooka"; // TODO Same issue as SPOUT.
    	break;
    	case "CANDELABRA": newName = "Candelabra";
    	break;
    	case "FTCURT": newName = "Front Curtain";
    	break;
    	case "BKCURT": newName = "Back Curtain";
    	break;
    	case "PEACOCK": newName = "Peacock";
    	break;
    	case "SWEEPA": newName = "Sweep A";
    	break;
    	case "SWEEPB": newName = "Sweep B";
    	break;
    	case "SWEEPLIMITAB": newName = "Sweep Limit A + B";
    	break;
    	case "SWEEPLIMITA": newName = "Sweep Limit A";
    	break;
    	case "SWEEPLIMITB": newName = "Sweep Limit B";
    	break;
    	case "MULTI": newName = "Wedding Cake Formation";
    	break;
    	case "PULSE": newName = "Repeat Jump @ 0.5sec";
    	break;
    	// From functions table
    	case "VOICE": newName = "Auto Cateloged Formations (water + light)"; //TODO needed? 
    	break;
    	case "INTERCHANGEAB": newName = "Interchange A+B module formations (water + light)"; //TODO needed?
    	break;
    	case "OFF": newName = "Off"; //TODO needed?
    	break;
    	case "MODULEA": newName = "Module A";
    	break;
    	case "MODULEB": newName = "Module B";
    	break;
    	case "CONNECTAB": newName = "Connect A + B";
    	break;
    	case "OFFRESET": newName = "Hold Center";
    	break;
    	case "SHORT": newName = "Short";
    	break;
    	case "LONG": newName = "Long";
    	break;
    	case "LARGO": newName = "Largo";
    	break;
    	case "ADAGIO": newName = "Slow Speed";
    	break;
    	case "ANDANTE": newName = "Andante";
    	break;
    	case "MODERATO": newName = "Medium Speed";
    	break;
    	case "ALLEGRETO": newName = "Allegreto";
    	break;
    	case "ALLEGRO": newName = "Fast Speed";
    	break;
    	case "PRESTO": newName = "Presto";
    	break;
    	case "PLAYPAUSE": newName = "Play/Pause Sweep";
    	break;
    	case "STOP": newName = "Stop Jumping";
    	break;
    	case "ADDRSWEEP": newName = "Sweep Formation";
    	break;
    	case "JUMPA": newName = "Jump A";
    	break;
    	case "JUMPB": newName = "Jump B";
    	break;
    	case "JUMP0OR1": newName = "Jump Phase";
    	break;
    	case "HOLDRIGHTOT": newName = "ROt";
    	break;
    	case "RIGHTOTRIGHTLONG": newName = "ROT RL";
    	break;
    	case "RIGHTOTRIGHTSHORT": newName = "ROT RS";
    	break;
    	case "RIGHTOTCENTER": newName = "ROT C";
    	break;
    	case "RIGHTOTLEFTSHORT": newName = "ROT LS";
    	break;
    	case "RIGHTOTLEFTLONG": newName = "ROT LL";
    	break;
    	case "RIGHTOTLEFTOT": newName = "ROT LOT";
    	break;
    	case "HOLDRIGHTLONG": newName = "RL";
    	break;
    	case "RIGHTLONGRIGHTSHORT": newName = "RS RL";
    	break;
    	case "RIGHTLONGCENTER": newName = "C RL";
    	break;
    	case "RIGHTLONGLEFTSHORT": newName = "LS RL";
    	break;
    	case "RIGHTLONGLEFTLONG": newName = "LL RL";
    	break;
    	case "RIGHTLONGLEFTOT": newName = "LOT RL";
    	break;
    	case "HOLDRIGHTSHORT": newName = "RS";
    	break;
    	case "RIGHTSHORTCENTER": newName = "C RS";
    	break;
    	case "RIGHTSHORTLEFTSHORT": newName = "LS RS";
    	break;
    	case "RIGHTSHORTLEFTLONG": newName = "LL RS";
    	break;
    	case "RIGHTSHORTLEFTOT": newName = "LOT RS";
    	break;
    	case "HOLDCENTER": newName = "C";
    	break;
    	case "CENTERLEFTSHORT": newName = "LS C";
    	break;
    	case "CENTERLEFTLONG": newName = "LL C";
    	break;
    	case "CENTERLEFTOT": newName = "LOT C";
    	break;
    	case "HOLDLEFTSHORT": newName = "LS";
    	break;
    	case "LEFTSHORTLEFTLONG": newName = "LL LS";
    	break;
    	case "LEFTSHORTLEFTOT": newName = "LOT LS";
    	break;
    	case "HOLDLEFTLONG": newName = "LL";
    	break;
    	case "LEFTLONGLEFTOT": newName = "LOT LL";
    	break;
    	case "HOLDLEFTOT": newName = "LOT";
    	break;
    	case "SWEEPSPECIAL": newName = "Special";
    	break;
    	default: newName = badName;
    	
    	}
    	return newName;
		
    }

    public void fireSliderChangeEvent() {
        timeline.sendTimelineInstanceToSliders(MusicPaneController.getInstance().getTenthsTime());
    }
    
    public void fireSimChangeEvent() {
    	if (MusicPaneController.getInstance().getTenthsTime() == 0){
        timeline.sendTimelineInstanceToSim(MusicPaneController.getInstance().getTenthsTime());
    	}
    	else {
            timeline.sendTimelineInstanceToSim(MusicPaneController.getInstance().getTenthsTime()+10);
    	}
    }
    
    public void fireSimClearEvent() {
        FountainSimController.getInstance().clearSim();
    }

    public String[] getLabelNames() {
            return labelNames;
    }

    public void setLabelNames(String[] labelNames) {
            this.labelNames = labelNames;
    }

    public void injectIntoGtfo(Integer[] newAddresses) {
        SortedMap<Integer, SortedMap<Integer, Integer>> gtfo = timeline.getChannelColorMap();
        for(Integer i: newAddresses) {
            gtfo.putIfAbsent(i, new ConcurrentSkipListMap<>());
        }
    }

    public void fireSubmapToSim() {
        timeline.sendSubmapToSim(MusicPaneController.getInstance().getTenthsTime());
    }

    public void disposeTimeline() {
        lightRecArray = null;
        waterRecArray = null;
        gridpaneLight.getChildren().clear();
        gridpaneWater.getChildren().clear();
        initializeTimelines();
        timeline.getChannelColorMap().clear();
    }
    public Timeline getTimeline() {
        return timeline;
    }
    
    public void killTimelines() {
    }
    
    public boolean checkForCollisions(Integer query) {
        for(Integer channel: channelAddresses) {
            if(Objects.equals(query, channel)) {
                return true;
            }
        }
        return false;
    }
}
