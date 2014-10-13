package choreography.view.colorPalette;

import java.net.URL;
import java.util.ResourceBundle;

<<<<<<< HEAD
=======
import choreography.model.color.ColorPaletteModel;
>>>>>>> origin/ColorPalette
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
<<<<<<< HEAD
=======
import javafx.scene.input.MouseButton;
>>>>>>> origin/ColorPalette
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
<<<<<<< HEAD
import choreography.model.color.ColorPaletteModel;
import choreography.view.timeline.TimelineController;
=======
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.geometry.Side;
>>>>>>> origin/ColorPalette

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
<<<<<<< HEAD
        rectangles = new Rectangle[32];
        colorRectanglePane = new HBox(0.5);
        
        // Sets the first color to red and creates the event handler
        for(int index = 0; index < 32 ; index++) {
            int index2 = index;   
            rectangles[index] = new Rectangle(25, 25, ColorPaletteModel.getInstance().getColors()[index]);
            rectangles[index].setOnMouseClicked(new EventHandler<MouseEvent> (){
            	
            	public void handle(MouseEvent e) {
            		ColorPaletteModel.getInstance().setSelectedIndex(index2);
                    setSelectedColor(ColorPaletteModel.getInstance().getColor(index2 + 1));
=======
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
                    		ColorPaletteModel.getInstance().setSelectedIndex(defaultColors.getChildren().indexOf(e.getSource()));
            			}
            		}
>>>>>>> origin/ColorPalette
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
        
<<<<<<< HEAD
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
                                System.out.println("Over Here");
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
=======
        for(int i = 0; i < customColors.getChildren().size(); i++) {
        	customColors.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
            	@Override
            	public void handle(MouseEvent e){
            		selectedRectangle.setStrokeWidth(1);
        			selectedRectangle = (Rectangle)e.getSource();
            		((Rectangle)e.getSource()).setStroke(Color.BLACK);
            		((Rectangle)e.getSource()).setStrokeWidth(4);
            		ColorPaletteModel.getInstance().setSelectedIndex(customColors.getChildren().indexOf(e.getSource()));
            		if(e.getButton() == MouseButton.SECONDARY){
            			contextMenu.show((Rectangle)e.getSource(),Side.TOP,0,0);
            		}
            	}
>>>>>>> origin/ColorPalette
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
