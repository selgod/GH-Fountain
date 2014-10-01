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
public class ColorPaletteModel{
    
    private static ColorPaletteModel instance;
    private Color[] colors;
    private int selectedIndex;
    private Paint selectedColor;
    
    public ColorPaletteModel() {
        colors = new Color[32];
        setDefaultColors();
    }
    
    public static ColorPaletteModel getInstance() {
        if(instance == null)
            instance = new ColorPaletteModel();
        return instance;
    }
    
    /**
     * @param aClassicColors the classicColors to set
     */
    public void setDefaultColors() {
        colors[0]=Color.web(ColorPaletteEnum.OFF.getColor());
        colors[1]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[2]=Color.web(ColorPaletteEnum.LIGHTRED.getColor());
        colors[3]=Color.web(ColorPaletteEnum.RED.getColor());
        colors[4]=Color.web(ColorPaletteEnum.LIGHTORANGE.getColor());
        colors[5]=Color.web(ColorPaletteEnum.ORANGE.getColor());
        colors[6]=Color.web(ColorPaletteEnum.LIGHTYELLOW.getColor());
        colors[7]=Color.web(ColorPaletteEnum.YELLOW.getColor());
        colors[8]=Color.web(ColorPaletteEnum.LIGHTGREEN.getColor());
        colors[9]=Color.web(ColorPaletteEnum.GREEN.getColor());
        colors[10]=Color.web(ColorPaletteEnum.LIGHTBLUE.getColor());
        colors[11]=Color.web(ColorPaletteEnum.CYAN.getColor());
        colors[12]=Color.web(ColorPaletteEnum.BLUE.getColor());
        colors[13]=Color.web(ColorPaletteEnum.VIOLET.getColor());
        colors[14]=Color.web(ColorPaletteEnum.LIGHTVIOLET.getColor());
        colors[15]=Color.web(ColorPaletteEnum.MAGENTA.getColor());
        colors[16]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[17]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[18]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[19]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[20]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[21]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[22]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[23]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[24]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[25]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[26]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[27]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[28]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[29]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[30]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[31]=Color.web(ColorPaletteEnum.WHITE.getColor());
    }    
    
    public Color[] getColors(){
    	return colors;
    }
    
    public Color getColor(int i){
        return colors[i];
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }
    
    public Paint getSelectedColor(){
    	return selectedColor;
    }
    
    public void setColor(Color newColor, int index){
        this.colors[index] = newColor;
    }		      

    public void setSelectedIndex(int selectedIndex){
        this.selectedIndex = selectedIndex;
    }
    
    public void setSelectedColor(Paint selectedColor){
        this.selectedColor = selectedColor;
    }
    
    public void resetModel(){
        instance = new ColorPaletteModel();
        ColorPaletteController.getInstance().rePaint();
    }
}
