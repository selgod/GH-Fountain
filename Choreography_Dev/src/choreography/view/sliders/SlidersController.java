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
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
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
import choreography.view.specialOperations.SpecialoperationsController;

/**
 *
 * @author elementsking
 */
public class SlidersController {

	private static SlidersController instance;

	public static SlidersController getInstance() {
		if (instance == null) {
			return instance = new SlidersController();
		}
		return instance;
	}

	private Fountain fountain;
	private ModuleGroup A;
	private ModuleGroup B;
	private ArrayList<Ring> rings1A, rings1B, rings2A, rings2B, rings3A, rings3B, rings4A, rings4B, rings5A, rings5B;
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

	@FXML
	private HBox slidersPane;

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
		allSliders = new Slider[] { r1A, r1B, r2A, r2B, r3A, r3B, r4A, r4B, r5A, r5B, mxA, mxB, candleA, candleB, swA, swB, ftC, bkC, pk, bz, sp };
		instance = this;
	}

	private void configureModules() {
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
	public void setupCannonSliderChangeListener(ArrayList<? extends Cannon> list, ModuleGroup aB, ModuleEnum me, CannonEnum ce, Slider s, Slider paired) {

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

		setupCannonSliderChangeListener(candlesA, A, ModuleEnum.A, CannonEnum.CANDELABRA, candleA, candleB);

		setupCannonSliderChangeListener(sweepsA, A, ModuleEnum.A, CannonEnum.SWEEP, swA, swB);
	}

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
	 * @param fountain
	 *            the fountain to set
	 */
	public void setFountain(Fountain fountain) {
		this.fountain = fountain;
	}

	/**
	 * Uses the fcw to adjust the height of the sliders
	 * So that when the simulation is paused, the sliders show
	 * what the value of the water is supposed to be. When the simulation
	 * is playing the sliders default
	 * @param fcws
	 */
	public synchronized void setSlidersWithFcw(ArrayList<FCW> fcws) {
		// Concurrency issues with for each loops -> therefore must use normal
		// for loop
		for (int g = 0; g < fcws.size(); g++) {
			FCW f = fcws.get(g);
			if (!f.getIsWater()) {
				fcws.remove(f);
			}
		}
		// resetAllSliders();
		Iterator<FCW> it = fcws.iterator();
		while (it.hasNext()) {
			FCW f = it.next();
			String[] actions = FCWLib.getInstance().reverseLookupData(f);
			// String module = findModule(actions);

			for (String action : actions) {
				switch (f.getAddr()) {
				case 1:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						r1A.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						r1B.setValue(level);
					}
					break;
				case 2:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						r2A.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						r2B.setValue(level);
					}
					break;
				case 3:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						r3A.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						r3B.setValue(level);
					}
					break;
				case 4:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						r4A.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						r4B.setValue(level);
					}
					break;
				case 5:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						r5A.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						r5B.setValue(level);
					}
					break;
				case 6:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						swA.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						swB.setValue(level);
					}
					break;
				case 7:
					if (action.equalsIgnoreCase("spout")) {
						int level = findLevel(actions);
						getSp().setValue(level);
					} else if (action.equalsIgnoreCase("bazooka")) {
						int level = findLevel(actions);
						bz.setValue(level);
					}
					break;
				case 8:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						candleA.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						candleB.setValue(level);
					}
					break;
				case 9:
					if (action.equalsIgnoreCase("peacock")) {
						int level = findLevel(actions);
						pk.setValue(level);
					} else if (action.equalsIgnoreCase("ftcurt")) {
						int level = findLevel(actions);
						ftC.setValue(level);
					} else if (action.equalsIgnoreCase("bkcurt")) {
						int level = findLevel(actions);
						bkC.setValue(level);
					}
					break;
				case 48:
					if (action.equalsIgnoreCase("modulea")) {
						int level = findLevel(actions);
						mxA.setValue(level);
					} else if (action.equalsIgnoreCase("moduleb")) {
						int level = findLevel(actions);
						mxB.setValue(level);
					}
					break;
				// Sweeps Speeds!
				case 33:
				case 34:
				case 38:
				case 39:
					if (action.equalsIgnoreCase("offreset")) {

					} else if (action.equalsIgnoreCase("short")) {

					} else if (action.equalsIgnoreCase("long")) {

					} else if (action.equalsIgnoreCase("largo")) {

					} else if (action.equalsIgnoreCase("adagio")) {

					} else if (action.equalsIgnoreCase("andante")) {

					} else if (action.equalsIgnoreCase("moderato")) {

					} else if (action.equalsIgnoreCase("allegreto")) {

					} else if (action.equalsIgnoreCase("allegro")) {

					} else if (action.equalsIgnoreCase("presto")) {

					} else if (action.equalsIgnoreCase("playpause")) {

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
	// public String findModule(String[] input) {
	// for(String action: input) {
	// if(action.equalsIgnoreCase("modulea")){
	// return "A";
	// }
	// else if(action.equalsIgnoreCase("moduleb")){
	// return "B";
	// }
	// else if(action.equalsIgnoreCase("ftcurt")) {
	// return "A";
	// }
	// }
	// return null;
	// }

	public int findLevel(String[] input) {
		for (String action : input) {
			switch (action) {
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
		for (Slider s : allSliders) {
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
	 * @param sp
	 *            the sp to set
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
