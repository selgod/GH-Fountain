/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.view.lagtime;

import choreography.io.LagTimeLibrary;
import choreography.model.lagtime.LagTime;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author elementsking
 */
public class LagTimeGUIController implements Initializable {
    @FXML
    private TableView<LagTime> lagTimeTable;
    @FXML
    private TableColumn<LagTime, String> delayName;
    @FXML
    private TableColumn<LagTime, Double> delayTime;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField addDelayName;
    @FXML
    private TextField addDelayTime;
    private Stage dialogStage;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<LagTime> ltt = LagTimeLibrary.getInstance().getLagTimes();
        ObservableList<LagTime> lagTimeList = FXCollections.observableArrayList(ltt);
        lagTimeTable.setEditable(true); delayName.setEditable(true);
        delayTime.setEditable(true);
        
        Callback<TableColumn<LagTime,String>, TableCell<LagTime,String>> cellFactory =
             new Callback<TableColumn<LagTime,String>, TableCell<LagTime,String>>() {
                 @Override
                 public TableCell call(TableColumn p) {
                    return new EditingCell();
                 }
             };
        
        delayName.setCellValueFactory(
            new PropertyValueFactory<LagTime,String>("delayName"));
        delayName.setCellFactory(cellFactory);
        delayName.setOnEditCommit(
             new EventHandler<CellEditEvent<LagTime, String>>() {
                @Override
                public void handle(CellEditEvent<LagTime, String> t) {
                    ((LagTime) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setDelayName(t.getNewValue());
                }
            }
)       ;
        
        
        delayTime.setCellValueFactory(
            new PropertyValueFactory<LagTime,Double>("delayTime"));
        ;
        
        Callback<TableColumn<LagTime,Double>, TableCell<LagTime,Double>> doubleCellFactory =
             new Callback<TableColumn<LagTime,Double>, TableCell<LagTime,Double>>() {
                 @Override
                 public TableCell call(TableColumn p) {
                    return new DoubleEditingCell();
                 }
             };
        
        delayTime.setCellFactory(doubleCellFactory);
        delayTime.setOnEditCommit(
             new EventHandler<CellEditEvent<LagTime, Double>>() {
                @Override
                public void handle(CellEditEvent<LagTime, Double> t) {
                    ((LagTime) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDelayTime(t.getNewValue());
                }
            }
)       ;
        
        lagTimeTable.setItems(lagTimeList);
        
        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                try {
                    saveLagTimes(t);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LagTimeGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                cancel(t);
            }
        });
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                lagTimeTable.getItems().add(new LagTime(
                    addDelayName.getText(),
                    Double.parseDouble(addDelayTime.getText())
                ));
                addDelayName.clear();
                addDelayTime.clear();
            }
        });
        
    }    

    @FXML
    private void cancel(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    private void saveLagTimes(ActionEvent event) throws FileNotFoundException{
        ArrayList<LagTime> lagTimes = new ArrayList<>();
        ObservableList<LagTime> tableTimes = lagTimeTable.getItems();
        for(LagTime lt: tableTimes) {
            lagTimes.add(lt);
        }
        LagTimeLibrary.getInstance().saveLagTimes(lagTimes);
        dialogStage.close();
    }
    
    /**
     *
     * @param dialog
     */
    public void setDialogStage(Stage dialog) {
        this.dialogStage = dialog;
    }

    public void setDelays(ArrayList<LagTime> lagTimes) {
        //convert LagTime into table rows and columns...
    }
    
}
