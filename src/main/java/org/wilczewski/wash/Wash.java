package org.wilczewski.wash;

public class Wash {
    boolean available;
    String type;

    public Wash(String type) {
        available = true;
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
