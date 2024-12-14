package org.wilczewski.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class QueueTableItem {
    private final IntegerProperty carId;

    public QueueTableItem(int carId) {
        this.carId = new SimpleIntegerProperty(carId);
    }

    public int getCarId() {
        return carId.get();
    }

    public IntegerProperty carIdProperty() {
        return carId;
    }
}
