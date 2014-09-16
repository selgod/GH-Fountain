/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choreography.view.sliders;
/**
 * Sample Skeleton for "Sliders.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/
import choreography.Main;
import choreography.io.FCWLib;
import choreography.model.cannon.Candelabra;
import choreography.model.cannon.Cannon;
import choreography.model.cannon.CannonEnum;
import choreography.model.cannon.IndependentCannon;
import choreography.model.cannon.Multi;
import choreography.model.cannon.Ring;
import choreography.model.cannon.Sweep;
import choreography.model.fcw.FCW;
import choreography.model.fountain.Fountain;
import choreography.model.fountain.ModuleEnum;
import choreography.model.fountain.ModuleGroup;
import choreography.view.sim.FountainSimController;
import choreography.view.specialOperations.SpecialoperationsController;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import javafx.util.Duration;
/**
 *
 * @author elementsking
 */
public class SlidersController {
    
    private static SlidersController instance;
    
    public static SlidersController getInstance() {
        if(instance == null) {
            return instance = new SlidersController();
        }
        return instance;
    }
    private Fountain fountain;
    private ModuleGroup A;
    private ModuleGroup B;
    private ArrayList<Ring> rings1A, rings1B, rings2A, rings2B, rings3A, 
            rings3B, rings4A, rings4B, rings5A, rings5B;
    private ArrayList<Multi> multisA, multisB;
    private ArrayList<Candelabra> candlesA, candlesB;
    private ArrayList<Sweep> sweepsA, sweepsB;
    private IndependentCannon peacock;
    private IndependentCannon bazooka;
    private IndependentCannon spout;
    private IndependentCannon bkCurt;
    private IndependentCannon ftCurt;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Slider bkC;

    @FXML
    private Slider bz;

    @FXML
    private Slider candleA;

    @FXML
    private Slider candleB;

    @FXML
    private Slider ftC;

    @FXML
    private Slider mxA;

    @FXML
    private Slider mxB;

    @FXML
    private Slider pk;

    @FXML
    private Slider r1A;

    @FXML
    private Slider r1B;

    @FXML
    private Slider r2A;

    @FXML
    private Slider r2B;

    @FXML
    private Slider r3A;

    @FXML
    private Slider r3B;

    @FXML
    private Slider r4A;

    @FXML
    private Slider r4B;

    @FXML
    private Slider r5A;

    @FXML
    private Slider r5B;

    @FXML
    private Slider sp;

    @FXML
    private Slider swA;

    @FXML
    private Slider swB;
    private Slider[] allSliders;
    
    @FXML private HBox slidersPane;


    @FXML
    void initialize() {
        assert bkC != null : "fx:id=\"bkC\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert bz != null : "fx:id=\"bz\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert candleA != null : "fx:id=\"candleA\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert candleB != null : "fx:id=\"candleB\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert ftC != null : "fx:id=\"ftC\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert mxA != null : "fx:id=\"mxA\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert mxB != null : "fx:id=\"mxB\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert pk != null : "fx:id=\"pk\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r1A != null : "fx:id=\"r1A\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r1B != null : "fx:id=\"r1B\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r2A != null : "fx:id=\"r2A\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r2B != null : "fx:id=\"r2B\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r3A != null : "fx:id=\"r3A\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r3B != null : "fx:id=\"r3B\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r4A != null : "fx:id=\"r4A\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r4B != null : "fx:id=\"r4B\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r5A != null : "fx:id=\"r5A\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert r5B != null : "fx:id=\"r5B\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert sp != null : "fx:id=\"sp\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert swA != null : "fx:id=\"swA\" was not injected: check your FXML file 'Sliders.fxml'.";
        assert swB != null : "fx:id=\"swB\" was not injected: check your FXML file 'Sliders.fxml'.";
        
        configureModules();
        allSliders = new Slider[]{r1A, r1B, r2A, r2B, r3A, r3B, r4A, r4B, r5A, 
           r5B, mxA, mxB, candleA, candleB, swA, swB, ftC, bkC, pk, bz, sp};
        instance = this;
       
//     // Listen for Slider value changes
//      		swA.valueProperty().addListener(new ChangeListener<Number>() {
//      			@Override
//      			public void changed(ObservableValue<? extends Number> observable,
//      					Number oldValue, Number newValue) {
//      				final Timeline timeline = new Timeline();
//      				timeline.setCycleCount(1);
//      				
//      				KeyValue kv25 = null;
//					KeyValue kv26 = null;
//					KeyValue kv27 = null;
//					KeyValue kv28 = null;
//					KeyValue kv29 = null;
//					KeyValue kv30 = null;
//					KeyValue kv31 = null;
//					KeyValue kv32 = null;
//					
//					FountainSimController.getInstance().getMod1sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod1sweep2().setVisible(true);
//  					FountainSimController.getInstance().getMod3sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod3sweep2().setVisible(true);
//  					FountainSimController.getInstance().getMod5sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod5sweep2().setVisible(true);
//  					FountainSimController.getInstance().getMod7sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod7sweep2().setVisible(true);
//  				
//					if (newValue.doubleValue()==0){
//  					kv25 = new KeyValue(FountainSimController.getInstance().getMod1sweep1().visibleProperty(), false);
//      				kv26 = new KeyValue(FountainSimController.getInstance().getMod1sweep2().visibleProperty(), false);
//      				
//      				kv27 = new KeyValue(FountainSimController.getInstance().getMod3sweep1().visibleProperty(), false);
//      				kv28 = new KeyValue(FountainSimController.getInstance().getMod3sweep2().visibleProperty(), false);
//      				
//      				kv29 = new KeyValue(FountainSimController.getInstance().getMod5sweep1().visibleProperty(), false);
//      				kv30 = new KeyValue(FountainSimController.getInstance().getMod5sweep2().visibleProperty(), false);
//      				
//      				kv31 = new KeyValue(FountainSimController.getInstance().getMod7sweep1().visibleProperty(), false);
//      				kv32 = new KeyValue(FountainSimController.getInstance().getMod7sweep2().visibleProperty(), false);
//  				}
//      				
//      				
//      				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod1sweep2().endYProperty(), ((35*newValue.doubleValue())));
//      				
//      				final KeyValue kv7 = new KeyValue(FountainSimController.getInstance().getMod3sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv8 = new KeyValue(FountainSimController.getInstance().getMod3sweep2().endYProperty(), ((35*newValue.doubleValue())));
//      				
//      				final KeyValue kv13 = new KeyValue(FountainSimController.getInstance().getMod5sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv14 = new KeyValue(FountainSimController.getInstance().getMod5sweep2().endYProperty(), ((35*newValue.doubleValue())));
//      				
//      				final KeyValue kv19 = new KeyValue(FountainSimController.getInstance().getMod7sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv20 = new KeyValue(FountainSimController.getInstance().getMod7sweep2().endYProperty(), ((35*newValue.doubleValue())));
//
//      				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv7, kv8, kv13, 
//      																		kv14, kv14, kv19, kv20,
//      																		kv25, kv26, kv27, kv28,
//      																		kv29, kv30, kv31, kv32);
//      				timeline.getKeyFrames().add(kf);
//      				timeline.play();     				
//      				System.out.println(newValue);
//      			}
//      		});
      		
//      	// Listen for Slider value changes
//      		swB.valueProperty().addListener(new ChangeListener<Number>() {
//      			@Override
//      			public void changed(ObservableValue<? extends Number> observable,
//      					Number oldValue, Number newValue) {
//      				final Timeline timeline = new Timeline();
//      				timeline.setCycleCount(1);
//      				
//      				KeyValue kv15 = null;
//  					KeyValue kv16 = null;
//  					KeyValue kv17 = null;
//  					KeyValue kv18 = null;
//  					KeyValue kv19 = null;
//  					KeyValue kv20 = null;
//  					
//  					FountainSimController.getInstance().getMod2sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod2sweep2().setVisible(true);
//  					FountainSimController.getInstance().getMod4sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod4sweep2().setVisible(true);
//  					FountainSimController.getInstance().getMod6sweep1().setVisible(true);
//  					FountainSimController.getInstance().getMod6sweep2().setVisible(true);
//
//  					if (newValue.doubleValue()==0){
//      					kv15 = new KeyValue(FountainSimController.getInstance().getMod2sweep1().visibleProperty(), false);
//          				kv16 = new KeyValue(FountainSimController.getInstance().getMod2sweep2().visibleProperty(), false);
//          				
//          				kv17 = new KeyValue(FountainSimController.getInstance().getMod4sweep1().visibleProperty(), false);
//          				kv18 = new KeyValue(FountainSimController.getInstance().getMod4sweep2().visibleProperty(), false);
//          				
//          				kv19 = new KeyValue(FountainSimController.getInstance().getMod6sweep1().visibleProperty(), false);
//          				kv20 = new KeyValue(FountainSimController.getInstance().getMod6sweep2().visibleProperty(), false);
//      				}
//      				
//      				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod2sweep2().endYProperty(), ((35*newValue.doubleValue())));
//      				
//      				final KeyValue kv7 = new KeyValue(FountainSimController.getInstance().getMod4sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv8 = new KeyValue(FountainSimController.getInstance().getMod4sweep2().endYProperty(), ((35*newValue.doubleValue())));
//      				
//      				final KeyValue kv13 = new KeyValue(FountainSimController.getInstance().getMod6sweep1().endYProperty(), ((35*newValue.doubleValue())));
//      				final KeyValue kv14 = new KeyValue(FountainSimController.getInstance().getMod6sweep2().endYProperty(), ((35*newValue.doubleValue())));
//      				
//      				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv7, kv8, kv13, 
//      																		kv14, kv15, kv16, kv17, kv18, kv19, kv20);
//      				timeline.getKeyFrames().add(kf);
//      				timeline.play();     				
//      				System.out.println(newValue);
//      			}
//      		});
        
//        // Listen for Slider value changes
// 		candleA.valueProperty().addListener(new ChangeListener<Number>() {
// 			@Override
// 			public void changed(ObservableValue<? extends Number> observable,
// 					Number oldValue, Number newValue) {
// 				final Timeline timeline = new Timeline();
// 				timeline.setCycleCount(1);
// 				
// 					KeyValue kv25 = null;
//					KeyValue kv26 = null;
//					KeyValue kv27 = null;
//					KeyValue kv28 = null;
//					KeyValue kv29 = null;
//					KeyValue kv30 = null;
//					KeyValue kv31 = null;
//					KeyValue kv32 = null;
//					KeyValue kv33 = null;
//					KeyValue kv34 = null;
//					KeyValue kv35 = null;
//					KeyValue kv36 = null;
//					KeyValue kv37 = null;
//					KeyValue kv38 = null;
//					KeyValue kv39 = null;
//					KeyValue kv40 = null;
//					KeyValue kv41 = null;
//					KeyValue kv42 = null;
//					KeyValue kv43 = null;
//					KeyValue kv44 = null;
//					KeyValue kv45 = null;
//					KeyValue kv46 = null;
//					KeyValue kv47 = null;
//					KeyValue kv48 = null;
//					FountainSimController.getInstance().getMod1candle1().setVisible(true);
//					FountainSimController.getInstance().getMod1candle2().setVisible(true);
//					FountainSimController.getInstance().getMod1candle3().setVisible(true);
//					FountainSimController.getInstance().getMod1candle4().setVisible(true);
//					FountainSimController.getInstance().getMod1candle5().setVisible(true);
//					FountainSimController.getInstance().getMod1candle6().setVisible(true);
//					FountainSimController.getInstance().getMod3candle1().setVisible(true);
//					FountainSimController.getInstance().getMod3candle2().setVisible(true);
//					FountainSimController.getInstance().getMod3candle3().setVisible(true);
//					FountainSimController.getInstance().getMod3candle4().setVisible(true);
//					FountainSimController.getInstance().getMod3candle5().setVisible(true);
//					FountainSimController.getInstance().getMod3candle6().setVisible(true);
//					FountainSimController.getInstance().getMod5candle1().setVisible(true);
//					FountainSimController.getInstance().getMod5candle2().setVisible(true);
//					FountainSimController.getInstance().getMod5candle3().setVisible(true);
//					FountainSimController.getInstance().getMod5candle4().setVisible(true);
//					FountainSimController.getInstance().getMod5candle5().setVisible(true);
//					FountainSimController.getInstance().getMod5candle6().setVisible(true);
//					FountainSimController.getInstance().getMod7candle1().setVisible(true);
//					FountainSimController.getInstance().getMod7candle2().setVisible(true);
//					FountainSimController.getInstance().getMod7candle3().setVisible(true);
//					FountainSimController.getInstance().getMod7candle4().setVisible(true);
//					FountainSimController.getInstance().getMod7candle5().setVisible(true);
//					FountainSimController.getInstance().getMod7candle6().setVisible(true);
//
//
//					
//  				
//					if (newValue.doubleValue()==0){
//  					kv25 = new KeyValue(FountainSimController.getInstance().getMod1candle1().visibleProperty(), false);
//      				kv26 = new KeyValue(FountainSimController.getInstance().getMod1candle2().visibleProperty(), false);      				
//      				kv27 = new KeyValue(FountainSimController.getInstance().getMod1candle3().visibleProperty(), false);
//      				kv28 = new KeyValue(FountainSimController.getInstance().getMod1candle4().visibleProperty(), false);      				
//      				kv29 = new KeyValue(FountainSimController.getInstance().getMod1candle5().visibleProperty(), false);
//      				kv30 = new KeyValue(FountainSimController.getInstance().getMod1candle6().visibleProperty(), false);
//      				
//      				kv31 = new KeyValue(FountainSimController.getInstance().getMod3candle1().visibleProperty(), false);
//      				kv32 = new KeyValue(FountainSimController.getInstance().getMod3candle2().visibleProperty(), false);      				
//      				kv33 = new KeyValue(FountainSimController.getInstance().getMod3candle3().visibleProperty(), false);
//      				kv34 = new KeyValue(FountainSimController.getInstance().getMod3candle4().visibleProperty(), false);      				
//      				kv35 = new KeyValue(FountainSimController.getInstance().getMod3candle5().visibleProperty(), false);
//      				kv36 = new KeyValue(FountainSimController.getInstance().getMod3candle6().visibleProperty(), false);
//      				
//      				kv37 = new KeyValue(FountainSimController.getInstance().getMod5candle1().visibleProperty(), false);
//      				kv38 = new KeyValue(FountainSimController.getInstance().getMod5candle2().visibleProperty(), false);      				
//      				kv39 = new KeyValue(FountainSimController.getInstance().getMod5candle3().visibleProperty(), false);
//      				kv40 = new KeyValue(FountainSimController.getInstance().getMod5candle4().visibleProperty(), false);      				
//      				kv41 = new KeyValue(FountainSimController.getInstance().getMod5candle5().visibleProperty(), false);
//      				kv42 = new KeyValue(FountainSimController.getInstance().getMod5candle6().visibleProperty(), false);
//      			
//      				kv43 = new KeyValue(FountainSimController.getInstance().getMod7candle1().visibleProperty(), false);
//      				kv44 = new KeyValue(FountainSimController.getInstance().getMod7candle2().visibleProperty(), false);      				
//      				kv45 = new KeyValue(FountainSimController.getInstance().getMod7candle3().visibleProperty(), false);
//      				kv46 = new KeyValue(FountainSimController.getInstance().getMod7candle4().visibleProperty(), false);      				
//      				kv47 = new KeyValue(FountainSimController.getInstance().getMod7candle5().visibleProperty(), false);
//      				kv48 = new KeyValue(FountainSimController.getInstance().getMod7candle6().visibleProperty(), false);
//      				
//      				
//  				}
// 				
// 				
// 				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1candle1().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod1candle2().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod1candle3().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod1candle4().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv5 = new KeyValue(FountainSimController.getInstance().getMod1candle5().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv6 = new KeyValue(FountainSimController.getInstance().getMod1candle6().endYProperty(), ((35*newValue.doubleValue())));
// 				
// 				final KeyValue kv7 = new KeyValue(FountainSimController.getInstance().getMod3candle1().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv8 = new KeyValue(FountainSimController.getInstance().getMod3candle2().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv9 = new KeyValue(FountainSimController.getInstance().getMod3candle3().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv10 = new KeyValue(FountainSimController.getInstance().getMod3candle4().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv11 = new KeyValue(FountainSimController.getInstance().getMod3candle5().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv12 = new KeyValue(FountainSimController.getInstance().getMod3candle6().endYProperty(), ((35*newValue.doubleValue())));
// 				
// 				final KeyValue kv13 = new KeyValue(FountainSimController.getInstance().getMod5candle1().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv14 = new KeyValue(FountainSimController.getInstance().getMod5candle2().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv15 = new KeyValue(FountainSimController.getInstance().getMod5candle3().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv16 = new KeyValue(FountainSimController.getInstance().getMod5candle4().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv17 = new KeyValue(FountainSimController.getInstance().getMod5candle5().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv18 = new KeyValue(FountainSimController.getInstance().getMod5candle6().endYProperty(), ((35*newValue.doubleValue())));
// 				
// 				final KeyValue kv19 = new KeyValue(FountainSimController.getInstance().getMod7candle1().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv20 = new KeyValue(FountainSimController.getInstance().getMod7candle2().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv21 = new KeyValue(FountainSimController.getInstance().getMod7candle3().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv22 = new KeyValue(FountainSimController.getInstance().getMod7candle4().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv23 = new KeyValue(FountainSimController.getInstance().getMod7candle5().endYProperty(), ((35*newValue.doubleValue())));
// 				final KeyValue kv24 = new KeyValue(FountainSimController.getInstance().getMod7candle6().endYProperty(), ((35*newValue.doubleValue())));
//
// 				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4, kv5, kv6,
// 																		kv7, kv8, kv9, kv10, kv11, kv12,
// 																		kv13, kv14, kv15, kv16, kv17, kv18,
// 																		kv19, kv20, kv21, kv22, kv23, kv24,
// 																		kv25, kv26, kv27, kv28, kv29, kv30, kv31, kv32,
// 																		kv33, kv34, kv35, kv36, kv37, kv38, kv39, kv40,
// 																		kv41, kv42, kv43, kv44, kv45, kv46, kv47, kv48);
// 				timeline.getKeyFrames().add(kf);
// 				timeline.play();     				
// 				System.out.println(newValue);
// 			}
// 		});
// 		
// 	// Listen for Slider value changes
// 	 		candleB.valueProperty().addListener(new ChangeListener<Number>() {
// 	 			@Override
// 	 			public void changed(ObservableValue<? extends Number> observable,
// 	 					Number oldValue, Number newValue) {
// 	 				final Timeline timeline = new Timeline();
// 	 				timeline.setCycleCount(1);
//
// 	 				KeyValue kv25 = null;
//					KeyValue kv26 = null;
//					KeyValue kv27 = null;
//					KeyValue kv28 = null;
//					KeyValue kv29 = null;
//					KeyValue kv30 = null;
//					KeyValue kv31 = null;
//					KeyValue kv32 = null;
//					KeyValue kv33 = null;
//					KeyValue kv34 = null;
//					KeyValue kv35 = null;
//					KeyValue kv36 = null;
//					KeyValue kv37 = null;
//					KeyValue kv38 = null;
//					KeyValue kv39 = null;
//					KeyValue kv40 = null;
//					KeyValue kv41 = null;
//					KeyValue kv42 = null;
//				
//					FountainSimController.getInstance().getMod2candle1().setVisible(true);
//					FountainSimController.getInstance().getMod2candle2().setVisible(true);
//					FountainSimController.getInstance().getMod2candle3().setVisible(true);
//					FountainSimController.getInstance().getMod2candle4().setVisible(true);
//					FountainSimController.getInstance().getMod2candle5().setVisible(true);
//					FountainSimController.getInstance().getMod2candle6().setVisible(true);
//					FountainSimController.getInstance().getMod4candle1().setVisible(true);
//					FountainSimController.getInstance().getMod4candle2().setVisible(true);
//					FountainSimController.getInstance().getMod4candle3().setVisible(true);
//					FountainSimController.getInstance().getMod4candle4().setVisible(true);
//					FountainSimController.getInstance().getMod4candle5().setVisible(true);
//					FountainSimController.getInstance().getMod4candle6().setVisible(true);
//					FountainSimController.getInstance().getMod6candle1().setVisible(true);
//					FountainSimController.getInstance().getMod6candle2().setVisible(true);
//					FountainSimController.getInstance().getMod6candle3().setVisible(true);
//					FountainSimController.getInstance().getMod6candle4().setVisible(true);
//					FountainSimController.getInstance().getMod6candle5().setVisible(true);
//					FountainSimController.getInstance().getMod6candle6().setVisible(true);
//  				
//					if (newValue.doubleValue()==0){
//  					kv25 = new KeyValue(FountainSimController.getInstance().getMod2candle1().visibleProperty(), false);
//      				kv26 = new KeyValue(FountainSimController.getInstance().getMod2candle2().visibleProperty(), false);      				
//      				kv27 = new KeyValue(FountainSimController.getInstance().getMod2candle3().visibleProperty(), false);
//      				kv28 = new KeyValue(FountainSimController.getInstance().getMod2candle4().visibleProperty(), false);      				
//      				kv29 = new KeyValue(FountainSimController.getInstance().getMod2candle5().visibleProperty(), false);
//      				kv30 = new KeyValue(FountainSimController.getInstance().getMod2candle6().visibleProperty(), false);
//      				
//      				kv31 = new KeyValue(FountainSimController.getInstance().getMod4candle1().visibleProperty(), false);
//      				kv32 = new KeyValue(FountainSimController.getInstance().getMod4candle2().visibleProperty(), false);      				
//      				kv33 = new KeyValue(FountainSimController.getInstance().getMod4candle3().visibleProperty(), false);
//      				kv34 = new KeyValue(FountainSimController.getInstance().getMod4candle4().visibleProperty(), false);      				
//      				kv35 = new KeyValue(FountainSimController.getInstance().getMod4candle5().visibleProperty(), false);
//      				kv36 = new KeyValue(FountainSimController.getInstance().getMod4candle6().visibleProperty(), false);
//      				
//      				kv37 = new KeyValue(FountainSimController.getInstance().getMod6candle1().visibleProperty(), false);
//      				kv38 = new KeyValue(FountainSimController.getInstance().getMod6candle2().visibleProperty(), false);      				
//      				kv39 = new KeyValue(FountainSimController.getInstance().getMod6candle3().visibleProperty(), false);
//      				kv40 = new KeyValue(FountainSimController.getInstance().getMod6candle4().visibleProperty(), false);      				
//      				kv41 = new KeyValue(FountainSimController.getInstance().getMod6candle5().visibleProperty(), false);
//      				kv42 = new KeyValue(FountainSimController.getInstance().getMod6candle6().visibleProperty(), false);
//      			
//  				}
// 	 				
// 	 				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2candle1().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod2candle2().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod2candle3().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod2candle4().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv5 = new KeyValue(FountainSimController.getInstance().getMod2candle5().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv6 = new KeyValue(FountainSimController.getInstance().getMod2candle6().endYProperty(), ((35*newValue.doubleValue())));
// 	 				
// 	 				final KeyValue kv7 = new KeyValue(FountainSimController.getInstance().getMod4candle1().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv8 = new KeyValue(FountainSimController.getInstance().getMod4candle2().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv9 = new KeyValue(FountainSimController.getInstance().getMod4candle3().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv10 = new KeyValue(FountainSimController.getInstance().getMod4candle4().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv11 = new KeyValue(FountainSimController.getInstance().getMod4candle5().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv12 = new KeyValue(FountainSimController.getInstance().getMod4candle6().endYProperty(), ((35*newValue.doubleValue())));
// 	 				
// 	 				final KeyValue kv13 = new KeyValue(FountainSimController.getInstance().getMod6candle1().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv14 = new KeyValue(FountainSimController.getInstance().getMod6candle2().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv15 = new KeyValue(FountainSimController.getInstance().getMod6candle3().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv16 = new KeyValue(FountainSimController.getInstance().getMod6candle4().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv17 = new KeyValue(FountainSimController.getInstance().getMod6candle5().endYProperty(), ((35*newValue.doubleValue())));
// 	 				final KeyValue kv18 = new KeyValue(FountainSimController.getInstance().getMod6candle6().endYProperty(), ((35*newValue.doubleValue())));
// 	 				
//
// 	 				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4, kv5, kv6,
// 	 																		kv7, kv8, kv9, kv10, kv11, kv12,
// 	 																		kv13, kv14, kv15, kv16, kv17, kv18,
// 	 																		kv25, kv26, kv27, kv28, kv29, kv30, kv31, kv32,
// 	 																		kv33, kv34, kv35, kv36, kv37, kv38, kv39, kv40,
// 	 																		kv41, kv42);
// 	 				timeline.getKeyFrames().add(kf);
// 	 				timeline.play();     				
// 	 				System.out.println(newValue);
// 	 			}
// 	 		});
 		
//     // Listen for Slider value changes
//     		r1A.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r1B.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				//final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		}); 
//     		
//     	// Listen for Slider value changes
//     		r2A.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring2().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r2B.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				//final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r3A.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring3().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r3B.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				//final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r4A.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring4().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r4B.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				//final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r5A.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring5().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
//     	// Listen for Slider value changes
//     		r5B.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring5().heightProperty(), ((35*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
     		
//     	// Listen for Slider value changes
//     		bz.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				KeyValue kv2 = null;
//     				KeyValue kv3 = null;
//     				KeyValue kv4 = null;
//     				KeyValue kv5 = null;
//     				KeyValue kv6 = null;
//     				KeyValue kv7 = null;
//
//     				timeline.setCycleCount(1);
//     				if (newValue.doubleValue() > 0.0){
//     					FountainSimController.getInstance().getBazooka1().setVisible(true);
//     					FountainSimController.getInstance().getBazooka2().setVisible(true);
//     					
//     					kv2 = new KeyValue(FountainSimController.getInstance().getBazooka1().endXProperty(), (10+(100*newValue.doubleValue())));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getBazooka1().endYProperty(), (5+(47*newValue.doubleValue())));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getBazooka2().endXProperty(), (10+1320-(100*newValue.doubleValue())));
//         				kv5 = new KeyValue(FountainSimController.getInstance().getBazooka2().endYProperty(), (5+(47*newValue.doubleValue())));
//
//
//     				}
//     				else{
//     					
//     					kv2 = new KeyValue(FountainSimController.getInstance().getBazooka1().endXProperty(), (10+(100*newValue.doubleValue())));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getBazooka1().endYProperty(), (10+(47*newValue.doubleValue())));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getBazooka2().endXProperty(), (1310-(100*newValue.doubleValue())));
//         				kv5 = new KeyValue(FountainSimController.getInstance().getBazooka2().endYProperty(), (10+(47*newValue.doubleValue())));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getBazooka1().visibleProperty(), false);
//         				kv7 = new KeyValue(FountainSimController.getInstance().getBazooka2().visibleProperty(), false);
//     					
//     				}
//
////     				final KeyValue kv2 = new KeyValue(FountainSimController.getInstance().getBazooka1().endXProperty(), (10+(100*newValue.doubleValue())));
////     				final KeyValue kv3 = new KeyValue(FountainSimController.getInstance().getBazooka1().endYProperty(), (5+(47*newValue.doubleValue())));
////     				final KeyValue kv4 = new KeyValue(FountainSimController.getInstance().getBazooka2().endXProperty(), (10+1320-(100*newValue.doubleValue())));
////     				final KeyValue kv5 = new KeyValue(FountainSimController.getInstance().getBazooka2().endYProperty(), (5+(47*newValue.doubleValue())));
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv2, kv3, kv4, kv5, kv6, kv7);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();  
//     				
//     			}
//     		});
     		
     		
     	// Listen for Slider value changes
//     		pk.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				
//     				KeyValue kv2 = null;
//     				KeyValue kv3 = null;
//     				KeyValue kv4 = null;
//     				KeyValue kv5 = null;
//     				KeyValue kv6 = null;
//     				KeyValue kv7 = null;
//     				KeyValue kv8 = null;
//     				KeyValue kv9 = null;
//     				KeyValue kv10 = null;
//     				KeyValue kv11 = null;
//     				KeyValue kv12 = null;
//     				KeyValue kv13 = null;
//     				KeyValue kv14 = null;
//     				KeyValue kv15 = null;
//     				KeyValue kv16 = null;
//     				KeyValue kv17 = null;
//     				KeyValue kv18 = null;
//     				KeyValue kv19 = null;
//     				KeyValue kv20 = null;
//     				KeyValue kv21 = null;
//     				KeyValue kv22 = null;
//     				KeyValue kv23 = null;
//     				KeyValue kv24 = null;
//     				KeyValue kv25 = null;
//     				KeyValue kv26 = null;
//     				KeyValue kv27 = null;
//     				KeyValue kv28 = null;
//
//     				timeline.setCycleCount(1);
//     				if (newValue.doubleValue() > 0.0){
//     					
//     					FountainSimController.getInstance().getPeacock1().setVisible(true);
//     					FountainSimController.getInstance().getPeacock2().setVisible(true);
//     					FountainSimController.getInstance().getPeacock3().setVisible(true);
//     					FountainSimController.getInstance().getPeacock4().setVisible(true);
//     					FountainSimController.getInstance().getPeacock5().setVisible(true);
//     					FountainSimController.getInstance().getPeacock6().setVisible(true);
//     					FountainSimController.getInstance().getPeacock7().setVisible(true);
//     					FountainSimController.getInstance().getPeacock8().setVisible(true);
//     					FountainSimController.getInstance().getPeacock9().setVisible(true);
//     					
//     					kv2 = new KeyValue(FountainSimController.getInstance().getPeacock1().endXProperty(), (689+(102.2*newValue.doubleValue())));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getPeacock1().endYProperty(), (5+(26*newValue.doubleValue())));
//         				
//         				kv4 = new KeyValue(FountainSimController.getInstance().getPeacock2().endXProperty(), (688+(66.4*newValue.doubleValue())));
//         				kv5 = new KeyValue(FountainSimController.getInstance().getPeacock2().endYProperty(), (17+(36.6*newValue.doubleValue())));
//
//         				kv6 = new KeyValue(FountainSimController.getInstance().getPeacock3().endXProperty(), (683+(38.4*newValue.doubleValue())));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getPeacock3().endYProperty(), (28+(39.4*newValue.doubleValue())));
//         				
//         				kv8 = new KeyValue(FountainSimController.getInstance().getPeacock4().endXProperty(), (675+(18*newValue.doubleValue())));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getPeacock4().endYProperty(), (37+(39.6*newValue.doubleValue())));
//         				
//         				kv10 = new KeyValue(FountainSimController.getInstance().getPeacock5().endXProperty(), (665+(0*newValue.doubleValue())));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getPeacock5().endYProperty(), (40+(40*newValue.doubleValue())));
//         				
//         				kv12 = new KeyValue(FountainSimController.getInstance().getPeacock6().endXProperty(), (655+(-18*newValue.doubleValue())));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getPeacock6().endYProperty(), (37+(39.6*newValue.doubleValue())));
//         				
//         				kv14 = new KeyValue(FountainSimController.getInstance().getPeacock7().endXProperty(), (647+(-39.4*newValue.doubleValue())));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getPeacock7().endYProperty(), (28+(39.4*newValue.doubleValue())));
//         				
//         				kv16 = new KeyValue(FountainSimController.getInstance().getPeacock8().endXProperty(), (643+(-66.6*newValue.doubleValue())));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getPeacock8().endYProperty(), (17+(36.6*newValue.doubleValue())));
//         				
//         				kv18 = new KeyValue(FountainSimController.getInstance().getPeacock9().endXProperty(), (642+(-101.4*newValue.doubleValue())));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getPeacock9().endYProperty(), (5+(26*newValue.doubleValue())));
//         				
//
//
//     				}
//     				else{
//     					
//     					kv2 = new KeyValue(FountainSimController.getInstance().getPeacock1().endXProperty(), (689+(102.2*newValue.doubleValue())));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getPeacock1().endYProperty(), (5+(26*newValue.doubleValue())));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getPeacock2().endXProperty(), (688+(66.4*newValue.doubleValue())));
//         				kv5 = new KeyValue(FountainSimController.getInstance().getPeacock2().endYProperty(), (17+(36.6*newValue.doubleValue())));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getPeacock3().endXProperty(), (683+(38.4*newValue.doubleValue())));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getPeacock3().endYProperty(), (28+(39.4*newValue.doubleValue())));         				
//         				kv8 = new KeyValue(FountainSimController.getInstance().getPeacock4().endXProperty(), (675+(18*newValue.doubleValue())));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getPeacock4().endYProperty(), (37+(39.6*newValue.doubleValue())));         				
//         				kv10 = new KeyValue(FountainSimController.getInstance().getPeacock5().endXProperty(), (665+(0*newValue.doubleValue())));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getPeacock5().endYProperty(), (40+(40*newValue.doubleValue())));         				
//         				kv12 = new KeyValue(FountainSimController.getInstance().getPeacock6().endXProperty(), (655+(-18*newValue.doubleValue())));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getPeacock6().endYProperty(), (37+(39.6*newValue.doubleValue())));         				
//         				kv14 = new KeyValue(FountainSimController.getInstance().getPeacock7().endXProperty(), (647+(-39.4*newValue.doubleValue())));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getPeacock7().endYProperty(), (28+(39.4*newValue.doubleValue())));         				
//         				kv16 = new KeyValue(FountainSimController.getInstance().getPeacock8().endXProperty(), (643+(-66.6*newValue.doubleValue())));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getPeacock8().endYProperty(), (17+(36.6*newValue.doubleValue())));         				
//         				kv18 = new KeyValue(FountainSimController.getInstance().getPeacock9().endXProperty(), (642+(-101.4*newValue.doubleValue())));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getPeacock9().endYProperty(), (5+(26*newValue.doubleValue())));
//         				
//         				kv20 = new KeyValue(FountainSimController.getInstance().getPeacock1().visibleProperty(), false);
//         				kv21 = new KeyValue(FountainSimController.getInstance().getPeacock2().visibleProperty(), false);
//         				kv22 = new KeyValue(FountainSimController.getInstance().getPeacock3().visibleProperty(), false);
//         				kv23 = new KeyValue(FountainSimController.getInstance().getPeacock4().visibleProperty(), false);
//         				kv24 = new KeyValue(FountainSimController.getInstance().getPeacock5().visibleProperty(), false);
//         				kv25 = new KeyValue(FountainSimController.getInstance().getPeacock6().visibleProperty(), false);
//         				kv26 = new KeyValue(FountainSimController.getInstance().getPeacock7().visibleProperty(), false);
//         				kv27 = new KeyValue(FountainSimController.getInstance().getPeacock8().visibleProperty(), false);
//         				kv28 = new KeyValue(FountainSimController.getInstance().getPeacock9().visibleProperty(), false);
//
//     				}
//
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv2, kv3, kv4, kv5, kv6, kv7, kv8,
//     						kv9, kv10, kv11, kv12, kv13, kv14, kv15, kv16, kv17, kv18, kv19, kv20, kv21,
//     						kv22, kv23, kv24, kv25, kv26, kv27, kv28);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();  
//     				
//     			}
//     		});
     		
//     		ftC.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getFrontCurtain().heightProperty(), ((40*newValue.doubleValue())));
//     				
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
                
//     		bkC.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getBackCurtain().heightProperty(), ((40*newValue.doubleValue())));
//     				
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
     		
//     		sp.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				//timeline.setAutoReverse(true);
//     				final KeyValue kv1 = new KeyValue(FountainSimController.getInstance().getSpoutRec().heightProperty(), ((40*newValue.doubleValue())));
//     				
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     			}
//     		});
     		
//     	// Listen for Slider value changes
//     		mxA.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				
//     				KeyValue kv1 = null;
//     				KeyValue kv2 = null;
//     				KeyValue kv3 = null;
//     				KeyValue kv4 = null;
//     				KeyValue kv5 = null;
//     				KeyValue kv6 = null;
//     				KeyValue kv7 = null;
//     				KeyValue kv8 = null;
//     				KeyValue kv9 = null;
//     				KeyValue kv10 = null;
//     				KeyValue kv11 = null;
//     				KeyValue kv12 = null;
//     				KeyValue kv13 = null;
//     				KeyValue kv14 = null;
//     				KeyValue kv15 = null;
//     				KeyValue kv16 = null;
//     				KeyValue kv17 = null;
//     				KeyValue kv18 = null;
//     				KeyValue kv19 = null;
//     				KeyValue kv20 = null;
//     				KeyValue kv21 = null;
//     				//timeline.setAutoReverse(true);
//     				if(newValue.doubleValue() == 1.0 || newValue.doubleValue() == 0.0 ){
//     				kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv5 = new KeyValue(FountainSimController.getInstance().getMod1ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv6 = new KeyValue(FountainSimController.getInstance().getMod3ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv7 = new KeyValue(FountainSimController.getInstance().getMod5ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv8 = new KeyValue(FountainSimController.getInstance().getMod7ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv9 = new KeyValue(FountainSimController.getInstance().getMod1ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv10 = new KeyValue(FountainSimController.getInstance().getMod3ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv11 = new KeyValue(FountainSimController.getInstance().getMod5ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv12 = new KeyValue(FountainSimController.getInstance().getMod7ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv13 = new KeyValue(FountainSimController.getInstance().getMod1ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv14 = new KeyValue(FountainSimController.getInstance().getMod3ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv15 = new KeyValue(FountainSimController.getInstance().getMod5ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv16 = new KeyValue(FountainSimController.getInstance().getMod7ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv17 = new KeyValue(FountainSimController.getInstance().getMod1ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				kv18 = new KeyValue(FountainSimController.getInstance().getMod3ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				kv19 = new KeyValue(FountainSimController.getInstance().getMod5ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				kv20 = new KeyValue(FountainSimController.getInstance().getMod7ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				}
//     				
//     				if(newValue.doubleValue() == 2.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod1ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod3ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod5ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv8 = new KeyValue(FountainSimController.getInstance().getMod7ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod1ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod3ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod5ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv12 = new KeyValue(FountainSimController.getInstance().getMod7ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod1ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod3ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod5ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv16 = new KeyValue(FountainSimController.getInstance().getMod7ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod1ring5().heightProperty(), ((35*newValue.doubleValue())));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod3ring5().heightProperty(), ((35*newValue.doubleValue())));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod5ring5().heightProperty(), ((35*newValue.doubleValue())));
//         				kv20 = new KeyValue(FountainSimController.getInstance().getMod7ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				}
//     				
//     				if(newValue.doubleValue() == 3.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod1ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod3ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod5ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv8 = new KeyValue(FountainSimController.getInstance().getMod7ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod1ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod3ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod5ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv12 = new KeyValue(FountainSimController.getInstance().getMod7ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod1ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod3ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod5ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv16 = new KeyValue(FountainSimController.getInstance().getMod7ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod1ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod3ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod5ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv20 = new KeyValue(FountainSimController.getInstance().getMod7ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//     				}
//     				
//     				if(newValue.doubleValue() == 4.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod1ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod3ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod5ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv8 = new KeyValue(FountainSimController.getInstance().getMod7ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod1ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod3ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod5ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv12 = new KeyValue(FountainSimController.getInstance().getMod7ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod1ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod3ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod5ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv16 = new KeyValue(FountainSimController.getInstance().getMod7ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod1ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod3ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod5ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv20 = new KeyValue(FountainSimController.getInstance().getMod7ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//     				}
//     				
//     				if(newValue.doubleValue() == 5.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod1ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod3ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod5ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//         				kv4 = new KeyValue(FountainSimController.getInstance().getMod7ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod1ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod3ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod5ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv8 = new KeyValue(FountainSimController.getInstance().getMod7ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod1ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod3ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod5ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv12 = new KeyValue(FountainSimController.getInstance().getMod7ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod1ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod3ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod5ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv16 = new KeyValue(FountainSimController.getInstance().getMod7ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod1ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod3ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod5ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv20 = new KeyValue(FountainSimController.getInstance().getMod7ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//     				}
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3, kv4,
//     																		kv5, kv6, kv7, kv8,
//     																		kv9, kv10, kv11, kv12,
//     																		kv13, kv14, kv15, kv16,
//     																		kv17, kv18, kv19, kv20);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
//     		
     		
//     	// Listen for Slider value changes
//     		mxB.valueProperty().addListener(new ChangeListener<Number>() {
//     			@Override
//     			public void changed(ObservableValue<? extends Number> observable,
//     					Number oldValue, Number newValue) {
//     				final Timeline timeline = new Timeline();
//     				timeline.setCycleCount(1);
//     				
//     				KeyValue kv1 = null;
//     				KeyValue kv2 = null;
//     				KeyValue kv3 = null;
//     				KeyValue kv4 = null;
//     				KeyValue kv5 = null;
//     				KeyValue kv6 = null;
//     				KeyValue kv7 = null;
//     				KeyValue kv8 = null;
//     				KeyValue kv9 = null;
//     				KeyValue kv10 = null;
//     				KeyValue kv11 = null;
//     				KeyValue kv12 = null;
//     				KeyValue kv13 = null;
//     				KeyValue kv14 = null;
//     				KeyValue kv15 = null;
//     				KeyValue kv16 = null;
//     				KeyValue kv17 = null;
//     				KeyValue kv18 = null;
//     				KeyValue kv19 = null;
//     				KeyValue kv20 = null;
//     				KeyValue kv21 = null;
//     				//timeline.setAutoReverse(true);
//     				if(newValue.doubleValue() == 1.0 || newValue.doubleValue() == 0.0 ){
//     				kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring1().heightProperty(), ((35*newValue.doubleValue())));
//     				kv5 = new KeyValue(FountainSimController.getInstance().getMod2ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv6 = new KeyValue(FountainSimController.getInstance().getMod4ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv7 = new KeyValue(FountainSimController.getInstance().getMod6ring2().heightProperty(), ((35*newValue.doubleValue())));
//     				kv9 = new KeyValue(FountainSimController.getInstance().getMod2ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv10 = new KeyValue(FountainSimController.getInstance().getMod4ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv11 = new KeyValue(FountainSimController.getInstance().getMod6ring3().heightProperty(), ((35*newValue.doubleValue())));
//     				kv13 = new KeyValue(FountainSimController.getInstance().getMod2ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv14 = new KeyValue(FountainSimController.getInstance().getMod4ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv15 = new KeyValue(FountainSimController.getInstance().getMod6ring4().heightProperty(), ((35*newValue.doubleValue())));
//     				kv17 = new KeyValue(FountainSimController.getInstance().getMod2ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				kv18 = new KeyValue(FountainSimController.getInstance().getMod4ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				kv19 = new KeyValue(FountainSimController.getInstance().getMod6ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				}
//     				
//     				if(newValue.doubleValue() == 2.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring1().heightProperty(), ((35*(newValue.doubleValue()-1))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod2ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod4ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod6ring2().heightProperty(), ((35*newValue.doubleValue())));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod2ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod4ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod6ring3().heightProperty(), ((35*newValue.doubleValue())));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod2ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod4ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod6ring4().heightProperty(), ((35*newValue.doubleValue())));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod2ring5().heightProperty(), ((35*newValue.doubleValue())));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod4ring5().heightProperty(), ((35*newValue.doubleValue())));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod6ring5().heightProperty(), ((35*newValue.doubleValue())));
//     				}
//     				
//     				if(newValue.doubleValue() == 3.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring1().heightProperty(), ((35*(newValue.doubleValue()-2))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod2ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod4ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod6ring2().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod2ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod4ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod6ring3().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod2ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod4ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod6ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod2ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod4ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod6ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//     				}
//     				
//     				if(newValue.doubleValue() == 4.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring1().heightProperty(), ((35*(newValue.doubleValue()-3))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod2ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod4ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod6ring2().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod2ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod4ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod6ring3().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod2ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod4ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod6ring4().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod2ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod4ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod6ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//     				}
//     				
//     				if(newValue.doubleValue() == 5.0){
//     					kv1 = new KeyValue(FountainSimController.getInstance().getMod2ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//         				kv2 = new KeyValue(FountainSimController.getInstance().getMod4ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//         				kv3 = new KeyValue(FountainSimController.getInstance().getMod6ring1().heightProperty(), ((35*(newValue.doubleValue()-4))));
//     					kv5 = new KeyValue(FountainSimController.getInstance().getMod2ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv6 = new KeyValue(FountainSimController.getInstance().getMod4ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv7 = new KeyValue(FountainSimController.getInstance().getMod6ring2().heightProperty(), ((35*(newValue.doubleValue()-3))));
//         				kv9 = new KeyValue(FountainSimController.getInstance().getMod2ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv10 = new KeyValue(FountainSimController.getInstance().getMod4ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv11 = new KeyValue(FountainSimController.getInstance().getMod6ring3().heightProperty(), ((35*(newValue.doubleValue()-2))));
//         				kv13 = new KeyValue(FountainSimController.getInstance().getMod2ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv14 = new KeyValue(FountainSimController.getInstance().getMod4ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv15 = new KeyValue(FountainSimController.getInstance().getMod6ring4().heightProperty(), ((35*(newValue.doubleValue()-1))));
//         				kv17 = new KeyValue(FountainSimController.getInstance().getMod2ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv18 = new KeyValue(FountainSimController.getInstance().getMod4ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//         				kv19 = new KeyValue(FountainSimController.getInstance().getMod6ring5().heightProperty(), ((35*(newValue.doubleValue()-0))));
//     				}
//     				final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3,
//     																		kv5, kv6, kv7,
//     																		kv9, kv10, kv11,
//     																		kv13, kv14, kv15,
//     																		kv17, kv18, kv19);
//     				timeline.getKeyFrames().add(kf);
//     				timeline.play();     				
//     				System.out.println(newValue);
//     			}
//     		});
     		

        
    }
    private void configureModules(){
        fountain = Main.getFountain();
        A = fountain.getA();
        B = fountain.getB();
        setupAModule();
        setupBModule();
        peacock = fountain.getPeacock();
        bazooka = fountain.getBazooka();
        spout = fountain.getSpout();
        bkCurt = fountain.getBkCurt();
        ftCurt = fountain.getFtCurt();
        setupIndependentCannons();
    }
    
    /**
     *
     * @param list
     * @param aB
     * @param ce
     * @param s
     */
    public void setupCannonSliderChangeListener(ArrayList<? extends Cannon> list, 
            ModuleGroup aB, ModuleEnum me, CannonEnum ce, Slider s, Slider paired){
        
        list = aB.getCannonGroup(ce);
        CannonSliderChangeListener<? extends Cannon> cs = new CannonSliderChangeListener<>(list, me.getModule());
        s.valueProperty().addListener(cs);
        SliderMouseReleasedEvent se = new SliderMouseReleasedEvent(ce, me, cs, paired);
        s.setOnMouseReleased(se);
    }

    private void setupAModule() {
        setupCannonSliderChangeListener(rings1A, A, ModuleEnum.A, CannonEnum.RING1, r1A, r1B);

        setupCannonSliderChangeListener(rings2A, A, ModuleEnum.A, CannonEnum.RING2, r2A, r2B);

        setupCannonSliderChangeListener(rings3A, A, ModuleEnum.A, CannonEnum.RING3, r3A, r3B);

        setupCannonSliderChangeListener(rings4A, A, ModuleEnum.A, CannonEnum.RING4, r4A, r4B);

        setupCannonSliderChangeListener(rings5A, A, ModuleEnum.A, CannonEnum.RING5, r5A, r5B);

        setupCannonSliderChangeListener(multisA, A, ModuleEnum.A, CannonEnum.MULTI, mxA, mxB);

        setupCannonSliderChangeListener(candlesA, A, ModuleEnum.A, CannonEnum.CANDELABRA, 
                candleA, candleB);

        setupCannonSliderChangeListener(sweepsA, A, ModuleEnum.A, CannonEnum.SWEEP, swA, swB);
    }


    /**
     * 
     */
    private void setupBModule() {
        setupCannonSliderChangeListener(rings1B, B, ModuleEnum.B, CannonEnum.RING1, r1B, r1A);

        setupCannonSliderChangeListener(rings2B, B, ModuleEnum.B, CannonEnum.RING2, r2B, r2A);

        setupCannonSliderChangeListener(rings3B, B, ModuleEnum.B, CannonEnum.RING3, r3B, r3A);

        setupCannonSliderChangeListener(rings4B, B, ModuleEnum.B, CannonEnum.RING4, r4B, r4A);

        setupCannonSliderChangeListener(rings5B, B, ModuleEnum.B, CannonEnum.RING5, r5B, r5A);

        setupCannonSliderChangeListener(multisB, B, ModuleEnum.B, CannonEnum.MULTI, mxB, mxA);

        setupCannonSliderChangeListener(candlesB, B, ModuleEnum.B, CannonEnum.CANDELABRA, candleB, candleA);

        setupCannonSliderChangeListener(sweepsB, B, ModuleEnum.B, CannonEnum.SWEEP, swB, swA);
    }
    
    private void setupIndependentCannons() {
    	setupIndepentCannonListeners(bz, CannonEnum.BAZOOKA, bazooka);
        setupIndepentCannonListeners(sp, CannonEnum.SPOUT, spout);
        setupIndepentCannonListeners(pk, CannonEnum.PEACOCK, peacock);
        setupIndepentCannonListeners(bkC, CannonEnum.BKCURT, bkCurt);
        setupIndepentCannonListeners(ftC, CannonEnum.FTCURT, ftCurt);
    }

    public void setupIndepentCannonListeners(Slider s, CannonEnum ce, IndependentCannon ic) {
        IndependentCannonSliderChangeListener bzIn = new IndependentCannonSliderChangeListener(ic);
        s.valueProperty().addListener(bzIn);
        IndependentSliderMouseReleasedEvent se = new IndependentSliderMouseReleasedEvent(ce, bzIn);
        s.setOnMouseReleased(se);
    }


    /**
     * @return the fountain
     */
    public Fountain getFountain() {
            return fountain;
    }

    /**
     * @param fountain the fountain to set
     */
    public void setFountain(Fountain fountain) {
            this.fountain = fountain;
    }
    
    public synchronized void setSlidersWithFcw(ArrayList<FCW> fcws) {
        for(FCW f: fcws) {
            if(!f.getIsWater()) {
                fcws.remove(f);
            }
        }
//        resetAllSliders();
        Iterator<FCW> it = fcws.iterator();
        while(it.hasNext()) {
            FCW f = it.next();
            String[] actions = FCWLib.getInstance().reverseLookupData(f);
//            String module = findModule(actions);
            
            for(String action: actions) {
            switch(f.getAddr()) {
                case 1:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        r5A.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        r5B.setValue(level);
                    }
                break;
                case 2:
                      if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        r4A.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        r4B.setValue(level);
                    }
                break;
                case 3:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        r3A.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        r3B.setValue(level);
                    }
                break;
                case 4:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        r2A.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        r2B.setValue(level);
                    }
                break;
                case 5:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        r1A.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        r1B.setValue(level);
                    }
                break;
                case 6:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        swA.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        swB.setValue(level);
                    }
                break; 
                case 7:
                    if(action.equalsIgnoreCase("spout")) {
                        int level = findLevel(actions);
                        getSp().setValue(level);
                    }
                    else if(action.equalsIgnoreCase("bazooka")) {
                        int level = findLevel(actions);
                        bz.setValue(level);
                    }
                break;
                case 8:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        candleA.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        candleB.setValue(level);
                    }
                break;
                case 9:
                    if(action.equalsIgnoreCase("peacock")){
                        int level = findLevel(actions);
                        pk.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("ftcurt")) {
                        int level = findLevel(actions);
                        ftC.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("bkcurt")) {
                        int level = findLevel(actions);
                        bkC.setValue(level);
                    }
                    break;
                case 48:
                    if(action.equalsIgnoreCase("modulea")){
                        int level = findLevel(actions);
                        mxA.setValue(level);
                    }
                    else if(action.equalsIgnoreCase("moduleb")) {
                        int level = findLevel(actions);
                        mxB.setValue(level);
                    }
                    break;
                //Sweeps Speeds!
                case 33:
                case 34:
                case 38:
                case 39:
                    if(action.equalsIgnoreCase("offreset")) {
                        
                    }
                    else if(action.equalsIgnoreCase("short")) {
                        
                    }
                    else if(action.equalsIgnoreCase("long")) {
                        
                    }
                    else if(action.equalsIgnoreCase("largo")) {
                        
                    }
                    else if(action.equalsIgnoreCase("adagio")) {
                        
                    }
                    else if(action.equalsIgnoreCase("andante")) {
                        
                    }
                    else if(action.equalsIgnoreCase("moderato")) {
                        
                    }
                    else if(action.equalsIgnoreCase("allegreto")) {
                        
                    }
                    else if(action.equalsIgnoreCase("allegro")) {
                        
                    }
                    else if(action.equalsIgnoreCase("presto")) {
                        
                    }
                    else if(action.equalsIgnoreCase("playpause")) {
                        
                    }
                case 35:
                case 36:
                case 37:
                    SpecialoperationsController.getInstance().setSweeps(f);
                    break;
            }
            }
        }
    }
//    
//    public String findModule(String[] input) {
//        for(String action: input) {
//            if(action.equalsIgnoreCase("modulea")){
//                return "A";
//            }
//            else if(action.equalsIgnoreCase("moduleb")){
//                return "B";
//            }
//            else if(action.equalsIgnoreCase("ftcurt")) {
//                return "A";
//            }
//        }
//        return null;
//    }
    
    public int findLevel(String[] input) {
        for(String action: input) {
            switch(action){
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;
                case "6":
                    return 6;
            }
            
        }
        return 0;
    }

    public void resetAllSliders() {
       for(Slider s: allSliders) {
           s.setValue(0);
       }
    }

    /**
     * @return the sp
     */
    public Slider getSp() {
        return sp;
    }

    /**
     * @param sp the sp to set
     */
    public void setSp(Slider sp) {
        this.sp = sp;
    }

    public void killSlidersPane() {
        slidersPane.setVisible(false);
    }
    
    public void resurrectSlidersPane() {
        slidersPane.setVisible(true);
    }
}
                   

