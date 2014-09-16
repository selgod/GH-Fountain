package SimpleJavaFXPlayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Steven Merdzinski
 */
public class SimpleJavaFXPlayer extends Application {
    private static Stage principalStage;
    private static SimpleJavaFXPlayer instance;

    public SimpleJavaFXPlayer() {
        instance=this;
    }
    
    public void close(){
        principalStage.close();
    }
    public void moveTo(double x, double y){
        principalStage.setX(x);
        principalStage.setY(y);
    }
    
    public static SimpleJavaFXPlayer getInstance(){
        return instance;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PlayerView.fxml"));
        
        Scene scene = new Scene(root);
        principalStage = stage;
        principalStage.setScene(scene);
        principalStage.setTitle("Music Player");
        //principalStage.initStyle(StageStyle.TRANSPARENT);
        principalStage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}