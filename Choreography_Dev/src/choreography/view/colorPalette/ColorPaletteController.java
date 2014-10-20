package choreography.view.colorPalette;

import java.net.URL;
import java.util.ResourceBundle;

import choreography.model.color.ColorPaletteModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.geometry.Side;

/**
 * UI for selecting which colors to use for the fountain's lights. Communicates with 
 * choreography.model.color.ColorPaletteModel by displaying the colors set in the model. 
 * Colors 1-16 are the fountain's default color and cannot be changed. Colors 17-32 are 
 * set to white by default but can be changed by the developer to fit their personal needs. 
 * 
 * @author Danny Selgo
 */
public class ColorPaletteController implements Initializable {
	
	@FXML private static ColorPaletteController cpc;
    @FXML private HBox defaultColors;
    @FXML private HBox customColors;
    
    private Tooltip[] defaultTooltips;
    private Tooltip[] customTooltips;
    private Rectangle selectedRectangle;
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem changeColor = new MenuItem();
    private ColorPicker colorPicker = new ColorPicker();
    
    /**
     * Constructor class ColorPaletteController. Initializes the UI elements that were not defined in ColorPalette.fxml.
     */
    public ColorPaletteController(){    	
    	defaultTooltips = new Tooltip[]{new Tooltip("Black"),new Tooltip("White"),new Tooltip("Light Red"),new Tooltip("Red"),
    					  new Tooltip("Light Orange"),new Tooltip("Orange"),new Tooltip("Light Yellow"),new Tooltip("Yellow"),
    					  new Tooltip("Light Green"),new Tooltip("Green"),new Tooltip("Light Blue"),new Tooltip("Cyan"),
    					  new Tooltip("Blue"),new Tooltip("Violet"),new Tooltip("Light Violet"),new Tooltip("Magenta")};
    	customTooltips = new Tooltip[16];
    	contextMenu = new ContextMenu();
    	changeColor = new MenuItem();
    	colorPicker = new ColorPicker();
    }
    
    /**
     * Checks to see if an instance of ColorPaletteController exists. 
     * If so, return it. If not, instantiate an instance of ColorPaletteController and return it.
     * 
     * @return the instance of ColorPaletteController
     */
    public static ColorPaletteController getInstance() {
    	if(cpc == null)
    		cpc = new ColorPaletteController();
        return cpc;
    }

    /**
     * Repaints the ColorPalette.
     */
    public void rePaint(){
        Color[] colors = ColorPaletteModel.getInstance().getColors();
        for(int i = 0; i < customColors.getChildren().size(); i++) {
        	((Rectangle)customColors.getChildren().get(i)).setFill(colors[i + 16]);
        	Color c = (Color) ((Rectangle)customColors.getChildren().get(i)).getFill();
       		String hex = String.format( "R:%d G:%d B:%d",
       		            (int)( c.getRed() * 255 ),
       		            (int)( c.getGreen() * 255 ),
       		            (int)( c.getBlue() * 255 ));
       		customTooltips[i] = new Tooltip(hex);
       		Tooltip.install(customColors.getChildren().get(i), customTooltips[i]);
       	}
   } 

    /**
     * Initializes the ColorPalette according to the given FXML file. Also sets up the Event Handlers for the ColorPalette.
     * 
     * @param arg0 - the filename of the controlling .fxml file
     * @param arg1 - the resources used to localize the root object
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	rePaint();
    	selectedRectangle = (Rectangle)defaultColors.getChildren().get(0);
    	ColorPaletteModel.getInstance().setSelectedIndex(0);
    	((Rectangle)defaultColors.getChildren().get(0)).setStroke(Color.GREY);
    	((Rectangle)defaultColors.getChildren().get(0)).setStrokeWidth(4);
    	ColorPaletteModel.getInstance().setDefaultColors();
    	contextMenu.getItems().add(changeColor);
    	changeColor.setGraphic(colorPicker);
    	
    	for(int i = 0; i < defaultColors.getChildren().size(); i++) {
    		Tooltip.install(defaultColors.getChildren().get(i), defaultTooltips[i]);
        	defaultColors.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
        		@Override
        		public void handle(MouseEvent e){
            		if(e.getButton() != MouseButton.SECONDARY){
            			selectedRectangle.setStrokeWidth(1);
            			selectedRectangle = (Rectangle)e.getSource();
            			if(e.getSource() == defaultColors.getChildren().get(0)){
            				((Rectangle)e.getSource()).setStroke(Color.GREY);
            				((Rectangle)e.getSource()).setStrokeWidth(4);
                    		ColorPaletteModel.getInstance().setSelectedIndex(defaultColors.getChildren().indexOf(e.getSource()));
            			}else{
                			((Rectangle)e.getSource()).setStroke(Color.BLACK);
            				((Rectangle)e.getSource()).setStrokeWidth(4);
            				switch((defaultColors.getChildren().indexOf(e.getSource()))){
            					case 1:
            						ColorPaletteModel.getInstance().setSelectedIndex(8);
            						break;
            					case 2:
            						ColorPaletteModel.getInstance().setSelectedIndex(9);
            						break;
            					case 3:
            						ColorPaletteModel.getInstance().setSelectedIndex(1);
            						break;
            					case 4:
            						ColorPaletteModel.getInstance().setSelectedIndex(13);
            						break;
            					case 5:
            						ColorPaletteModel.getInstance().setSelectedIndex(5);
            						break;
            					case 6:
            						ColorPaletteModel.getInstance().setSelectedIndex(12);
            						break;
            					case 7:
            						ColorPaletteModel.getInstance().setSelectedIndex(4);
            						break;
            					case 8:
            						ColorPaletteModel.getInstance().setSelectedIndex(14);
            						break;
            					case 9:
            						ColorPaletteModel.getInstance().setSelectedIndex(6);
            						break;
            					case 10:
            						ColorPaletteModel.getInstance().setSelectedIndex(10);
            						break;
            					case 11:
            						ColorPaletteModel.getInstance().setSelectedIndex(15);
            						break;
            					case 12:
            						ColorPaletteModel.getInstance().setSelectedIndex(2);
            						break;
            					case 13:
            						ColorPaletteModel.getInstance().setSelectedIndex(3);
            						break;
            					case 14:
            						ColorPaletteModel.getInstance().setSelectedIndex(11);
            						break;
            					case 15:
            						ColorPaletteModel.getInstance().setSelectedIndex(7);
            						break;
            					default:
            						throw new IllegalArgumentException("Invalid color selection");
            				}
            			}
            		}
            	}
            });
        	
        	defaultColors.getChildren().get(i).setOnMouseEntered(new EventHandler<MouseEvent>(){
        		@Override
        		public void handle(MouseEvent e){
        			if(e.getSource() != selectedRectangle){
        				((Rectangle)e.getSource()).setStroke(Color.GREY);
            			((Rectangle)e.getSource()).setStrokeWidth(3);
        			}
        		}
        	});
        	
        	defaultColors.getChildren().get(i).setOnMouseExited(new EventHandler<MouseEvent>(){
        		@Override
        		public void handle(MouseEvent e){
        			if(e.getSource() != selectedRectangle){
        				((Rectangle)e.getSource()).setStroke(Color.BLACK);
            			((Rectangle)e.getSource()).setStrokeWidth(1);
        			}
        		}
        	});
        }
        
        for(int i = 0; i < customColors.getChildren().size(); i++) {
        	customColors.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
            	@Override
            	public void handle(MouseEvent e){
            		selectedRectangle.setStrokeWidth(1);
        			selectedRectangle = (Rectangle)e.getSource();
            		((Rectangle)e.getSource()).setStroke(Color.BLACK);
            		((Rectangle)e.getSource()).setStrokeWidth(4);
            		ColorPaletteModel.getInstance().setSelectedIndex(16+customColors.getChildren().indexOf(e.getSource()));
            		if(e.getButton() == MouseButton.SECONDARY){
            			contextMenu.show((Rectangle)e.getSource(),Side.TOP,0,0);
            		}
            	}
            });
        	
        	customColors.getChildren().get(i).setOnMouseEntered(new EventHandler<MouseEvent>(){
        		@Override
        		public void handle(MouseEvent e){
        			if(e.getSource() != selectedRectangle){
        				((Rectangle)e.getSource()).setStroke(Color.GREY);
            			((Rectangle)e.getSource()).setStrokeWidth(3);
        			}
        		}
        	});
        	
        	customColors.getChildren().get(i).setOnMouseExited(new EventHandler<MouseEvent>(){
        		@Override
        		public void handle(MouseEvent e){
        			if(e.getSource() != selectedRectangle){
        				((Rectangle)e.getSource()).setStroke(Color.BLACK);
            			((Rectangle)e.getSource()).setStrokeWidth(1);
        			}
        		}
        	});
        }
        
        colorPicker.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		ColorPaletteModel.getInstance().setColor(((ColorPicker)e.getSource()).getValue(), 16 + customColors.getChildren().indexOf(selectedRectangle));
        	}
        });
        
        cpc = this;
    }
}