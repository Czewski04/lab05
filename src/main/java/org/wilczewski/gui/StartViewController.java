package org.wilczewski.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartViewController {
    @FXML
    public TextField numberOfCarsTxtField;
    @FXML
    public TextField numberOfStandsTxtField;
    @FXML
    public TextField queue1SizeTxtField;
    @FXML
    public TextField queue2SizeTxtField;
    private int q1Size;
    private int q2Size;
    private int numberOfWashStands;
    private int numberOfCars;

    private Stage stage;

    public void initialize() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void readParameters() {
        try {
            String numberOfCarsStr = numberOfCarsTxtField.getText();
            if (numberOfCarsStr.isEmpty()) {
                throw new IllegalArgumentException("Number of cars cannot be empty.");
            }
            numberOfCars = Integer.parseInt(numberOfCarsStr);

            String numberOfStandsStr = numberOfStandsTxtField.getText();
            if (numberOfStandsStr.isEmpty()) {
                throw new IllegalArgumentException("Number of stands cannot be empty.");
            }
            numberOfWashStands = Integer.parseInt(numberOfStandsStr);

            String queue1SizeStr = queue1SizeTxtField.getText();
            if (queue1SizeStr.isEmpty()) {
                throw new IllegalArgumentException("Queue 1 size cannot be empty.");
            }
            q1Size = Integer.parseInt(queue1SizeStr);

            String queue2SizeStr = queue2SizeTxtField.getText();
            if (queue2SizeStr.isEmpty()) {
                throw new IllegalArgumentException("Queue 2 size cannot be empty.");
            }
            q2Size = Integer.parseInt(queue2SizeStr);

            stage.close();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }


    public Stage getStage() {
        return stage;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public int getNumberOfWashStands() {
        return numberOfWashStands;
    }

    public int getQ2Size() {
        return q2Size;
    }

    public int getQ1Size() {
        return q1Size;
    }
}
