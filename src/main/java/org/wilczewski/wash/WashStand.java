package org.wilczewski.wash;

import java.util.ArrayList;

public class WashStand {
    boolean available;
    ArrayList<Wash> washesList;

    public  WashStand() {
        available = true;
        washesList = new ArrayList<>();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public ArrayList<Wash> getWashesList() {
        return washesList;
    }

    public void setWashesList(ArrayList<Wash> washesList) {
        this.washesList = washesList;
    }
}
