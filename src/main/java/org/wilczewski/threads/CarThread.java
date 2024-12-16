package org.wilczewski.threads;

import javafx.application.Platform;
import org.wilczewski.gui.SimulationViewController;
import org.wilczewski.wash.CarWash;
import org.wilczewski.wash.WashStand;

import java.util.Random;

public class CarThread extends Thread {
    private final int carId;
    private final CarWash carWash;
    private boolean inQueue = false;
    private WashStand washStand;
    private final SimulationViewController simulationViewController;

    public CarThread(int carId, CarWash carWash, SimulationViewController simulationViewController) {
        this.carId = carId;
        this.carWash = carWash;
        this.simulationViewController = simulationViewController;
    }

    @Override
    public void run() {
        try {
            while(true) {
                while (!inQueue) {
                    addToQueue();
                }
                synchronized (this) {
                    this.wait();
                }
                wash();
                returnToStreet();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void returnToStreet() {
        synchronized (washStand) {
            washStand.setCarThread(null);
            washStand.setAvailable();
            Platform.runLater(() -> simulationViewController.leavedStand(this.getCarId(), washStand.getWashStandId()));
            washStand.notify();
        }
        this.inQueue = false;
    }

    private void wash() throws InterruptedException {
        synchronized (washStand) {
            washStage1And3();
            washStage2();
            washStage1And3();
        }
    }

    private void washStage1And3() throws InterruptedException {
        synchronized (washStand.getWashesList()) {
            for(int i=0; i<washStand.getWashesList().size(); i+=2) {
                synchronized (washStand.getWashesList().get(i)) {
                    if(washStand.getWashesList().get(i).isAvailable()){
                        washStand.getWashesList().get(i).setUnavailable();
                        if(i==2)
                            Platform.runLater(() -> simulationViewController.washedStageStart(this.carId, this.washStand.getWashStandId(), "water"+2));
                        else {
                            if (this.washStand.getWashStandId() == 0)
                                Platform.runLater(() -> simulationViewController.washedStageStart(this.carId, this.washStand.getWashStandId(), "water" + 2));
                            else
                                Platform.runLater(() -> simulationViewController.washedStageStart(this.carId, this.washStand.getWashStandId(), "water" + 1));
                        }
                        Random random = new Random();
                        int randomInt = random.nextInt(2000 - 500 + 1) + 500;
                        Thread.sleep(2000+randomInt);
                        if(i==2)
                            Platform.runLater(() -> simulationViewController.washedStageEnd(this.carId, this.washStand.getWashStandId(), "water"+2));
                        else {
                            if (this.washStand.getWashStandId() == 0)
                                Platform.runLater(() -> simulationViewController.washedStageEnd(this.carId, this.washStand.getWashStandId(), "water" + 2));
                            else
                                Platform.runLater(() -> simulationViewController.washedStageEnd(this.carId, this.washStand.getWashStandId(), "water" + 1));
                        }
                        washStand.getWashesList().get(i).setAvailable();
                        return;
                    }
                }
            }
        }
    }

    private void washStage2() throws InterruptedException {
        synchronized (washStand.getWashesList()) {
            for(int i=1; i<washStand.getWashesList().size(); i+=2) {
                synchronized (washStand.getWashesList().get(i)) {
                    if(washStand.getWashesList().get(i).isAvailable()){
                        washStand.getWashesList().get(i).setUnavailable();
                        if(i==3)
                            Platform.runLater(() -> simulationViewController.washedStageStart(this.carId, this.washStand.getWashStandId(), "soap"+2));
                        else {
                            if (this.washStand.getWashStandId() == 0)
                                Platform.runLater(() -> simulationViewController.washedStageStart(this.carId, this.washStand.getWashStandId(), "soap" + 2));
                            else
                                Platform.runLater(() -> simulationViewController.washedStageStart(this.carId, this.washStand.getWashStandId(), "soap" + 1));
                        }
                        Random random = new Random();
                        int randomInt = random.nextInt(2000 - 500 + 1) + 500;
                        Thread.sleep(2000+randomInt);
                        if(i==3)
                            Platform.runLater(() -> simulationViewController.washedStageEnd(this.carId, this.washStand.getWashStandId(), "soap"+2));
                        else {
                            if (this.washStand.getWashStandId() == 0)
                                Platform.runLater(() -> simulationViewController.washedStageEnd(this.carId, this.washStand.getWashStandId(), "soap" + 2));
                            else
                                Platform.runLater(() -> simulationViewController.washedStageEnd(this.carId, this.washStand.getWashStandId(), "soap" + 1));
                        }
                        washStand.getWashesList().get(i).setAvailable();
                        return;
                    }
                }
            }
        }
    }

    private void addToQueue() throws InterruptedException {
        boolean firstQueue = false;
        boolean secondQueue = false;
        synchronized (carWash.getQueue1()) {
            synchronized (carWash.getQueue2()) {
                if(carWash.getQueue1().size() <= carWash.getQueue2().size())
                    firstQueue = true;
                else
                    secondQueue = true;
            }
        }
        if(firstQueue) {
            synchronized (carWash.getQueue1()) {
                if(carWash.getQueue1().size() < carWash.getQueue1Size()) {
                    carWash.addToQueue1(this);
                    Platform.runLater(() -> simulationViewController.joinedToQ1(this.getCarId()));
                    inQueue = true;
                    carWash.getQueue1().notifyAll();
                }
            }
        }
        if(secondQueue) {
            synchronized (carWash.getQueue2()) {
                if(carWash.getQueue2().size() < carWash.getQueue2Size()) {
                    carWash.addToQueue2(this);
                    Platform.runLater(() -> simulationViewController.joinedToQ2(this.getCarId()));
                    inQueue = true;
                    carWash.getQueue2().notifyAll();
                }
            }
        }
    }

    public int getCarId() {
        return carId;
    }

    public void setWashStand(WashStand washStand) {
        this.washStand = washStand;
    }
}
