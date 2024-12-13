package org.wilczewski.wash;

import java.util.ArrayList;

public class CarWash {
    ArrayList<WashStand> washStandsList;

    public CarWash() {
        washStandsList = new ArrayList<>();
    }

    public ArrayList<WashStand> getWashStandsList() {
        return washStandsList;
    }

    public void setWashStandsList(ArrayList<WashStand> washStandsList) {
        this.washStandsList = washStandsList;
    }
}
