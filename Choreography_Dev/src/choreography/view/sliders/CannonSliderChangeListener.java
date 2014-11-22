package choreography.view.sliders;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import choreography.io.FCWLib;
import choreography.model.cannon.Cannon;
import choreography.model.fcw.FCW;
import choreography.view.ChoreographyController;

/**
 *
 * @author elementsking
 */
class CannonSliderChangeListener<T extends Cannon> implements ChangeListener<Number> {

	ArrayList<T> cannons;
	String name;
	String module;
	private int lastNumber;

	public CannonSliderChangeListener(ArrayList<T> list, String module) {
		this.module = module;
		cannons = new ArrayList<>();
		for (T c : list) {
			cannons.add(c);
		}

		name = list.get(0).getName();
	}

	/*
	 * It finds the FCW given a change in one of the sliders and then outputs
	 * the result onto the bottom left corner of the screen in fcwOutput
	 */
	@Override
	public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
		int level = t1.intValue();
		lastNumber = level;
		for (T c : cannons) {
			c.setLevel(level);
		}
		String[] actions = new String[] { module, Integer.toString(level) };
		FCW f = FCWLib.getInstance().getFCW(name, actions);
		ChoreographyController.getInstance().setfcwOutput(f.toString());
	}

	/**
	 * @return the lastNumber
	 */
	public int getLastNumber() {
		return lastNumber;
	}
}
