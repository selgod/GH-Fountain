/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.model.lagtime;

import java.beans.PropertyChangeSupport;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author elementsking
 */
public class LagTime {

    /**
     *
     */
    public static final String PROP_DELAYNAME = "PROP_DELAYNAME";

    /**
     *
     */
    public static final String PROP_DELAYTIME = "PROP_DELAYTIME";
    private SimpleStringProperty delayName;
    private SimpleDoubleProperty delayTime;
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    /**
     *
     * @param name
     * @param time
     */
    public LagTime(String name, Double time) {
        delayName = new SimpleStringProperty(name.toUpperCase());
        delayTime = new SimpleDoubleProperty(time);
    }
    
    /**
     * @return the delayName
     */
    public String getDelayName() {
        return delayName.get();
    }

    /**
     * @return the delayTime
     */
    public Double getDelayTime() {
        return delayTime.get();
    }

    /**
     * @param delayName the delayName to set
     */
    public void setDelayName(String delayName) {
        javafx.beans.property.SimpleStringProperty oldDelayName = this.delayName;
        this.delayName.set(delayName);
        propertyChangeSupport.firePropertyChange(PROP_DELAYNAME, oldDelayName, delayName);
    }

    /**
     * @param delayTime the delayTime to set
     */
    public void setDelayTime(Double delayTime) {
        javafx.beans.property.SimpleDoubleProperty oldDelayTime = this.delayTime;
        this.delayTime.set(delayTime);
        propertyChangeSupport.firePropertyChange(PROP_DELAYTIME, oldDelayTime, delayTime);
    }
    
    
}
