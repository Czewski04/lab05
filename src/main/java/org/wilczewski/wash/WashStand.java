package org.wilczewski.wash;

import org.wilczewski.threads.CarThread;

import java.util.ArrayList;

public class WashStand {
    boolean available;
    ArrayList<Wash> washesList;
    CarThread carThread;

    public  WashStand() {
        available = true;
        washesList = new ArrayList<>();
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

    public void setWashesList(ArrayList<Wash> washesList) {
        this.washesList = washesList;
    }

    public CarThread getCarThread() {
        return carThread;
    }

    public void setCarThread(CarThread carThread) {
        this.carThread = carThread;
    }
}
