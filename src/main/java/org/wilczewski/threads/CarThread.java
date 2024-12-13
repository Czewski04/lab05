package org.wilczewski.threads;

import org.wilczewski.wash.CarWash;

public class CarThread extends Thread {
    private int carId;
    private CarWash carWash;
    private boolean inQueue = false;

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
            System.out.println("wjechałem" + carId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void wash() {
        System.out.println("Washing " + carId);
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
                    System.out.println("Car" + this.carId + "added to q1");
                    inQueue = true;
                    carWash.getQueue1().notifyAll();
                }
            }
        }
        if(secondQueue) {
            synchronized (carWash.getQueue2()) {
                if(carWash.getQueue2().size() < carWash.getQueue2Size()) {
                    carWash.addToQueue2(this);
                    System.out.println("Car" + this.carId + "added to q2");
                    inQueue = true;
                    carWash.getQueue2().notifyAll();
                }
            }
        }
    }

    public int getCarId() {
        return carId;
    }
}
