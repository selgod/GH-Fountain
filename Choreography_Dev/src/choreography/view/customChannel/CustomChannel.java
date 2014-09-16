package choreography.view.customChannel;

import choreography.io.FCWLib;
import choreography.view.timeline.TimelineController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author cdea
 */
public class CustomChannel {

    public static void start(Stage primaryStage) {
        primaryStage.setTitle("Select Channels to Add");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 450, Color.WHITE);

        // create a grid pane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        // selectable module label
        Label candidatesLbl = new Label("Selectable Channels");
        GridPane.setHalignment(candidatesLbl, HPos.CENTER);
        gridpane.add(candidatesLbl, 0, 0);

        Label heroesLbl = new Label("Selected Channels");
        gridpane.add(heroesLbl, 2, 0);
        GridPane.setHalignment(heroesLbl, HPos.CENTER);

        // modules
        final ObservableList<String> moduleList = FXCollections.observableArrayList(FCWLib.getInstance().getLightTable());
        final ListView<String> moduleListView = new ListView<>(moduleList);
        moduleListView.setPrefWidth(150);
        moduleListView.setPrefHeight(350);

        gridpane.add(moduleListView, 0, 1);

        // selected
        final ObservableList<String> selectedList = FXCollections.observableArrayList(
        		TimelineController.getInstance().getLabelNames());
        final ListView<String> selectedListView = new ListView<>(selectedList);
        selectedListView.setPrefWidth(150);
        selectedListView.setPrefHeight(350);

        gridpane.add(selectedListView, 2, 1);


        // selected modules
        Button sendRightButton = new Button(">");
        sendRightButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                String potential = moduleListView.getSelectionModel().getSelectedItem();
                if (potential != null) {
                    moduleListView.getSelectionModel().clearSelection();
                    moduleList.remove(potential);
                    selectedList.add(potential);
                }
            }
        });

        // deselect modules
        Button sendLeftButton = new Button("<");
        sendLeftButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                String notHero = selectedListView.getSelectionModel().getSelectedItem();
                if (notHero != null) {
                    selectedListView.getSelectionModel().clearSelection();
                    selectedList.remove(notHero);
                    moduleList.add(notHero);
                }
            }
        });
        
        Button finishButton = new Button("Finish");
        gridpane.add(finishButton, 2, 3);
        
        // Finish button
        finishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String[] selectedArray;
                selectedArray = selectedListView.getItems().toArray(new String[1]);
                for(int i = 0; i < selectedArray.length;i++ ){
                    TimelineController.getInstance().setLabelGridPane(selectedArray);
                }
                try {
                    //Node  source = (Node)  ActionEvent.getSource(); 
                    //Stage stage  = (Stage) source.getScene().getWindow();
                    //stage.close();
                } catch (Exception e) {
                }
            }
        });
        
        

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(sendRightButton,sendLeftButton);

        gridpane.add(vbox, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2,HPos.CENTER, VPos.CENTER);

        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}