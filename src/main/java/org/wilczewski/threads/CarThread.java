package org.wilczewski.threads;

import org.wilczewski.wash.CarWash;
import org.wilczewski.wash.WashStand;

public class CarThread extends Thread {
    private final int carId;
    private final CarWash carWash;
    private boolean inQueue = false;
    private WashStand washStand;

    public CarThread(int carId, CarWash carWash) {
        this.carId = carId;
        this.carWash = carWash;
    }

    @Override
    public void run() {
        try {
            while (!inQueue) {
                addToQueue();
            }
            synchronized (this){
                this.wait();
            }
            System.out.println("wjechałem " + carId);
            wash();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
                        synchronized (washStand.getWashesList().get(i)) {
                            washStand.getWashesList().get(i).setUnavailable();
                            System.out.println("starting water" + carId);
                            Thread.sleep(2000);
                            System.out.println("ending water" + carId);
                            washStand.getWashesList().get(i).setAvailable();
                            return;
                        }
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
                        synchronized (washStand.getWashesList().get(i)) {
                            washStand.getWashesList().get(i).setUnavailable();
                            System.out.println("starting soap" + carId);
                            Thread.sleep(2000);
                            System.out.println("ending soap" + carId);
                            washStand.getWashesList().get(i).setAvailable();
                            return;
                        }
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
                    System.out.println("Car " + carId + " added to q1");
                    inQueue = true;
                    carWash.getQueue1().notifyAll();
                }
            }
        }
        if(secondQueue) {
            synchronized (carWash.getQueue2()) {
                if(carWash.getQueue2().size() < carWash.getQueue2Size()) {
                    carWash.addToQueue2(this);
                    System.out.println("Car" + carId + "added to q2");
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
