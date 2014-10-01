package choreography.view.colorPalette;

import java.net.URL;
import java.util.ResourceBundle;

import choreography.model.color.ColorPaletteModel;
import choreography.view.timeline.TimelineController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.geometry.Side;


//TODO get rid of ColorPaletteModel?
public class ColorPaletteController implements Initializable {
	
	@FXML private static ColorPaletteController cpc;
    @FXML private HBox defaultColorsBox;
    @FXML private HBox customColorsBox;
    
    @FXML private Rectangle rectangle_OFF;
    @FXML private Rectangle rectangle_WHITE;
    @FXML private Rectangle rectangle_LIGHT_RED;
    @FXML private Rectangle rectangle_RED;
    @FXML private Rectangle rectangle_LIGHT_ORANGE;
    @FXML private Rectangle rectangle_ORANGE;
    @FXML private Rectangle rectangle_LIGHT_YELLOW;
    @FXML private Rectangle rectangle_YELLOW;
    @FXML private Rectangle rectangle_LIGHT_GREEN;
    @FXML private Rectangle rectangle_GREEN;
    @FXML private Rectangle rectangle_LIGHT_BLUE;
    @FXML private Rectangle rectangle_CYAN;
    @FXML private Rectangle rectangle_BLUE;
    @FXML private Rectangle rectangle_VIOLET;
    @FXML private Rectangle rectangle_LIGHT_VIOLET;
    @FXML private Rectangle rectangle_MAGENTA;
    
    @FXML private Rectangle custom1;
    @FXML private Rectangle custom2;
    @FXML private Rectangle custom3;
    @FXML private Rectangle custom4;
    @FXML private Rectangle custom5;
    @FXML private Rectangle custom6;
    @FXML private Rectangle custom7;
    @FXML private Rectangle custom8;
    @FXML private Rectangle custom9;
    @FXML private Rectangle custom10;
    @FXML private Rectangle custom11;
    @FXML private Rectangle custom12;
    @FXML private Rectangle custom13;
    @FXML private Rectangle custom14;
    @FXML private Rectangle custom15;
    @FXML private Rectangle custom16;
    
    private Rectangle[] rectangles;
    private Color[] colors;
    
    private Rectangle changingRectangle;
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem changeColor = new MenuItem();
    private ColorPicker colorPicker = new ColorPicker();
    
    public ColorPaletteController(){
    	rectangles = new Rectangle[]{rectangle_OFF,rectangle_WHITE,rectangle_LIGHT_RED,rectangle_RED,
				  	  rectangle_LIGHT_ORANGE,rectangle_ORANGE,rectangle_LIGHT_YELLOW,rectangle_YELLOW,
				  	  rectangle_LIGHT_GREEN,rectangle_GREEN,rectangle_LIGHT_BLUE,rectangle_CYAN,
				  	  rectangle_BLUE,rectangle_VIOLET,rectangle_LIGHT_VIOLET,rectangle_MAGENTA,
				  	  custom1,custom2,custom3,custom4,custom5,custom6,custom7,custom8,
				  	  custom9,custom10,custom11,custom12,custom13,custom14,custom15,custom16};
    	
    	colors = ColorPaletteModel.getInstance().getColors();

    	changingRectangle = null;
    	contextMenu = new ContextMenu();
    	changeColor = new MenuItem();
    	colorPicker = new ColorPicker();
    }
    
    /**
     * @return cpc the current colorPalette
     * Returns the colorPalette that has been fully built. 
     */
    public static ColorPaletteController getInstance() {
    	if(cpc == null)
    		cpc = new ColorPaletteController();
        return cpc;
    }
   
   public void setSelectedColorIndex(int c) {
        ColorPaletteModel.getInstance().setSelectedIndex(c);
   }

   public void rePaint(){
        Color[] colors = ColorPaletteModel.getInstance().getColors();
        for(int i = 0; i < colors.length; i++) {
        	rectangles[i].setFill(colors[i]);
        }
   } 
   
   public void resetStrokeWidths(){
	   for(int i = 0; i < rectangles.length; i++){
		   rectangles[i].setStrokeWidth(1);
	   }
   }

    /**
    * @param arg0
    * @param arg1
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     * Initializes the colorPalette. 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	ColorPaletteModel.getInstance().setDefaultColors();
    	contextMenu.getItems().add(changeColor);
    	changeColor.setGraphic(colorPicker);
    	
    	for(int i = 0; i < 16; i++) {
        	rectangles[i].setFill(colors[i]);
        	rectangles[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
        		public void handle(MouseEvent e){
            		if(e.getButton() != MouseButton.SECONDARY){
            			resetStrokeWidths();
            			if(e.getSource() == rectangles[1]){
            				((Rectangle)e.getSource()).setStroke(Color.WHITE);
            				((Rectangle)e.getSource()).setStrokeWidth(4);
            				ColorPaletteModel.getInstance().setSelectedColor(((Rectangle)e.getSource()).getFill());
            			}else{
            				((Rectangle)e.getSource()).setStrokeWidth(4);
            				ColorPaletteModel.getInstance().setSelectedColor(((Rectangle)e.getSource()).getFill());
            			}
            		}
            	}
            });
        }
        
        for(int i = 16; i < colors.length; i++) {
            rectangles[i].setFill(colors[i]);
            rectangles[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
            	public void handle(MouseEvent e){
            		if(e.getButton() == MouseButton.SECONDARY){
            			changingRectangle = (Rectangle)e.getSource();
            			contextMenu.show((Rectangle)e.getSource(),Side.TOP,0,0);
            		}else{
            			resetStrokeWidths();
            			((Rectangle)e.getSource()).setStrokeWidth(4);
            			ColorPaletteModel.getInstance().setSelectedColor(((Rectangle)e.getSource()).getFill());
            		}
            	}
            });
        }
        
        for(int i = 0; i < rectangles.length; i++){
        	rectangles[i].setOnMouseEntered(new EventHandler<MouseEvent>(){
        		public void handle(MouseEvent e){
        			((Rectangle)e.getSource()).setStroke(Color.GREY);
        			((Rectangle)e.getSource()).setStrokeWidth(2);
        		}
        	});
        	
        	rectangles[i].setOnMouseExited(new EventHandler<MouseEvent>(){
        		public void handle(MouseEvent e){
        			((Rectangle)e.getSource()).setStroke(Color.BLACK);
        			((Rectangle)e.getSource()).setStrokeWidth(1);
        		}
        	});
        }
        
        colorPicker.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent e){
        		changingRectangle.setFill(((ColorPicker)e.getSource()).getValue());
        	}
        });
        
        cpc = this;
    }
}
