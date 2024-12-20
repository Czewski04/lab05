package org.wilczewski.threads;

import javafx.application.Platform;
import org.wilczewski.gui.SimulationViewController;
import org.wilczewski.wash.CarWash;
import org.wilczewski.wash.WashStand;


public class ControllerThread extends Thread {
    private final CarWash carWash;
    private boolean lastUsedQ1;
    private final SimulationViewController simulationViewController;

    public ControllerThread(CarWash carWash, SimulationViewController controller) {
        this.carWash = carWash;
        this.lastUsedQ1 = false;
        this.simulationViewController = controller;
    }

    @Override
    public void run() {
        while (true) {
            try {
                redirectToWashStand();
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void redirectToWashStand() throws InterruptedException {
        CarThread car = null;
        synchronized (carWash.getWashStandsList()) {
            for(WashStand washStand : carWash.getWashStandsList()) {
                synchronized (washStand) {
                    if (washStand.isAvailable()) {
                        if (lastUsedQ1) {
                            car = redirectFromQ2(washStand);
                            lastUsedQ1 = false;
                            if (car == null) {
                                car = redirectFromQ1(washStand);
                                lastUsedQ1 = true;
                            }
                        } else {
                            car = redirectFromQ1(washStand);
                            lastUsedQ1 = true;
                            if (car == null) {
                                car = redirectFromQ2(washStand);
                                lastUsedQ1 = false;
                            }
                        }
                        if (car != null) {
                            synchronized (car) {
                                washStand.setCarThread(car);
                                washStand.setUnavailable();
                                car.setWashStand(washStand);
                                car.notify();
                                System.out.println("na stanowisku");
                            }
                        }
                    }
                }
            }
        }
    }

    private CarThread redirectFromQ1(WashStand washStand) throws InterruptedException {
        CarThread car = null;
        synchronized (carWash.getQueue1()) {
            if(!carWash.getQueue1().isEmpty()) {
                car = carWash.getQueue1().poll();
                carWash.getQueue1().notifyAll();
                CarThread finalCar = car;
                Platform.runLater(() -> simulationViewController.enteredToStand(finalCar.getCarId(), washStand.getWashStandId(), 1));
                System.out.println("też na stanowisku");
            }
        }
        return car;
    }

    private CarThread redirectFromQ2(WashStand washStand) throws InterruptedException {
        CarThread car = null;
        synchronized (carWash.getQueue2()) {
            if(!carWash.getQueue2().isEmpty()) {
                car = carWash.getQueue2().poll();
                carWash.getQueue2().notifyAll();
                CarThread finalCar = car;
                Platform.runLater(() -> simulationViewController.enteredToStand(finalCar.getCarId(), washStand.getWashStandId(), 2));
                System.out.println("też na stanowisku");
            }
        }
        return car;
    }

    public CarWash getCarWash() {
        return carWash;
    }
}
