package org.wilczewski.threads;

import org.wilczewski.wash.CarWash;
import org.wilczewski.wash.WashStand;


public class ControllerThread extends Thread {
    private final CarWash carWash;
    private boolean lastUsedQ1;

    public ControllerThread(CarWash carWash) {
        this.carWash = carWash;
        this.lastUsedQ1 = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                redirectToWashStand();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void redirectToWashStand() throws InterruptedException {
        CarThread car = null;
        synchronized (carWash.getWashStandsList()) {
            for(WashStand washStand : carWash.getWashStandsList()) {
                if(washStand.isAvailable()){
                    if(lastUsedQ1){
                        car = redirectFromQ2();
                        lastUsedQ1 = false;
                        if(car == null){
                            car = redirectFromQ1();
                            lastUsedQ1 = true;
                        }
                    }
                    else {
                        car = redirectFromQ1();
                        lastUsedQ1 = true;
                        if(car == null){
                            car = redirectFromQ2();
                            lastUsedQ1 = false;
                        }
                    }
                    if(car != null){
                        synchronized (car) {
                            washStand.setCarThread(car);
                            washStand.setUnavailable();
                            car.notify();
                        }
                    }
                }
            }
        }
    }

    private CarThread redirectFromQ1() throws InterruptedException {
        CarThread car = null;
        synchronized (carWash.getQueue1()) {
            if(!carWash.getQueue1().isEmpty()) {
                car = carWash.getQueue1().poll();
                carWash.getQueue1().notifyAll();
                System.out.println("car " + car.getCarId() +" deleted from q1");
            }
        }
        return car;
    }

    private CarThread redirectFromQ2() throws InterruptedException {
        CarThread car = null;
        synchronized (carWash.getQueue2()) {
            if(!carWash.getQueue2().isEmpty()) {
                car = carWash.getQueue2().poll();
                carWash.getQueue2().notifyAll();
                System.out.println("car " + car.getCarId() + " deleted from q2");
            }
        }
        return car;
    }

    public CarWash getCarWash() {
        return carWash;
    }
}
