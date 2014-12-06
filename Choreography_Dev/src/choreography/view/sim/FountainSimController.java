package choreography.view.sim;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import choreography.io.FCWLib;
import choreography.io.LagTimeLibrary;
import choreography.model.color.ColorPaletteModel;
import choreography.model.fcw.FCW;
import choreography.view.music.MusicPaneController;

public class FountainSimController implements Initializable {

	private static FountainSimController instance;

	// These timelines are for the animations of the sweepers, not to be
	// confused with timeline.java
	Timeline leftSweepTimeline = new Timeline();
	Timeline rightSweepTimeline = new Timeline();
	int sweepLevel = 0;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane fountainPane;

	@FXML
	private Group mod5;
	@FXML
	private Rectangle mod3ring4;

	@FXML
	private Group mod4;

	@FXML
	private Group mod3;

	@FXML
	private Rectangle mod3ring2;

	@FXML
	private Rectangle mod6ring4;

	@FXML
	private Group mod2;

	@FXML
	private Rectangle mod6ring5;

	@FXML
	private Slider ring4Slider;

	@FXML
	private Group mod7;

	@FXML
	private Group mod6;

	@FXML
	private MenuButton ring5Level;

	@FXML
	private Slider ring1Slider;

	@FXML
	private Group mod1;

	@FXML
	private Rectangle mod6ring2;

	@FXML
	private Rectangle mod6ring3;

	@FXML
	private Slider ring3Slider;

	@FXML
	private Rectangle mod6ring1;

	@FXML
	private Rectangle mod3ring3;

	@FXML
	private Slider ring5Slider;

	@FXML
	private Rectangle mod3ring1;

	@FXML
	private Rectangle mod3ring5;

	@FXML
	private Rectangle mod4ring1;

	@FXML
	private Rectangle mod7ring4;

	@FXML
	private Rectangle mod7ring3;

	@FXML
	private Rectangle mod2ring5;

	@FXML
	private Rectangle mod7ring2;

	@FXML
	private Rectangle mod2ring4;

	@FXML
	private Rectangle mod7ring1;

	@FXML
	private Rectangle mod2ring3;

	@FXML
	private Rectangle mod5ring4;

	@FXML
	private Rectangle mod2ring2;

	@FXML
	private Rectangle mod5ring3;

	@FXML
	private Rectangle mod2ring1;

	@FXML
	private Rectangle mod5ring5;

	@FXML
	private Rectangle mod5ring2;

	@FXML
	private Rectangle mod5ring1;

	@FXML
	private Rectangle mod7ring5;

	@FXML
	private Slider ring2Slider;

	@FXML
	private Rectangle mod1ring1;

	@FXML
	private Rectangle mod4ring2;

	@FXML
	private Rectangle mod1ring2;

	@FXML
	private Rectangle mod4ring3;

	@FXML
	private Rectangle mod4ring4;

	@FXML
	private Rectangle mod4ring5;

	@FXML
	private Rectangle mod1ring5;

	@FXML
	private Rectangle mod1ring3;

	@FXML
	private Rectangle mod1ring4;

	@FXML
	private QuadCurve bazookaB;

	@FXML
	private Line bazooka1;

	@FXML
	private Line bazooka2;

	@FXML
	private Line bazooka3;

	@FXML
	private Line bazooka4;

	@FXML
	private Line bazooka5;

	@FXML
	private Line peacock1;

	@FXML
	private Line peacock2;

	@FXML
	private Line peacock3;

	@FXML
	private Line peacock4;

	@FXML
	private Line peacock5;

	@FXML
	private Line peacock6;

	@FXML
	private Line peacock7;

	@FXML
	private Line peacock8;

	@FXML
	private Line peacock9;

	@FXML
	private Rectangle frontCurtain1;

	@FXML
	private Rectangle frontCurtain2;

	@FXML
	private Rectangle frontCurtain3;

	@FXML
	private Rectangle frontCurtain4;

	@FXML
	private Rectangle frontCurtain5;

	@FXML
	private Rectangle frontCurtain6;

	@FXML
	private Rectangle frontCurtain7;

	@FXML
	private Rectangle frontCurtain8;

	@FXML
	private Rectangle frontCurtain9;

	@FXML
	private Rectangle frontCurtain10;

	@FXML
	private Rectangle frontCurtain11;

	@FXML
	private Rectangle frontCurtain12;

	@FXML
	private Rectangle frontCurtain13;

	@FXML
	private Rectangle frontCurtain14;

	@FXML
	private Rectangle backCurtain1;

	@FXML
	private Rectangle backCurtain2;

	@FXML
	private Rectangle backCurtain3;

	@FXML
	private Rectangle backCurtain4;

	@FXML
	private Rectangle backCurtain5;

	@FXML
	private Rectangle backCurtain6;

	@FXML
	private Rectangle backCurtain7;

	@FXML
	private Rectangle backCurtain8;

	@FXML
	private Rectangle backCurtain9;

	@FXML
	private Rectangle backCurtain10;

	@FXML
	private Rectangle backCurtain11;

	@FXML
	private Rectangle backCurtain12;

	@FXML
	private Rectangle backCurtain13;

	@FXML
	private Rectangle backCurtain14;

	@FXML
	private Line mod1sweep1;

	@FXML
	private Line mod1sweep2;

	@FXML
	private Line mod1candle1;

	@FXML
	private Line mod1candle2;

	@FXML
	private Line mod1candle3;

	@FXML
	private Line mod1candle4;

	@FXML
	private Line mod1candle5;

	@FXML
	private Line mod1candle6;

	@FXML
	private Line mod2sweep1;

	@FXML
	private Line mod2sweep2;

	@FXML
	private Line mod2candle1;

	@FXML
	private Line mod2candle2;

	@FXML
	private Line mod2candle3;

	@FXML
	private Line mod2candle4;

	@FXML
	private Line mod2candle5;

	@FXML
	private Line mod2candle6;

	@FXML
	private Line mod3sweep1;

	@FXML
	private Line mod3sweep2;

	@FXML
	private Line mod3candle1;

	@FXML
	private Line mod3candle2;

	@FXML
	private Line mod3candle3;

	@FXML
	private Line mod3candle4;

	@FXML
	private Line mod3candle5;

	@FXML
	private Line mod3candle6;

	@FXML
	private Line mod4sweep1;

	@FXML
	private Line mod4sweep2;

	@FXML
	private Line mod4candle1;

	@FXML
	private Line mod4candle2;

	@FXML
	private Line mod4candle3;

	@FXML
	private Line mod4candle4;

	@FXML
	private Line mod4candle5;

	@FXML
	private Line mod4candle6;

	@FXML
	private Line mod5sweep1;

	@FXML
	private Line mod5sweep2;

	@FXML
	private Line mod5candle1;

	@FXML
	private Line mod5candle2;

	@FXML
	private Line mod5candle3;

	@FXML
	private Line mod5candle4;

	@FXML
	private Line mod5candle5;

	@FXML
	private Line mod5candle6;

	@FXML
	private Line mod6sweep1;

	@FXML
	private Line mod6sweep2;

	@FXML
	private Line mod6candle1;

	@FXML
	private Line mod6candle2;

	@FXML
	private Line mod6candle3;

	@FXML
	private Line mod6candle4;

	@FXML
	private Line mod6candle5;

	@FXML
	private Line mod6candle6;

	@FXML
	private Line mod7sweep1;

	@FXML
	private Line mod7sweep2;

	@FXML
	private Line mod7candle1;

	@FXML
	private Line mod7candle2;

	@FXML
	private Line mod7candle3;

	@FXML
	private Line mod7candle4;

	@FXML
	private Line mod7candle5;

	@FXML
	private Line mod7candle6;

	@FXML
	private Rectangle spoutRec;

	// Used to keep track of what the current motion is for the sweepers
	private String sweepCommand = "None";

	/*
	 * Tracks the synchonization of the sweeps. 0 = Independant 1 = Together 2 =
	 * Opposite
	 */
	private int sweepType = 1;

	// Speed factors for the sweeper motions
	private double leftSweepSpeed = 1;
	private double rightSweepSpeed = 1;

	private ConcurrentNavigableMap<Integer, ArrayList<FCW>> bufferedFcws = new ConcurrentSkipListMap<Integer, ArrayList<FCW>>();

	public static FountainSimController getInstance() {
		if (instance == null)
			instance = new FountainSimController();
		return instance;
	}

	// The method that is used to animate the simulation
	public void playSim() {
		for (Integer i : bufferedFcws.keySet()) {
			drawFcw(bufferedFcws.get(i));
		}
	}

	/**
	 * This method is used to redraw the simulation given the current fcw at a
	 * moment of time. There is a switch statement where each case is a
	 * different address. This switch statement is extremely long, 1000's of
	 * lines. It could use a lot of refactoring, but it does work correctly.
	 * 
	 * @param fcws
	 */
	public void drawFcw(ArrayList<FCW> fcws) {
		if (fcws != null) {
			Iterator<FCW> it = fcws.iterator();
			while (it.hasNext()) {
				double lagTime = 0;
				FCW f = it.next();
				String[] actions = FCWLib.getInstance().reverseLookupData(f);
				final int ll = -45; // Left Long
				final int ls = -30; // Left Short
				final int lv = -15; // Left Very Short
				final int rl = 45; // Right Long
				final int rs = 30; // Right Short
				final int rv = 15; // Right Very Short
				
				
				double fading[][] = new double[7][3];
				Color crossFading[][] = new Color[7][2];
				
				if (f.getIsWater() && f.getAddr() != 99) {
					lagTime = LagTimeLibrary.getInstance().getLagTimeInSeconds(f);
				}
				ArrayList<String> actionsList = new ArrayList<>();
				for (String s : actions) {
					actionsList.add(s);
				}
				switch (f.getAddr()) {

				// Draws Ring 5
				case 5:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing5A(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing5B(level, lagTime);
					}
					break;

				// Draws Ring 4
				case 4:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing4A(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing4B(level, lagTime);
					}
					break;

				// Draws Ring 3
				case 3:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing3A(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing3B(level, lagTime);
					}
					break;

				// Draws Ring 2
				case 2:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing2A(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing2B(level, lagTime);
					}
					break;

				// Draws Ring 1
				case 1:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing1A(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawRing1B(level, lagTime);
					}
					break;

				// Draws Sweeps, does not do any animations
				case 6:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawSweepsA(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawSweepsB(level, lagTime);
					}
					break;

				// Draws Spoud/Voice and Bazooka
				// Apex has these two water features on the same address for
				// some odd reason
				case 7:
					if (actionsList.contains("SPOUT")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawSpout(level, lagTime);
					}
					if (actionsList.contains("BAZOOKA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawBazooka(level, lagTime);
					}
					break;

				// Draws the Candelabras
				case 8:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawCandlesA(level, lagTime);
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawCandlesB(level, lagTime);
					}
					break;

				// Draws the Front and Back Curtain, and the Peacock
				case 9:
					if (actionsList.contains("FTCURT")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawFtCurtain(level, lagTime);
					}
					if (actionsList.contains("PEACOCK")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawPeacock(level, lagTime);
					}
					if (actionsList.contains("BKCURT")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						drawBkCurtain(level, lagTime);
					}
					break;

				// Legacy command for the sweeper animations
				// Controls both of the sweeper sets with speed and motion and
				// has them moving together
				case 33:

					// Stops all motions
					if (actionsList.contains("OFFRESET")) {
						mod1sweep1.getTransforms().clear();
						mod2sweep1.getTransforms().clear();
						mod3sweep1.getTransforms().clear();
						mod4sweep1.getTransforms().clear();
						mod5sweep1.getTransforms().clear();
						mod6sweep1.getTransforms().clear();
						mod7sweep1.getTransforms().clear();
						mod1sweep2.getTransforms().clear();
						mod2sweep2.getTransforms().clear();
						mod3sweep2.getTransforms().clear();
						mod4sweep2.getTransforms().clear();
						mod5sweep2.getTransforms().clear();
						mod6sweep2.getTransforms().clear();
						mod7sweep2.getTransforms().clear();
					}

					// Animates the sweepers if the are paused or pauses them if
					// they are moving
					if (actionsList.contains("PLAYPAUSE")) {

						if (leftSweepTimeline.getStatus() == Animation.Status.PAUSED) {
							leftSweepTimeline.pause();
							rightSweepTimeline.pause();
						} else {
							leftSweepTimeline.play();
							rightSweepTimeline.play();
						}
					}
					if (actionsList.contains("LARGO")) {
						leftSweepTimeline.setRate(0.2);
						leftSweepSpeed = 4.0;
						rightSweepTimeline.setRate(0.2);
						rightSweepSpeed = 4.0;
					}
					if (actionsList.contains("ADAGIO")) {
						leftSweepTimeline.setRate(0.5);
						leftSweepSpeed = 3.0;
						rightSweepTimeline.setRate(0.5);
						rightSweepSpeed = 3.0;
					}
					if (actionsList.contains("ANDANTE")) {
						leftSweepTimeline.setRate(0.8);
						leftSweepSpeed = 2.5;
						rightSweepTimeline.setRate(0.8);
						rightSweepSpeed = 2.5;
					}
					if (actionsList.contains("MODERATO")) {
						leftSweepTimeline.setRate(1.0);
						leftSweepSpeed = 1.0;
						rightSweepTimeline.setRate(1.0);
						rightSweepSpeed = 1.0;
					}
					if (actionsList.contains("ALLEGRETO")) {
						leftSweepTimeline.setRate(1.3);
						leftSweepSpeed = 1.8;
						rightSweepTimeline.setRate(1.3);
						rightSweepSpeed = 1.8;
					}
					if (actionsList.contains("ALLEGRO")) {
						leftSweepTimeline.setRate(1.5);
						leftSweepSpeed = 1.6;
						rightSweepTimeline.setRate(1.5);
						rightSweepSpeed = 1.6;
					}
					if (actionsList.contains("PRESTO")) {
						leftSweepTimeline.setRate(1.8);
						leftSweepSpeed = 1.5;
						rightSweepTimeline.setRate(1.8);
						rightSweepSpeed = 1.5;
					}

					// Animates the sweepers in a sweeping motion from -30 to 30
					// degrees
					if (actionsList.contains("SHORT")) {
						if (sweepCommand != "SHORT") {
							sweepCommand = "SHORT";
							sweepLeftSweeps(ls, rs);
							sweepRightSweeps(ls, rs);
						}
					}

					// Animates the sweepers in a sweeping motion from -45 to 45
					// degrees
					if (actionsList.contains("LONG")) {
						if (sweepCommand != "LONG") {
							sweepCommand = "LONG";
							sweepLeftSweeps(ll, rl);
							sweepRightSweeps(ll, rl);
						}
					}
					break;

				// Same as case 33 except sweepers move against each other.
				case 34:
					if (actionsList.contains("OFFRESET")) {
						mod1sweep1.getTransforms().clear();
						mod2sweep1.getTransforms().clear();
						mod3sweep1.getTransforms().clear();
						mod4sweep1.getTransforms().clear();
						mod5sweep1.getTransforms().clear();
						mod6sweep1.getTransforms().clear();
						mod7sweep1.getTransforms().clear();
						mod1sweep2.getTransforms().clear();
						mod2sweep2.getTransforms().clear();
						mod3sweep2.getTransforms().clear();
						mod4sweep2.getTransforms().clear();
						mod5sweep2.getTransforms().clear();
						mod6sweep2.getTransforms().clear();
						mod7sweep2.getTransforms().clear();
					}
					if (actionsList.contains("PLAYPAUSE")) {

						if (leftSweepTimeline.getStatus() == Animation.Status.PAUSED) {
							leftSweepTimeline.pause();
							rightSweepTimeline.pause();
						} else {
							leftSweepTimeline.play();
							rightSweepTimeline.play();
						}
					}
					if (actionsList.contains("LARGO")) {
						leftSweepTimeline.setRate(0.2);
						leftSweepSpeed = 4.0;
						rightSweepTimeline.setRate(0.2);
						rightSweepSpeed = 4.0;
					}
					if (actionsList.contains("ADAGIO")) {
						leftSweepTimeline.setRate(0.5);
						leftSweepSpeed = 3.0;
						rightSweepTimeline.setRate(0.5);
						rightSweepSpeed = 3.0;
					}
					if (actionsList.contains("ANDANTE")) {
						leftSweepTimeline.setRate(0.8);
						leftSweepSpeed = 2.5;
						rightSweepTimeline.setRate(0.8);
						rightSweepSpeed = 2.5;
					}
					if (actionsList.contains("MODERATO")) {
						leftSweepTimeline.setRate(1.0);
						leftSweepSpeed = 1.0;
						rightSweepTimeline.setRate(1.0);
						rightSweepSpeed = 1.0;
					}
					if (actionsList.contains("ALLEGRETO")) {
						leftSweepTimeline.setRate(1.3);
						leftSweepSpeed = 1.8;
						rightSweepTimeline.setRate(1.3);
						rightSweepSpeed = 1.8;
					}
					if (actionsList.contains("ALLEGRO")) {
						leftSweepTimeline.setRate(1.5);
						leftSweepSpeed = 1.6;
						rightSweepTimeline.setRate(1.5);
						rightSweepSpeed = 1.6;
					}
					if (actionsList.contains("PRESTO")) {
						leftSweepTimeline.setRate(1.8);
						leftSweepSpeed = 1.5;
						rightSweepTimeline.setRate(1.8);
						rightSweepSpeed = 1.5;
					}
					if (actionsList.contains("SHORT")) {
						if (sweepCommand != "SHORT") {
							sweepCommand = "SHORT";
							sweepLeftSweeps(ls, rs);
							sweepRightSweeps(rs, ls);
						}
					}
					if (actionsList.contains("LONG")) {
						if (sweepCommand != "LONG") {
							sweepCommand = "LONG";
							sweepLeftSweeps(ll, rl);
							sweepRightSweeps(rl, ll);
						}
					}
					break;

				/*
				 * New sweep commands that animates both left and right sweeps
				 * Sweep type 1 is together and 2 is opposed Oscillating
				 * sweepers are like a fan. -30...0...30...0...-30 repeat
				 * Sweeping sweepers work from left to right.
				 * -30...0...30...-30...0...30 repeat They only known problem is
				 * that the sweepers jump from command to command. Say the
				 * sweeper is at 17 degrees and it gets a command to sweep from
				 * -45 t0 45. The line will just jump to -45 from wherever it is
				 * at and start sweeping. We looked at fixing the problem but
				 * could not find a working solution since java keeps no record
				 * of the current state of an animation since it uses keyframes.
				 * Maybe there is a workaround we couldn't find or something
				 * will be implemented in a new javafx update. One possible
				 * option is creating a virtual line. Given the complexity of
				 * this option we did not implement it.
				 */
				case 35:
					// Action list contains what sweeper action is supposed to
					// be occuring at each tenth of a second.
					if (actionsList.contains("RIGHTSHORTLEFTLONG")) { // 39
						// Checks to make sure the most recent command is not
						// rs,ll. If true, then nothing, else make current
						// command rs, ll and animate sweepers accordingly
						if (sweepCommand != "RIGHTSHORTLEFTLONG") {
							sweepCommand = "RIGHTSHORTLEFTLONG";
							if (sweepType == 1) {
								sweepLeftSweeps(rs, ll);
								sweepRightSweeps(rs, ll);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rs, ll);
								sweepRightSweeps(ll, rs);
							}
						}
					}
					if (actionsList.contains("RIGHTSHORTLEFTSHORT")) { // 38
						if (sweepCommand != "RIGHTSHORTLEFTSHORT") {
							sweepCommand = "RIGHTSHORTLEFTSHORT";
							if (sweepType == 1) {
								sweepLeftSweeps(rs, ls);
								sweepRightSweeps(rs, ls);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rs, ls);
								sweepRightSweeps(ls, rs);
							}
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTLONG")) { // 23
						if (sweepCommand != "RIGHTLONGLEFTLONG") {
							sweepCommand = "RIGHTLONGLEFTLONG";
							if (sweepType == 1) {
								sweepLeftSweeps(rl, ll);
								sweepRightSweeps(rl, ll);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rl, ll);
								sweepRightSweeps(ll, rs);
							}
						}
					}
					if (actionsList.contains("HOLDRIGHTLONG")) { // 17
						if (sweepCommand != "HOLDRIGHTLONG") {
							sweepCommand = "HOLDRIGHTLONG";
							sweepLeftSweeps(rl, rl);
							sweepRightSweeps(rl, rl);
						}
					}
					if (actionsList.contains("HOLDRIGHTSHORT")) { // 34
						if (sweepCommand != "HOLDRIGHTSHORT") {
							sweepCommand = "HOLDRIGHTSHORT";
							sweepLeftSweeps(rs, rs);
							sweepRightSweeps(rs, rs);
						}
					}
					if (actionsList.contains("HOLDLEFTLONG")) { // 119
						if (sweepCommand != "HOLDLEFTLONG") {
							sweepCommand = "HOLDLEFTLONG";
							sweepLeftSweeps(ll, ll);
							sweepRightSweeps(ll, ll);
						}
					}
					if (actionsList.contains("HOLDLEFTSHORT")) { // 102
						if (sweepCommand != "HOLDLEFTSHORT") {
							sweepCommand = "HOLDLEFTSHORT";
							sweepLeftSweeps(ls, ls);
							sweepRightSweeps(ls, ls);
						}
					}
					if (actionsList.contains("RIGHTLONGRIGHTVERYSHORT")) { // 19
						if (sweepCommand != "RIGHTLONGRIGHTVERYSHORT") {
							sweepCommand = "RIGHTLONGRIGHTVERYSHORT";
							if (sweepType == 1) {
								sweepLeftSweeps(rl, rv);
								sweepRightSweeps(rl, rv);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rl, rv);
								sweepRightSweeps(rv, rl);
							}
						}
					}
					if (actionsList.contains("RIGHTLONGCENTER")) { // 20
						if (sweepCommand != "RIGHTLONGCENTER") {
							sweepCommand = "RIGHTLONGCENTER";
							if (sweepType == 1) {
								sweepLeftSweeps(rl, 0);
								sweepRightSweeps(rl, 0);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rl, 0);
								sweepRightSweeps(0, rl);
							}
						}
					}
					if (actionsList.contains("RIGHTSHORTCENTER")) { // 36
						if (sweepCommand != "RIGHTSHORTCENTER") {
							sweepCommand = "RIGHTSHORTCENTER";
							if (sweepType == 1) {
								sweepLeftSweeps(rs, 0);
								sweepRightSweeps(rs, 0);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rs, 0);
								sweepRightSweeps(0, rs);
							}
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTSHORT")) { // 22
						if (sweepCommand != "RIGHTLONGLEFTSHORT") {
							sweepCommand = "RIGHTLONGLEFTSHORT";
							if (sweepType == 1 || sweepType == 0) {
								sweepLeftSweeps(rl, ls);
								sweepRightSweeps(rl, ls);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rl, ls);
								sweepRightSweeps(ls, rl);
							}
						}
					}
					if (actionsList.contains("HOLDCENTER")) { // 0,68
						if (sweepCommand != "HOLDCENTER") {
							sweepCommand = "HOLDCENTER";
							sweepLeftSweeps(0, 0);
							sweepRightSweeps(0, 0);
						}
					}
					if (actionsList.contains("CENTERLEFTSHORT")) { // 70
						if (sweepCommand != "CENTERLEFTSHORT") {
							sweepCommand = "CENTERLEFTSHORT";
							if (sweepType == 1 || sweepType == 0) {
								sweepLeftSweeps(0, ls);
								sweepRightSweeps(0, ls);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(0, ls);
								sweepRightSweeps(ls, 0);
							}
						}
					}
					if (actionsList.contains("OSCLEFTVERYSHORT")) { // 69
						if (sweepCommand != "OSCLEFTVERYSHORT") {
							sweepCommand = "OSCLEFTVERYSHORT";
							if (sweepType == 1) {
								oscillateLeftSweeps(lv, 0);
								oscillateRightSweeps(lv, 0);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(lv, 0);
								oscillateRightSweeps(0, lv);
							}
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTVERYSHORT")) { // 21
						if (sweepCommand != "RIGHTLONGLEFTVERYSHORT") {
							sweepCommand = "RIGHTLONGLEFTVERYSHORT";
							if (sweepType == 1) {
								sweepLeftSweeps(rl, lv);
								sweepRightSweeps(rl, lv);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rl, lv);
								sweepRightSweeps(lv, rl);
							}
						}
					}
					if (actionsList.contains("RIGHTSHORTLEFTVERYSHORT")) { // 37
						if (sweepCommand != "RIGHTSHORTLEFTVERYSHORT") {
							sweepCommand = "RIGHTSHORTLEFTVERYSHORT";
							if (sweepType == 1) {
								sweepLeftSweeps(rs, lv);
								sweepRightSweeps(rs, lv);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rs, lv);
								sweepRightSweeps(lv, rs);
							}
						}
					}
					if (actionsList.contains("RIGHTVERYSHORTLEFTSHORT")) { // 54
						if (sweepCommand != "RIGHTVERYSHORTLEFTSHORT") {
							sweepCommand = "RIGHTVERYSHORTLEFTSHORT";
							if (sweepType == 1) {
								sweepLeftSweeps(rv, ls);
								sweepRightSweeps(rv, ls);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rv, ls);
								sweepRightSweeps(ls, rv);
							}
						}
					}
					if (actionsList.contains("RIGHTVERYSHORTLEFTLONG")) { // 55
						if (sweepCommand != "RIGHTVERYSHORTLEFTLONG") {
							sweepCommand = "RIGHTVERYSHORTLEFTLONG";
							if (sweepType == 1) {
								sweepLeftSweeps(rv, ll);
								sweepRightSweeps(rv, ll);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(rv, ll);
								sweepRightSweeps(ll, rv);
							}
						}
					}
					if (actionsList.contains("CENTERLEFTLONG")) { // 71
						if (sweepCommand != "CENTERLEFTLONG") {
							sweepCommand = "CENTERLEFTLONG";
							if (sweepType == 1) {
								sweepLeftSweeps(0, ll);
								sweepRightSweeps(0, ll);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(0, ll);
								sweepRightSweeps(ll, 0);
							}
						}
					}
					if (actionsList.contains("LEFTVERYSHORTLEFTLONG")) { // 87
						if (sweepCommand != "LEFTVERYSHORTLEFTLONG") {
							sweepCommand = "LEFTVERYSHORTLEFTLONG";
							if (sweepType == 1) {
								sweepLeftSweeps(lv, ll);
								sweepRightSweeps(lv, ll);
							}
							if (sweepType == 2) {
								sweepLeftSweeps(lv, ll);
								sweepRightSweeps(ll, lv);
							}
						}
					}
					if (actionsList.contains("HOLDRIGHTVERYSHORT")) { // 51
						if (sweepCommand != "HOLDRIGHTVERYSHORT") {
							sweepCommand = "HOLDRIGHTVERYSHORT";
							if (sweepType == 1) {
								oscillateLeftSweeps(rv, rv);
								sweepRightSweeps(rv, rv);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(rv, rv);
								oscillateRightSweeps(rv, rv);
							}
						}
					}
					if (actionsList.contains("HOLDLEFTVERYSHORT")) { // 85
						if (sweepCommand != "HOLDLEFTVERYSHORT") {
							sweepCommand = "HOLDLEFTVERYSHORT";
							if (sweepType == 1) {
								oscillateLeftSweeps(lv, lv);
								oscillateRightSweeps(lv, lv);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(lv, lv);
								oscillateRightSweeps(lv, lv);
							}
						}
					}
					if (actionsList.contains("OSCRIGHTLONG")) { // 18
						if (sweepCommand != "OSCRIGHTLONG") {
							sweepCommand = "OSCRIGHTLONG";
							if (sweepType == 1) {
								oscillateLeftSweeps(rl, rs);
								oscillateRightSweeps(rl, rs);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(rl, rs);
								oscillateRightSweeps(rs, rl);
							}
						}
					}
					if (actionsList.contains("OSCRIGHTSHORT")) { // 35
						if (sweepCommand != "OSCRIGHTSHORT") {
							sweepCommand = "OSCRIGHTSHORT";
							if (sweepType == 1) {
								oscillateLeftSweeps(rs, rv);
								oscillateRightSweeps(rs, rv);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(rs, rv);
								oscillateRightSweeps(rv, rs);
							}
						}
					}
					if (actionsList.contains("OSCRIGHTVERYSHORT")) { // 52
						if (sweepCommand != "OSCRIGHTVERYSHORT") {
							sweepCommand = "OSCRIGHTVERYSHORT";
							if (sweepType == 1) {
								oscillateLeftSweeps(rv, 0);
								oscillateRightSweeps(rv, 0);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(rv, 0);
								oscillateRightSweeps(0, rv);
							}
						}
					}
					if (actionsList.contains("OSCCENTER")) { // 53
						if (sweepCommand != "OSCCENTER") {
							sweepCommand = "OSCCENTER";
							if (sweepType == 1) {
								oscillateLeftSweeps(lv, rv);
								oscillateRightSweeps(lv, rv);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(lv, rv);
								oscillateRightSweeps(rv, lv);
							}
						}
					}
					if (actionsList.contains("OSCLEFTSHORT")) { // 86
						if (sweepCommand != "OSCLEFTSHORT") {
							sweepCommand = "OSCLEFTSHORT";
							if (sweepType == 1) {
								oscillateLeftSweeps(ls, lv);
								oscillateRightSweeps(ls, lv);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(ls, lv);
								oscillateRightSweeps(lv, ls);
							}
						}
					}
					if (actionsList.contains("OSCLEFTLONG")) { // 103
						if (sweepCommand != "OSCLEFTLONG") {
							sweepCommand = "OSCLEFTLONG";
							if (sweepType == 1) {
								oscillateLeftSweeps(ll, ls);
								oscillateRightSweeps(ll, ls);
							}
							if (sweepType == 2) {
								oscillateLeftSweeps(ll, ls);
								oscillateRightSweeps(ls, ll);
							}
						}
					}

					break;
				// Same as case 36 but used for independent sweeping of the left
				// sweepers
				case 36:
					if (actionsList.contains("RIGHTSHORTLEFTLONG")) { // 39
						if (sweepCommand != "RIGHTSHORTLEFTLONG") {
							sweepCommand = "RIGHTSHORTLEFTLONG";
							sweepLeftSweeps(rs, ll);
						}
					}
					if (actionsList.contains("RIGHTSHORTLEFTSHORT")) { // 38
						if (sweepCommand != "RIGHTSHORTLEFTSHORT") {
							sweepCommand = "RIGHTSHORTLEFTSHORT";
							sweepLeftSweeps(rs, ls);
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTLONG")) { // 23
						if (sweepCommand != "RIGHTLONGLEFTLONG") {
							sweepCommand = "RIGHTLONGLEFTLONG";
							sweepLeftSweeps(rl, ll);
						}
					}
					if (actionsList.contains("HOLDRIGHTLONG")) { // 17
						if (sweepCommand != "HOLDRIGHTLONG") {
							sweepCommand = "HOLDRIGHTLONG";
							sweepLeftSweeps(rl, rl);
						}
					}
					if (actionsList.contains("HOLDRIGHTSHORT")) { // 34
						if (sweepCommand != "HOLDRIGHTSHORT") {
							sweepCommand = "HOLDRIGHTSHORT";
							sweepLeftSweeps(rs, rs);
						}
					}
					if (actionsList.contains("HOLDLEFTLONG")) { // 119
						if (sweepCommand != "HOLDLEFTLONG") {
							sweepCommand = "HOLDLEFTLONG";
							sweepLeftSweeps(ll, ll);
						}
					}
					if (actionsList.contains("HOLDLEFTSHORT")) { // 102
						if (sweepCommand != "HOLDLEFTSHORT") {
							sweepCommand = "HOLDLEFTSHORT";
							sweepLeftSweeps(ls, ls);
						}
					}
					if (actionsList.contains("RIGHTLONGRIGHTVERYSHORT")) { // 19
						if (sweepCommand != "RIGHTLONGRIGHTVERYSHORT") {
							sweepCommand = "RIGHTLONGRIGHTVERYSHORT";
							sweepLeftSweeps(rl, rv);
						}
					}
					if (actionsList.contains("RIGHTLONGCENTER")) { // 20
						if (sweepCommand != "RIGHTLONGCENTER") {
							sweepCommand = "RIGHTLONGCENTER";
							sweepLeftSweeps(rl, 0);
						}
					}
					if (actionsList.contains("RIGHTSHORTCENTER")) { // 36
						if (sweepCommand != "RIGHTSHORTCENTER") {
							sweepCommand = "RIGHTSHORTCENTER";
							sweepLeftSweeps(rs, 0);
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTSHORT")) { // 22
						if (sweepCommand != "RIGHTLONGLEFTSHORT") {
							sweepCommand = "RIGHTLONGLEFTSHORT";
							sweepLeftSweeps(rl, ls);
						}
					}
					if (actionsList.contains("HOLDCENTER")) { // 0,68
						if (sweepCommand != "HOLDCENTER") {
							sweepCommand = "HOLDCENTER";
							sweepLeftSweeps(0, 0);
						}
					}
					if (actionsList.contains("CENTERLEFTSHORT")) { // 70
						if (sweepCommand != "CENTERLEFTSHORT") {
							sweepCommand = "CENTERLEFTSHORT";
							sweepLeftSweeps(0, ls);
						}
					}
					if (actionsList.contains("OSCLEFTVERYSHORT")) { // 69
						if (sweepCommand != "OSCLEFTVERYSHORT") {
							sweepCommand = "OSCLEFTVERYSHORT";
							oscillateLeftSweeps(lv, 0);
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTVERYSHORT")) { // 21
						if (sweepCommand != "RIGHTLONGLEFTVERYSHORT") {
							sweepCommand = "RIGHTLONGLEFTVERYSHORT";
							sweepLeftSweeps(rl, lv);
						}
					}
					if (actionsList.contains("RIGHTSHORTLEFTVERYSHORT")) { // 37
						if (sweepCommand != "RIGHTSHORTLEFTVERYSHORT") {
							sweepCommand = "RIGHTSHORTLEFTVERYSHORT";
							sweepLeftSweeps(rs, lv);
						}
					}
					if (actionsList.contains("RIGHTVERYSHORTLEFTSHORT")) { // 54
						if (sweepCommand != "RIGHTVERYSHORTLEFTSHORT") {
							sweepCommand = "RIGHTVERYSHORTLEFTSHORT";
							sweepLeftSweeps(rv, ls);
						}
					}
					if (actionsList.contains("RIGHTVERYSHORTLEFTLONG")) { // 55
						if (sweepCommand != "RIGHTVERYSHORTLEFTLONG") {
							sweepCommand = "RIGHTVERYSHORTLEFTLONG";
							sweepLeftSweeps(rv, ll);
						}
					}
					if (actionsList.contains("CENTERLEFTLONG")) { // 71
						if (sweepCommand != "CENTERLEFTLONG") {
							sweepCommand = "CENTERLEFTLONG";
							sweepLeftSweeps(0, ll);
						}
					}
					if (actionsList.contains("LEFTVERYSHORTLEFTLONG")) { // 87
						if (sweepCommand != "LEFTVERYSHORTLEFTLONG") {
							sweepCommand = "LEFTVERYSHORTLEFTLONG";
							sweepLeftSweeps(lv, ll);
						}
					}
					if (actionsList.contains("HOLDRIGHTVERYSHORT")) { // 51
						if (sweepCommand != "HOLDRIGHTVERYSHORT") {
							sweepCommand = "HOLDRIGHTVERYSHORT";
							oscillateLeftSweeps(rv, rv);
						}
					}
					if (actionsList.contains("HOLDLEFTVERYSHORT")) { // 85
						if (sweepCommand != "HOLDLEFTVERYSHORT") {
							sweepCommand = "HOLDLEFTVERYSHORT";
							oscillateLeftSweeps(lv, lv);
						}
					}
					if (actionsList.contains("OSCRIGHTLONG")) { // 18
						if (sweepCommand != "OSCRIGHTLONG") {
							sweepCommand = "OSCRIGHTLONG";
							oscillateLeftSweeps(rl, rs);
						}
					}
					if (actionsList.contains("OSCRIGHTSHORT")) { // 35
						if (sweepCommand != "OSCRIGHTSHORT") {
							sweepCommand = "OSCRIGHTSHORT";
							oscillateLeftSweeps(rs, rv);
						}
					}
					if (actionsList.contains("OSCRIGHTVERYSHORT")) { // 52
						if (sweepCommand != "OSCRIGHTVERYSHORT") {
							sweepCommand = "OSCRIGHTVERYSHORT";
							oscillateLeftSweeps(rv, 0);
						}
					}
					if (actionsList.contains("OSCCENTER")) { // 53
						if (sweepCommand != "OSCCENTER") {
							sweepCommand = "OSCCENTER";
							oscillateLeftSweeps(lv, rv);
						}
					}
					if (actionsList.contains("OSCLEFTSHORT")) { // 86
						if (sweepCommand != "OSCLEFTSHORT") {
							sweepCommand = "OSCLEFTSHORT";
							oscillateLeftSweeps(ls, lv);
						}
					}
					if (actionsList.contains("OSCLEFTLONG")) { // 103
						if (sweepCommand != "OSCLEFTLONG") {
							sweepCommand = "OSCLEFTLONG";
							oscillateLeftSweeps(ll, ls);
						}
					}

					break;
				// Same as case 36 but used for independent sweeping of the
				// right sweepers
				case 37:
					if (actionsList.contains("RIGHTSHORTLEFTLONG")) { // 39
						if (sweepCommand != "RIGHTSHORTLEFTLONG") {
							sweepCommand = "RIGHTSHORTLEFTLONG";
							sweepRightSweeps(rs, ll);
						}
					}
					if (actionsList.contains("RIGHTSHORTLEFTSHORT")) { // 38
						if (sweepCommand != "RIGHTSHORTLEFTSHORT") {
							sweepCommand = "RIGHTSHORTLEFTSHORT";
							sweepRightSweeps(rs, ls);
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTLONG")) { // 23
						if (sweepCommand != "RIGHTLONGLEFTLONG") {
							sweepCommand = "RIGHTLONGLEFTLONG";
							sweepRightSweeps(rl, ll);
						}
					}
					if (actionsList.contains("HOLDRIGHTLONG")) { // 17
						if (sweepCommand != "HOLDRIGHTLONG") {
							sweepCommand = "HOLDRIGHTLONG";
							sweepRightSweeps(rl, rl);
						}
					}
					if (actionsList.contains("HOLDRIGHTSHORT")) { // 34
						if (sweepCommand != "HOLDRIGHTSHORT") {
							sweepCommand = "HOLDRIGHTSHORT";
							sweepRightSweeps(rs, rs);
						}
					}
					if (actionsList.contains("HOLDLEFTLONG")) { // 119
						if (sweepCommand != "HOLDLEFTLONG") {
							sweepCommand = "HOLDLEFTLONG";
							sweepRightSweeps(ll, ll);
						}
					}
					if (actionsList.contains("HOLDLEFTSHORT")) { // 102
						if (sweepCommand != "HOLDLEFTSHORT") {
							sweepCommand = "HOLDLEFTSHORT";
							sweepRightSweeps(ls, ls);
						}
					}
					if (actionsList.contains("RIGHTLONGRIGHTVERYSHORT")) { // 19
						if (sweepCommand != "RIGHTLONGRIGHTVERYSHORT") {
							sweepCommand = "RIGHTLONGRIGHTVERYSHORT";
							sweepRightSweeps(rl, rv);
						}
					}
					if (actionsList.contains("RIGHTLONGCENTER")) { // 20
						if (sweepCommand != "RIGHTLONGCENTER") {
							sweepCommand = "RIGHTLONGCENTER";
							sweepRightSweeps(rl, 0);
						}
					}
					if (actionsList.contains("RIGHTSHORTCENTER")) { // 36
						if (sweepCommand != "RIGHTSHORTCENTER") {
							sweepCommand = "RIGHTSHORTCENTER";
							sweepRightSweeps(rs, 0);
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTSHORT")) { // 22
						if (sweepCommand != "RIGHTLONGLEFTSHORT") {
							sweepCommand = "RIGHTLONGLEFTSHORT";
							sweepRightSweeps(rl, ls);
						}
					}
					if (actionsList.contains("HOLDCENTER")) { // 0,68
						if (sweepCommand != "HOLDCENTER") {
							sweepCommand = "HOLDCENTER";
							sweepRightSweeps(0, 0);
						}
					}
					if (actionsList.contains("CENTERLEFTSHORT")) { // 70
						if (sweepCommand != "CENTERLEFTSHORT") {
							sweepCommand = "CENTERLEFTSHORT";
							sweepRightSweeps(0, ls);
						}
					}
					if (actionsList.contains("OSCLEFTVERYSHORT")) { // 69
						if (sweepCommand != "OSCLEFTVERYSHORT") {
							sweepCommand = "OSCLEFTVERYSHORT";
							oscillateRightSweeps(lv, 0);
						}
					}
					if (actionsList.contains("RIGHTLONGLEFTVERYSHORT")) { // 21
						if (sweepCommand != "RIGHTLONGLEFTVERYSHORT") {
							sweepCommand = "RIGHTLONGLEFTVERYSHORT";
							sweepRightSweeps(rl, lv);

						}
					}
					if (actionsList.contains("RIGHTSHORTLEFTVERYSHORT")) { // 37
						if (sweepCommand != "RIGHTSHORTLEFTVERYSHORT") {
							sweepCommand = "RIGHTSHORTLEFTVERYSHORT";
							sweepRightSweeps(rs, lv);
						}
					}
					if (actionsList.contains("RIGHTVERYSHORTLEFTSHORT")) { // 54
						if (sweepCommand != "RIGHTVERYSHORTLEFTSHORT") {
							sweepCommand = "RIGHTVERYSHORTLEFTSHORT";
							sweepRightSweeps(rv, ls);
						}
					}
					if (actionsList.contains("RIGHTVERYSHORTLEFTLONG")) { // 55
						if (sweepCommand != "RIGHTVERYSHORTLEFTLONG") {
							sweepCommand = "RIGHTVERYSHORTLEFTLONG";
							sweepRightSweeps(rv, ll);
						}
					}
					if (actionsList.contains("CENTERLEFTLONG")) { // 71
						if (sweepCommand != "CENTERLEFTLONG") {
							sweepCommand = "CENTERLEFTLONG";
							sweepRightSweeps(0, ll);
						}
					}
					if (actionsList.contains("LEFTVERYSHORTLEFTLONG")) { // 87
						if (sweepCommand != "LEFTVERYSHORTLEFTLONG") {
							sweepCommand = "LEFTVERYSHORTLEFTLONG";
							sweepRightSweeps(lv, ll);
						}
					}
					if (actionsList.contains("HOLDRIGHTVERYSHORT")) { // 51
						if (sweepCommand != "HOLDRIGHTVERYSHORT") {
							sweepCommand = "HOLDRIGHTVERYSHORT";
							oscillateRightSweeps(rv, rv);
						}
					}
					if (actionsList.contains("HOLDLEFTVERYSHORT")) { // 85
						if (sweepCommand != "HOLDLEFTVERYSHORT") {
							sweepCommand = "HOLDLEFTVERYSHORT";
							oscillateRightSweeps(lv, lv);
						}
					}
					if (actionsList.contains("OSCRIGHTLONG")) { // 18
						if (sweepCommand != "OSCRIGHTLONG") {
							sweepCommand = "OSCRIGHTLONG";
							oscillateRightSweeps(rl, rs);
						}
					}
					if (actionsList.contains("OSCRIGHTSHORT")) { // 35
						if (sweepCommand != "OSCRIGHTSHORT") {
							sweepCommand = "OSCRIGHTSHORT";
							oscillateRightSweeps(rs, rv);
						}
					}
					if (actionsList.contains("OSCRIGHTVERYSHORT")) { // 52
						if (sweepCommand != "OSCRIGHTVERYSHORT") {
							sweepCommand = "OSCRIGHTVERYSHORT";
							oscillateRightSweeps(rv, 0);
						}
					}
					if (actionsList.contains("OSCCENTER")) { // 53
						if (sweepCommand != "OSCCENTER") {
							sweepCommand = "OSCCENTER";
							oscillateRightSweeps(lv, rv);
						}
					}
					if (actionsList.contains("OSCLEFTSHORT")) { // 86
						if (sweepCommand != "OSCLEFTSHORT") {
							sweepCommand = "OSCLEFTSHORT";
							oscillateRightSweeps(ls, lv);
						}
					}
					if (actionsList.contains("OSCLEFTLONG")) { // 103
						if (sweepCommand != "OSCLEFTLONG") {
							sweepCommand = "OSCLEFTLONG";
							oscillateRightSweeps(ll, ls);
						}
					}

					break;
				// Controls the speed of the left sweepers
				case 38:
					if (actionsList.contains("LARGO")) {
						leftSweepTimeline.setRate(0.2);
						leftSweepSpeed = 4.0;
					}
					if (actionsList.contains("ANDANTE")) {
						leftSweepTimeline.setRate(0.8);
						leftSweepSpeed = 3.0;
					}
					if (actionsList.contains("ALLEGRETO")) {
						leftSweepTimeline.setRate(1.3);
						leftSweepSpeed = 2.25;
					}
					if (actionsList.contains("PRESTO")) {
						leftSweepTimeline.setRate(1.8);
						leftSweepSpeed = 1.8;
					}
					if (actionsList.contains("OFFRESET")) {
						mod1sweep1.getTransforms().clear();
						mod2sweep1.getTransforms().clear();
						mod3sweep1.getTransforms().clear();
						mod4sweep1.getTransforms().clear();
						mod5sweep1.getTransforms().clear();
						mod6sweep1.getTransforms().clear();
						mod7sweep1.getTransforms().clear();
					}
					if (actionsList.contains("PLAYPAUSE")) {

						if (leftSweepTimeline.getStatus() == Animation.Status.PAUSED) {
							leftSweepTimeline.play();
						} else {
							leftSweepTimeline.pause();
						}
					}
					break;

				// Controls the speed of the right sweepers
				case 39:
					if (actionsList.contains("LARGO")) {
						rightSweepTimeline.setRate(0.2);
						rightSweepSpeed = 4.0;
					}
					if (actionsList.contains("ANDANTE")) {
						rightSweepTimeline.setRate(0.8);
						rightSweepSpeed = 3.0;
					}
					if (actionsList.contains("ALLEGRETO")) {
						rightSweepTimeline.setRate(1.3);
						rightSweepSpeed = 2.25;
					}
					if (actionsList.contains("PRESTO")) {
						rightSweepTimeline.setRate(1.8);
						rightSweepSpeed = 1.8;
					}
					if (actionsList.contains("OFFRESET")) {
						mod1sweep2.getTransforms().clear();
						mod2sweep2.getTransforms().clear();
						mod3sweep2.getTransforms().clear();
						mod4sweep2.getTransforms().clear();
						mod5sweep2.getTransforms().clear();
						mod6sweep2.getTransforms().clear();
						mod7sweep2.getTransforms().clear();
					}
					if (actionsList.contains("PLAYPAUSE")) {

						if (rightSweepTimeline.getStatus() == Animation.Status.PAUSED) {
							rightSweepTimeline.play();
						} else {
							rightSweepTimeline.pause();
						}
					}
					break;
				// Controls the speeds of both sweepers
				case 40:
					if (actionsList.contains("OFFRESET")) {
						mod1sweep1.getTransforms().clear();
						mod2sweep1.getTransforms().clear();
						mod3sweep1.getTransforms().clear();
						mod4sweep1.getTransforms().clear();
						mod5sweep1.getTransforms().clear();
						mod6sweep1.getTransforms().clear();
						mod7sweep1.getTransforms().clear();
						mod1sweep2.getTransforms().clear();
						mod2sweep2.getTransforms().clear();
						mod3sweep2.getTransforms().clear();
						mod4sweep2.getTransforms().clear();
						mod5sweep2.getTransforms().clear();
						mod6sweep2.getTransforms().clear();
						mod7sweep2.getTransforms().clear();
					}
					if (actionsList.contains("PLAYPAUSE")) {
						if (leftSweepTimeline.getStatus() == Animation.Status.PAUSED) {
							leftSweepTimeline.pause();
							rightSweepTimeline.pause();
						} else {
							leftSweepTimeline.play();
							rightSweepTimeline.play();
						}
					}
					if (actionsList.contains("LARGO")) {
						leftSweepTimeline.setRate(0.2);
						leftSweepSpeed = 4.0;
						rightSweepTimeline.setRate(0.2);
						rightSweepSpeed = 4.0;
					}
					if (actionsList.contains("ADAGIO")) {
						leftSweepTimeline.setRate(0.5);
						leftSweepSpeed = 3.0;
						rightSweepTimeline.setRate(0.5);
						rightSweepSpeed = 3.0;
					}
					if (actionsList.contains("ANDANTE")) {
						leftSweepTimeline.setRate(0.8);
						leftSweepSpeed = 2.5;
						rightSweepTimeline.setRate(0.8);
						rightSweepSpeed = 2.5;
					}
					if (actionsList.contains("MODERATO")) {
						leftSweepTimeline.setRate(1.0);
						leftSweepSpeed = 1.0;
						rightSweepTimeline.setRate(1.0);
						rightSweepSpeed = 1.0;
					}
					if (actionsList.contains("ALLEGRETO")) {
						leftSweepTimeline.setRate(1.3);
						leftSweepSpeed = 1.8;
						rightSweepTimeline.setRate(1.3);
						rightSweepSpeed = 1.8;
					}
					if (actionsList.contains("ALLEGRO")) {
						leftSweepTimeline.setRate(1.5);
						leftSweepSpeed = 1.6;
						rightSweepTimeline.setRate(1.5);
						rightSweepSpeed = 1.6;
					}
					if (actionsList.contains("PRESTO")) {
						leftSweepTimeline.setRate(1.8);
						leftSweepSpeed = 1.5;
						rightSweepTimeline.setRate(1.8);
						rightSweepSpeed = 1.5;
					}
					break;
				// Each case says what mode that the sweepers are supposed to be
				// in
				case 42:
					if (actionsList.contains("TOGETHER")) {
						setSweepType(1);
					}
					if (actionsList.contains("OPPOSED")) {
						setSweepType(2);
					}
					if (actionsList.contains("INDEPENDENT")) {
						setSweepType(0);
					}
					break;
				// Legacy Command
				// Water Modules W1-W6 & Wedding Cake formation
				case 48:
					if (actionsList.contains("MODULEA")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						// Left over code from first group, not sure if needed
						if (level == 6) {
							drawMultiA(level - 1, lagTime);
							drawSweepsA(level - 1, lagTime);
						} else {
							drawMultiA(level, lagTime);
						}
					}
					if (actionsList.contains("MODULEB")) {
						int level = FCWLib.getInstance().reverseGetLevel(f);
						if (level == 6) {
							drawMultiB(level - 1, lagTime);
							drawSweepsB(level - 1, lagTime);
						} else {
							drawMultiB(level, lagTime);
						}
					}
					break;
				case 501:
					System.out.println("Fading!!!");
					crossFading[0][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod1ring4.getFill()));
					crossFading[0][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 601:
					fading[0][0]=f.getData()/10;
					fading[0][1]=mod1.getOpacity();
					fading[0][2]= ((int) f.getData()) / 100;
					fadeModule(1, fading[0][0], fading[0][1], fading[0][2], crossFading[0][0], crossFading[0][1]);
					break;
				case 502:
					crossFading[1][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod2ring4.getFill()));
					crossFading[1][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 602:
					fading[1][0]=f.getData()/10;
					fading[1][1]=mod1.getOpacity();
					fading[1][2]= ((int) f.getData()) / 100;
					fadeModule(2, fading[1][0], fading[1][1], fading[1][2], crossFading[1][0], crossFading[1][1]);
					break;
				case 503:
					crossFading[2][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod3ring4.getFill()));
					crossFading[2][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 603:
					fading[2][0]=f.getData()/10;
					fading[2][1]=mod1.getOpacity();
					fading[2][2]= ((int) f.getData()) / 100;
					fadeModule(3, fading[2][0], fading[2][1], fading[2][2], crossFading[2][0], crossFading[2][1]);
					break;
				case 504:
					crossFading[3][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod4ring4.getFill()));
					crossFading[3][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 604:
					fading[3][0]=f.getData()/10;
					fading[3][1]=mod1.getOpacity();
					fading[3][2]= ((int) f.getData()) / 100;
					fadeModule(4, fading[3][0], fading[3][1], fading[3][2], crossFading[3][0], crossFading[3][1]);
					break;
				case 505:
					crossFading[4][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod5ring4.getFill()));
					crossFading[4][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 605:
					fading[4][0]=f.getData()/10;
					fading[4][1]=mod1.getOpacity();
					fading[4][2]= ((int) f.getData()) / 100;
					fadeModule(5, fading[4][0], fading[4][1], fading[4][2], crossFading[4][0], crossFading[4][1]);
					break;
				case 506:
					crossFading[5][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod6ring4.getFill()));
					crossFading[5][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 606:
					fading[5][0]=f.getData()/10;
					fading[5][1]=mod1.getOpacity();
					fading[5][2]= ((int) f.getData()) / 100;
					fadeModule(6, fading[5][0], fading[5][1], fading[5][2], crossFading[5][0], crossFading[5][1]);
					break;
				case 507:
					crossFading[6][0]=ColorPaletteModel.getInstance().getColor( ColorPaletteModel.getInstance().checkColor(mod7ring4.getFill()));
					crossFading[6][1]=ColorPaletteModel.getInstance().getColor(f.getData()%100);
					break;
				case 607:
					fading[6][0]=f.getData()/10;
					fading[6][1]=mod1.getOpacity();
					fading[6][2]= ((int) f.getData()) / 100;
					fadeModule(7, fading[6][0], fading[6][1], fading[6][2], crossFading[6][0], crossFading[6][1]);
					break;
					
				// Clears fountain/simulation
				// Used only for debugging and testing purposes
				case 99:
					resetAll();
					clearSweeps();
					break;
				}
			}
		}
	}

	/*
	 * Animation controller for the front curtain. All of the following methods
	 * have the same concept with the exception of the sweeper classes since
	 * they have more complex movements. These methods are animations written in
	 * java, so experience with blender or the likes is helpful
	 */
	public void drawFtCurtain(int level, double lagTime) {

		// Holds all of the animation information
		final Timeline timeline = new Timeline();

		// How many times the animation happens
		timeline.setCycleCount(1);

		// Each of these the end value for the animation, which in this and most
		// cases is just a height change
		final KeyValue kv1 = new KeyValue(frontCurtain1.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(frontCurtain2.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(frontCurtain3.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(frontCurtain4.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv5 = new KeyValue(frontCurtain5.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv6 = new KeyValue(frontCurtain6.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv7 = new KeyValue(frontCurtain7.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv8 = new KeyValue(frontCurtain8.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv9 = new KeyValue(frontCurtain9.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv10 = new KeyValue(frontCurtain10.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv11 = new KeyValue(frontCurtain11.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv12 = new KeyValue(frontCurtain12.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv13 = new KeyValue(frontCurtain13.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv14 = new KeyValue(frontCurtain14.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);

		// Adds all of the movements to the keyframe with a duration as given by
		// lagtime
		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14);
		timeline.getKeyFrames().add(kf);

		// Starts the animation
		timeline.play();
	}

	public void drawBkCurtain(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(backCurtain1.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(backCurtain2.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(backCurtain3.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(backCurtain4.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv5 = new KeyValue(backCurtain5.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv6 = new KeyValue(backCurtain6.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv7 = new KeyValue(backCurtain7.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv8 = new KeyValue(backCurtain8.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv9 = new KeyValue(backCurtain9.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv10 = new KeyValue(backCurtain10.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv11 = new KeyValue(backCurtain11.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv12 = new KeyValue(backCurtain12.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv13 = new KeyValue(backCurtain13.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv14 = new KeyValue(backCurtain14.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing1A(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod1ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod3ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod5ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod7ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing1B(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod2ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod4ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod6ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing2A(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod1ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod3ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod5ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod7ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing2B(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod2ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod4ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod6ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing3A(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod1ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod3ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod5ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod7ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing3B(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod2ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod4ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod6ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing4A(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod1ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod3ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod5ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod7ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing4B(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod2ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod4ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod6ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing5A(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod1ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod3ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod5ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod7ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawRing5B(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(mod2ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod4ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod6ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawSpout(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		final KeyValue kv1 = new KeyValue(spoutRec.heightProperty(), ((40 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawBazooka(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		KeyValue kv2 = null;
		KeyValue kv3 = null;
		KeyValue kv4 = null;
		KeyValue kv5 = null;
		KeyValue kv6 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv9 = null;
		KeyValue kv10 = null;
		KeyValue kv11 = null;
		KeyValue kv12 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv15 = null;
		KeyValue kv16 = null;
		KeyValue kv17 = null;
		KeyValue kv18 = null;
		KeyValue kv19 = null;

		timeline.setCycleCount(1);
		if (level == 0) {

			kv2 = new KeyValue(bazooka1.endXProperty(), (10 + (100 * level)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(bazooka1.endYProperty(), (5 + (47 * level)), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(bazooka2.endXProperty(), (10 + 1320 - (100 * level)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(bazooka2.endYProperty(), (5 + (47 * level)), Interpolator.EASE_BOTH);

			kv7 = new KeyValue(bazooka3.endXProperty(), (10 + (100 * level)), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(bazooka3.endYProperty(), (5 + (47 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(bazooka4.endXProperty(), (10 + 1320 - (100 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(bazooka4.endYProperty(), (5 + (47 * level)), Interpolator.EASE_BOTH);

			kv11 = new KeyValue(bazooka1.visibleProperty(), false);
			kv12 = new KeyValue(bazooka2.visibleProperty(), false);
			kv13 = new KeyValue(bazooka3.visibleProperty(), false);
			kv14 = new KeyValue(bazooka4.visibleProperty(), false);
		} else if (level == 1 || level == 2) {

			bazooka1.setVisible(true);
			bazooka2.setVisible(true);
			bazooka3.setVisible(true);
			bazooka4.setVisible(true);

			kv2 = new KeyValue(bazooka1.endXProperty(), (10 + (100 * level)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(bazooka1.endYProperty(), (10 + (47 * level)), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(bazooka2.endXProperty(), (1310 - (100 * level)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(bazooka2.endYProperty(), (10 + (47 * level)), Interpolator.EASE_BOTH);

			kv8 = new KeyValue(bazooka3.endXProperty(), (10 + (100 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(bazooka3.endYProperty(), (10 + (47 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(bazooka4.endXProperty(), (1310 - (100 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(bazooka4.endYProperty(), (10 + (47 * level)), Interpolator.EASE_BOTH);

		}

		else {

			bazooka1.setVisible(true);
			bazooka2.setVisible(true);
			bazooka3.setVisible(true);
			bazooka4.setVisible(true);
			kv2 = new KeyValue(bazooka1.endXProperty(), (10 + (100 * 2)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(bazooka1.endYProperty(), (10 + (47 * 2)), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(bazooka2.endXProperty(), (1310 - (100 * 2)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(bazooka2.endYProperty(), (12 + (47 * 2)), Interpolator.EASE_BOTH);

			kv8 = new KeyValue(bazooka3.endXProperty(), (10 + (100 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(bazooka3.endYProperty(), (10 + (47 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(bazooka4.endXProperty(), (1310 - (100 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(bazooka4.endYProperty(), (10 + (47 * level)), Interpolator.EASE_BOTH);
		}

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14, kv15, kv16, kv17, kv18, kv19);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawPeacock(int level, double lagTime) {
		final Timeline timeline = new Timeline();

		KeyValue kv2 = null;
		KeyValue kv3 = null;
		KeyValue kv4 = null;
		KeyValue kv5 = null;
		KeyValue kv6 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv9 = null;
		KeyValue kv10 = null;
		KeyValue kv11 = null;
		KeyValue kv12 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv15 = null;
		KeyValue kv16 = null;
		KeyValue kv17 = null;
		KeyValue kv18 = null;
		KeyValue kv19 = null;
		KeyValue kv20 = null;
		KeyValue kv21 = null;
		KeyValue kv22 = null;
		KeyValue kv23 = null;
		KeyValue kv24 = null;
		KeyValue kv25 = null;
		KeyValue kv26 = null;
		KeyValue kv27 = null;
		KeyValue kv28 = null;

		timeline.setCycleCount(1);
		if (level > 0.0) {

			peacock1.setVisible(true);
			peacock2.setVisible(true);
			peacock3.setVisible(true);
			peacock4.setVisible(true);
			peacock5.setVisible(true);
			peacock6.setVisible(true);
			peacock7.setVisible(true);
			peacock8.setVisible(true);
			peacock9.setVisible(true);

			kv2 = new KeyValue(peacock1.endXProperty(), (689 + (102.2 * level)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(peacock1.endYProperty(), (5 + (26 * level)), Interpolator.EASE_BOTH);

			kv4 = new KeyValue(peacock2.endXProperty(), (688 + (66.4 * level)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(peacock2.endYProperty(), (17 + (36.6 * level)), Interpolator.EASE_BOTH);

			kv6 = new KeyValue(peacock3.endXProperty(), (683 + (38.4 * level)), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(peacock3.endYProperty(), (28 + (39.4 * level)), Interpolator.EASE_BOTH);

			kv8 = new KeyValue(peacock4.endXProperty(), (675 + (18 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(peacock4.endYProperty(), (37 + (39.6 * level)), Interpolator.EASE_BOTH);

			kv10 = new KeyValue(peacock5.endXProperty(), (665 + (0 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(peacock5.endYProperty(), (40 + (40 * level)), Interpolator.EASE_BOTH);

			kv12 = new KeyValue(peacock6.endXProperty(), (655 + (-18 * level)), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(peacock6.endYProperty(), (37 + (39.6 * level)), Interpolator.EASE_BOTH);

			kv14 = new KeyValue(peacock7.endXProperty(), (647 + (-39.4 * level)), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(peacock7.endYProperty(), (28 + (39.4 * level)), Interpolator.EASE_BOTH);

			kv16 = new KeyValue(peacock8.endXProperty(), (643 + (-66.6 * level)), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(peacock8.endYProperty(), (17 + (36.6 * level)), Interpolator.EASE_BOTH);

			kv18 = new KeyValue(peacock9.endXProperty(), (642 + (-101.4 * level)), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(peacock9.endYProperty(), (5 + (26 * level)), Interpolator.EASE_BOTH);

		} else {

			kv2 = new KeyValue(peacock1.endXProperty(), (689 + (102.2 * level)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(peacock1.endYProperty(), (5 + (26 * level)), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(peacock2.endXProperty(), (688 + (66.4 * level)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(peacock2.endYProperty(), (17 + (36.6 * level)), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(peacock3.endXProperty(), (683 + (38.4 * level)), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(peacock3.endYProperty(), (28 + (39.4 * level)), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(peacock4.endXProperty(), (675 + (18 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(peacock4.endYProperty(), (37 + (39.6 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(peacock5.endXProperty(), (665 + (0 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(peacock5.endYProperty(), (40 + (40 * level)), Interpolator.EASE_BOTH);
			kv12 = new KeyValue(peacock6.endXProperty(), (655 + (-18 * level)), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(peacock6.endYProperty(), (37 + (39.6 * level)), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(peacock7.endXProperty(), (647 + (-39.4 * level)), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(peacock7.endYProperty(), (28 + (39.4 * level)), Interpolator.EASE_BOTH);
			kv16 = new KeyValue(peacock8.endXProperty(), (643 + (-66.6 * level)), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(peacock8.endYProperty(), (17 + (36.6 * level)), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(peacock9.endXProperty(), (642 + (-101.4 * level)), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(peacock9.endYProperty(), (5 + (26 * level)), Interpolator.EASE_BOTH);

			kv20 = new KeyValue(peacock1.visibleProperty(), false);
			kv21 = new KeyValue(peacock2.visibleProperty(), false);
			kv22 = new KeyValue(peacock3.visibleProperty(), false);
			kv23 = new KeyValue(peacock4.visibleProperty(), false);
			kv24 = new KeyValue(peacock5.visibleProperty(), false);
			kv25 = new KeyValue(peacock6.visibleProperty(), false);
			kv26 = new KeyValue(peacock7.visibleProperty(), false);
			kv27 = new KeyValue(peacock8.visibleProperty(), false);
			kv28 = new KeyValue(peacock9.visibleProperty(), false);

		}

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14, kv15, kv16, kv17, kv18, kv19, kv20, kv21, kv22, kv23, kv24, kv25, kv26, kv27, kv28);
		timeline.getKeyFrames().add(kf);
		timeline.play();

	}

	public void drawSweepsA(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		sweepLevel = level;

		KeyValue kv25 = null;
		KeyValue kv26 = null;
		KeyValue kv27 = null;
		KeyValue kv28 = null;
		KeyValue kv29 = null;
		KeyValue kv30 = null;
		KeyValue kv31 = null;
		KeyValue kv32 = null;

		mod1sweep1.setVisible(true);
		mod1sweep2.setVisible(true);
		mod3sweep1.setVisible(true);
		mod3sweep2.setVisible(true);
		mod5sweep1.setVisible(true);
		mod5sweep2.setVisible(true);
		mod7sweep1.setVisible(true);
		mod7sweep2.setVisible(true);

		if (level == 0) {
			kv25 = new KeyValue(mod1sweep1.visibleProperty(), false);
			kv26 = new KeyValue(mod1sweep2.visibleProperty(), false);

			kv27 = new KeyValue(mod3sweep1.visibleProperty(), false);
			kv28 = new KeyValue(mod3sweep2.visibleProperty(), false);

			kv29 = new KeyValue(mod5sweep1.visibleProperty(), false);
			kv30 = new KeyValue(mod5sweep2.visibleProperty(), false);

			kv31 = new KeyValue(mod7sweep1.visibleProperty(), false);
			kv32 = new KeyValue(mod7sweep2.visibleProperty(), false);
		}

		final KeyValue kv1 = new KeyValue(mod1sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod1sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv7 = new KeyValue(mod3sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv8 = new KeyValue(mod3sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv13 = new KeyValue(mod5sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv14 = new KeyValue(mod5sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv19 = new KeyValue(mod7sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv20 = new KeyValue(mod7sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(.5), kv1, kv2, kv7, kv8, kv13, kv14, kv14, kv19, kv20, kv25, kv26, kv27, kv28, kv29, kv30, kv31, kv32);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawSweepsB(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyValue kv15 = null;
		KeyValue kv16 = null;
		KeyValue kv17 = null;
		KeyValue kv18 = null;
		KeyValue kv19 = null;
		KeyValue kv20 = null;

		mod2sweep1.setVisible(true);
		mod2sweep2.setVisible(true);
		mod4sweep1.setVisible(true);
		mod4sweep2.setVisible(true);
		mod6sweep1.setVisible(true);
		mod6sweep2.setVisible(true);

		if (level == 0) {
			kv15 = new KeyValue(mod2sweep1.visibleProperty(), false);
			kv16 = new KeyValue(mod2sweep2.visibleProperty(), false);

			kv17 = new KeyValue(mod4sweep1.visibleProperty(), false);
			kv18 = new KeyValue(mod4sweep2.visibleProperty(), false);

			kv19 = new KeyValue(mod6sweep1.visibleProperty(), false);
			kv20 = new KeyValue(mod6sweep2.visibleProperty(), false);
		}

		final KeyValue kv1 = new KeyValue(mod2sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod2sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv7 = new KeyValue(mod4sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv8 = new KeyValue(mod4sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv13 = new KeyValue(mod6sweep1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv14 = new KeyValue(mod6sweep2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(.5), kv1, kv2, kv7, kv8, kv13, kv14, kv15, kv16, kv17, kv18, kv19, kv20);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void sweepLeftSweeps(double leftLimit, double rightLimit) {
		leftSweepTimeline = new Timeline();

		// Pauses/resumes the animation
		if (MusicPaneController.getInstance().getMediaPlayer().statusProperty().getValue() == Status.PLAYING) {
			leftSweepTimeline.setCycleCount(Animation.INDEFINITE);
		} else {
			leftSweepTimeline.setCycleCount(1);
		}

		// Using this replays the animation
		leftSweepTimeline.setAutoReverse(true);

		// These are for the animations and hold time values
		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv19 = null;

		// Makes sure that there are no current animations happening to the
		// object
		mod1sweep1.getTransforms().clear();
		mod2sweep1.getTransforms().clear();
		mod3sweep1.getTransforms().clear();
		mod4sweep1.getTransforms().clear();
		mod5sweep1.getTransforms().clear();
		mod6sweep1.getTransforms().clear();
		mod7sweep1.getTransforms().clear();

		// Creates a rotate object Rotate (X,Y,Z)
		Rotate rotate1 = new Rotate(leftLimit, mod1sweep1.getStartX(), mod1sweep1.getStartY());
		Rotate rotate2 = new Rotate(leftLimit, mod2sweep1.getStartX(), mod2sweep1.getStartY());
		Rotate rotate3 = new Rotate(leftLimit, mod3sweep1.getStartX(), mod3sweep1.getStartY());
		Rotate rotate4 = new Rotate(leftLimit, mod4sweep1.getStartX(), mod4sweep1.getStartY());
		Rotate rotate5 = new Rotate(leftLimit, mod5sweep1.getStartX(), mod5sweep1.getStartY());
		Rotate rotate6 = new Rotate(leftLimit, mod6sweep1.getStartX(), mod6sweep1.getStartY());
		Rotate rotate7 = new Rotate(leftLimit, mod7sweep1.getStartX(), mod7sweep1.getStartY());

		// Tells the sweep that it needs to rotate
		mod1sweep1.getTransforms().add(rotate1);
		mod2sweep1.getTransforms().add(rotate2);
		mod3sweep1.getTransforms().add(rotate3);
		mod4sweep1.getTransforms().add(rotate4);
		mod5sweep1.getTransforms().add(rotate5);
		mod6sweep1.getTransforms().add(rotate6);
		mod7sweep1.getTransforms().add(rotate7);

		kv1 = new KeyValue(rotate1.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv2 = new KeyValue(rotate2.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv7 = new KeyValue(rotate3.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv8 = new KeyValue(rotate4.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv13 = new KeyValue(rotate5.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv14 = new KeyValue(rotate6.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv19 = new KeyValue(rotate7.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(leftSweepSpeed), kv1, kv2, kv7, kv8, kv13, kv14, kv19);
		leftSweepTimeline.getKeyFrames().add(kf);
		leftSweepTimeline.play();
	}

	public void sweepRightSweeps(double leftLimit, double rightLimit) {
		rightSweepTimeline = new Timeline();
		if (MusicPaneController.getInstance().getMediaPlayer().statusProperty().getValue() == Status.PLAYING) {
			rightSweepTimeline.setCycleCount(Animation.INDEFINITE);
		} else {
			rightSweepTimeline.setCycleCount(1);
		}
		rightSweepTimeline.setAutoReverse(true);

		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv19 = null;

		mod1sweep2.setVisible(true);
		mod2sweep2.setVisible(true);
		mod3sweep2.setVisible(true);
		mod4sweep2.setVisible(true);
		mod5sweep2.setVisible(true);
		mod6sweep2.setVisible(true);
		mod7sweep2.setVisible(true);

		mod1sweep2.getTransforms().clear();
		mod2sweep2.getTransforms().clear();
		mod3sweep2.getTransforms().clear();
		mod4sweep2.getTransforms().clear();
		mod5sweep2.getTransforms().clear();
		mod6sweep2.getTransforms().clear();
		mod7sweep2.getTransforms().clear();

		Rotate rotate1 = new Rotate(leftLimit, mod1sweep2.getStartX(), mod1sweep2.getStartY());
		Rotate rotate2 = new Rotate(leftLimit, mod2sweep2.getStartX(), mod2sweep2.getStartY());
		Rotate rotate3 = new Rotate(leftLimit, mod3sweep2.getStartX(), mod3sweep2.getStartY());
		Rotate rotate4 = new Rotate(leftLimit, mod4sweep2.getStartX(), mod4sweep2.getStartY());
		Rotate rotate5 = new Rotate(leftLimit, mod5sweep2.getStartX(), mod5sweep2.getStartY());
		Rotate rotate6 = new Rotate(leftLimit, mod6sweep2.getStartX(), mod6sweep2.getStartY());
		Rotate rotate7 = new Rotate(leftLimit, mod7sweep2.getStartX(), mod7sweep2.getStartY());

		mod1sweep2.getTransforms().add(rotate1);
		mod2sweep2.getTransforms().add(rotate2);
		mod3sweep2.getTransforms().add(rotate3);
		mod4sweep2.getTransforms().add(rotate4);
		mod5sweep2.getTransforms().add(rotate5);
		mod6sweep2.getTransforms().add(rotate6);
		mod7sweep2.getTransforms().add(rotate7);

		kv1 = new KeyValue(rotate1.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv2 = new KeyValue(rotate2.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv7 = new KeyValue(rotate3.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv8 = new KeyValue(rotate4.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv13 = new KeyValue(rotate5.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv14 = new KeyValue(rotate6.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv19 = new KeyValue(rotate7.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(rightSweepSpeed), kv1, kv2, kv7, kv8, kv13, kv14, kv19);
		rightSweepTimeline.getKeyFrames().add(kf);

		rightSweepTimeline.play();
	}

	public void oscillateLeftSweeps(double leftLimit, double rightLimit) {
		leftSweepTimeline = new Timeline();
		if (MusicPaneController.getInstance().getMediaPlayer().statusProperty().getValue() == Status.PLAYING) {
			leftSweepTimeline.setCycleCount(Animation.INDEFINITE);
		} else {
			leftSweepTimeline.setCycleCount(1);
		}
		leftSweepTimeline.setAutoReverse(false);

		// These are for the animations and hold time values
		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv19 = null;

		// Makes sure that there are no current animations happening to the
		// object
		mod1sweep1.getTransforms().clear();
		mod2sweep1.getTransforms().clear();
		mod3sweep1.getTransforms().clear();
		mod4sweep1.getTransforms().clear();
		mod5sweep1.getTransforms().clear();
		mod6sweep1.getTransforms().clear();
		mod7sweep1.getTransforms().clear();

		// Creates a rotate object Rotate (X,Y,Z)
		Rotate rotate1 = new Rotate(leftLimit, mod1sweep1.getStartX(), mod1sweep1.getStartY());
		Rotate rotate2 = new Rotate(leftLimit, mod2sweep1.getStartX(), mod2sweep1.getStartY());
		Rotate rotate3 = new Rotate(leftLimit, mod3sweep1.getStartX(), mod3sweep1.getStartY());
		Rotate rotate4 = new Rotate(leftLimit, mod4sweep1.getStartX(), mod4sweep1.getStartY());
		Rotate rotate5 = new Rotate(leftLimit, mod5sweep1.getStartX(), mod5sweep1.getStartY());
		Rotate rotate6 = new Rotate(leftLimit, mod6sweep1.getStartX(), mod6sweep1.getStartY());
		Rotate rotate7 = new Rotate(leftLimit, mod7sweep1.getStartX(), mod7sweep1.getStartY());

		// Tells the sweep that it needs to rotate
		mod1sweep1.getTransforms().add(rotate1);
		mod2sweep1.getTransforms().add(rotate2);
		mod3sweep1.getTransforms().add(rotate3);
		mod4sweep1.getTransforms().add(rotate4);
		mod5sweep1.getTransforms().add(rotate5);
		mod6sweep1.getTransforms().add(rotate6);
		mod7sweep1.getTransforms().add(rotate7);

		kv1 = new KeyValue(rotate1.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv2 = new KeyValue(rotate2.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv7 = new KeyValue(rotate3.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv8 = new KeyValue(rotate4.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv13 = new KeyValue(rotate5.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv14 = new KeyValue(rotate6.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv19 = new KeyValue(rotate7.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(leftSweepSpeed), kv1, kv2, kv7, kv8, kv13, kv14, kv19);
		leftSweepTimeline.getKeyFrames().add(kf);
		leftSweepTimeline.play();
	}

	public void oscillateRightSweeps(double leftLimit, double rightLimit) {
		rightSweepTimeline = new Timeline();
		if (MusicPaneController.getInstance().getMediaPlayer().statusProperty().getValue() == Status.PLAYING) {
			rightSweepTimeline.setCycleCount(Animation.INDEFINITE);
		} else {
			rightSweepTimeline.setCycleCount(1);
		}
		rightSweepTimeline.setAutoReverse(false);

		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv19 = null;

		mod1sweep2.setVisible(true);
		mod2sweep2.setVisible(true);
		mod3sweep2.setVisible(true);
		mod4sweep2.setVisible(true);
		mod5sweep2.setVisible(true);
		mod6sweep2.setVisible(true);
		mod7sweep2.setVisible(true);

		mod1sweep2.getTransforms().clear();
		mod2sweep2.getTransforms().clear();
		mod3sweep2.getTransforms().clear();
		mod4sweep2.getTransforms().clear();
		mod5sweep2.getTransforms().clear();
		mod6sweep2.getTransforms().clear();
		mod7sweep2.getTransforms().clear();

		Rotate rotate1 = new Rotate(leftLimit, mod1sweep2.getStartX(), mod1sweep2.getStartY());
		Rotate rotate2 = new Rotate(leftLimit, mod2sweep2.getStartX(), mod2sweep2.getStartY());
		Rotate rotate3 = new Rotate(leftLimit, mod3sweep2.getStartX(), mod3sweep2.getStartY());
		Rotate rotate4 = new Rotate(leftLimit, mod4sweep2.getStartX(), mod4sweep2.getStartY());
		Rotate rotate5 = new Rotate(leftLimit, mod5sweep2.getStartX(), mod5sweep2.getStartY());
		Rotate rotate6 = new Rotate(leftLimit, mod6sweep2.getStartX(), mod6sweep2.getStartY());
		Rotate rotate7 = new Rotate(leftLimit, mod7sweep2.getStartX(), mod7sweep2.getStartY());

		mod1sweep2.getTransforms().add(rotate1);
		mod2sweep2.getTransforms().add(rotate2);
		mod3sweep2.getTransforms().add(rotate3);
		mod4sweep2.getTransforms().add(rotate4);
		mod5sweep2.getTransforms().add(rotate5);
		mod6sweep2.getTransforms().add(rotate6);
		mod7sweep2.getTransforms().add(rotate7);

		kv1 = new KeyValue(rotate1.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv2 = new KeyValue(rotate2.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv7 = new KeyValue(rotate3.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv8 = new KeyValue(rotate4.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv13 = new KeyValue(rotate5.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv14 = new KeyValue(rotate6.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv19 = new KeyValue(rotate7.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(rightSweepSpeed), kv1, kv2, kv7, kv8, kv13, kv14, kv19);
		rightSweepTimeline.getKeyFrames().add(kf);

		rightSweepTimeline.play();
	}

	/*
	 * Unimplimented Method This method is meant to be called before each
	 * sweeping action. It is supposed to take the sweeper and move it to the
	 * initial start position of the new FCW. For example, 035-23, will have
	 * both sweepers sweep from left long to right long. All sweeps/oscillations
	 * work from the left to the right. This method will take the current angle
	 * and move it to the left long position before it calls sweepLeftSweeps(ll,
	 * rl). However, there currently is no way to find the angle of the animated
	 * line. If a way to find that angle is found, all that has to be done is
	 * put that in for double angle.
	 */
	public void smoothLeftSweeps(double leftLimit) {
		leftSweepTimeline = new Timeline();
		leftSweepTimeline.setCycleCount(1);
		leftSweepTimeline.setAutoReverse(false);

		// These are for the animations and hold actions
		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv19 = null;

		// Makes sure that there are no current animations happening to the
		// object
		mod1sweep1.getTransforms().clear();
		mod2sweep1.getTransforms().clear();
		mod3sweep1.getTransforms().clear();
		mod4sweep1.getTransforms().clear();
		mod5sweep1.getTransforms().clear();
		mod6sweep1.getTransforms().clear();
		mod7sweep1.getTransforms().clear();

		// The current angle of the line
		double angle = 0;

		// Creates a rotate object Rotate (X,Y,Z)
		Rotate rotate1 = new Rotate(angle, mod1sweep1.getStartX(), mod1sweep1.getStartY());
		Rotate rotate2 = new Rotate(angle, mod2sweep1.getStartX(), mod2sweep1.getStartY());
		Rotate rotate3 = new Rotate(angle, mod3sweep1.getStartX(), mod3sweep1.getStartY());
		Rotate rotate4 = new Rotate(angle, mod4sweep1.getStartX(), mod4sweep1.getStartY());
		Rotate rotate5 = new Rotate(angle, mod5sweep1.getStartX(), mod5sweep1.getStartY());
		Rotate rotate6 = new Rotate(angle, mod6sweep1.getStartX(), mod6sweep1.getStartY());
		Rotate rotate7 = new Rotate(angle, mod7sweep1.getStartX(), mod7sweep1.getStartY());

		// Tells the sweep that it needs to rotate
		mod1sweep1.getTransforms().add(rotate1);
		mod2sweep1.getTransforms().add(rotate2);
		mod3sweep1.getTransforms().add(rotate3);
		mod4sweep1.getTransforms().add(rotate4);
		mod5sweep1.getTransforms().add(rotate5);
		mod6sweep1.getTransforms().add(rotate6);
		mod7sweep1.getTransforms().add(rotate7);

		kv1 = new KeyValue(rotate1.angleProperty(), leftLimit, Interpolator.EASE_BOTH);
		kv2 = new KeyValue(rotate2.angleProperty(), leftLimit, Interpolator.EASE_BOTH);

		kv7 = new KeyValue(rotate3.angleProperty(), leftLimit, Interpolator.EASE_BOTH);
		kv8 = new KeyValue(rotate4.angleProperty(), leftLimit, Interpolator.EASE_BOTH);

		kv13 = new KeyValue(rotate5.angleProperty(), leftLimit, Interpolator.EASE_BOTH);
		kv14 = new KeyValue(rotate6.angleProperty(), leftLimit, Interpolator.EASE_BOTH);

		kv19 = new KeyValue(rotate7.angleProperty(), leftLimit, Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(2), kv1, kv2, kv7, kv8, kv13, kv14, kv14, kv19);
		leftSweepTimeline.getKeyFrames().add(kf);
		leftSweepTimeline.play();
	}

	/*
	 * Unimplimented Method This method is meant to be called before each
	 * sweeping action. It is supposed to take the sweeper and move it to the
	 * initial start position of the new FCW. For example, 035-23, will have
	 * both sweepers sweep from left long to right long. All sweeps/oscillations
	 * work from the left to the right. This method will take the current angle
	 * and move it to the left long position before it calls
	 * sweepRightSweeps(ll, rl). However, there currently is no way to find the
	 * angle of the animated line. If a way to find that angle is found, all
	 * that has to be done is put that in for double angle.
	 */
	public void smoothRightSweeps(double leftLimit) {
		double rightLimit = -leftLimit;
		rightSweepTimeline = new Timeline();
		if (MusicPaneController.getInstance().getMediaPlayer().statusProperty().getValue() == Status.PLAYING) {
			rightSweepTimeline.setCycleCount(Animation.INDEFINITE);
		} else {
			rightSweepTimeline.setCycleCount(1);
		}
		rightSweepTimeline.setAutoReverse(false);

		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv19 = null;

		mod1sweep2.setVisible(true);
		mod2sweep2.setVisible(true);
		mod3sweep2.setVisible(true);
		mod4sweep2.setVisible(true);
		mod5sweep2.setVisible(true);
		mod6sweep2.setVisible(true);
		mod7sweep2.setVisible(true);

		mod1sweep2.getTransforms().clear();
		mod2sweep2.getTransforms().clear();
		mod3sweep2.getTransforms().clear();
		mod4sweep2.getTransforms().clear();
		mod5sweep2.getTransforms().clear();
		mod6sweep2.getTransforms().clear();
		mod7sweep2.getTransforms().clear();

		Rotate rotate1 = new Rotate(leftLimit, mod1sweep2.getStartX(), mod1sweep2.getStartY());
		Rotate rotate2 = new Rotate(leftLimit, mod2sweep2.getStartX(), mod2sweep2.getStartY());
		Rotate rotate3 = new Rotate(leftLimit, mod3sweep2.getStartX(), mod3sweep2.getStartY());
		Rotate rotate4 = new Rotate(leftLimit, mod4sweep2.getStartX(), mod4sweep2.getStartY());
		Rotate rotate5 = new Rotate(leftLimit, mod5sweep2.getStartX(), mod5sweep2.getStartY());
		Rotate rotate6 = new Rotate(leftLimit, mod6sweep2.getStartX(), mod6sweep2.getStartY());
		Rotate rotate7 = new Rotate(leftLimit, mod7sweep2.getStartX(), mod7sweep2.getStartY());

		mod1sweep2.getTransforms().add(rotate1);
		mod2sweep2.getTransforms().add(rotate2);
		mod3sweep2.getTransforms().add(rotate3);
		mod4sweep2.getTransforms().add(rotate4);
		mod5sweep2.getTransforms().add(rotate5);
		mod6sweep2.getTransforms().add(rotate6);
		mod7sweep2.getTransforms().add(rotate7);

		kv1 = new KeyValue(rotate1.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv2 = new KeyValue(rotate2.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv7 = new KeyValue(rotate3.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv8 = new KeyValue(rotate4.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv13 = new KeyValue(rotate5.angleProperty(), rightLimit, Interpolator.EASE_BOTH);
		kv14 = new KeyValue(rotate6.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		kv19 = new KeyValue(rotate7.angleProperty(), rightLimit, Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(rightSweepSpeed), kv1, kv2, kv7, kv8, kv13, kv14, kv19);
		rightSweepTimeline.getKeyFrames().add(kf);

		rightSweepTimeline.play();
	}

	public void drawMultiA(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv3 = null;
		KeyValue kv4 = null;
		KeyValue kv5 = null;
		KeyValue kv6 = null;
		KeyValue kv7 = null;
		KeyValue kv8 = null;
		KeyValue kv9 = null;
		KeyValue kv10 = null;
		KeyValue kv11 = null;
		KeyValue kv12 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv15 = null;
		KeyValue kv16 = null;
		KeyValue kv17 = null;
		KeyValue kv18 = null;
		KeyValue kv19 = null;
		KeyValue kv20 = null;

		if (level == 1.0 || level == 0.0) {
			kv1 = new KeyValue(mod1ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod3ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod5ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(mod7ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod1ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod3ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod5ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(mod7ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod1ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod3ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod5ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv12 = new KeyValue(mod7ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod1ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod3ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod5ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv16 = new KeyValue(mod7ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod1ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod3ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod5ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv20 = new KeyValue(mod7ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		}

		if (level == 2.0) {
			kv1 = new KeyValue(mod1ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod3ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod5ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(mod7ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod1ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod3ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod5ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(mod7ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod1ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod3ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod5ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv12 = new KeyValue(mod7ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod1ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod3ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod5ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv16 = new KeyValue(mod7ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod1ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod3ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod5ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv20 = new KeyValue(mod7ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		}

		if (level == 3.0) {
			kv1 = new KeyValue(mod1ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod3ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod5ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(mod7ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod1ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod3ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod5ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(mod7ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod1ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod3ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod5ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv12 = new KeyValue(mod7ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod1ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod3ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod5ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv16 = new KeyValue(mod7ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod1ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod3ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod5ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv20 = new KeyValue(mod7ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
		}

		if (level == 4.0) {
			kv1 = new KeyValue(mod1ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod3ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod5ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(mod7ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod1ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod3ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod5ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(mod7ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod1ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod3ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod5ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv12 = new KeyValue(mod7ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod1ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod3ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod5ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv16 = new KeyValue(mod7ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod1ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod3ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod5ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv20 = new KeyValue(mod7ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
		}

		if (level == 5.0) {
			kv1 = new KeyValue(mod1ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod3ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod5ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv4 = new KeyValue(mod7ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod1ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod3ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod5ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv8 = new KeyValue(mod7ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod1ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod3ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod5ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv12 = new KeyValue(mod7ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod1ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod3ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod5ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv16 = new KeyValue(mod7ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod1ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod3ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod5ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv20 = new KeyValue(mod7ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
		}
		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14, kv15, kv16, kv17, kv18, kv19, kv20);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawMultiB(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyValue kv1 = null;
		KeyValue kv2 = null;
		KeyValue kv3 = null;
		KeyValue kv5 = null;
		KeyValue kv6 = null;
		KeyValue kv7 = null;
		KeyValue kv9 = null;
		KeyValue kv10 = null;
		KeyValue kv11 = null;
		KeyValue kv13 = null;
		KeyValue kv14 = null;
		KeyValue kv15 = null;
		KeyValue kv17 = null;
		KeyValue kv18 = null;
		KeyValue kv19 = null;

		if (level == 1.0 || level == 0.0) {
			kv1 = new KeyValue(mod2ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod4ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod6ring1.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod2ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod4ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod6ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod2ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod4ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod6ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod2ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod4ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod6ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod2ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod4ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod6ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		}

		if (level == 2.0) {
			kv1 = new KeyValue(mod2ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod4ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod6ring1.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod2ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod4ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod6ring2.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod2ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod4ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod6ring3.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod2ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod4ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod6ring4.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod2ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod4ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod6ring5.heightProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		}

		if (level == 3.0) {
			kv1 = new KeyValue(mod2ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod4ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod6ring1.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod2ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod4ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod6ring2.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod2ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod4ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod6ring3.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod2ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod4ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod6ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod2ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod4ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod6ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
		}

		if (level == 4.0) {
			kv1 = new KeyValue(mod2ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod4ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod6ring1.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod2ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod4ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod6ring2.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod2ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod4ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod6ring3.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod2ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod4ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod6ring4.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod2ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod4ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod6ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
		}

		if (level == 5.0) {
			kv1 = new KeyValue(mod2ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv2 = new KeyValue(mod4ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv3 = new KeyValue(mod6ring1.heightProperty(), ((35 * (level - 4))), Interpolator.EASE_BOTH);
			kv5 = new KeyValue(mod2ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv6 = new KeyValue(mod4ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv7 = new KeyValue(mod6ring2.heightProperty(), ((35 * (level - 3))), Interpolator.EASE_BOTH);
			kv9 = new KeyValue(mod2ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv10 = new KeyValue(mod4ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv11 = new KeyValue(mod6ring3.heightProperty(), ((35 * (level - 2))), Interpolator.EASE_BOTH);
			kv13 = new KeyValue(mod2ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv14 = new KeyValue(mod4ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv15 = new KeyValue(mod6ring4.heightProperty(), ((35 * (level - 1))), Interpolator.EASE_BOTH);
			kv17 = new KeyValue(mod2ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv18 = new KeyValue(mod4ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
			kv19 = new KeyValue(mod6ring5.heightProperty(), ((35 * (level - 0))), Interpolator.EASE_BOTH);
		}
		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv5, kv6, kv7, kv9, kv10, kv11, kv13, kv14, kv15, kv17, kv18, kv19);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	public void drawCandlesA(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyValue kv25 = null;
		KeyValue kv26 = null;
		KeyValue kv27 = null;
		KeyValue kv28 = null;
		KeyValue kv29 = null;
		KeyValue kv30 = null;
		KeyValue kv31 = null;
		KeyValue kv32 = null;
		KeyValue kv33 = null;
		KeyValue kv34 = null;
		KeyValue kv35 = null;
		KeyValue kv36 = null;
		KeyValue kv37 = null;
		KeyValue kv38 = null;
		KeyValue kv39 = null;
		KeyValue kv40 = null;
		KeyValue kv41 = null;
		KeyValue kv42 = null;
		KeyValue kv43 = null;
		KeyValue kv44 = null;
		KeyValue kv45 = null;
		KeyValue kv46 = null;
		KeyValue kv47 = null;
		KeyValue kv48 = null;
		mod1candle1.setVisible(true);
		mod1candle2.setVisible(true);
		mod1candle3.setVisible(true);
		mod1candle4.setVisible(true);
		mod1candle5.setVisible(true);
		mod1candle6.setVisible(true);
		mod3candle1.setVisible(true);
		mod3candle2.setVisible(true);
		mod3candle3.setVisible(true);
		mod3candle4.setVisible(true);
		mod3candle5.setVisible(true);
		mod3candle6.setVisible(true);
		mod5candle1.setVisible(true);
		mod5candle2.setVisible(true);
		mod5candle3.setVisible(true);
		mod5candle4.setVisible(true);
		mod5candle5.setVisible(true);
		mod5candle6.setVisible(true);
		mod7candle1.setVisible(true);
		mod7candle2.setVisible(true);
		mod7candle3.setVisible(true);
		mod7candle4.setVisible(true);
		mod7candle5.setVisible(true);
		mod7candle6.setVisible(true);

		if (level == 0) {
			kv25 = new KeyValue(mod1candle1.visibleProperty(), false);
			kv26 = new KeyValue(mod1candle2.visibleProperty(), false);
			kv27 = new KeyValue(mod1candle3.visibleProperty(), false);
			kv28 = new KeyValue(mod1candle4.visibleProperty(), false);
			kv29 = new KeyValue(mod1candle5.visibleProperty(), false);
			kv30 = new KeyValue(mod1candle6.visibleProperty(), false);

			kv31 = new KeyValue(mod3candle1.visibleProperty(), false);
			kv32 = new KeyValue(mod3candle2.visibleProperty(), false);
			kv33 = new KeyValue(mod3candle3.visibleProperty(), false);
			kv34 = new KeyValue(mod3candle4.visibleProperty(), false);
			kv35 = new KeyValue(mod3candle5.visibleProperty(), false);
			kv36 = new KeyValue(mod3candle6.visibleProperty(), false);

			kv37 = new KeyValue(mod5candle1.visibleProperty(), false);
			kv38 = new KeyValue(mod5candle2.visibleProperty(), false);
			kv39 = new KeyValue(mod5candle3.visibleProperty(), false);
			kv40 = new KeyValue(mod5candle4.visibleProperty(), false);
			kv41 = new KeyValue(mod5candle5.visibleProperty(), false);
			kv42 = new KeyValue(mod5candle6.visibleProperty(), false);

			kv43 = new KeyValue(mod7candle1.visibleProperty(), false);
			kv44 = new KeyValue(mod7candle2.visibleProperty(), false);
			kv45 = new KeyValue(mod7candle3.visibleProperty(), false);
			kv46 = new KeyValue(mod7candle4.visibleProperty(), false);
			kv47 = new KeyValue(mod7candle5.visibleProperty(), false);
			kv48 = new KeyValue(mod7candle6.visibleProperty(), false);

		}
		final KeyValue kv1 = new KeyValue(mod1candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod1candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod1candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod1candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv5 = new KeyValue(mod1candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv6 = new KeyValue(mod1candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv7 = new KeyValue(mod3candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv8 = new KeyValue(mod3candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv9 = new KeyValue(mod3candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv10 = new KeyValue(mod3candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv11 = new KeyValue(mod3candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv12 = new KeyValue(mod3candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv13 = new KeyValue(mod5candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv14 = new KeyValue(mod5candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv15 = new KeyValue(mod5candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv16 = new KeyValue(mod5candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv17 = new KeyValue(mod5candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv18 = new KeyValue(mod5candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv19 = new KeyValue(mod7candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv20 = new KeyValue(mod7candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv21 = new KeyValue(mod7candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv22 = new KeyValue(mod7candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv23 = new KeyValue(mod7candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv24 = new KeyValue(mod7candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14, kv15, kv16, kv17, kv18, kv19, kv20, kv21, kv22, kv23, kv24, kv25, kv26, kv27, kv28, kv29, kv30, kv31, kv32, kv33, kv34, kv35, kv36, kv37, kv38, kv39, kv40,
				kv41, kv42, kv43, kv44, kv45, kv46, kv47, kv48);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	/**
	 * Implements fading (dimming) and crossfading (color transitions) Should be
	 * a "plug and play" method, with the only possible trouble spot being the
	 * transition not happening to a module
	 * 
	 * @param mod
	 *            The module that needs to fade
	 * @param length
	 *            How long it takes to fade
	 * @param start
	 *            The starting brightness of the fade
	 * @param end
	 *            The ending brightness of the fade
	 * @param begin
	 *            The starting color
	 * @param stop
	 *            The ending color of the fade
	 * 
	 *            Bazooka's still need a little work.
	 */
	public void fadeModule(int mod, double length, double start, double end, Color begin, Color stop) {
		// Fade transitions
		FadeTransition ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16;
		FillTransition ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16;
		ParallelTransition pt;

		// Converts seconds to milliseconds
		double millis = length * 1000;

		switch (mod) {
		case 1:
			ct0 = new FillTransition(Duration.millis(millis), bazooka1, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka1);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod1ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod1ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod1ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod1ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod1ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod1ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod1ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod1ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod1ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod1ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock1, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock1);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain1, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain1);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain1, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain1);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod1sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod1sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod1sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod1sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod1candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod1candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod1candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod1candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod1candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod1candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod1candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod1candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod1candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod1candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod1candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod1candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;
		case 2:
			ct0 = new FillTransition(Duration.millis(millis), bazooka2, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka2);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod2ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod2ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod2ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod2ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod2ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod2ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod2ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod2ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod2ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod2ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock2, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock2);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain2, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain2);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain2, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain2);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod2sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod2sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod2sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod2sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod2candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod2candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod2candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod2candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod2candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod2candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod2candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod2candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod2candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod2candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod2candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod2candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;

		case 3:
			ct0 = new FillTransition(Duration.millis(millis), bazooka3, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka3);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod3ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod3ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod3ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod3ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod3ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod3ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod3ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod3ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod3ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod3ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock3, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock3);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain3, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain3);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain3, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain3);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod3sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod3sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod3sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod3sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod3candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod3candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod3candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod3candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod3candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod3candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod3candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod3candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod3candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod3candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod3candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod3candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;

		case 4:
			ct0 = new FillTransition(Duration.millis(millis), bazooka4, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka4);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod4ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod4ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod4ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod4ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod4ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod4ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod4ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod4ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod4ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod4ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock4, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock4);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain4, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain4);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain4, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain4);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod4sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod4sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod4sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod4sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod4candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod4candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod4candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod4candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod4candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod4candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod4candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod4candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod4candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod4candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod4candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod4candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;

		case 5:
			ct0 = new FillTransition(Duration.millis(millis), bazooka3, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka3);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod5ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod5ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod5ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod5ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod5ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod5ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod5ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod5ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod5ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod5ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock5, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock5);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain5, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain5);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain5, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain5);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod5sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod5sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod5sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod5sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod5candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod5candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod5candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod5candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod5candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod5candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod5candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod5candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod5candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod5candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod5candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod5candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;

		case 6:
			ct0 = new FillTransition(Duration.millis(millis), bazooka2, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka2);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod6ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod6ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod6ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod6ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod6ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod6ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod6ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod6ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod6ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod6ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock6, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock6);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain6, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain6);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain6, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain6);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod6sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod6sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod6sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod6sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod6candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod6candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod6candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod6candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod6candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod6candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod6candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod6candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod6candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod6candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod6candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod6candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;

		case 7:
			ct0 = new FillTransition(Duration.millis(millis), bazooka1, begin, stop);
			ct0.setCycleCount(1);
			ct0.setAutoReverse(false);
			ft0 = new FadeTransition(Duration.millis(millis), bazooka1);
			ft0.setFromValue(start);
			ft0.setToValue(end);
			ft0.setCycleCount(1);
			ft0.setAutoReverse(false);
			ct1 = new FillTransition(Duration.millis(millis), mod7ring1, begin, stop);
			ct1.setCycleCount(1);
			ct1.setAutoReverse(false);
			ft1 = new FadeTransition(Duration.millis(millis), mod7ring1);
			ft1.setFromValue(start);
			ft1.setToValue(end);
			ft1.setCycleCount(1);
			ft1.setAutoReverse(false);
			ct2 = new FillTransition(Duration.millis(millis), mod7ring2, begin, stop);
			ct2.setCycleCount(1);
			ct2.setAutoReverse(false);
			ft2 = new FadeTransition(Duration.millis(millis), mod7ring2);
			ft2.setFromValue(start);
			ft2.setToValue(end);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(false);
			ct3 = new FillTransition(Duration.millis(millis), mod7ring3, begin, stop);
			ct3.setCycleCount(1);
			ct3.setAutoReverse(false);
			ft3 = new FadeTransition(Duration.millis(millis), mod7ring3);
			ft3.setFromValue(start);
			ft3.setToValue(end);
			ft3.setCycleCount(1);
			ft3.setAutoReverse(false);
			ct4 = new FillTransition(Duration.millis(millis), mod7ring4, begin, stop);
			ct4.setCycleCount(1);
			ct4.setAutoReverse(false);
			ft4 = new FadeTransition(Duration.millis(millis), mod7ring4);
			ft4.setFromValue(start);
			ft4.setToValue(end);
			ft4.setCycleCount(1);
			ft4.setAutoReverse(false);
			ct5 = new FillTransition(Duration.millis(millis), mod7ring5, begin, stop);
			ct5.setCycleCount(1);
			ct5.setAutoReverse(false);
			ft5 = new FadeTransition(Duration.millis(millis), mod7ring5);
			ft5.setFromValue(start);
			ft5.setToValue(end);
			ft5.setCycleCount(1);
			ft5.setAutoReverse(false);
			ct6 = new FillTransition(Duration.millis(millis), peacock7, begin, stop);
			ct6.setCycleCount(1);
			ct6.setAutoReverse(false);
			ft6 = new FadeTransition(Duration.millis(millis), peacock7);
			ft6.setFromValue(start);
			ft6.setToValue(end);
			ft6.setCycleCount(1);
			ft6.setAutoReverse(false);
			ct7 = new FillTransition(Duration.millis(millis), frontCurtain7, begin, stop);
			ct7.setCycleCount(1);
			ct7.setAutoReverse(false);
			ft7 = new FadeTransition(Duration.millis(millis), frontCurtain7);
			ft7.setFromValue(start);
			ft7.setToValue(end);
			ft7.setCycleCount(1);
			ft7.setAutoReverse(false);
			ct8 = new FillTransition(Duration.millis(millis), backCurtain7, begin, stop);
			ct8.setCycleCount(1);
			ct8.setAutoReverse(false);
			ft8 = new FadeTransition(Duration.millis(millis), backCurtain7);
			ft8.setFromValue(start);
			ft8.setToValue(end);
			ft8.setCycleCount(1);
			ft8.setAutoReverse(false);
			ct9 = new FillTransition(Duration.millis(millis), mod7sweep1, begin, stop);
			ct9.setCycleCount(1);
			ct9.setAutoReverse(false);
			ft9 = new FadeTransition(Duration.millis(millis), mod7sweep1);
			ft9.setFromValue(start);
			ft9.setToValue(end);
			ft9.setCycleCount(1);
			ft9.setAutoReverse(false);
			ct10 = new FillTransition(Duration.millis(millis), mod7sweep2, begin, stop);
			ct10.setCycleCount(1);
			ct10.setAutoReverse(false);
			ft10 = new FadeTransition(Duration.millis(millis), mod7sweep2);
			ft10.setFromValue(start);
			ft10.setToValue(end);
			ft10.setCycleCount(1);
			ft10.setAutoReverse(false);
			ct11 = new FillTransition(Duration.millis(millis), mod7candle1, begin, stop);
			ct11.setCycleCount(1);
			ct11.setAutoReverse(false);
			ft11 = new FadeTransition(Duration.millis(millis), mod7candle1);
			ft11.setFromValue(start);
			ft11.setToValue(end);
			ft11.setCycleCount(1);
			ft11.setAutoReverse(false);
			ct12 = new FillTransition(Duration.millis(millis), mod7candle2, begin, stop);
			ct12.setCycleCount(1);
			ct12.setAutoReverse(false);
			ft12 = new FadeTransition(Duration.millis(millis), mod7candle2);
			ft12.setFromValue(start);
			ft12.setToValue(end);
			ft12.setCycleCount(1);
			ft12.setAutoReverse(false);
			ct13 = new FillTransition(Duration.millis(millis), mod7candle3, begin, stop);
			ct13.setCycleCount(1);
			ct13.setAutoReverse(false);
			ft13 = new FadeTransition(Duration.millis(millis), mod7candle3);
			ft13.setFromValue(start);
			ft13.setToValue(end);
			ft13.setCycleCount(1);
			ft13.setAutoReverse(false);
			ct14 = new FillTransition(Duration.millis(millis), mod7candle4, begin, stop);
			ct14.setCycleCount(1);
			ct14.setAutoReverse(false);
			ft14 = new FadeTransition(Duration.millis(millis), mod7candle4);
			ft14.setFromValue(start);
			ft14.setToValue(end);
			ft14.setCycleCount(1);
			ft14.setAutoReverse(false);
			ct15 = new FillTransition(Duration.millis(millis), mod7candle5, begin, stop);
			ct15.setCycleCount(1);
			ct15.setAutoReverse(false);
			ft15 = new FadeTransition(Duration.millis(millis), mod7candle5);
			ft15.setFromValue(start);
			ft15.setToValue(end);
			ft15.setCycleCount(1);
			ft15.setAutoReverse(false);
			ct16 = new FillTransition(Duration.millis(millis), mod7candle6, begin, stop);
			ct16.setCycleCount(1);
			ct16.setAutoReverse(false);
			ft16 = new FadeTransition(Duration.millis(millis), mod7candle6);
			ft16.setFromValue(start);
			ft16.setToValue(end);
			ft16.setCycleCount(1);
			ft16.setAutoReverse(false);

			pt = new ParallelTransition(ct0, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, ct13, ct14, ct15, ct16, ft0, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
			pt.play();
			break;
		}

	}

	public void drawCandlesB(int level, double lagTime) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyValue kv25 = null;
		KeyValue kv26 = null;
		KeyValue kv27 = null;
		KeyValue kv28 = null;
		KeyValue kv29 = null;
		KeyValue kv30 = null;
		KeyValue kv31 = null;
		KeyValue kv32 = null;
		KeyValue kv33 = null;
		KeyValue kv34 = null;
		KeyValue kv35 = null;
		KeyValue kv36 = null;
		KeyValue kv37 = null;
		KeyValue kv38 = null;
		KeyValue kv39 = null;
		KeyValue kv40 = null;
		KeyValue kv41 = null;
		KeyValue kv42 = null;

		mod2candle1.setVisible(true);
		mod2candle2.setVisible(true);
		mod2candle3.setVisible(true);
		mod2candle4.setVisible(true);
		mod2candle5.setVisible(true);
		mod2candle6.setVisible(true);
		mod4candle1.setVisible(true);
		mod4candle2.setVisible(true);
		mod4candle3.setVisible(true);
		mod4candle4.setVisible(true);
		mod4candle5.setVisible(true);
		mod4candle6.setVisible(true);
		mod6candle1.setVisible(true);
		mod6candle2.setVisible(true);
		mod6candle3.setVisible(true);
		mod6candle4.setVisible(true);
		mod6candle5.setVisible(true);
		mod6candle6.setVisible(true);

		if (level == 0) {
			kv25 = new KeyValue(mod2candle1.visibleProperty(), false);
			kv26 = new KeyValue(mod2candle2.visibleProperty(), false);
			kv27 = new KeyValue(mod2candle3.visibleProperty(), false);
			kv28 = new KeyValue(mod2candle4.visibleProperty(), false);
			kv29 = new KeyValue(mod2candle5.visibleProperty(), false);
			kv30 = new KeyValue(mod2candle6.visibleProperty(), false);

			kv31 = new KeyValue(mod4candle1.visibleProperty(), false);
			kv32 = new KeyValue(mod4candle2.visibleProperty(), false);
			kv33 = new KeyValue(mod4candle3.visibleProperty(), false);
			kv34 = new KeyValue(mod4candle4.visibleProperty(), false);
			kv35 = new KeyValue(mod4candle5.visibleProperty(), false);
			kv36 = new KeyValue(mod4candle6.visibleProperty(), false);

			kv37 = new KeyValue(mod6candle1.visibleProperty(), false);
			kv38 = new KeyValue(mod6candle2.visibleProperty(), false);
			kv39 = new KeyValue(mod6candle3.visibleProperty(), false);
			kv40 = new KeyValue(mod6candle4.visibleProperty(), false);
			kv41 = new KeyValue(mod6candle5.visibleProperty(), false);
			kv42 = new KeyValue(mod6candle6.visibleProperty(), false);

		}

		final KeyValue kv1 = new KeyValue(mod2candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv2 = new KeyValue(mod2candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv3 = new KeyValue(mod2candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv4 = new KeyValue(mod2candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv5 = new KeyValue(mod2candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv6 = new KeyValue(mod2candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv7 = new KeyValue(mod4candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv8 = new KeyValue(mod4candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv9 = new KeyValue(mod4candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv10 = new KeyValue(mod4candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv11 = new KeyValue(mod4candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv12 = new KeyValue(mod4candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyValue kv13 = new KeyValue(mod6candle1.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv14 = new KeyValue(mod6candle2.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv15 = new KeyValue(mod6candle3.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv16 = new KeyValue(mod6candle4.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv17 = new KeyValue(mod6candle5.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);
		final KeyValue kv18 = new KeyValue(mod6candle6.endYProperty(), ((35 * level)), Interpolator.EASE_BOTH);

		final KeyFrame kf = new KeyFrame(Duration.seconds(lagTime), kv1, kv2, kv3, kv4, kv5, kv6, kv7, kv8, kv9, kv10, kv11, kv12, kv13, kv14, kv15, kv16, kv17, kv18, kv25, kv26, kv27, kv28, kv29, kv30, kv31, kv32, kv33, kv34, kv35, kv36, kv37, kv38, kv39, kv40, kv41, kv42);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		assert fountainPane != null : "fx:id=\"fountainPane\" was not injected: check your FXML file 'fountainSim.fxml'.";
		// assert ring1Rec != null :
		// "fx:id=\"ring1Rec\" was not injected: check your FXML file 'fountainSim.fxml'.";
		assert ring1Slider != null : "fx:id=\"ring1Slider\" was not injected: check your FXML file 'fountainSim.fxml'.";
		// assert ring2Rec != null :
		// "fx:id=\"ring2Rec\" was not injected: check your FXML file 'fountainSim.fxml'.";

		assert ring2Slider != null : "fx:id=\"ring2Slider\" was not injected: check your FXML file 'fountainSim.fxml'.";
		// assert ring3Rec != null :
		// "fx:id=\"ring3Rec\" was not injected: check your FXML file 'fountainSim.fxml'.";
		assert ring3Slider != null : "fx:id=\"ring3Slider\" was not injected: check your FXML file 'fountainSim.fxml'.";
		// assert ring4Rec != null :
		// "fx:id=\"ring4Rec\" was not injected: check your FXML file 'fountainSim.fxml'.";
		assert ring4Slider != null : "fx:id=\"ring4Slider\" was not injected: check your FXML file 'fountainSim.fxml'.";
		assert ring5Level != null : "fx:id=\"ring5Level\" was not injected: check your FXML file 'fountainSim.fxml'.";
		// assert ring5Rec != null :
		// "fx:id=\"ring5Rec\" was not injected: check your FXML file 'fountainSim.fxml'.";
		assert ring5Slider != null : "fx:id=\"ring5Slider\" was not injected: check your FXML file 'fountainSim.fxml'.";

		instance = this;

	}

	public Rectangle getMod7ring5() {
		return mod7ring5;
	}

	public void setMod7ring5(Rectangle mod7ring5) {
		this.mod7ring5 = mod7ring5;
	}

	public Group getMod5() {
		return mod5;
	}

	public void setMod5(Group mod5) {
		this.mod5 = mod5;
	}

	public Rectangle getMod3ring4() {
		return mod3ring4;
	}

	public void setMod3ring4(Rectangle mod3ring4) {
		this.mod3ring4 = mod3ring4;
	}

	public Group getMod4() {
		return mod4;
	}

	public void setMod4(Group mod4) {
		this.mod4 = mod4;
	}

	public Group getMod3() {
		return mod3;
	}

	public void setMod3(Group mod3) {
		this.mod3 = mod3;
	}

	public Rectangle getMod3ring2() {
		return mod3ring2;
	}

	public void setMod3ring2(Rectangle mod3ring2) {
		this.mod3ring2 = mod3ring2;
	}

	public Rectangle getMod6ring4() {
		return mod6ring4;
	}

	public void setMod6ring4(Rectangle mod6ring4) {
		this.mod6ring4 = mod6ring4;
	}

	public Group getMod2() {
		return mod2;
	}

	public void setMod2(Group mod2) {
		this.mod2 = mod2;
	}

	public Rectangle getMod6ring5() {
		return mod6ring5;
	}

	public void setMod6ring5(Rectangle mod6ring5) {
		this.mod6ring5 = mod6ring5;
	}

	public Group getMod7() {
		return mod7;
	}

	public void setMod7(Group mod7) {
		this.mod7 = mod7;
	}

	public Group getMod6() {
		return mod6;
	}

	public void setMod6(Group mod6) {
		this.mod6 = mod6;
	}

	public Group getMod1() {
		return mod1;
	}

	public void setMod1(Group mod1) {
		this.mod1 = mod1;
	}

	public Rectangle getMod6ring2() {
		return mod6ring2;
	}

	public void setMod6ring2(Rectangle mod6ring2) {
		this.mod6ring2 = mod6ring2;
	}

	public Rectangle getMod6ring3() {
		return mod6ring3;
	}

	public void setMod6ring3(Rectangle mod6ring3) {
		this.mod6ring3 = mod6ring3;
	}

	public Rectangle getMod6ring1() {
		return mod6ring1;
	}

	public void setMod6ring1(Rectangle mod6ring1) {
		this.mod6ring1 = mod6ring1;
	}

	public Rectangle getMod3ring3() {
		return mod3ring3;
	}

	public void setMod3ring3(Rectangle mod3ring3) {
		this.mod3ring3 = mod3ring3;
	}

	public Rectangle getMod3ring1() {
		return mod3ring1;
	}

	public void setMod3ring1(Rectangle mod3ring1) {
		this.mod3ring1 = mod3ring1;
	}

	public Rectangle getMod3ring5() {
		return mod3ring5;
	}

	public void setMod3ring5(Rectangle mod3ring5) {
		this.mod3ring5 = mod3ring5;
	}

	public Rectangle getMod4ring1() {
		return mod4ring1;
	}

	public void setMod4ring1(Rectangle mod4ring1) {
		this.mod4ring1 = mod4ring1;
	}

	public Rectangle getMod7ring4() {
		return mod7ring4;
	}

	public void setMod7ring4(Rectangle mod7ring4) {
		this.mod7ring4 = mod7ring4;
	}

	public Rectangle getMod7ring3() {
		return mod7ring3;
	}

	public void setMod7ring3(Rectangle mod7ring3) {
		this.mod7ring3 = mod7ring3;
	}

	public Rectangle getMod2ring5() {
		return mod2ring5;
	}

	public void setMod2ring5(Rectangle mod2ring5) {
		this.mod2ring5 = mod2ring5;
	}

	public Rectangle getMod7ring2() {
		return mod7ring2;
	}

	public void setMod7ring2(Rectangle mod7ring2) {
		this.mod7ring2 = mod7ring2;
	}

	public Rectangle getMod2ring4() {
		return mod2ring4;
	}

	public void setMod2ring4(Rectangle mod2ring4) {
		this.mod2ring4 = mod2ring4;
	}

	public Rectangle getMod7ring1() {
		return mod7ring1;
	}

	public void setMod7ring1(Rectangle mod7ring1) {
		this.mod7ring1 = mod7ring1;
	}

	public Rectangle getMod2ring3() {
		return mod2ring3;
	}

	public void setMod2ring3(Rectangle mod2ring3) {
		this.mod2ring3 = mod2ring3;
	}

	public Rectangle getMod5ring4() {
		return mod5ring4;
	}

	public void setMod5ring4(Rectangle mod5ring4) {
		this.mod5ring4 = mod5ring4;
	}

	public Rectangle getMod2ring2() {
		return mod2ring2;
	}

	public void setMod2ring2(Rectangle mod2ring2) {
		this.mod2ring2 = mod2ring2;
	}

	public Rectangle getMod5ring3() {
		return mod5ring3;
	}

	public void setMod5ring3(Rectangle mod5ring3) {
		this.mod5ring3 = mod5ring3;
	}

	public Rectangle getMod2ring1() {
		return mod2ring1;
	}

	public void setMod2ring1(Rectangle mod2ring1) {
		this.mod2ring1 = mod2ring1;
	}

	public Rectangle getMod5ring5() {
		return mod5ring5;
	}

	public void setMod5ring5(Rectangle mod5ring5) {
		this.mod5ring5 = mod5ring5;
	}

	public Rectangle getMod5ring2() {
		return mod5ring2;
	}

	public void setMod5ring2(Rectangle mod5ring2) {
		this.mod5ring2 = mod5ring2;
	}

	public Rectangle getMod5ring1() {
		return mod5ring1;
	}

	public void setMod5ring1(Rectangle mod5ring1) {
		this.mod5ring1 = mod5ring1;
	}

	public Rectangle getMod1ring1() {
		return mod1ring1;
	}

	public void setMod1ring1(Rectangle mod1ring1) {
		this.mod1ring1 = mod1ring1;
	}

	public Rectangle getMod4ring2() {
		return mod4ring2;
	}

	public void setMod4ring2(Rectangle mod4ring2) {
		this.mod4ring2 = mod4ring2;
	}

	public Rectangle getMod1ring2() {
		return mod1ring2;
	}

	public void setMod1ring2(Rectangle mod1ring2) {
		this.mod1ring2 = mod1ring2;
	}

	public Rectangle getMod4ring3() {
		return mod4ring3;
	}

	public void setMod4ring3(Rectangle mod4ring3) {
		this.mod4ring3 = mod4ring3;
	}

	public Rectangle getMod4ring4() {
		return mod4ring4;
	}

	public void setMod4ring4(Rectangle mod4ring4) {
		this.mod4ring4 = mod4ring4;
	}

	public Rectangle getMod4ring5() {
		return mod4ring5;
	}

	public void setMod4ring5(Rectangle mod4ring5) {
		this.mod4ring5 = mod4ring5;
	}

	public Rectangle getMod1ring5() {
		return mod1ring5;
	}

	public void setMod1ring5(Rectangle mod1ring5) {
		this.mod1ring5 = mod1ring5;
	}

	public Rectangle getMod1ring3() {
		return mod1ring3;
	}

	public void setMod1ring3(Rectangle mod1ring3) {
		this.mod1ring3 = mod1ring3;
	}

	public Rectangle getMod1ring4() {
		return mod1ring4;
	}

	public void setMod1ring4(Rectangle mod1ring4) {
		this.mod1ring4 = mod1ring4;
	}

	public QuadCurve getBazookaB() {
		return bazookaB;
	}

	public void setBazookaB(QuadCurve bazookaB) {
		this.bazookaB = bazookaB;
	}

	public Line getBazooka1() {
		return bazooka1;
	}

	public void setBazooka1(Line bazooka1) {
		this.bazooka1 = bazooka1;
	}

	public Line getBazooka2() {
		return bazooka2;
	}

	public void setBazooka2(Line bazooka2) {
		this.bazooka2 = bazooka2;
	}

	public Line getBazooka3() {
		return bazooka3;
	}

	public void setBazooka3(Line bazooka3) {
		this.bazooka3 = bazooka3;
	}

	public Line getBazooka4() {
		return bazooka4;
	}

	public void setBazooka4(Line bazooka4) {
		this.bazooka4 = bazooka4;
	}

	public Line getBazooka5() {
		return bazooka5;
	}

	public void setBazooka5(Line bazooka5) {
		this.bazooka5 = bazooka5;
	}

	public Line getPeacock1() {
		return peacock1;
	}

	public void setPeacock1(Line peacock1) {
		this.peacock1 = peacock1;
	}

	public Line getPeacock2() {
		return peacock2;
	}

	public void setPeacock2(Line peacock2) {
		this.peacock2 = peacock2;
	}

	public Line getPeacock3() {
		return peacock3;
	}

	public void setPeacock3(Line peacock3) {
		this.peacock3 = peacock3;
	}

	public Line getPeacock4() {
		return peacock4;
	}

	public void setPeacock4(Line peacock4) {
		this.peacock4 = peacock4;
	}

	public Line getPeacock5() {
		return peacock5;
	}

	public void setPeacock5(Line peacock5) {
		this.peacock5 = peacock5;
	}

	public Line getPeacock6() {
		return peacock6;
	}

	public void setPeacock6(Line peacock6) {
		this.peacock6 = peacock6;
	}

	public Line getPeacock7() {
		return peacock7;
	}

	public void setPeacock7(Line peacock7) {
		this.peacock7 = peacock7;
	}

	public Line getPeacock8() {
		return peacock8;
	}

	public void setPeacock8(Line peacock8) {
		this.peacock8 = peacock8;
	}

	public Line getPeacock9() {
		return peacock9;
	}

	public void setPeacock9(Line peacock9) {
		this.peacock9 = peacock9;
	}

	// public Rectangle getFrontCurtain() {
	// return frontCurtain;
	// }
	//
	// public void setFrontCurtain(Rectangle frontCurtain) {
	// this.frontCurtain = frontCurtain;
	// }

	// public Rectangle getBackCurtain() {
	// return backCurtain;
	// }
	//
	// public void setBackCurtain(Rectangle backCurtain) {
	// this.backCurtain = backCurtain;
	// }

	public Line getMod1sweep1() {
		return mod1sweep1;
	}

	public void setMod1sweep1(Line mod1sweep1) {
		this.mod1sweep1 = mod1sweep1;
	}

	public Line getMod1sweep2() {
		return mod1sweep2;
	}

	public void setMod1sweep2(Line mod1sweep2) {
		this.mod1sweep2 = mod1sweep2;
	}

	public Line getMod1candle1() {
		return mod1candle1;
	}

	public void setMod1candle1(Line mod1candle1) {
		this.mod1candle1 = mod1candle1;
	}

	public Line getMod1candle2() {
		return mod1candle2;
	}

	public void setMod1candle2(Line mod1candle2) {
		this.mod1candle2 = mod1candle2;
	}

	public Line getMod1candle3() {
		return mod1candle3;
	}

	public void setMod1candle3(Line mod1candle3) {
		this.mod1candle3 = mod1candle3;
	}

	public Line getMod1candle4() {
		return mod1candle4;
	}

	public void setMod1candle4(Line mod1candle4) {
		this.mod1candle4 = mod1candle4;
	}

	public Line getMod1candle5() {
		return mod1candle5;
	}

	public void setMod1candle5(Line mod1candle5) {
		this.mod1candle5 = mod1candle5;
	}

	public Line getMod1candle6() {
		return mod1candle6;
	}

	public void setMod1candle6(Line mod1candle6) {
		this.mod1candle6 = mod1candle6;
	}

	public Line getMod2sweep1() {
		return mod2sweep1;
	}

	public void setMod2sweep1(Line mod2sweep1) {
		this.mod2sweep1 = mod2sweep1;
	}

	public Line getMod2sweep2() {
		return mod2sweep2;
	}

	public void setMod2sweep2(Line mod2sweep2) {
		this.mod2sweep2 = mod2sweep2;
	}

	public Line getMod2candle1() {
		return mod2candle1;
	}

	public void setMod2candle1(Line mod2candle1) {
		this.mod2candle1 = mod2candle1;
	}

	public Line getMod2candle2() {
		return mod2candle2;
	}

	public void setMod2candle2(Line mod2candle2) {
		this.mod2candle2 = mod2candle2;
	}

	public Line getMod2candle3() {
		return mod2candle3;
	}

	public void setMod2candle3(Line mod2candle3) {
		this.mod2candle3 = mod2candle3;
	}

	public Line getMod2candle4() {
		return mod2candle4;
	}

	public void setMod2candle4(Line mod2candle4) {
		this.mod2candle4 = mod2candle4;
	}

	public Line getMod2candle5() {
		return mod2candle5;
	}

	public void setMod2candle5(Line mod2candle5) {
		this.mod2candle5 = mod2candle5;
	}

	public Line getMod2candle6() {
		return mod2candle6;
	}

	public void setMod2candle6(Line mod2candle6) {
		this.mod2candle6 = mod2candle6;
	}

	public Line getMod3sweep1() {
		return mod3sweep1;
	}

	public void setMod3sweep1(Line mod3sweep1) {
		this.mod3sweep1 = mod3sweep1;
	}

	public Line getMod3sweep2() {
		return mod3sweep2;
	}

	public void setMod3sweep2(Line mod3sweep2) {
		this.mod3sweep2 = mod3sweep2;
	}

	public Line getMod3candle1() {
		return mod3candle1;
	}

	public void setMod3candle1(Line mod3candle1) {
		this.mod3candle1 = mod3candle1;
	}

	public Line getMod3candle2() {
		return mod3candle2;
	}

	public void setMod3candle2(Line mod3candle2) {
		this.mod3candle2 = mod3candle2;
	}

	public Line getMod3candle3() {
		return mod3candle3;
	}

	public void setMod3candle3(Line mod3candle3) {
		this.mod3candle3 = mod3candle3;
	}

	public Line getMod3candle4() {
		return mod3candle4;
	}

	public void setMod3candle4(Line mod3candle4) {
		this.mod3candle4 = mod3candle4;
	}

	public Line getMod3candle5() {
		return mod3candle5;
	}

	public void setMod3candle5(Line mod3candle5) {
		this.mod3candle5 = mod3candle5;
	}

	public Line getMod3candle6() {
		return mod3candle6;
	}

	public void setMod3candle6(Line mod3candle6) {
		this.mod3candle6 = mod3candle6;
	}

	public Line getMod4sweep1() {
		return mod4sweep1;
	}

	public void setMod4sweep1(Line mod4sweep1) {
		this.mod4sweep1 = mod4sweep1;
	}

	public Line getMod4sweep2() {
		return mod4sweep2;
	}

	public void setMod4sweep2(Line mod4sweep2) {
		this.mod4sweep2 = mod4sweep2;
	}

	public Line getMod4candle1() {
		return mod4candle1;
	}

	public void setMod4candle1(Line mod4candle1) {
		this.mod4candle1 = mod4candle1;
	}

	public Line getMod4candle2() {
		return mod4candle2;
	}

	public void setMod4candle2(Line mod4candle2) {
		this.mod4candle2 = mod4candle2;
	}

	public Line getMod4candle3() {
		return mod4candle3;
	}

	public void setMod4candle3(Line mod4candle3) {
		this.mod4candle3 = mod4candle3;
	}

	public Line getMod4candle4() {
		return mod4candle4;
	}

	public void setMod4candle4(Line mod4candle4) {
		this.mod4candle4 = mod4candle4;
	}

	public Line getMod4candle5() {
		return mod4candle5;
	}

	public void setMod4candle5(Line mod4candle5) {
		this.mod4candle5 = mod4candle5;
	}

	public Line getMod4candle6() {
		return mod4candle6;
	}

	public void setMod4candle6(Line mod4candle6) {
		this.mod4candle6 = mod4candle6;
	}

	public Line getMod5sweep1() {
		return mod5sweep1;
	}

	public void setMod5sweep1(Line mod5sweep1) {
		this.mod5sweep1 = mod5sweep1;
	}

	public Line getMod5sweep2() {
		return mod5sweep2;
	}

	public void setMod5sweep2(Line mod5sweep2) {
		this.mod5sweep2 = mod5sweep2;
	}

	public Line getMod5candle1() {
		return mod5candle1;
	}

	public void setMod5candle1(Line mod5candle1) {
		this.mod5candle1 = mod5candle1;
	}

	public Line getMod5candle2() {
		return mod5candle2;
	}

	public void setMod5candle2(Line mod5candle2) {
		this.mod5candle2 = mod5candle2;
	}

	public Line getMod5candle3() {
		return mod5candle3;
	}

	public void setMod5candle3(Line mod5candle3) {
		this.mod5candle3 = mod5candle3;
	}

	public Line getMod5candle4() {
		return mod5candle4;
	}

	public void setMod5candle4(Line mod5candle4) {
		this.mod5candle4 = mod5candle4;
	}

	public Line getMod5candle5() {
		return mod5candle5;
	}

	public void setMod5candle5(Line mod5candle5) {
		this.mod5candle5 = mod5candle5;
	}

	public Line getMod5candle6() {
		return mod5candle6;
	}

	public void setMod5candle6(Line mod5candle6) {
		this.mod5candle6 = mod5candle6;
	}

	public Line getMod6sweep1() {
		return mod6sweep1;
	}

	public void setMod6sweep1(Line mod6sweep1) {
		this.mod6sweep1 = mod6sweep1;
	}

	public Line getMod6sweep2() {
		return mod6sweep2;
	}

	public void setMod6sweep2(Line mod6sweep2) {
		this.mod6sweep2 = mod6sweep2;
	}

	public Line getMod6candle1() {
		return mod6candle1;
	}

	public void setMod6candle1(Line mod6candle1) {
		this.mod6candle1 = mod6candle1;
	}

	public Line getMod6candle2() {
		return mod6candle2;
	}

	public void setMod6candle2(Line mod6candle2) {
		this.mod6candle2 = mod6candle2;
	}

	public Line getMod6candle3() {
		return mod6candle3;
	}

	public void setMod6candle3(Line mod6candle3) {
		this.mod6candle3 = mod6candle3;
	}

	public Line getMod6candle4() {
		return mod6candle4;
	}

	public void setMod6candle4(Line mod6candle4) {
		this.mod6candle4 = mod6candle4;
	}

	public Line getMod6candle5() {
		return mod6candle5;
	}

	public void setMod6candle5(Line mod6candle5) {
		this.mod6candle5 = mod6candle5;
	}

	public Line getMod6candle6() {
		return mod6candle6;
	}

	public void setMod6candle6(Line mod6candle6) {
		this.mod6candle6 = mod6candle6;
	}

	public Line getMod7sweep1() {
		return mod7sweep1;
	}

	public void setMod7sweep1(Line mod7sweep1) {
		this.mod7sweep1 = mod7sweep1;
	}

	public Line getMod7sweep2() {
		return mod7sweep2;
	}

	public void setMod7sweep2(Line mod7sweep2) {
		this.mod7sweep2 = mod7sweep2;
	}

	public Line getMod7candle1() {
		return mod7candle1;
	}

	public void setMod7candle1(Line mod7candle1) {
		this.mod7candle1 = mod7candle1;
	}

	public Line getMod7candle2() {
		return mod7candle2;
	}

	public void setMod7candle2(Line mod7candle2) {
		this.mod7candle2 = mod7candle2;
	}

	public Line getMod7candle3() {
		return mod7candle3;
	}

	public void setMod7candle3(Line mod7candle3) {
		this.mod7candle3 = mod7candle3;
	}

	public Line getMod7candle4() {
		return mod7candle4;
	}

	public void setMod7candle4(Line mod7candle4) {
		this.mod7candle4 = mod7candle4;
	}

	public Line getMod7candle5() {
		return mod7candle5;
	}

	public void setMod7candle5(Line mod7candle5) {
		this.mod7candle5 = mod7candle5;
	}

	public Line getMod7candle6() {
		return mod7candle6;
	}

	public void setMod7candle6(Line mod7candle6) {
		this.mod7candle6 = mod7candle6;
	}

	public Rectangle getSpoutRec() {
		return spoutRec;
	}

	public void setSpoutRec(Rectangle spoutRec) {
		this.spoutRec = spoutRec;
	}

	public Rectangle getFrontCurtain1() {
		return frontCurtain1;
	}

	public void setFrontCurtain1(Rectangle frontCurtain1) {
		this.frontCurtain1 = frontCurtain1;
	}

	public Rectangle getFrontCurtain2() {
		return frontCurtain2;
	}

	public void setFrontCurtain2(Rectangle frontCurtain2) {
		this.frontCurtain2 = frontCurtain2;
	}

	public Rectangle getFrontCurtain3() {
		return frontCurtain3;
	}

	public void setFrontCurtain3(Rectangle frontCurtain3) {
		this.frontCurtain3 = frontCurtain3;
	}

	public Rectangle getFrontCurtain4() {
		return frontCurtain4;
	}

	public void setFrontCurtain4(Rectangle frontCurtain4) {
		this.frontCurtain4 = frontCurtain4;
	}

	public Rectangle getFrontCurtain5() {
		return frontCurtain5;
	}

	public void setFrontCurtain5(Rectangle frontCurtain5) {
		this.frontCurtain5 = frontCurtain5;
	}

	public Rectangle getFrontCurtain6() {
		return frontCurtain6;
	}

	public void setFrontCurtain6(Rectangle frontCurtain6) {
		this.frontCurtain6 = frontCurtain6;
	}

	public Rectangle getFrontCurtain7() {
		return frontCurtain7;
	}

	public void setFrontCurtain7(Rectangle frontCurtain7) {
		this.frontCurtain7 = frontCurtain7;
	}

	public Rectangle getFrontCurtain8() {
		return frontCurtain8;
	}

	public void setFrontCurtain8(Rectangle frontCurtain8) {
		this.frontCurtain8 = frontCurtain8;
	}

	public Rectangle getFrontCurtain9() {
		return frontCurtain9;
	}

	public void setFrontCurtain9(Rectangle frontCurtain9) {
		this.frontCurtain9 = frontCurtain9;
	}

	public Rectangle getFrontCurtain10() {
		return frontCurtain10;
	}

	public void setFrontCurtain10(Rectangle frontCurtain10) {
		this.frontCurtain10 = frontCurtain10;
	}

	public Rectangle getFrontCurtain11() {
		return frontCurtain11;
	}

	public void setFrontCurtain11(Rectangle frontCurtain11) {
		this.frontCurtain11 = frontCurtain11;
	}

	public Rectangle getFrontCurtain12() {
		return frontCurtain12;
	}

	public void setFrontCurtain12(Rectangle frontCurtain12) {
		this.frontCurtain12 = frontCurtain12;
	}

	public Rectangle getFrontCurtain13() {
		return frontCurtain13;
	}

	public void setFrontCurtain13(Rectangle frontCurtain13) {
		this.frontCurtain13 = frontCurtain13;
	}

	public Rectangle getFrontCurtain14() {
		return frontCurtain14;
	}

	public void setFrontCurtain14(Rectangle frontCurtain14) {
		this.frontCurtain14 = frontCurtain14;
	}

	public Rectangle getBackCurtain1() {
		return backCurtain1;
	}

	public void setBackCurtain1(Rectangle backCurtain1) {
		this.backCurtain1 = backCurtain1;
	}

	public Rectangle getBackCurtain2() {
		return backCurtain2;
	}

	public void setBackCurtain2(Rectangle backCurtain2) {
		this.backCurtain2 = backCurtain2;
	}

	public Rectangle getBackCurtain3() {
		return backCurtain3;
	}

	public void setBackCurtain3(Rectangle backCurtain3) {
		this.backCurtain3 = backCurtain3;
	}

	public Rectangle getBackCurtain4() {
		return backCurtain4;
	}

	public void setBackCurtain4(Rectangle backCurtain4) {
		this.backCurtain4 = backCurtain4;
	}

	public Rectangle getBackCurtain5() {
		return backCurtain5;
	}

	public void setBackCurtain5(Rectangle backCurtain5) {
		this.backCurtain5 = backCurtain5;
	}

	public Rectangle getBackCurtain6() {
		return backCurtain6;
	}

	public void setBackCurtain6(Rectangle backCurtain6) {
		this.backCurtain6 = backCurtain6;
	}

	public Rectangle getBackCurtain7() {
		return backCurtain7;
	}

	public void setBackCurtain7(Rectangle backCurtain7) {
		this.backCurtain7 = backCurtain7;
	}

	public Rectangle getBackCurtain8() {
		return backCurtain8;
	}

	public void setBackCurtain8(Rectangle backCurtain8) {
		this.backCurtain8 = backCurtain8;
	}

	public Rectangle getBackCurtain9() {
		return backCurtain9;
	}

	public void setBackCurtain9(Rectangle backCurtain9) {
		this.backCurtain9 = backCurtain9;
	}

	public Rectangle getBackCurtain10() {
		return backCurtain10;
	}

	public void setBackCurtain10(Rectangle backCurtain10) {
		this.backCurtain10 = backCurtain10;
	}

	public Rectangle getBackCurtain11() {
		return backCurtain11;
	}

	public void setBackCurtain11(Rectangle backCurtain11) {
		this.backCurtain11 = backCurtain11;
	}

	public Rectangle getBackCurtain12() {
		return backCurtain12;
	}

	public void setBackCurtain12(Rectangle backCurtain12) {
		this.backCurtain12 = backCurtain12;
	}

	public Rectangle getBackCurtain13() {
		return backCurtain13;
	}

	public void setBackCurtain13(Rectangle backCurtain13) {
		this.backCurtain13 = backCurtain13;
	}

	public Rectangle getBackCurtain14() {
		return backCurtain14;
	}

	public void setBackCurtain14(Rectangle backCurtain14) {
		this.backCurtain14 = backCurtain14;
	}

	public void acceptSubmapOfFcws(ConcurrentNavigableMap<Integer, ArrayList<FCW>> subMap) {
		bufferedFcws = subMap;

	}

	public void pauseLeftSweep() {
		leftSweepTimeline.pause();
	}

	public void playLeftSweep() {
		leftSweepTimeline.play();
	}

	public void pauseRightSweep() {
		rightSweepTimeline.pause();
	}

	public void playRightSweep() {
		rightSweepTimeline.play();
	}

	public int getSweepType() {
		return sweepType;
	}

	public void setSweepType(int sweepType) {
		this.sweepType = sweepType;
	}

	public Timeline getTimeline2() {
		return leftSweepTimeline;
	}

	public void setTimeline2(Timeline timeline2) {
		this.leftSweepTimeline = timeline2;
	}

	public Timeline getTimeline3() {
		return rightSweepTimeline;
	}

	public void setTimeline3(Timeline timeline3) {
		this.rightSweepTimeline = timeline3;
	}

	public double getLeftSweepSpeed() {
		return leftSweepSpeed;
	}

	public void setLeftSweepSpeed(double leftSweepSpeed) {
		this.leftSweepSpeed = leftSweepSpeed;
	}

	public double getRightSweepSpeed() {
		return rightSweepSpeed;
	}

	public void setRightSweepSpeed(double rightSweepSpeed) {
		this.rightSweepSpeed = rightSweepSpeed;
	}

	// Stops all animation of the sweepers
	public void clearSweeps() {
		mod1sweep1.getTransforms().clear();
		mod2sweep1.getTransforms().clear();
		mod3sweep1.getTransforms().clear();
		mod4sweep1.getTransforms().clear();
		mod5sweep1.getTransforms().clear();
		mod6sweep1.getTransforms().clear();
		mod7sweep1.getTransforms().clear();
		mod1sweep2.getTransforms().clear();
		mod2sweep2.getTransforms().clear();
		mod3sweep2.getTransforms().clear();
		mod4sweep2.getTransforms().clear();
		mod5sweep2.getTransforms().clear();
		mod6sweep2.getTransforms().clear();
		mod7sweep2.getTransforms().clear();
	}

	// Sets the heights of all water features to zero, effectivly turning them
	// off.
	public void resetAll() {
		drawBazooka(0, 0);
		drawBkCurtain(0, 0);
		drawCandlesA(0, 0);
		drawCandlesB(0, 0);
		drawFtCurtain(0, 0);
		drawMultiA(0, 0);
		drawMultiB(0, 0);
		drawPeacock(0, 0);
		drawRing1A(0, 0);
		drawRing1B(0, 0);
		drawRing2A(0, 0);
		drawRing2B(0, 0);
		drawRing3A(0, 0);
		drawRing3B(0, 0);
		drawRing4A(0, 0);
		drawRing4B(0, 0);
		drawRing5A(0, 0);
		drawRing5B(0, 0);
		drawSpout(0, 0);
		drawSweepsA(0, 0);
		drawSweepsB(0, 0);
	}
}