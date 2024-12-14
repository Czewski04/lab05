package org.wilczewski.wash;

public class Wash {
    private boolean available;
    private String type;

    public Wash(String type) {
        available = true;
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable() {
        this.available = true;
    }

    public void setUnavailable() {
        this.available = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
