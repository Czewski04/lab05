package org.wilczewski.wash;

import org.wilczewski.threads.CarThread;

import java.util.ArrayList;

public class WashStand {
    private boolean available;
    private ArrayList<Wash> washesList;
    private CarThread carThread;
    private int washStandId;

    public  WashStand(int washId) {
        available = true;
        washesList = new ArrayList<>();
        this.washStandId = washId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable() {
        this.available = true;
    }

    public void setUnavailable() {this.available = false;}

    public ArrayList<Wash> getWashesList() {
        return washesList;
    }

    public void setCarThread(CarThread carThread) {
        this.carThread = carThread;
    }

    public int getWashStandId() {
        return washStandId;
    }
}
