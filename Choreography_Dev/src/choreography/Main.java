

package choreography;
	
import choreography.model.fountain.Fountain;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import choreography.model.*;
import javafx.scene.image.Image;

/**
 * The Main class calls of the needed methods and classes 
 * that are needed for the program to run.
 * @author Frank Madrid
 */

public class Main extends Application {
	
	private static Fountain fountain;
	private VBox root;
	private static Stage primaryStage;

    /**
     * The main method that is used for the program. 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns fountain which is what is used for the program.
     * @return fountain
     */
    public static Fountain getFountain() {
		return fountain;
	}

    /**
     * The start method uses the primary stage created by the main
     * method to make the program. 
     * @param primaryStage
     */
    @Override
	public void start(Stage primaryStage) {
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/ghmf_cs_logo.png")));
//            primaryStage.setIconified(true);
            this.setPrimaryStage(primaryStage);
            
            Main.primaryStage = primaryStage;
            primaryStage.setTitle("GHMF Choreography Studio");
            fountain = Fountain.getInstance();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("view/Choreography.fxml"));
            root = (VBox)fxml.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Returns the primary stage for the program to use throughout the program. 
     * @return the primaryStage
     */
	public static Stage getPrimaryStage() {
        return primaryStage;
    }

	/**
	 * Sets the primary stage for the program to use. Able to be called
	 * from other classes. 
     * @param primaryStage the primaryStage to set
     */
    public void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }
}
