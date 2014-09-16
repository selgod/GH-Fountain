/**
 * 
 */
package choreography.view.sliders;

import choreography.io.FCWLib;
import choreography.model.cannon.IndependentCannon;
import choreography.model.fcw.FCW;
import choreography.view.ChoreographyController;
import choreography.view.music.MusicPaneController;
import choreography.view.sim.FountainSimController;
import choreography.view.timeline.TimelineController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author madridf
 *
 */
public class IndependentCannonSliderChangeListener implements ChangeListener<Number>{

	private final IndependentCannon cannon;
	private final String name;
        private int lastNumber;

	/**
	 * @param cannon
	 */
	public IndependentCannonSliderChangeListener(IndependentCannon cannon) {
            this.cannon = cannon;
            name = cannon.getName();
	}

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    @Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue,
			Number newValue) {
            int level = newValue.intValue();
            lastNumber = level;
            cannon.setLevel(level);
            String[] actions = new String[]{Integer.toString(level), name};
            FCW f = FCWLib.getInstance().getFCW(name, actions);
            ChoreographyController.getInstance().setfcwOutput(
                f.toString());
//            TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(
//                MusicPaneController.getInstance().getTenthsTime(), f);
            FountainSimController.getInstance().acceptFcw(f);
	}

    public int getLastNumber() {
        return lastNumber;
    }

    String getName() {
        return name;
    }

}
