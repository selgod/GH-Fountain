package choreography.view.colorPalette;

import choreography.io.MapLib;
import choreography.model.color.ColorPaletteModel;
import java.net.URL;
import java.util.ResourceBundle;

//import javax.swing.JButton;


import choreography.view.timeline.TimelineController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
//import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author Nick Van Kuiken
 * @author Frank Madrid
 * Controls the colorPalette
 */
public class ColorPaletteController implements Initializable {

    @FXML private static ColorPaletteController cpc;
    // HBox used to hold the colorPalette
    private HBox colorRectanglePane;
    @FXML private HBox colorPalette;
    // Color picker used for the custom colors. 
    @FXML ColorPicker colorPicker;
    @FXML private Rectangle[] rectangles;
    @FXML private Paint selectedColor;
    @FXML private int selectedColorIndex;
    @FXML private Button addRec;
    @FXML private Button changeButton;
    
    /**
     * @return cpc the current colorPalette
     * Returns the colorPalette that has been fully built. 
     */
    public static ColorPaletteController getInstance() {
            if(cpc == null)
                    cpc = new ColorPaletteController();
            return cpc;
    }
    
    
    /**
    * @return selectedColor current instance of the selected color
    * Returns the current instance of the selected color.
    */
   public Paint getSelectedColor() {
       return selectedColor;
   }

   /**
    * @param c The passes color that has been selected
    * Sets the global variable selectedColor to the color that has been chosen
    */
   public void setSelectedColor(int c) {
        setSelectedColor(ColorPaletteModel.getInstance().getColors()[c]);
   }

   public void setSelectedColorIndex(int c) {
        ColorPaletteModel.getInstance().setSelectedIndex(c);
   }

    /**
    * @param arg0
    * @param arg1
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     * Initializes the colorPalette. 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        rectangles = new Rectangle[32];
        colorRectanglePane = new HBox(0.5);
        
        // Sets the first color to red and creates the event handler
        for(int index = 0; index < 16 ; index++) {
            final int index2 = index;   
            rectangles[index] = new Rectangle(25, 25, ColorPaletteModel.getInstance().getColors()[index]);
            rectangles[index].setOnMouseClicked(new EventHandler<MouseEvent> (){
            	
            	public void handle(MouseEvent e) {
            		ColorPaletteModel.getInstance().setSelectedIndex(index2);
                    setSelectedColor(ColorPaletteModel.getInstance().getColor(index2 + 1));
            	}
            });
            colorRectanglePane.getChildren().add(rectangles[index]);
        }
        
            addRec.setOnAction(new EventHandler<ActionEvent> () {

                @Override
                public void handle(ActionEvent event) {
                    int index = colorRectanglePane.getChildren().size();
                        //	final int index2 = index;					
                    Color c = colorPicker.getValue();
                    Rectangle rectangle = rectangles[index] = new Rectangle(25, 25,c);														 
                    colorRectanglePane.getChildren().add(rectangle);
                    ColorPaletteModel.getInstance().setColor(c, index);
                    
                    rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) { 
                                ColorPaletteModel.getInstance().setSelectedIndex(index);							
                                setSelectedColor(c);
                        }
                });
                } 
            });
            
       
            
            changeButton.setOnAction(new EventHandler<ActionEvent>() {
				
                @Override
                public void handle(ActionEvent arg0) {
                    int index2 = ColorPaletteModel.getInstance().getSelectedIndex();
                    Color c = colorPicker.getValue();
                    if(ColorPaletteModel.getInstance().changeColor(c, index2)) {
                        Rectangle rectangle = rectangles[index2];
                        rectangle.setFill(colorPicker.getValue());
                        
                        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent me) {
                                ColorPaletteModel.getInstance().setSelectedIndex(index2);
                                setSelectedColor(c);
                            }
                            
                        });
                        
                        TimelineController.getInstance().rePaint();
                    } 
                }
            });
            colorPalette.getChildren().add(colorRectanglePane);
         cpc = this;
        } 
    
    
    /**
     * @return the selectedColorIndex
     */
    public int getSelectedColorIndex() {
        return selectedColorIndex;
    }

    /**
     * @param selectedColor the selectedColor to set
     */
    public void setSelectedColor(Paint selectedColor) {
        this.selectedColor = selectedColor;
       
    }

    public void rePaint() {
        
//            rectangles = new Rectangle[32];
            colorRectanglePane.getChildren().clear();
            Color[] colors = ColorPaletteModel.getInstance().getColors();
        
            // Sets the first color to red and creates the event handler
            for(int index = 0; index < colors.length; index++) {
                colorPalette.getChildren().remove(colorRectanglePane);
                final int index2 = index;   
                rectangles[index] = new Rectangle(25, 25, ColorPaletteModel.getInstance().getColors()[index]);
                rectangles[index].setOnMouseClicked(new EventHandler<MouseEvent> (){

                        @Override
                    public void handle(MouseEvent e) {
                        ColorPaletteModel.getInstance().setSelectedIndex(index2);
                        setSelectedColor(ColorPaletteModel.getInstance().getColor(index2 + 1));
                    }
                });
                colorRectanglePane.getChildren().add(rectangles[index]);
            }
            for(int i = 0; i < colors.length; i++) {
                rectangles[i].setFill(colors[i]);
            }
            if(!ColorPaletteModel.getInstance().isClassicColors()) {
                colorPalette.getChildren().add(colorRectanglePane);
            }
//            else  {
//                colorPalette.setVisible(false);
//            }
    } 

    public void resurrectColorPalettePane() {
        colorPalette.setVisible(true);
    }
}
