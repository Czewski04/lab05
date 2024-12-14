package org.wilczewski.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.wilczewski.observer.Observer;

import java.util.Objects;

public class SimulationViewController implements Observer {
    @FXML
    public TableView<StandTableItem> standTable;
    @FXML
    public TableColumn<StandTableItem, Integer> standColumn;
    @FXML
    public TableColumn<StandTableItem, String> carIdColumn;
    @FXML
    public TableColumn<StandTableItem, String> water1Column;
    @FXML
    public TableColumn<StandTableItem, String> soap1Column;
    @FXML
    public TableColumn<StandTableItem, String> water2Column;
    @FXML
    public TableColumn<StandTableItem, String> soap2Column;
    @FXML
    public TableView<QueueTableItem> q1Table;
    @FXML
    public TableColumn<QueueTableItem, Integer> q1Column;
    @FXML
    public TableView<QueueTableItem> q2Table;
    @FXML
    public TableColumn<QueueTableItem, Integer> q2Column;

    private int numberOfWashStands;

    public void initializeController() {

        ObservableList<StandTableItem> stands = FXCollections.observableArrayList();
        ObservableList<QueueTableItem> q1List = FXCollections.observableArrayList();
        ObservableList<QueueTableItem> q2List = FXCollections.observableArrayList();

        for (int i = 0; i < numberOfWashStands; i++) {
            if(i==0)
                stands.add(new StandTableItem(i + 1, "", "-", "-", "Available", "Available"));
            else if(i==numberOfWashStands-1)
                stands.add(new StandTableItem(i + 1, "", "Available", "Available", "-", "-"));
            else
                stands.add(new StandTableItem(i + 1, "", "Available", "Available", "Available", "Available"));
        }

        standTable.setItems(stands);
        q1Table.setItems(q1List);
        q2Table.setItems(q2List);

        standColumn.setCellValueFactory(cellData -> cellData.getValue().standIdProperty().asObject());
        carIdColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
        water1Column.setCellValueFactory(cellData -> cellData.getValue().water1Property());
        soap1Column.setCellValueFactory(cellData -> cellData.getValue().soap1Property());
        water2Column.setCellValueFactory(cellData -> cellData.getValue().water2Property());
        soap2Column.setCellValueFactory(cellData -> cellData.getValue().soap2Property());
        q1Column.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());
        q2Column.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());

        standTable.getColumns().clear();
        standTable.getColumns().addAll(standColumn, carIdColumn, water1Column, soap1Column, water2Column, soap2Column);
    }

    public void setNumberOfWashStands(int numberOfWashStands) {
        this.numberOfWashStands = numberOfWashStands;
    }

    @Override
    public void joinedToQ1(int carId) {
        QueueTableItem queueTableItem = new QueueTableItem(carId);
        q1Table.getItems().add(queueTableItem);
    }

    @Override
    public void joinedToQ2(int carId) {
        QueueTableItem queueTableItem = new QueueTableItem(carId);
        q2Table.getItems().add(queueTableItem);
    }


    @Override
    public void enteredToStand(int carId, int stand, int fromQueue) {
        StandTableItem standTableItem = standTable.getItems().get(stand);
        standTableItem.setCarId(String.valueOf(carId));

        if (fromQueue == 1) {
            q1Table.getItems().removeIf(item -> item.getCarId() == carId);
        } else if (fromQueue == 2) {
            q2Table.getItems().removeIf(item -> item.getCarId() == carId);
        }
    }


    @Override
    public void leavedStand(int carId, int stand) {
        StandTableItem standTableItem = standTable.getItems().get(stand);
        if (Objects.equals(standTableItem.getCarId(), String.valueOf(carId))) {
            standTableItem.setCarId("");
        }
    }


    @Override
    public void washedStageStart(int carId, int stand, String name) {
        StandTableItem standTableItem = standTable.getItems().get(stand);

        if ("water1".equals(name)) {
            standTableItem.setWater1("In Use");
        }
        else if ("soap1".equals(name)) {
            standTableItem.setSoap1("In Use");
        }
        else if ("water2".equals(name)) {
            standTableItem.setWater2("In Use");
        }
        else if ("soap2".equals(name)) {
            standTableItem.setSoap2("In Use");
        }

        if(name.equals("water1") || name.equals("soap1")) {
            StandTableItem standNeighborItem = standTable.getItems().get(stand-1);
            if(name.equals("water1"))
                standNeighborItem.setWater2("In Use by neighbor");
            else
                standNeighborItem.setSoap2("In Use by neighbor");
        }
        else if(name.equals("water2") || name.equals("soap2")) {
            StandTableItem standNeighborItem = standTable.getItems().get(stand+1);
            if(name.equals("water2"))
                standNeighborItem.setWater1("In Use by neighbor");
            else
                standNeighborItem.setSoap1("In Use by neighbor");
        }
    }

    @Override
    public void washedStageEnd(int carId, int stand, String name) {
        StandTableItem standTableItem = standTable.getItems().get(stand);

        if ("water1".equals(name)) {
            standTableItem.setWater1("Available");
        }
        else if ("soap1".equals(name)) {
            standTableItem.setSoap1("Available");
        }
        else if ("water2".equals(name)) {
            standTableItem.setWater2("Available");
        }
        else if ("soap2".equals(name)) {
            standTableItem.setSoap2("Available");
        }

        if(name.equals("water1") || name.equals("soap1")) {
            StandTableItem standNeighborItem = standTable.getItems().get(stand-1);
            if(name.equals("water1"))
                standNeighborItem.setWater2("Available");
            else
                standNeighborItem.setSoap2("Available");
        }
        else if(name.equals("water2") || name.equals("soap2")) {
            StandTableItem standNeighborItem = standTable.getItems().get(stand+1);
            if(name.equals("water2"))
                standNeighborItem.setWater1("Available");
            else
                standNeighborItem.setSoap1("Available");
        }
    }

}
