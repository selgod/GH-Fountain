package SimpleJavaFXPlayer;
 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
 
public class TimeLineTestConcept extends Application {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Hello World");
        Group root = new Group();
        final Scene scene = new Scene(root, 600, 600);
        
        final ColorPicker colorPicker = new ColorPicker();
        
        colorPicker.setLayoutX(300);
        colorPicker.setLayoutY(300);

        
        colorPicker.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Color c = colorPicker.getValue();
                System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
            }
        });
    	
    	GridPane gridpaneRec = new GridPane();
    	GridPane gridpaneMods = new GridPane();
    	ScrollPane scrollpane = new ScrollPane();
    	gridpaneRec.setGridLinesVisible(true);

    	final Rectangle[][] recArray = new Rectangle[101][70];
    	 for(int i=0; i<101; i++){
      		  gridpaneRec.getColumnConstraints().add(new ColumnConstraints(30));
      		  if (i < 70){ //because the array is not square this needs to be here
      			 gridpaneRec.getRowConstraints().add(new RowConstraints(30));
      		  }
      		  
       	  for(int j=0; j<70; j++){
       		  if (i == 0){
       			 recArray[i][j] = new Rectangle(50,25, Color.RED);
       			 continue;
       		  }
       		  recArray[i][j] = new Rectangle(25,25, Color.LIGHTGREY);
       		  gridpaneRec.add(recArray[i][j], i, j);
       		  //these are needed to talk to the mouse pressed events
       		  final int testI = i;
       		  final int testJ = j;
       		  
       		recArray[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
       			  public void handle(MouseEvent me) {
       	        System.out.println("Col " + (testI) + " Row " + (testJ+1));
       	     recArray[testI][testJ].setFill(colorPicker.getValue());
       	    }
       	});
       		
       		recArray[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
     			  public void handle(MouseEvent me) {
     				  //starts the drag event
     				 recArray[testI][testJ].startFullDrag();
     	    }
     			  
     	});
       		//continues and ends the drag event
       		recArray[i][j].setOnMouseDragOver(new EventHandler<MouseEvent>() {
   			  public void handle(MouseEvent me) {
   				  recArray[testI][testJ].setFill(colorPicker.getValue());
   	    }
       	});
       	  }
         }
      
        scrollpane.setPrefSize(600, 250);
        scrollpane.setContent(gridpaneRec);
    	root.getChildren().addAll(scrollpane, colorPicker);
        primaryStage.setScene(scene);
        primaryStage.show();
}}