package org.wilczewski.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StandTableItem {
    private final IntegerProperty standId;
    private final StringProperty carId;
    private final StringProperty water1;
    private final StringProperty soap1;
    private final StringProperty water2;
    private final StringProperty soap2;

    public StandTableItem(int standId, String carId, String water1, String soap1, String water2, String soap2) {
        this.standId = new SimpleIntegerProperty(standId);
        this.carId = new SimpleStringProperty(carId);
        this.water1 = new SimpleStringProperty(water1);
        this.soap1 = new SimpleStringProperty(soap1);
        this.water2 = new SimpleStringProperty(water2);
        this.soap2 = new SimpleStringProperty(soap2);
    }

    public IntegerProperty standIdProperty() {
        return standId;
    }

    public String getCarId() {
        return carId.get();
    }

    public void setCarId(String carId) {
        this.carId.set(carId);
    }

    public StringProperty carIdProperty() {
        return carId;
    }

    public void setWater1(String water1) {
        this.water1.set(water1);
    }

    public StringProperty water1Property() {
        return water1;
    }

    public void setSoap1(String soap1) {
        this.soap1.set(soap1);
    }

    public StringProperty soap1Property() {
        return soap1;
    }

    public void setWater2(String water2) {
        this.water2.set(water2);
    }

    public StringProperty water2Property() {
        return water2;
    }

    public void setSoap2(String soap2) {
        this.soap2.set(soap2);
    }

    public StringProperty soap2Property() {
        return soap2;
    }
}
