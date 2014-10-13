package choreography.model.color;

/**
 * Enumerated type definition for the 16 default colors.
 *
 * @author Danny Selgo
 */

public enum ColorPaletteEnum {
<<<<<<< HEAD
    BLACK ("000000"),
    RED ("FF0000"), 
    ORANGE ("FF1900"), 
    YELLOW ("FFC800"), 
    GREEN ("00F000"), 
    BLUE ("0000FF"), 
    VIOLET ("3B11FF"), 
    LIGHTRED ("FF0037"), 
    LIGHTORANGE ("FF3205"), 
    LIGHTYELLOW ("3BFF00"), 
    LIGHTGREEN ("3BFF00"), 
    LIGHTBLUE ("00FFFF"), 
    LIGHTVIOLET ("9646FF"), 
    MAGENTA ("D100C5"), 
    CYAN ("0096EF"), 
    OFF ("000000"), 
    WHITE ("FFFFFF"),
    AMBER ("FFC200");
=======
	
    RED ("#FF0000"), 
    ORANGE ("#FF1900"), 
    YELLOW ("#FFC800"), 
    GREEN ("#00F000"), 
    BLUE ("#0000FF"), 
    VIOLET ("#3B11FF"), 
    LIGHTRED ("#FF0037"), 
    LIGHTORANGE ("#FF3205"), 
    LIGHTYELLOW ("#FFFF08"), 
    LIGHTGREEN ("#3BFF00"), 
    LIGHTBLUE ("#00FFFF"), 
    LIGHTVIOLET ("#9646FF"), 
    MAGENTA ("#D100C5"), 
    CYAN ("#0096EF"), 
    OFF ("#000000"), 
    WHITE ("#FFFFFF");
>>>>>>> origin/ColorPalette
    
    private String color;
    
    private ColorPaletteEnum(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }
}
