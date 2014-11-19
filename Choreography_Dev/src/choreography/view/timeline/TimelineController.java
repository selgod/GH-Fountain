package choreography.view.timeline;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javafx.util.Duration;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import choreography.io.FCWLib;
import choreography.model.color.ColorPaletteEnum;
import choreography.model.color.ColorPaletteModel;
import choreography.model.fcw.FCW;
import choreography.model.timeline.Timeline;

import choreography.view.ChoreographyController;
import choreography.view.music.MusicPaneController;
import choreography.view.sim.FountainSimController;
import choreography.view.sliders.SlidersController;

/**
 * FXML Controller class
 */
public class TimelineController implements Initializable {

	/*
	 * Singleton used.
	 */
	private static TimelineController instance;

	public static TimelineController getInstance() {
		if (instance == null)
			instance = new TimelineController();
		return instance;
	}

	private Integer[] channelAddresses;
	boolean oldRecHasValue = false;
	Rectangle oldRec = new Rectangle();
	Rectangle copyWaterRec = new Rectangle();
	int copyWaterLocation = 0;
	private String[] labelNames;
	Rectangle selectedRec = new Rectangle();
	int selectedTimeIndex = 0;
	int selectedLabelIndex = 0;
	private int start;
	Tooltip t;
	private Timeline timeline;

	@FXML
	private GridPane timelineLabelPane;
	@FXML
	private ScrollPane timelineScrollPane, labelScrollPane;
	// NonFXML
	private int time;
	int startRow = 0;
	int rowNumber;
	GridPane gridpaneLight;
	GridPane gridpaneWater;
	Rectangle[] waterRecArray;
	Rectangle[][] lightRecArray;
	Rectangle[][] copyArray;
	int pasteTimeIndex;
	int pasteLabelIndex;
	final ArrayList<Rectangle> copyAL;
	final ArrayList<Integer> colAL;
	final ArrayList<Integer> rowAL;
	Rectangle startRectangle;
	int startTimeIndex;
	int startLabelIndex;
	Rectangle endRectangle;
	int endTimeIndex;
	int endLabelIndex;

	MenuItem lightCut = new MenuItem("Cut");
	MenuItem lightCopy = new MenuItem("Copy");
	MenuItem lightPaste = new MenuItem("Paste");
	MenuItem lightSelect = new MenuItem("Select");
	Menu fadeUp = new Menu("Fade Up");
	Menu fadeDown = new Menu("Fade Down");
	MenuItem fadeUpMenuItem = new MenuItem();
	MenuItem fadeDownMenuItem = new MenuItem();
	MenuItem waterCut = new MenuItem("Cut");
	MenuItem waterCopy = new MenuItem("Copy");
	MenuItem waterPaste = new MenuItem("Paste");
	MenuItem waterSelect = new MenuItem("Select");

	ObservableList<String> options1 = FXCollections.observableArrayList(".10",
			".20", ".30", ".40", ".50", ".60", ".70", ".80", ".90", "1.0");
	ObservableList<String> options2 = FXCollections.observableArrayList(".90",
			".80", ".70", ".60", ".50", ".40", ".30", ".20", ".10", "0.0");
	ComboBox<String> fadeUpComboBox = new ComboBox<String>(options1);
	ComboBox<String> fadeDownComboBox = new ComboBox<String>(options2);

	final ContextMenu lightCM = new ContextMenu();
	final ContextMenu waterCM = new ContextMenu();

	public TimelineController() {
		timeline = new Timeline();
		ColorPaletteEnum.values();
		this.rowAL = new ArrayList<Integer>();
		this.colAL = new ArrayList<Integer>();
		this.copyAL = new ArrayList<Rectangle>();
		this.channelAddresses = new Integer[1];
	}

	public ArrayList<Integer> getColAL() {
		return this.colAL;
	}

	public ArrayList<Integer> getRowAL() {
		return this.rowAL;
	}

	public void setLightRecArrayFade(int row, int col, double opacity) {
		lightRecArray[col][row].setOpacity(opacity);
	}

	/**
	 * Sets the UI color at the given time index and channel index
	 */
	public void setLightRecArrayStrobe(int channelIndex, int timeIndex,
			Paint color) {
		lightRecArray[timeIndex][channelIndex].setFill(color);
	}

	/**
	 * Just fills in the lightRecArray with gray, it doesn't actually delete
	 * from the timeline. Should this ever be used?
	 */
	public void delete(int col, int row) {
		lightRecArray[col][row].setFill(Color.LIGHTGRAY);
	}

	/**
	 * Deletes all fcw at the given point and for "length" afterwards on the
	 * given channelIndex
	 * 
	 * todo Only operates if first==true, why? this should push to the timeline,
	 * then pull from timeline when repainting
	 */
	public void delete(int timeIndex, int channelIndex, int length,
			boolean first) {
		if (first) {
			lightRecArray[timeIndex][channelIndex].setFill(Color.LIGHTGRAY);
			if (timeline.getChannelColorMap().containsKey(channelIndex)) {
				// tgetChannelColorMaptGtfoMap().get(row).remove(row,
				// value);//TODO ask frank about this
			}
			FCW off = new FCW(channelAddresses[channelIndex], 0);
			lightRecArray[timeIndex][channelIndex].setFill(Color.LIGHTGRAY);
			timeline.setLightFcw(off, timeIndex, timeIndex + length);// not sure
			// if
			// length
			// is
			// needed?
		}

	}

	public void delete(int waterCol) {
		if (waterCol != 0) {
			timeline.deleteActionAtTime(waterCol - 1);
			waterRecArray[waterCol - 1].setFill(Color.LIGHTGRAY);
			Tooltip.uninstall(waterRecArray[waterCol - 1], t);
		} else {
			timeline.deleteActionAtTime(waterCol);
			waterRecArray[waterCol].setFill(Color.LIGHTGRAY);
			Tooltip.uninstall(waterRecArray[waterCol], t);
		}
		SlidersController.getInstance().resetAllSliders();
	}

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// setWaterGridPane();
		instance = this;
		initializeTimelines();

		fadeUpMenuItem.setGraphic(fadeUpComboBox);
		fadeDownMenuItem.setGraphic(fadeDownComboBox);
		fadeUp.getItems().add(fadeUpMenuItem);
		fadeDown.getItems().add(fadeDownMenuItem);
		lightCM.getItems().add(lightCut);
		lightCM.getItems().add(lightCopy);
		lightCM.getItems().add(lightPaste);
		lightCM.getItems().add(lightSelect);
		lightCM.getItems().add(fadeUp);
		lightCM.getItems().add(fadeDown);

		lightCut.setDisable(true);
		lightCopy.setDisable(true);
		lightPaste.setDisable(true);
		fadeUp.setDisable(true);
		fadeDown.setDisable(true);

		lightCut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				copyArray = new Rectangle[(endTimeIndex - startTimeIndex) + 1][(endLabelIndex - startLabelIndex) + 1];
				for (int i = 0; i < copyArray.length; i++) {
					for (int j = 0; j < copyArray[i].length; j++) {
						copyArray[i][j] = new Rectangle();
						copyArray[i][j].setFill(lightRecArray[startTimeIndex
						                                      + i][startLabelIndex + j].getFill());
						lightRecArray[startTimeIndex + i][startLabelIndex + j]
								.setFill(Color.LIGHTGRAY); // TODO
						// use
						// delete()
						// here
						// instead
						lightRecArray[startTimeIndex + i][startLabelIndex + j]
								.setOpacity(1);
					}
				}
				lightCM.hide();
				lightPaste.setDisable(false);
			}
		});

		lightCopy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				copyArray = new Rectangle[(endTimeIndex - startTimeIndex) + 1][(endLabelIndex - startLabelIndex) + 1];
				for (int i = 0; i < copyArray.length; i++) {
					for (int j = 0; j < copyArray[i].length; j++) {
						copyArray[i][j] = lightRecArray[startTimeIndex + i][startLabelIndex
						                                                    + j];
					}
				}
				lightCM.hide();
				lightPaste.setDisable(false);
			}
		});

		lightPaste.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO does not check for Timeline bounds
				for (int i = 0; i < copyArray.length; i++) {
					for (int j = 0; j < copyArray[i].length; j++) {
						lightRecArray[pasteTimeIndex + i][pasteLabelIndex + j]
								.setFill(copyArray[i][j].getFill());
					}
				}
				TimelineController.getInstance().rePaintLightTimeline();
				lightCM.hide();
			}
		});

		fadeUpComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				double interval = Double.parseDouble((String) fadeUpComboBox
						.getValue()) / ((endTimeIndex - startTimeIndex) + 1);
				for (int i = startTimeIndex; i <= endTimeIndex; i++) {
					for (int j = startLabelIndex; j <= endLabelIndex; j++) {
						lightRecArray[i][j].setOpacity(interval
								* ((i - startTimeIndex) + 1));
					}
				}
			}
		});

		fadeDownComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				double interval = (1 - Double
						.parseDouble((String) fadeDownComboBox.getValue()))
						/ ((endTimeIndex - startTimeIndex) + 1);
				for (int i = startTimeIndex; i <= endTimeIndex; i++) {
					for (int j = startLabelIndex; j <= endLabelIndex; j++) {
						lightRecArray[i][j]
								.setOpacity(1 - (interval * (i - startTimeIndex)));
					}
				}
			}
		});
	}

	public void initializeTimelines() {
		setLabelNames(new String[] { "Module1", "Module2", "Module3",
				"Module4", "Module5", "Module6", "Module7", "PeacockAB",
				"FrontCurtain", "BackCurtain" });
		setLabelGridPane(getLabelNames());
		setTimelineGridPane();
		// ChoreographyController.getInstance().setBeatMarkGridPane();
	}

	public void disableCopyPaste() {
		lightCopy.setDisable(true);
		lightPaste.setDisable(true);
	}

	public Integer[] getSpecialChannels() {
		/*
		 * TODO yay magic numbers :(
		 */
		// all of the strobe, fades, and the initial 10
		Integer[] specialChannels = new Integer[] {
				// 17, 18, 19, 20, 21, 22, 23, 24, 27, 200,
				16, 101, 102, 103, 106, 107, 108, 111, 112, 113, 116, 117, 118,
				121, 122, 123, 126, 127, 128, 131, 132, 133, 136, 137, 138,
				141, 142, 143, 191, 192, 193, 201, 202, 203, 206, 207, 208,
				231, 232, 233, 251, 252, 253 };

		return specialChannels;

	}

	public void setLabelGridPane(String[] labelNames) {
		setLabelNames(labelNames);
		Set<Integer> addresses = new HashSet<Integer>();
		for (String in : labelNames) {
			Integer addr = FCWLib.getInstance().lookupAddress(in);
			addresses.add(addr);
		}
		setChannelAddresses(addresses);
		injectIntoGtfo(channelAddresses);
		setLabelGridPane(channelAddresses);
		rePaintLightTimeline();
	}

	/**
	 * Sets channelAddresses and labelNames to match what is in
	 * timeline.getChannelColormap().keySet()
	 */
	public void setLabelGridPaneWithCtl() {
		Set<Integer> channelAddressesSet = timeline.getChannelColorMap()
				.keySet();
		setChannelAddresses(channelAddressesSet);
		labelNames = new String[channelAddresses.length];
		for (int channelIndex = 0; channelIndex < channelAddresses.length; channelIndex++) {
			labelNames[channelIndex] = FCWLib.getInstance()
					.reverseLookupAddress(channelAddresses[channelIndex]);
		}
		setLabelGridPane(channelAddresses);
		setTimelineGridPane();
		rePaintLightTimeline();
	}

	/**
	 * Sets channelAddresses based on input parameter
	 */
	public void setChannelAddresses(Set<Integer> channelAddressesSet) {

		// TODO is okChannels even used for useful things?
		ArrayList<Integer> okChannels = new ArrayList<>();
		for (Integer query : channelAddressesSet) {
			if (!checkForCollisions(query)) {
				okChannels.add(query);
			}
		}
		channelAddresses = channelAddressesSet.toArray(new Integer[1]);
	}

	/**
	 * Adds constraints and words to the labels on the side of the light grid
	 * timeline. Also adds listeners to the labelScrollPane and
	 * TimelineScrollPane
	 */
	public void setLabelGridPane(Integer[] addresses) {
		timelineLabelPane = new GridPane();
		timelineLabelPane.setGridLinesVisible(true);
		Label[] labelArray = new Label[addresses.length];

		// 26 is also used in the setTimelineGridPane function as the size of
		// square
		// todo take care of magic numbers
		int LABEL_HEIGHT = 26;
		int LABEL_WIDTH = 98;

		// set up the width and height of the labels
		timelineLabelPane.getColumnConstraints().add(
				new ColumnConstraints(LABEL_WIDTH));
		for (int addressIndex = 0; addressIndex < addresses.length; addressIndex++) {
			timelineLabelPane.getRowConstraints().add(
					new RowConstraints(LABEL_HEIGHT));
		}

		// add the names of the labels
		for (int addressIndex = 0; addressIndex < addresses.length; addressIndex++) {
			String nameOfAddress = FCWLib.getInstance().reverseLookupAddress(
					(addresses[addressIndex]));
			labelArray[addressIndex] = new Label(nameOfAddress);
			timelineLabelPane.add(labelArray[addressIndex], 0, addressIndex);
		}

		timelineScrollPane.vvalueProperty().addListener(
				new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<?> o, Object oldVal,
							Object newVal) {
						labelScrollPane.setVvalue(timelineScrollPane
								.getVvalue());
					}
				});
		labelScrollPane.vvalueProperty().addListener(
				new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<?> o, Object oldVal,
							Object newVal) {
						timelineScrollPane.setVvalue(labelScrollPane
								.getVvalue());
					}
				});
		labelScrollPane.setContent(timelineLabelPane);
	}

	/**
	 * Sets up the light grid timeline(size constraints and mouse handlers).
	 */
	public void setTimelineGridPane() {
		gridpaneLight = new GridPane();
		time = MusicPaneController.SONG_TIME;
		gridpaneLight.setGridLinesVisible(true);
		rowNumber = labelNames.length;
		lightRecArray = new Rectangle[time][labelNames.length];

		// add constraints to the light array
		int SIZE_OF_SQUARE = 26; // yay magic number :(

		// set the width of the light timeline squares
		for (int timeCounter = 0; timeCounter < time; timeCounter++) {
			gridpaneLight.getColumnConstraints().add(
					new ColumnConstraints(SIZE_OF_SQUARE));
		}

		// set the height of the light timeline squares
		for (int labelCounter = 0; labelCounter < labelNames.length; labelCounter++) {
			gridpaneLight.getRowConstraints().add(
					new RowConstraints(SIZE_OF_SQUARE));
		}

		// adds mouse handlers to all of the squares on the light timeline grid
		for (int timeIndex = 0; timeIndex < time; timeIndex++) {
			for (int labelIndex = 0; labelIndex < labelNames.length; labelIndex++) {
				lightRecArray[timeIndex][labelIndex] = new Rectangle(25, 25,Color.LIGHTGRAY);
				gridpaneLight.add(lightRecArray[timeIndex][labelIndex],timeIndex, labelIndex);
				
				//these are used to tell the position of the rectangle while in the handler
				final int timeIndexConst = timeIndex;
				final int labelIndexConst = labelIndex;

				lightRecArray[timeIndex][labelIndex].setOnMousePressed(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								selectedRec = lightRecArray[timeIndexConst][labelIndexConst];
								selectedTimeIndex = timeIndexConst;
								selectedLabelIndex = labelIndexConst;
								
								//if right clicked
								if (e.getButton() == MouseButton.SECONDARY) {
									lightCM.show(
											lightRecArray[timeIndexConst][labelIndexConst],
											e.getScreenX(), e.getScreenY());
									pasteTimeIndex = timeIndexConst;
									pasteLabelIndex = labelIndexConst;

								} 
								
								
								else if (ChoreographyController.getInstance().getShiftPressed()) {
									for (int i = startTimeIndex; i <= endTimeIndex; i++) {
										for (int j = startLabelIndex; j <= endLabelIndex; j++) {
											lightRecArray[i][j].setStroke(null);
										}
									}
									
									//disable menu items
									lightCut.setDisable(true);
									lightCopy.setDisable(true);
									fadeUp.setDisable(true);
									fadeDown.setDisable(true);
									lightRecArray[timeIndexConst][labelIndexConst]
											.setStroke(Color.BLACK);
									lightRecArray[timeIndexConst][labelIndexConst]
											.setStrokeWidth(2);

									startTimeIndex = timeIndexConst;
									startLabelIndex = labelIndexConst;
								} 
								
								else {
									for (int i = startTimeIndex; i <= endTimeIndex; i++) {
										for (int j = startLabelIndex; j <= endLabelIndex; j++) {
											lightRecArray[i][j].setStroke(null);
										}
									}
									lightCut.setDisable(true);
									lightCopy.setDisable(true);
									fadeUp.setDisable(true);
									fadeDown.setDisable(true);
									startRow = labelIndexConst;
									lightRecArray[timeIndexConst][labelIndexConst]
											.setFill(ColorPaletteModel
													.getInstance()
													.getSelectedColor());
									start = timeIndexConst;
								}
							}
						});

				//if we are dragging
				lightRecArray[timeIndex][labelIndex]
						.setOnDragDetected((MouseEvent e) -> {
							if (ChoreographyController.getInstance().getShiftPressed()) {
								lightRecArray[timeIndexConst][labelIndexConst]
										.setStroke(Color.BLACK);
								lightRecArray[timeIndexConst][labelIndexConst]
										.setStrokeWidth(2);
							}

							lightRecArray[timeIndexConst][labelIndexConst]
									.startFullDrag();
						});

				// continues and ends the drag event
				lightRecArray[timeIndex][labelIndex]
						.setOnMouseDragOver((MouseEvent e) -> {
							if (ChoreographyController.getInstance().getShiftPressed()) {
								if (timeIndexConst >= startTimeIndex
										&& labelIndexConst >= startLabelIndex) {
									if (timeIndexConst < endTimeIndex) {
										for (int i = 0; i <= endLabelIndex - startLabelIndex; i++) {
											lightRecArray[endTimeIndex][startLabelIndex + i]
													.setStroke(null);
										}
									}
									if (labelIndexConst < endLabelIndex) {
										for (int i = 0; i <= endTimeIndex - startTimeIndex; i++) {
											lightRecArray[startTimeIndex + i][endLabelIndex]
													.setStroke(null);
										}
									}
									endTimeIndex = timeIndexConst;
									endLabelIndex = labelIndexConst;
									for (int i = 0; i <= endTimeIndex - startTimeIndex; i++) {
										for (int j = 0; j <= endLabelIndex - startLabelIndex; j++) {
											lightRecArray[startTimeIndex + i][startLabelIndex + j]
													.setStroke(Color.BLACK);
											lightRecArray[startTimeIndex + i][startLabelIndex + j]
													.setStrokeWidth(2);
										}
									}
								}
							} else {
								if (labelIndexConst == startRow) {
									lightRecArray[timeIndexConst][labelIndexConst]
											.setFill(ColorPaletteModel.getInstance()
													.getSelectedColor());
								}
							}
						});

				lightRecArray[timeIndex][labelIndex]
						.setOnMouseDragReleased((MouseEvent e) -> {
							if (!ChoreographyController.getInstance()
									.getShiftPressed()) {
								if (startRow != labelIndexConst) {
									FCW f = new FCW(channelAddresses[startRow],
											ColorPaletteModel.getInstance()
											.getSelectedIndex());
									timeline.setLightFcw(f, start,
											timeIndexConst + 1);
									System.out.println(f + " " + start + " " + timeIndexConst + 1);
								} else {
									FCW f = new FCW(
											channelAddresses[labelIndexConst],
											ColorPaletteModel.getInstance()
											.getSelectedIndex());
									timeline.setLightFcw(f, start,timeIndexConst + 1);
								}
							} else {
								lightCut.setDisable(false);
								lightCopy.setDisable(false);
								fadeUp.setDisable(false);
								fadeDown.setDisable(false);
							}
						});
			}
		}

		timelineScrollPane.setContent(gridpaneLight);
	}

	public void clearAllAL() {
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

		time = MusicPaneController.SONG_TIME;

		gridpaneWater.setGridLinesVisible(true);

		waterRecArray = new Rectangle[time];
		for (int i = 0; i < time; i++) {
			final int testI = i;
			gridpaneWater.getColumnConstraints().add(new ColumnConstraints(26));
			
			// because the array is not square this needs to be here
			if (i < 1) { 
				gridpaneWater.getRowConstraints().add(new RowConstraints(26));
			}

			waterRecArray[i] = new Rectangle(25, 25, Color.LIGHTGREY);
			gridpaneWater.add(waterRecArray[i], i, 0);

			waterRecArray[i].setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {

					if (me.getButton() == MouseButton.SECONDARY) {
						// currentWaterRec = waterRecArray[testI];
						copyWaterLocation = testI;
						waterCopy.setDisable(false);
						waterCM.show(waterRecArray[testI], me.getScreenX(),
								me.getScreenY());
					}

					Duration duration = MusicPaneController.getInstance()
							.getMediaPlayer().getTotalDuration();
					MusicPaneController
					.getInstance()
					.getMediaPlayer()
					.seek(Duration.seconds((((double) testI + 1) / 10)));
				}
			});

			MusicPaneController.getInstance().getWaterPane()
			.setContent(gridpaneWater);
		}
	}

	/**
	 * 
	 * @return
	 */
	public ScrollPane getScrollPane() {
		return timelineScrollPane;
	}

	/* 
	 * Repaints the gui when calles
	 */
	public void rePaint() {
		rePaintWaterTimeline();
		rePaintLightTimeline();
	}

	/*
	 * Repaints the water timeline, called from rePaint()
	 */
	public void rePaintWaterTimeline() {
		SortedMap<Integer, ArrayList<FCW>> waterTimeline = timeline
				.getWaterTimeline();
		for (Integer i : waterTimeline.keySet()) {
			ArrayList<String> actionsList = new ArrayList<>();
			int tenthOfSec = i % 10;
			int secondsOnly = i / 10;
			double tenths = (double) tenthOfSec;
			double newTime = secondsOnly + (tenths / 10);
			int colAtTime = (int) (newTime * MusicPaneController.getInstance()
					.getTimeFactor());
			if (colAtTime != 0) {
				colAtTime = colAtTime - 1;
			}
			for (FCW f : waterTimeline.get(i)) {
				actionsList.add(f.getPrettyString());
			}
			waterRecArray[colAtTime].setFill(Color.AZURE);
			t = new Tooltip(actionsList.toString());
			t.setAutoHide(true);
			Tooltip.install(waterRecArray[colAtTime], t);
		}
	}

	/*
	 * Repaint light timeline (grid with colors)
	 * 
	 */
	public void rePaintLightTimeline() {
		SortedMap<Integer, SortedMap<Integer, Integer>> channelColorMap = timeline.getChannelColorMap();
		
		//paint one channel(row) at a time
		for (int channel : channelColorMap.keySet()) {
			
			//for each time
			for (int timeIndex : channelColorMap.get(channel).keySet()) {
				
				//gets the color index form the color palete, then gets the real color(paintColor)
				Integer colorIndex = timeline.getChannelColorMap().get(channel).get(timeIndex);
				Paint paintColor = ColorPaletteModel.getInstance().getColor(colorIndex);
				
				//default to light gray
				if (paintColor == null || paintColor.equals(Color.BLACK)) { 
					paintColor = Color.LIGHTGRAY; 
				}
				int row = lightRowLookupNumber(channel); 
				
				//paints the rectangle
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
