package choreography.model.color;

import choreography.view.colorPalette.ColorPaletteController;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Represents the collection of colors that the developer is able to use. 
 * Takes input from choreography.view.colorPalette.ColorPaletteController and alters the collection of colors.
 *
 * @author Danny Selgo
 */
public class ColorPaletteModel{
    
    private static ColorPaletteModel instance;
    private Color[] colors;
    private int selectedIndex;
    
    /**
     * Constructor class for ColorPaletteModel. 
     * Initializes the array of colors and sets the first 16 colors to the default colors.
     */
    public ColorPaletteModel() {
        colors = new Color[32];
        setDefaultColors();
    }
    
    /**
     * Checks to see if an instance of ColorPaletteModel exists. 
     * If so, return it. If not, instantiate an instance of ColorPaletteModel and return it.
     * 
     * @return the instance of ColorPaletteModel
     */
    public static ColorPaletteModel getInstance() {
        if(instance == null)
            instance = new ColorPaletteModel();
        return instance;
    }
    
    /**
     * Sets colors 1-16 to the fountains default colors. Then sets colors 17-32 to white by default.
     */
    public void setDefaultColors() {
        colors[0]=Color.web(ColorPaletteEnum.OFF.getColor());
        colors[1]=Color.web(ColorPaletteEnum.RED.getColor());
        colors[2]=Color.web(ColorPaletteEnum.BLUE.getColor());
        colors[3]=Color.web(ColorPaletteEnum.VIOLET.getColor());
        colors[4]=Color.web(ColorPaletteEnum.YELLOW.getColor());
        colors[5]=Color.web(ColorPaletteEnum.ORANGE.getColor());
        colors[6]=Color.web(ColorPaletteEnum.GREEN.getColor());
        colors[7]=Color.web(ColorPaletteEnum.MAGENTA.getColor());
        colors[8]=Color.web(ColorPaletteEnum.WHITE.getColor());
        colors[9]=Color.web(ColorPaletteEnum.LIGHTRED.getColor());
        colors[10]=Color.web(ColorPaletteEnum.LIGHTBLUE.getColor());
        colors[11]=Color.web(ColorPaletteEnum.LIGHTVIOLET.getColor());
        colors[12]=Color.web(ColorPaletteEnum.LIGHTYELLOW.getColor());
        colors[13]=Color.web(ColorPaletteEnum.LIGHTORANGE.getColor());
        colors[14]=Color.web(ColorPaletteEnum.LIGHTGREEN.getColor());
        colors[15]=Color.web(ColorPaletteEnum.CYAN.getColor());
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
    
    /**
     * Returns the array of colors.
     * 
     * @return the array of colors
     */
    public Color[] getColors(){
    	return colors;
    }
    
    /**
     * Returns the color specified by the given index.
     * 
     * @param i - index of color to get
     * @return the color specified by the given index
     */
    public Color getColor(int i){
    	try{
    		return colors[i];	
    	}
    	catch(ArrayIndexOutOfBoundsException e){
    		return null;
    	}
        
    }

    /**
     * Returns what index is currently selected.
     * 
     * @return the selected index
     */
    public int getSelectedIndex(){
        return selectedIndex;
    }
    
    /**
     * Returns what color is currently selected.
     * 
     * @return the selected color
     */
    public Paint getSelectedColor(){
    	return colors[getSelectedIndex()];
    }
    
    /**
     * Sets the selected index.
     * 
     * @param selectedIndex - new selected index
     */
    public void setSelectedIndex(int selectedIndex){
        this.selectedIndex = selectedIndex;
    }
    
    /**
     * Adds a new color to the array by replacing the color associated by the given index with the given color.
     * 
     * @param newColor - the new color to be added
     * @param index - the index in the array to put the new color
     */
    public void setColor(Color newColor, int index){
        this.colors[index] = newColor;
        ColorPaletteController.getInstance().rePaint();
    }		      
    
    /**
     * Resets the model to its default colors and then signals the GUI to repaint.
     */
    public void resetModel(){
        instance = new ColorPaletteModel();
        ColorPaletteController.getInstance().rePaint();
    }

    /**
     * Compares a color from the light timeline and return the index number that it 
     * is from the color palette
     * @param grid
     * @return
     */
    public int checkColor(Paint grid) {
    	int index = 0;
    	
    	// Creates a hex representation of the color
    	Color c = (Color) grid;
    	String hexGrid = String.format( "#%02X%02X%02X",
    	            (int)( c.getRed() * 255 ),
    	            (int)( c.getGreen() * 255 ),
    	            (int)( c.getBlue() * 255 ) );
    	
    	// Compares to hex representation of all colors in the palette
    	for(index=0;index < 32; index++) {
    		String hexIndex = String.format( "#%02X%02X%02X",
    	            (int)( colors[index].getRed() * 255 ),
    	            (int)( colors[index].getGreen() * 255 ),
    	            (int)( colors[index].getBlue() * 255 ) );
    		if (hexGrid.compareTo(hexIndex)==0)
    			return index;
    	}
    	System.out.println("Color Not Found");
    	return 89;
    }
}

