package org.wilczewski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.wilczewski.gui.SimulationViewController;
import org.wilczewski.gui.StartViewController;
import org.wilczewski.threads.CarThread;
import org.wilczewski.threads.ControllerThread;
import org.wilczewski.wash.CarWash;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {
    private CarWash carWash;
    private ControllerThread controllerThread;
    private ArrayList<CarThread> allCarsList;
    private int numberOfCars;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StartViewController startViewController = getStartController();
        numberOfCars = startViewController.getNumberOfCars();
        int q1size = startViewController.getQ1Size();
        int q2size = startViewController.getQ2Size();
        int numberOfWashStands = startViewController.getNumberOfWashStands();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appview/simulationView.fxml"));
        Parent root = loader.load();

        SimulationViewController controller = loader.getController();
        controller.setNumberOfWashStands(numberOfWashStands);
        controller.initializeController();

        initializeCarWash(controller, numberOfWashStands, q1size, q2size);

        primaryStage.setTitle("Car Wash Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public StartViewController getStartController() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appview/startView.fxml"));
        Parent root = loader.load();

        StartViewController controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle("Simulator Configuration");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        return controller;
    }

    private void initializeCarWash(SimulationViewController controller, int numberOfWashStands, int q1Size, int q2Size) {
        carWash = new CarWash(numberOfWashStands, q1Size, q2Size);
        controllerThread = new ControllerThread(carWash, controller);
        controllerThread.start();
        allCarsList = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            CarThread carThread = new CarThread(i, carWash, controller, controllerThread);
            allCarsList.add(carThread);
            carThread.start();
        }
    }
}

