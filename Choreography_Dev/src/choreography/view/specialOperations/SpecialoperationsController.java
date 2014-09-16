/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.view.specialOperations;

import choreography.io.FCWLib;
import choreography.model.fcw.FCW;
import choreography.view.ChoreographyController;
import choreography.view.colorPalette.ColorPaletteController;
import choreography.view.music.MusicPaneController;
import choreography.model.timeline.Timeline;
import choreography.view.timeline.TimelineController;

import java.awt.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import javax.swing.event.ChangeEvent;

/**
 * FXML Controller class
 *
 * @author elementsking
 */
public class SpecialoperationsController implements Initializable {
    
    private static SpecialoperationsController instance;
    
    public static SpecialoperationsController getInstance() {
        if(instance == null) {
            return new SpecialoperationsController();
        }
        return instance;
    }
    @FXML
    private CheckBox voiceCheckbox;

    @FXML
    private AnchorPane bSweepsPane;

    @FXML
    private ToggleButton independentSweeps;

    @FXML
    private Button fadeDownButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ToggleGroup sweepGroup;

    @FXML
    private ToggleButton opposedSweeps;

    @FXML
    private Button fadeUpButton;

    @FXML
    private Button strobeButton;

    @FXML
    private ToggleButton parallelSweeps;

    @FXML
    private TitledPane sweepControlsPane;

    @FXML
    private AnchorPane aSweepsPane;

    @FXML
    private Button resetButton;
    
    @FXML private ChoiceBox<String> aSpeedSelector;
    
    @FXML private ChoiceBox<String> bSpeedSelector;
    
    @FXML private ComboBox<Integer> strobeFrequency;
    
    @FXML private Accordion specialOpsPane;
    
    private RangeSlider aSweeps;
    private RangeSlider bSweeps;
    private String cannon;
    private SweepsEventHandlerImpl aSweepsEventHandler;
    private SweepsEventHandlerImpl bSweepsEventHandler;
    private int selectedHZ;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aSweeps = new RangeSlider(0.0, 4.0, 1.0, 3.0);
        bSweeps = new RangeSlider(0.0, 4.0, 1.0, 3.0);
        
        aSweeps.setSnapToTicks(true);
        aSweeps.setMajorTickUnit(1); aSweeps.setShowTickMarks(true); 
        aSweeps.setMinorTickCount(0); aSweeps.setBlockIncrement(1);
        
        bSweeps.setSnapToTicks(true);
        bSweeps.setMajorTickUnit(1); bSweeps.setShowTickMarks(true); 
        bSweeps.setMinorTickCount(0); bSweeps.setBlockIncrement(1);
        
        aSweepsPane.getChildren().add(aSweeps); 
        bSweepsPane.getChildren().add(bSweeps); 
        aSweeps.setVisible(false);
        bSweeps.setVisible(false);
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for(int i = 0; i < 255; i++){
        	list.add(i+1);
        }
        
        ObservableList<Integer> observableList = FXCollections.observableList(list);
        strobeFrequency.setItems(observableList);
        strobeFrequency.getSelectionModel().select(0);
        
//        strobeFrequency.selectionModelProperty().addListener(new ChangeListener<SingleSelectionModel<Integer>>() {
//
//			@Override
//			public void changed(
//					ObservableValue<? extends SingleSelectionModel<Integer>> observable,
//					SingleSelectionModel<Integer> oldValue,
//					SingleSelectionModel<Integer> newValue) {
//				
//				selectedHZ = ;
//				
//			}
//        	
//        });
        
        fadeUpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	ArrayList<Integer> colAL = TimelineController.getInstance().getColAL();
            	ArrayList<Integer> rowAL = TimelineController.getInstance().getRowAL();
            	
            	ArrayList<Integer> colALcopy = new ArrayList<>();
            	colALcopy.addAll(colAL);
            	ArrayList<Integer> rowALcopy = new ArrayList<>();
            	rowALcopy.addAll(rowAL);
            	
            	TimelineController.getInstance().clearAllAL();
            	
            	double size = colALcopy.size();
            	double opScale = 1/size;
            	double opacity = 0+opScale;
            	
                for(int i = 0; i < size; i++){
                	TimelineController.getInstance().setLightRecArrayFade(rowALcopy.get(i), colALcopy.get(i), opacity);
                	opacity = opacity + opScale;
                }
            }
        });
        
        fadeDownButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	ArrayList<Integer> colAL = TimelineController.getInstance().getColAL();
            	ArrayList<Integer> rowAL = TimelineController.getInstance().getRowAL();
            	
            	ArrayList<Integer> colALcopy = new ArrayList<>();
            	colALcopy.addAll(colAL);
            	ArrayList<Integer> rowALcopy = new ArrayList<>();
            	rowALcopy.addAll(rowAL);
            	
            	TimelineController.getInstance().clearAllAL();
            	
            	double size = colALcopy.size();
            	double opScale = 1/size;
            	double opacity = 1;
            	
                for(int i = 0; i < size; i++){
                	TimelineController.getInstance().setLightRecArrayFade(rowALcopy.get(i), colALcopy.get(i), opacity);
                	opacity = opacity - opScale;
                }
            }
        });
        
        strobeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	ArrayList<Integer> colAL = TimelineController.getInstance().getColAL();
            	ArrayList<Integer> rowAL = TimelineController.getInstance().getRowAL();
            	
            	int low = 4;
            	int mid = 3;
            	int high = 2;
            	
//            	Stop[] stops1 = new Stop[] { new Stop(0, Color.BLACK), new Stop(.65, (Color) ColorPaletteController.getInstance().getSelectedColor())};
//                RadialGradient rad1 = new RadialGradient(0, .1, 12.5, 12.5, 20, false, CycleMethod.NO_CYCLE, stops1);
                
//                Stop[] stops = new Stop[] { new Stop(.5, Color.LIGHTGRAY), new Stop(1, (Color) ColorPaletteController.getInstance().getSelectedColor())};
//                LinearGradient linearGradient_Med
//                    = LinearGradientBuilder.create()
//                    .startX(7)
//                    .startY(7)
//                    .endX(0)
//                    .endY(7)
//                    .proportional(false)
//                    .cycleMethod(CycleMethod.REPEAT)
//                    .stops(stops)
//                    .build();
//                
//                LinearGradient linearGradient_Low
//                = LinearGradientBuilder.create()
//                .startX(13)
//                .startY(13)
//                .endX(0)
//                .endY(13)
//                .proportional(false)
//                .cycleMethod(CycleMethod.REPEAT)
//                .stops(stops)
//                .build();
//                
//                LinearGradient linearGradient_High
//                 = LinearGradientBuilder.create()
//                 .startX(6)
//                 .startY(6)
//                 .endX(0)
//                 .endY(6)
//                 .proportional(false)
//                 .cycleMethod(CycleMethod.REPEAT)
//                 .stops(stops)
//                 .build();
                
                if(strobeFrequency.getSelectionModel().getSelectedItem() <= 85){
                	for(int i = 0; i < colAL.size(); i++){
                            if(i % low == 0){
                                TimelineController.getInstance().setLightRecArrayStrobe(rowAL.get(i), colAL.get(i), ColorPaletteController.getInstance().getSelectedColor());
                            }
                        }
                    	
                } else if (strobeFrequency.getSelectionModel().getSelectedItem() <= 170){
                	for(int i = 0; i < colAL.size(); i++){
                            if(i % mid == 0){
                                TimelineController.getInstance().setLightRecArrayStrobe(rowAL.get(i), colAL.get(i), ColorPaletteController.getInstance().getSelectedColor());
                            }
                        }
                } else{
                    for(int i = 0; i < colAL.size(); i++){
                        if(i % high == 0){
                            TimelineController.getInstance().setLightRecArrayStrobe(rowAL.get(i), colAL.get(i), ColorPaletteController.getInstance().getSelectedColor());
                        }
                    }
                }                 
            }
        });
        
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	ArrayList<Integer> colAL = TimelineController.getInstance().getColAL();
            	ArrayList<Integer> rowAL = TimelineController.getInstance().getRowAL();
            	
//            	ArrayList<Integer> colALcopy = new ArrayList<>();
//            	colALcopy.addAll(colAL);
//            	ArrayList<Integer> rowALcopy = new ArrayList<>();
//            	rowALcopy.addAll(rowAL);
//            	
//            	TimelineController.getInstance().clearAllAL();
//            	
//            	double size = colALcopy.size();
//            	double opScale = 1/size;
//            	double opacity = 1;
//            	
            	if(ChoreographyController.getInstance().getIsSelected()){ // if deleting light
            		for(int i = 0; i < colAL.size(); i++){
                	if(i==0){
                		TimelineController.getInstance().delete(colAL.get(i), rowAL.get(i),  colAL.size(), true);
                	}
                	else{
                		TimelineController.getInstance().delete(colAL.get(i),rowAL.get(i));
                	}
//                	opacity = opacity - opScale;
                	
                }
            	}
            	else{//if deleting water
            		int time = MusicPaneController.getInstance().getTenthsTime();
            		TimelineController.getInstance().delete(time);
            	}
//            	if(MusicPaneController.getInstance().getWaterPane().isFocused()){
////            		double time = MusicPaneController.getInstance().getMediaPlayer().getCurrentTime();
//            		
//            	}
                
            	
            }
        });
        
        opposedSweeps.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                aSweeps.setVisible(true);
                bSweeps.setVisible(false);
                aSweepsEventHandler.setOpposed(true);
                bSweepsEventHandler.setOpposed(true);
            }
        });
        
        independentSweeps.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                aSweeps.setVisible(true);
                bSweeps.setVisible(true);
                aSweepsEventHandler.setOpposed(false);
                bSweepsEventHandler.setOpposed(false);
                bSpeedSelector.setVisible(true);
            }
        });
        
        parallelSweeps.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                aSweeps.setVisible(true);
                bSweeps.setVisible(false);
                aSweepsEventHandler.setOpposed(true);
                bSweepsEventHandler.setOpposed(true);
            }
        });
        
        aSweepsEventHandler = new SweepsEventHandlerImpl("SWEEPLIMITA", aSweeps);
        bSweepsEventHandler = new SweepsEventHandlerImpl("SWEEPLIMITB", bSweeps);
        
        aSweeps.setOnMouseReleased(aSweepsEventHandler);
        bSweeps.setOnMouseReleased(bSweepsEventHandler);
        
        aSpeedSelector.selectionModelProperty().addListener(new ChangeListener<SingleSelectionModel<String>> () {
            @Override
            public void changed(ObservableValue<? extends SingleSelectionModel<String>> observable, SingleSelectionModel<String> oldValue, SingleSelectionModel<String> newValue) {
                if(independentSweeps.isSelected()) {
                    String[] actions = new String[]{aSpeedSelector.getValue()};
                    FCW f = FCWLib.getInstance().getFCW("SWEEPASPEED", actions);
                    TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(MusicPaneController.getInstance().getTenthsTime(), f);
                    
                }
                else {
                    String[] actions = new String[]{aSpeedSelector.getValue()};
                    FCW f = FCWLib.getInstance().getFCW("SWEEPASPEED", actions);
                    TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(MusicPaneController.getInstance().getTenthsTime(), f);
                    actions = new String[]{bSpeedSelector.getValue()};
                    f = FCWLib.getInstance().getFCW("SWEEPBSPEED", actions);
                    TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(MusicPaneController.getInstance().getTenthsTime(), f);
                    ChoreographyController.getInstance().setfcwOutput(f.toString());
                }
            }
        });
    
        bSpeedSelector.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String[] actions = new String[]{bSpeedSelector.getValue()};
                FCW f = FCWLib.getInstance().getFCW("SWEEPBSPEED", actions);
                TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(MusicPaneController.getInstance().getTenthsTime(), f);
            }
        });
        
        
        voiceCheckbox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(voiceCheckbox.isSelected()) {
                    setVoiceOnOff("ON");
                }
                else {
                    setVoiceOnOff("OFF");
                }
            }

            public void setVoiceOnOff(String onOff) {
                ArrayList<String> AL = new ArrayList<>(3);
                AL.add(onOff);
                String[] actions = AL.toArray(new String[1]);
                FCW f = FCWLib.getInstance().getFCW("VOICE", actions);
                int tenths = MusicPaneController.getInstance().getTenthsTime();
                TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(tenths, f);
                TimelineController.getInstance().rePaintWaterTimeline();
                ChoreographyController.getInstance().setfcwOutput(f.toString());
            }
        });
        
        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ArrayList<String> AL = new ArrayList<>(3);
                AL.add("RESETALL");
                String[] actions = AL.toArray(new String[1]);
                FCW f = FCWLib.getInstance().getFCW("OFF", actions);
                int tenths = MusicPaneController.getInstance().getTenthsTime();
                TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(tenths, f);
                TimelineController.getInstance().rePaintWaterTimeline();
                ChoreographyController.getInstance().setfcwOutput(f.toString());
            }
        });
        
        initializeSweepSpeedSelectors();
        
        instance = this;
        
        //TODO Add Fadeup
        //TODO Add Fadedown
        //TODO Add Strobe
        //TODO Modify 
    }    

    public void initializeSweepSpeedSelectors() {
        aSpeedSelector.getItems().clear(); bSpeedSelector.getItems().clear();
        aSpeedSelector.getItems().addAll(FCWLib.getInstance().getSweepSpeeds());
        bSpeedSelector.setVisible(false);
        bSpeedSelector.getItems().addAll(FCWLib.getInstance().getSweepSpeeds());
    }

    public void setSweeps(FCW f) {
        String[] actions = FCWLib.getInstance().reverseLookupData(f);
        
        System.out.println(Arrays.toString(actions));
        switch(f.getAddr()) {
            case 33:
                parallelSweeps.setSelected(true);
                opposedSweeps.setSelected(false);
                independentSweeps.setSelected(false);
                for(String s: actions) {
                    sweepsSpeedSwitch(aSpeedSelector, s);
                }
                break;
            case 34:
                parallelSweeps.setSelected(false);
                opposedSweeps.setSelected(true);
                independentSweeps.setSelected(false);
                for(String s: actions) {
                    sweepsSpeedSwitch(aSpeedSelector, s);
                }
                break;
            case 35:
                for(String s: actions) {
                    sweepsTravelSwitch(aSweeps, s);
                    sweepsTravelSwitch(bSweeps, s);
                }
                break;
            case 36:
                for(String s: actions) {
                    sweepsTravelSwitch(aSweeps, s);
                }
                break;
            case 37:
                for(String s: actions) {
                    sweepsTravelSwitch(aSweeps, s);
                }
                break;
            case 38:
                for(String s: actions) {
                     sweepsSpeedSwitch(aSpeedSelector, s);
                 }
            case 39:
                 for(String s: actions) {
                     sweepsSpeedSwitch(bSpeedSelector, s);
                 }
            case 40:
                for(String s: actions) {
                    switch(s) {
                        case "TOGETHER":
                            parallelSweeps.setSelected(true);
                            opposedSweeps.setSelected(false);
                            independentSweeps.setSelected(false);
                            break;
                        case "OPPOSED":
                            parallelSweeps.setSelected(false);
                            opposedSweeps.setSelected(true);
                            independentSweeps.setSelected(false);
                            break;
                        case "INDEPENDENT":
                            parallelSweeps.setSelected(false);
                            opposedSweeps.setSelected(false);
                            independentSweeps.setSelected(true);
                    }
                }
                
        }
    }

    private void sweepsTravelSwitch(RangeSlider slider, String s) {
        switch(s){
            case "HOLDRIGHTLONG":
                setSweeps(slider, 4, 4);
                break;
            case "RIGHTLONGRIGHTSHORT":
                setSweeps(slider, 3, 4);
                break;
            case "RIGHTLONGCENTER":
                setSweeps(slider, 2, 4);
                break;
            case "RIGHTLONGLEFTSHORT":
                setSweeps(slider, 1, 4);
                break;
            case "RIGHTLONGLEFTLONG":
                setSweeps(slider, 0, 4);
                aSweeps.setHighValue(5);
                aSweeps.setLowValue(1);
                break;
            case "HOLDRIGHTSHORT":
                setSweeps(slider, 3, 3);
                break;
            case "RIGHTSHORTCENTER":
                setSweeps(slider, 2, 3);
                break;
            case "RIGHTSHORTLEFTSHORT":
                setSweeps(slider, 1, 3);
                break;
            case "RIGHTSHORTLEFTLONG":
                setSweeps(slider, 0, 3);
                break;
            case "HOLDCENTER":
                setSweeps(slider, 2, 2);
                break;
            case "CENTERLEFTSHORT":
                setSweeps(slider, 1, 2);
                break;
            case "CENTERLEFTLONG":
                setSweeps(slider, 0, 2);
                break;
            case "HOLDLEFTSHORT":
                setSweeps(slider, 1, 1);
                break;
            case "LEFTSHORTLEFTLONG":
                setSweeps(slider, 0, 1);
                break;
            case "HOLDLEFTLONG":
                setSweeps(slider, 0, 0);
                break;
                
        }
    }

    private void sweepsSpeedSwitch(ChoiceBox c, String s) {
        if(s.equals("SHORT")) {
            setSweeps(aSweeps, 1, 3);
        }
        else if(s.equals("LONG")) {
            setSweeps(aSweeps, 0, 4);
        }
        else {
            c.getSelectionModel().select(s);
        }
//        if(s.equals("LARGO")) {
//            c.getSelectionModel().select(s);
//        }
//        if(s.equals("ADAGIO")) {
//            
//        }
//        if(s.equals("ANDANTE")) {
//            
//        }
//        if(s.equals("MODERATO")) {
//            
//        }
//        if(s.equals("ALLEGRETO")) {
//            
//        }
//        if(s.equals("ALLEGRO")) {
//            
//        }
//        if(s.equals("PRESTO")) {
//            
//        }
//        if(s.equals("PLAYPAUSE")) {
//            
//        }
    }

    private void setSweeps(RangeSlider slider, int low, int high) {
        slider.setHighValue(high);
        slider.setLowValue(low);
    }

    public void killSpecialOpsPane() {
        specialOpsPane.setVisible(false);
    }

    public void resurrectSpecialOpsPane() {
        specialOpsPane.setVisible(true);
    }

    private class SweepsEventHandlerImpl implements EventHandler<MouseEvent> {
        
        String sweeps;
        RangeSlider slider;
        boolean opposed;

        public SweepsEventHandlerImpl(String sweeps, RangeSlider slider) {
            this.sweeps = sweeps;
            this.slider = slider;
        }
        
        public boolean isOpposed() {
            return opposed;
        }
        
        public void setOpposed(boolean opposed) {
            this.opposed = opposed;
        }

        @Override
        public void handle(MouseEvent event) {
            String[] actions = new String[2];
//            String action;
            FCW f = null;
            int tenths = MusicPaneController.getInstance().getTenthsTime();
            if(opposedSweeps.isSelected()) {
                f = createFcw(actions);
                FCW opposed = FCWLib.getInstance().getFCW("SWEEPTYPE", new String[]{"OPPOSED"});
                TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(tenths, opposed);
            } else if(parallelSweeps.isSelected()) {
                f = createFcw(actions);
                FCW opposed = FCWLib.getInstance().getFCW("SWEEPTYPE", new String[]{"TOGETHER"});
                TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(tenths, opposed);
            } else if(independentSweeps.isSelected()) {
                f = createFcw(actions);
                FCW opposed = FCWLib.getInstance().getFCW("SWEEPTYPE", new String[]{"INDEPENDENT"});
                TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(tenths, opposed);
            }
//            else { f = new FCW(0, 0); }
            ChoreographyController.getInstance().setfcwOutput(f.toString());
            TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(tenths, f);
            TimelineController.getInstance().rePaintWaterTimeline();
        }

        public FCW createFcw(String[] actions) {
            String action;
            FCW f;
            int low = (int)slider.getLowValue();
            int high = (int)slider.getHighValue();
//            System.out.println(low + " " + high);
            action = buildSweepLimitString(low, actions, high);
//            System.out.println(action);
            if(opposed) {
                f = FCWLib.getInstance().getFCW("SWEEPLIMITAB", new String[]{action});
            } else
                f = FCWLib.getInstance().getFCW(sweeps, new String[]{action});
            return f;
        }

            public String buildSweepLimitString(int low, String[] actions, int high) {
                String action;
                switch(low) {
                    case 0:
                        actions[0] = "LEFTLONG";break;
                    case 1:
                        actions[0] = "LEFTSHORT";break;
                    case 2:
                        actions[0] = "CENTER";break;
                    case 3:
                        actions[0] = "RIGHTSHORT";break;
                    case 4:
                        actions[0] = "RIGHTLONG";break;
                }
                switch(high) {
                    case 0:
                        actions[1] = "LEFTLONG";break;
                    case 1:
                        actions[1] = "LEFTSHORT";break;
                    case 2:
                        actions[1] = "CENTER";break;
                    case 3:
                        actions[1] = "RIGHTSHORT";break;
                    case 4:
                        actions[1] = "RIGHTLONG";break;
                }
                if(low == high) {
                    action = "HOLD" + actions[0];
                }
                else {
                    action = actions[1] + actions[0];
                }
                return action;
            }
    }    
    
}
