package org.wilczewski;

import org.wilczewski.threads.CarThread;
import org.wilczewski.threads.ControllerThread;
import org.wilczewski.wash.CarWash;

import java.util.ArrayList;

public class App {
    CarWash carWash;
    ControllerThread controllerThread;
    ArrayList<CarThread> allCarsList;
    int numberOfCars = 10;

    public App() {
        carWash = new CarWash();
        controllerThread = new ControllerThread(carWash);
        controllerThread.start();
        allCarsList = new ArrayList<>();
        for (int i = 0; i < numberOfCars; i++) {
            allCarsList.add(new CarThread(i, carWash));
            allCarsList.getLast().setPriority(10);
            allCarsList.getLast().start();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
    }
}
