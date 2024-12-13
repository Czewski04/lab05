package org.wilczewski.threads;

import org.wilczewski.wash.CarWash;

public class ControllerThread extends Thread {
    CarWash carWash;

    public ControllerThread(CarWash carWash) {
        this.carWash = carWash;
    }

    @Override
    public void run() {
        while (true) {
            try {
                deleteFirst();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteFirst() throws InterruptedException {
        deleteFromQ1();
        deleteFromQ2();
    }

    private void deleteFromQ1() throws InterruptedException {
        synchronized (carWash.getQueue1()) {
            if(!carWash.getQueue1().isEmpty()) {
                carWash.getQueue1().poll();
                carWash.getQueue1().notifyAll();
                System.out.println("car deleted from q1");
            }
        }
    }

    private void deleteFromQ2() throws InterruptedException {
        synchronized (carWash.getQueue2()) {
            if(!carWash.getQueue2().isEmpty()) {
                carWash.getQueue2().poll();
                carWash.getQueue2().notifyAll();
                System.out.println("car deleted from q2");
            }
        }
    }

    public CarWash getCarWash() {
        return carWash;
    }
}
