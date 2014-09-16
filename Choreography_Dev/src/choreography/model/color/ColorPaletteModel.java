/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */

package choreography.model.color;

import choreography.io.MapLib;
import choreography.view.colorPalette.ColorPaletteController;
import choreography.view.timeline.TimelineController;
import java.io.File;
import java.util.HashMap;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author elementsking
 */
public class ColorPaletteModel {
    
    private static ColorPaletteModel instance;
    private boolean classicColors;
    private Color[] colors;
    private int availableColors;
    private int selectedIndex;
    private HashMap<Integer, Integer> classicMap;
    
    public static ColorPaletteModel getInstance() {
        if(instance == null)
            instance = new ColorPaletteModel();
        return instance;
    }
    
    /**
     * @return the classicColors
     */
    public boolean isClassicColors() {
        return classicColors;
    }

    /**
     * @param aClassicColors the classicColors to set
     */
    public void setClassicColors(boolean aClassicColors) {
        classicColors = aClassicColors;
        classicMap = new HashMap<>();
        classicMap.put(1, 1);
        classicMap.put(2, 5);
        classicMap.put(3, 6);
        classicMap.put(4, 3);
        classicMap.put(5, 2);
        classicMap.put(6, 4);
        classicMap.put(7, 13);
        classicMap.put(8, 15);
        classicMap.put(9, 7);
        classicMap.put(10, 11);
        classicMap.put(11, 12);
        classicMap.put(12, 9);
        classicMap.put(13, 8);
        classicMap.put(14, 10);
        classicMap.put(15, 14);
        classicMap.put(16, 4);
        classicMap.put(32, 3);
        classicMap.put(48, 10);
        ColorPaletteController.getInstance().rePaint();
    }
    
    public ColorPaletteModel() {
        colors = new Color[32];
        availableColors = 16;
        classicMap = new HashMap<>(32);
    }
    

    /**
     * @return the colors
     */
    public Color[] getColors() {
        return colors;
    }
    

    /**
     * @return the availableColors
     */
    public int getAvailableColors() {
        return availableColors;
    }

      /**
     * @return the selectedIndex
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    
    public void setColor(Color newColor, int index){
        this.colors[index] = newColor;
        this.colors[availableColors] = newColor;
        availableColors++;
    }		      
       
    /**
     * @param newColor
     * @param index
     * @return 
     */    
    public boolean changeColor(Color newColor, int index) {
    	if(index > 15) {
            colors[index] = newColor;
            return true;
    	}
    	return false;  		 	
    }
     
    /**
     * @param availableColors the availableColors to set
     */
    public void setAvailableColors(int availableColors) {
        this.availableColors = availableColors;
         }

       /**
     * @param selectedIndex the selectedIndex to set
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    
    public Paint getColor(int i){
        if(classicColors) {
            if(i == 0) {
                return Color.web(ColorPaletteEnum.OFF.getColor());
            }
            return colors[classicMap.get(i) - 1];
        }
        else {
            if(i == 0)
                return colors[0];
            else
                return colors[i - 1];
        }
//        throw new IllegalArgumentException(i + "isn't a valid color reference...");
    }

    public void setColors(Color[] parseMap) {
        this.colors = parseMap;
        ColorPaletteController.getInstance().rePaint();
    }
    
    public void resetModel() {
        instance = new ColorPaletteModel();
        ColorPaletteController.getInstance().rePaint();
    }

}
