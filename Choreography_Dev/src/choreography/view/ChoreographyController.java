/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.view;



import choreography.Main;
import choreography.io.CtlLib;
import choreography.io.FCWLib;
import choreography.io.FilePayload;
import choreography.io.GhmfLibrary;
import choreography.io.LagTimeLibrary;
import choreography.io.MapLib;
import choreography.io.MarkLib;
import choreography.model.color.ColorPaletteModel;
import choreography.model.fcw.FCW;
import choreography.view.colorPalette.ColorPaletteController;
import choreography.view.lagtime.LagTimeGUIController;
import choreography.view.music.MusicPaneController;
import choreography.view.sim.FountainSimController;
import choreography.view.sliders.SlidersController;
import choreography.view.specialOperations.SpecialoperationsController;
import choreography.view.timeline.TimelineController;
import choreography.view.customChannel.CustomChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import com.sun.javafx.scene.control.skin.FXVK.Type;

/**
 * FXML Controller class
 *
 * @author elementsking
 */
public class ChoreographyController implements Initializable {
    
    public static final String WORKINGDIRECTORY = "";
    
    private static ChoreographyController cc;
    private ConcurrentSkipListMap<Integer, ArrayList<FCW>> events;
    GridPane gridpaneBeatMarks;
    private int time;
    Rectangle[] beatMarkRecArray;
    
    @FXML
    private VBox csGUI;
    @FXML
    private Label fcwOutput;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem newItemMenuItem;
    @FXML
    private MenuItem openMusicMenuItem;
    @FXML
    private MenuItem openCTLMenuItem;
    @FXML
    private Menu openRecentMenuItemItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem revertMenuItem;
    @FXML
    private MenuItem advancedCheckMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private Menu editMenu;
    @FXML
    private MenuItem addChannelsMenuItem;
    @FXML
    private MenuItem undoMenuItem;
    @FXML
    private MenuItem redoMenuItem;
    @FXML
    private MenuItem cutMenuItem;
    @FXML
    private MenuItem copyMenuItem;
    @FXML
    private MenuItem pasteMenuItem;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private MenuItem selectAllMenuItem;
    @FXML
    private MenuItem unselectAllMenuItem;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem setLagTimesMenuItem;
    @FXML
    private ToggleButton selectionButton;
    @FXML
    private Pane simPane;
    @FXML
    private ScrollPane beatMarkScrollPane;
    @FXML
    private MenuItem openGhmfMenuItem;
//    @FXML
//    private ProgressIndicator progressIndicator;
    
    private File saveLocation;
    private boolean isSaved;
    private boolean isAdvanced;
    private boolean isSelected = false;
	boolean isFirst = true;
    Timer timelineTimer = new Timer("progressTimer", true);
    Timer sliderTimer = new Timer("progressTimer", true);


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        progressIndicator = new ProgressIndicator();
    	
    	beatMarkScrollPane
        .setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                 
               if (ke.getCode() == KeyCode.SPACE){
            	   beatMarkRecArray[MusicPaneController.getInstance().getTenthsTime()].setFill(Color.BLACK);
                ke.consume();
               }
               
               
            }
            

        });
    	
    	openMusicMenuItem.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
//                progressIndicator.setProgress(-1);
                loadDefaultMap();
                MusicPaneController.getInstance().selectMusic();
//                progressIndicator.setProgress(1);
                TimelineController.getInstance().initializeTimelines();
                openCTLMenuItem.setDisable(false);
            }
    		
    	});
    	
    	selectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//				selectButton.isPressed();
                if (isSelected){
                    isSelected = false;
//					System.out.println("Off");
                    TimelineController.getInstance().clearAllAL();
                    TimelineController.getInstance().disableCopyPaste();
                }
                else {
                    isSelected = true;
//					System.out.println("On");

                }

            }
    		
		});
    	
        openCTLMenuItem.setOnAction(new EventHandler<ActionEvent> () {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        loadDefaultMap();
                        CtlLib.getInstance().openCtl();
                        cc.setfcwOutput("CTL file has loaded!");
                        if(ColorPaletteModel.getInstance().isClassicColors()) {
                            Dialogs.create().message("You've loaded a legacy file. "
                                            + "Currently, they are read-only.").showWarning();
                            
//                            killFeaturesOnLegacy();
                        }
                        SpecialoperationsController.getInstance().initializeSweepSpeedSelectors();
                    } catch (IOException ex) {
                        Logger.getLogger(ChoreographyController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NullPointerException e) {
                        
                    }
                }
        });
        
        advancedCheckMenuItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    Integer[] advancedOnlyLightNames = FCWLib.getInstance().getAdvancedLightNames();
                    TimelineController.getInstance().setLabelGridPane(advancedOnlyLightNames);
                    TimelineController.getInstance().setTimelineGridPane();
                    TimelineController.getInstance().rePaintLightTimeline();
                }
            });
    	
    	quitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Action result = Dialogs.create()
                        .title("Quit?")
                        .masthead("")
                        .message( "Are you sure you want to quit?")
                        .showConfirm();
                if(result != Actions.YES) {
//                    System.out.println(result);
                } else {
                    if(isSaved) {
                        Platform.exit();
                    }
                    else {
                        Action saveResult = Dialogs.create().title("Save?")
                                .masthead("You haven't saved before exiting.")
                                .message("Would you like to save before quiting?")
                                .showConfirm();
                        if(saveResult == Actions.YES) {
                            saveAsMenuItem.getOnAction().handle(t);
                        }
                        else if(saveResult == Actions.NO) {
                            Platform.exit();
                        }
                    }
                }
            }
        });
        
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent t) {
                    if(isSaved) {
                        saveGhmfZipFile();
                    } else {
                        saveAsMenuItem.getOnAction().handle(t);
                    }
                }
            });
        
        saveAsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    saveLocation  = selectSaveLocation();
                    saveGhmfZipFile();
                    
                }
            });
        
//        setLagTimesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
//
//                @Override
//                public void handle(ActionEvent t) {
////                    openLagTimeDialog();
//                }
//            });
        
        newItemMenuItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
//                    MusicPaneController.getInstance().disposeMusic();
                    stopSliderTimer();
                    MusicPaneController.getInstance().resetAll();
                    TimelineController.getInstance().disposeTimeline();
                    FountainSimController.getInstance().clearSweeps();
                    FountainSimController.getInstance().clearSim();
                    ColorPaletteModel.getInstance().resetModel();
                    MapLib.setMapLoaded(false);
                    
                    SlidersController.getInstance().resurrectSlidersPane();
                    SpecialoperationsController.getInstance().resurrectSpecialOpsPane();
                    MapLib.openMap(getClass().getResourceAsStream("/resources/default.map"));
                    ColorPaletteController.getInstance().resurrectColorPalettePane();
                }
                
            });
        
        events = new ConcurrentSkipListMap<>();
        fcwOutput.setText("Choreographer has loaded!");
        openCTLMenuItem.setDisable(true);
        cc = this;
    }
    
    public void loadDefaultMap() {
        boolean isMap = MapLib.isMapLoaded();
        if(!isMap) {
            MapLib.openMap(getClass().getResourceAsStream("/resources/default.map"));
        }
    }

    public void killFeaturesOnLegacy() {
        SpecialoperationsController.getInstance().killSpecialOpsPane();
        SlidersController.getInstance().killSlidersPane();
        ColorPaletteModel.getInstance().setClassicColors(true);
        ColorPaletteController.getInstance().rePaint();
    }

    private void saveGhmfZipFile() {
        try {
//            if(ColorPaletteModel.getInstance().isClassicColors()) {
//                Dialogs.create()
//                        .message("It is currently impossible to save legacy files.")
//                        .title("Cannot Save Legacy CTL")
//                        .showError();
//            }
            FilePayload ctl = CtlLib.getInstance().createFilePayload(
                    TimelineController.getInstance().getTimeline().getTimeline());
            FilePayload map = MapLib.createFilePayload();
            FilePayload music = MusicPaneController.getInstance().createFilePayload();
            FilePayload marks = MarkLib.createFilePayload();
            isSaved = GhmfLibrary.writeGhmfZip(saveLocation, ctl, map, music, marks);
        } catch (IOException ex) {
            Logger.getLogger(ChoreographyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File selectSaveLocation() {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("GHMF", "*.ghmf"));
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        saveLocation = fc.showSaveDialog(null);
        saveLocation = new File(saveLocation.getAbsoluteFile() + ".ghmf");
        isSaved = true;
        return saveLocation;
    }
    
    /**
     *
     * @return
     */
    public boolean openLagTimeDialog() {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/lagtime/LagTimeGUI.fxml"));
            GridPane page = (GridPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Lag Times");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the lagtimes into the controller
            LagTimeGUIController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDelays(LagTimeLibrary.getInstance().getLagTimes());

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return true;

            } catch (IOException e) {
              return false;
            }
    }
    
    /**
     *
     * @param s
     */
    public void setfcwOutput(String s) {
        fcwOutput.setText(s);
    }
    
    public void addChannels(){
//    	if (isFirst){
    	Stage primaryStage = new Stage();
    	CustomChannel.start(primaryStage);
//    	isFirst = false;
//    	}
//    	else{
//    		CustomChannel.showStage();
//    	}
    }
    
    /**
     *
     * @return
     */
    public static ChoreographyController getInstance() {
        return cc;
    }

    /**
     * Sets the 
     * @param parsedCTL
     */
    public void setEventTimeline(ConcurrentSkipListMap<Integer, ArrayList<FCW>> parsedCTL) {
        events.putAll(parsedCTL);
        TimelineController.getInstance().setTimeline(parsedCTL);
        TimelineController.getInstance().setLabelGridPaneWithCtl();
        TimelineController.getInstance().rePaint();
    }
    
    /**
     * Returns the event Timeline
     * @return
     */
    public SortedMap<Integer, ArrayList<FCW>> getEventTimeline() {
        return events;
    }

    public void setAdvanced(boolean b) {
        isAdvanced = b;
    }

    public boolean getAdvanced() {
        return isAdvanced;
    }
    
    /**
     * Updates timeSlider in MusicPaneController every 1/8th of a second
     */
    public void startPollingTimeSliderAlgorithm() {
        
        sliderTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    MusicPaneController.getInstance().updateProgress();
                });
            }
        }, 0l, 125l);
    }
    
    /**
     * Draws SIM and sets sliders every 10th of a second
     */
    
    public void startPollingSlidersAlgorithm() {
       
        timelineTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
//                	TimelineController.getInstance().fireSubmapToSim();
//                    TimelineController.getInstance().fireSliderChangeEvent();
//                    Timeline.getInstance().drawSim(MusicPaneController.getInstance().getTenthsTime());
                });
            }
        }, 0l, 20000l);
    }
    
    public void startPollingSimAlgorithm() {
        
        timelineTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                	//TimelineController.getInstance().fireSubmapToSim();
                    TimelineController.getInstance().fireSimChangeEvent();
                    //FountainSimController.getInstance().drawSim(MusicPaneController.getInstance().getTenthsTime());
                });
            }
        }, 0l, 100l);
    }
    
    public void startPollingColorAlgorithm() {
        
        timelineTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    TimelineController.getInstance().updateColors(MusicPaneController.getInstance().getTenthsTime());
                });
            }
        }, 0l, 100l);
    }
 
    public boolean getIsSelected(){
	return isSelected;
    }

    public void stopTimelineTimer() {
       timelineTimer.purge();
    }

    public void stopSliderTimer() {
        sliderTimer.purge();
    }
    
    public void openMapFileMenuItemHandler() {
        try {
            MapLib.openMap();
        } catch (FileNotFoundException ex) {
            Dialogs.create().title("Invalid MAP file")
                    .message("You've selected an invalid MAP file. "
                            + "Please try again.").showError();
            Logger.getLogger(ChoreographyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startPlayingSim() {
        TimelineController.getInstance().fireSubmapToSim();
        FountainSimController.getInstance().playSim();
    }
    public ScrollPane getBeatMarkScrollPane(){
    	return this.beatMarkScrollPane;
    }
    public void setBeatMarkGridPane(){
    	gridpaneBeatMarks = new GridPane();
    		
    	time = MusicPaneController.SONG_TIME;
    	
    	gridpaneBeatMarks.setGridLinesVisible(true);
    	beatMarkRecArray = new Rectangle[time];
    	
    	for (int i = 0; i < time; i++) {    		
    		gridpaneBeatMarks.getColumnConstraints().add(new ColumnConstraints(26));
            if (i < 1) { // because the array is not square this needs to be
                                          // here
            	gridpaneBeatMarks.getRowConstraints().add(new RowConstraints(26));
            }
            
            beatMarkRecArray[i] = new Rectangle(25, 25, Color.LIGHTGREY);
            gridpaneBeatMarks.add(beatMarkRecArray[i], i, 0);
            int testI = i;
            beatMarkRecArray[i]
                    .setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
            	beatMarkRecArray[testI].setFill(Color.LIGHTGRAY);
            }});
            
    	}
    	
    	beatMarkScrollPane.setContent(gridpaneBeatMarks);
    }
    
    public Integer[] getBeatmarks() {
        Integer[] beatMarksArray = new Integer[beatMarkRecArray.length];
        for(int i = 0; i < beatMarkRecArray.length; i++) {
            if(beatMarkRecArray[i].getFill() == Color.LIGHTGREY) {
                beatMarksArray[i] = 0;
            }
            else {
                beatMarksArray[i] = 1;
            }
        }
        return beatMarksArray;
    }
    
    public void setBeatmarks(Integer[] beatmarksArray) {
        for(int i = 0; i < beatmarksArray.length; i++) {
            if(beatmarksArray[i] == 0) {
                beatMarkRecArray[i].setFill(Color.LIGHTGREY);
            }
            else {
                beatMarkRecArray[i].setFill(Color.BLACK);
            }
        }
    }
    
//    public void rePaintBeatMarks() {
//        for()
//    }
    
    @FXML
    public void openGhmfFile(ActionEvent event) {
        GhmfLibrary.openGhmfFile();
    }
    
    @FXML
    public void aboutDialogueBox() {
    	Dialogs.create().title("About GHMF Choreography Studio")
        .message("The Grand Valley State University senior project team, Excalibur Solutions, created the GHMF Choreography Studio on April 15, 2014. "
                + System.lineSeparator() + System.lineSeparator() 
                + "This software is used to create light shows for the Grand Haven "
                + "Musical Fountain located in Grand Haven Michigan.  ")
                .masthead("About").showInformation();
    }
    
    @FXML
    public void userManual() {
    	Stage stage = new Stage();
    	Scene scene;
////    	scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
////        stage.setScene(scene);
////        stage.setTitle("Web View");
//////        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");        
////        stage.show();
//    	File f = new File("/resources/User_Manual_v10.htm");
//    	// ..
//    	final WebView webview = new WebView();
//    	try {
//			webview.getEngine().load(f.toURI().toURL().toString());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
    	// Names the browser window
    	 stage.setTitle("Help - User Manual");
    	 
    	    MyBrowser myBrowser = new MyBrowser();
    	    scene = new Scene(myBrowser, 800, 600);
    	 
    	    stage.setScene(scene);
    	    // Opens the browser 
    	    stage.show();
    }

    class MyBrowser extends Region{
    	 
        final String userManualHtml = "User_Manual_v10.htm";
     
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
             
        public MyBrowser(){
        	// Points to the location of the htm file for the manual
            URL urlHello = getClass().getResource("/resources/User_Manual_v10.htm");
            webEngine.load(urlHello.toExternalForm());
            // Adds the browser to the scene
            getChildren().add(webView);
        }
    }
}
    
//class Browser extends Region{
//	
//	final WebView browser = new WebView();
//	final WebEngine webEngine = browser.getEngine();
//
//	public Browser() {
////		URL manual = getClass().getResource("/resources/User_Manual_v10.htm").toExternalForm();
//		webEngine.load("http://google.com");
//		getChildren().add(browser);
//	}
//	
//	   private Node createSpacer() {
//	        Region spacer = new Region();
//	        HBox.setHgrow(spacer, Priority.ALWAYS);
//	        return spacer;
//	    }
//	 
//	    @Override protected void layoutChildren() {
//	        double w = getWidth();
//	        double h = getHeight();
//	        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
//	    }
//	 
//	    @Override protected double computePrefWidth(double height) {
//	        return 750;
//	    }
//	 
//	    @Override protected double computePrefHeight(double width) {
//	        return 500;
//	    }
//}}
