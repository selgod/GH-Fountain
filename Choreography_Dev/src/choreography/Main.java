package choreography;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import choreography.io.CtlLib;
import choreography.io.GhmfLibrary;
import choreography.io.MapLib;
import choreography.model.fountain.Fountain;
import choreography.view.ChoreographyController;
import choreography.view.music.MusicPaneController;
import choreography.view.specialOperations.SpecialoperationsController;
import choreography.view.timeline.TimelineController;

/**
 * The Main class calls of the needed methods and classes that are needed for
 * the program to run.
 * 
 * @author Frank Madrid
 */

public class Main extends Application {

	private static Fountain fountain;
	private VBox root;
	private static Stage primaryStage;

	/**
	 * The main method that is used for the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Returns fountain which is what is used for the program.
	 * 
	 * @return fountain
	 */
	public static Fountain getFountain() {
		return fountain;
	}

	/**
	 * The start method uses the primary stage created by the main method to
	 * make the program.
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/ghmf_cs_logo.png")));
			this.setPrimaryStage(primaryStage);
			Main.primaryStage = primaryStage;
			primaryStage.setTitle("GHMF Choreography Studio");
			fountain = Fountain.getInstance();
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("view/Choreography.fxml"));
			root = (VBox) fxml.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			
			scene.setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					if (db.hasFiles()) {
						event.acceptTransferModes(TransferMode.COPY);
					} else {
						event.consume();
					}
				}
			});

			// Drag and Drop
			scene.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					String filePath = null;
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;

						for (File file : db.getFiles()) {
							filePath = file.getAbsolutePath();

							// Opens Wave Folder
							if (filePath.substring(filePath.length() - 4).equalsIgnoreCase(".wav")) {
								MusicPaneController.getInstance().openMusicFile(file);
								TimelineController.getInstance().initializeTimelines();
								ChoreographyController.getInstance().getOpenCTLMenuItem().setDisable(false);
								MusicPaneController.getInstance().getPlayButton().setDisable(false);
								MusicPaneController.getInstance().getResetButton().setDisable(false);
							}

							// Opens ZipFiles
							if (filePath.substring(filePath.length() - 4).equalsIgnoreCase("ghmf")) {
								ZipFile ghmfFile = null;
								try {
									ghmfFile = new ZipFile(file);
								} catch (ZipException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								try {
									GhmfLibrary.readGhmfZip(ghmfFile);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							
							// Open Control File
							if (filePath.substring(filePath.length() - 4).equalsIgnoreCase(".ctl")) {
								if (MusicPaneController.getInstance().getMusicName() != "" || MusicPaneController.getInstance().getMusicName() != null) {
									try {
										CtlLib.getInstance().openCtl(file);
										SpecialoperationsController.getInstance().initializeSweepSpeedSelectors();
									} catch (IOException e) {
										e.printStackTrace();
									}
								} 
							}

							// Open Color Map
							if (filePath.substring(filePath.length() - 4).equalsIgnoreCase(".map")) {
								try {
									MapLib.openMap(file);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}

							if (filePath.substring(filePath.length() - 4).equalsIgnoreCase("mark")) {
								System.out.println("Mark File");
							}

						}
					}
					event.setDropCompleted(success);
					event.consume();
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the primary stage for the program to use throughout the program.
	 * 
	 * @return the primaryStage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Sets the primary stage for the program to use. Able to be called from
	 * other classes.
	 * 
	 * @param primaryStage
	 *            the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage) {
		Main.primaryStage = primaryStage;
	}
}
